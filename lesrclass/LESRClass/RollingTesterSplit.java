package LESRClass;

import inputGenerators.InputGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.jfree.data.time.TimeSeries;

import rulemakers.RollingFunctionApproxToRules;
import rules.Rule;
import rules.RuleSet;
import rules.RuleSetBuyHold;
import xcsf.XCSF;
import xcsf.XCSFConstants;
import xcsfExtensions.PriceFunctionRolling;
import xcsfExtensions.RollingPopTracker;
import Charting.TimeSeriesChart;
import LESRData.PriceData;
import LESRData.Stock;
import LESRData.TimeTick;

public class RollingTesterSplit {

	int maxlength;
	int longestSMA;		// length in days of the period used in the longest SMA variable
	String stockName;
	int numberPoints;
	String fileName;
	double fitness;
	Stock stock;
	RuleSet ruleset;

	TimeSeriesChart outChart;

	PriceData hist;
	private InputGenerator ig;
	int rollperiod;

	String strategySeriesName = "Apply Strategy";
	TimeSeries strategySeries;

	public RollingTesterSplit(String fileName, InputGenerator ig,
			int rollperiod, int maxlength) {
		this.fileName = fileName;
		stockName = "SP500";
		stock = new Stock(stockName, 0);
		outChart = new TimeSeriesChart("./Data/NewChart.png");
		hist = new PriceData(stockName, fileName);
		this.ig = ig;
		this.rollperiod = rollperiod;
		if (maxlength == -1)
			this.maxlength = hist.getLength() - 1;
		else
			this.maxlength = maxlength;

		strategySeries = outChart.createSeries(strategySeriesName);

		
	}

	public void test() {
		List<TimeSeries> seriesList = generateSeries();
		chart(seriesList);
	}

	private void chart(List<TimeSeries> seriesList) {
		for (TimeSeries t : seriesList) {
			outChart.addSeries(t);

		}

		outChart.printChart(stock.getSymbol());
	}

	private List<TimeSeries> generateSeries() {
	
		List<TimeSeries> seriesList = new ArrayList<TimeSeries>();

		runRollingSeries(ruleset);

		RuleSet buyHoldRules = new RuleSetBuyHold();
		String buyHoldSeriesName = "Buy and Hold";
		TimeSeries buyHoldSeries = outChart.createSeries(buyHoldSeriesName);
		buyHoldSeries = runSeries(buyHoldRules, buyHoldSeriesName);

		seriesList.add(strategySeries);
		seriesList.add(buyHoldSeries);

		return seriesList;
	}

	public double evalPrediction(PriceData hist, int tick, Rule.RecType rec) {

		double returnVal = 1.0;
		double tomorrow = (tick < (hist.getLength() - 1)) ? hist
				.getAdjClose(tick + 1) : hist.getAdjClose(tick);
		double today = hist.getAdjClose(tick);

		returnVal = tomorrow / today;

		if (rec == Rule.RecType.SHORT)
			returnVal = 1 / returnVal;
		if (rec == Rule.RecType.DONOTHING)
			returnVal = 1.00;

		return returnVal;
	}

	private TimeSeries runSeries(RuleSet rules, String seriesName) {

		fitness = 1;
		TimeSeries series = new TimeSeries(seriesName);
		TimeTick today = new TimeTick();

		double inputs[];
		double outputs[] = new double[1];

		Rule.RecType rec = Rule.RecType.DONOTHING;

		for (int a = longestSMA; a < maxlength; a++) {
			inputs = ig.generateInput(hist, today.getTickNum());
			setTodaysValues(hist, today, stock, a);

			int currtick = today.getTickNum();
			inputs = ig.generateInput(hist, currtick);
			Date date = stock.getDate();

			outputs[0] = currtick < (hist.getLength() - 1) ? (hist
					.getAdjClose(currtick + 1) / hist.getAdjClose(currtick)) * 100
					: 100.0;

			rec = rules.getRecommendation(inputs, outputs);
			fitness *= evalPrediction(hist, currtick, rec);
			outChart.addPoint(series, date, fitness);
		}
		System.out.println("Series: " + seriesName + " Fitness: " + fitness);
		return series;
	}

	private void setTodaysValues(PriceData hist, TimeTick today, Stock stock,
			int tickCounter) {
		today.setTickNum(tickCounter);

		stock.setPrice(hist.getAdjClose(tickCounter));
		stock.setVolume(hist.getVolume(tickCounter));

		stock.setTodaysOpen(hist.getTodaysOpen(tickCounter));
		stock.setTodaysHigh(hist.getHigh(tickCounter));
		stock.setTodaysLow(hist.getLow(tickCounter));

		stock.setCrossover(hist, tickCounter);
		stock.setDate(hist.getCDate(tickCounter));
		stock.setTrainTest(hist.getTrainTest(tickCounter));
	}

	private void runRollingSeries(RuleSet rules) {
		
		double fitness = 1.0;
		int dim = ig.getDim();
		PriceFunctionRolling f = new PriceFunctionRolling(1, 0, 0, dim,
				fileName, rollperiod);
		f.setInputGenerator(ig);

		XCSFConstants.load("xcsf.ini");

		XCSF xcsf = new XCSF(f);

		RollingPopTracker rpt = new RollingPopTracker(null);
		xcsf.addListener(rpt);

		RollingFunctionApproxToRules rulemaker = null;
		
		for (int i = longestSMA; i < maxlength - 1; i++) {
			f.setStart(i);
			xcsf.runExperiments();

			rulemaker = new RollingFunctionApproxToRules(rpt);
			rulemaker.parseRulesFromPopulation();
			fitness *= runOnePoint(i + rollperiod, rulemaker);

			TimeTick today = new TimeTick();

			setTodaysValues(hist, today, stock, i);

			Date date = stock.getDate();
			outChart.addPoint(strategySeries, date, fitness);
		}
		System.out.println("Series: " + strategySeriesName + " Fitness: "
				+ fitness);
	
	}

	public double runOnePoint(int tickNum, RollingFunctionApproxToRules rm) {
		double fitness = 1;
		double inputs[];
		double outputs[] = new double[1];

		Rule.RecType rec = Rule.RecType.DONOTHING;

		inputs = ig.generateInput(hist, tickNum);

		outputs[0] = tickNum < (hist.getLength() - 1) ? (hist
				.getAdjClose(tickNum + 1) / hist.getAdjClose(tickNum)) * 100
				: 100.0;

		rec = rm.getRuleset().getRecommendation(inputs, outputs);
		fitness = evalPrediction(hist, tickNum, rec);
		return fitness;

	}
}
