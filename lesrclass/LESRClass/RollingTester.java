package LESRClass;

import inputGenerators.InputGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class RollingTester {

	int maxlength;
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

	public RollingTester(String fileName, InputGenerator ig, int rollperiod) {
		this.fileName = fileName;
		stockName = "SP500";
		stock = new Stock(stockName, 0);
		outChart = new TimeSeriesChart("./Data/NewChart.png");
		hist = new PriceData(stockName, fileName);
		this.ig = ig;
		this.rollperiod = rollperiod;
		maxlength = hist.getLength() -1;
		
		maxlength = 2000;
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

		String strategyTrainSeriesName = "Apply Strategy On Training Data";
		TimeSeries strategyTrainSeries = outChart
				.createSeries(strategyTrainSeriesName);
		strategyTrainSeries = runRollingSeries(ruleset, "train",
				strategyTrainSeriesName);

		/*
		 * String strategyTestSeriesName = "Apply Strategy On Test Data";
		 * TimeSeries strategyTestSeries = outChart
		 * .createSeries(strategyTestSeriesName); strategyTestSeries =
		 * runRollingSeries(ruleset, "test", strategyTestSeriesName);
		 */

		RuleSet buyHoldRules = new RuleSetBuyHold();
		String buyHoldSeriesName = "Buy and Hold";
		TimeSeries buyHoldSeries = outChart
				.createSeries(buyHoldSeriesName);
		buyHoldSeries = runSeries(buyHoldRules, buyHoldSeriesName);

//		String buyHoldTestSeriesName = "Buy and Hold With Test Data";
//		TimeSeries buyHoldTestSeries = outChart
//				.createSeries(buyHoldTestSeriesName);

//		buyHoldTestSeries = runSeries(new RuleSetBuyHold(), "test",
//				buyHoldTestSeriesName);

		// seriesList.add(strategyTestSeries);
		seriesList.add(strategyTrainSeries);
		seriesList.add(buyHoldSeries);
//		seriesList.add(buyHoldTrainSeries);

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

		for (int a = 1; a < maxlength; a++) {

			inputs = ig.generateInput(hist, today.getTickNum());
//			if (hist.getTrainTest(a) == trainOrTest) {
				setTodaysValues(hist, today, stock, a);

				int currtick = today.getTickNum();
				inputs = ig.generateInput(hist, currtick);
				Date date = stock.getDate();

				outputs[0] = currtick < (hist.getLength() - 1) ? (hist
						.getAdjClose(currtick + 1) / hist.getAdjClose(currtick)) * 100
						: 100.0;

				rec = rules.getRecommendation(inputs, outputs);
				fitness *= evalPrediction(hist, currtick, rec);
				double tomorrow = (a < (hist.getLength() - 1) ? hist
						.getAdjClose(a + 1) : hist.getAdjClose(a));
				outChart.addPoint(series, date, fitness);
//			}
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

	private TimeSeries runRollingSeries(RuleSet rules, String trainOrTest,
			String seriesName) {
		TimeSeries series = new TimeSeries(seriesName);
		fitness = 1;
		int dim = ig.getDim();
		PriceFunctionRolling f = new PriceFunctionRolling(1, 0, 0, dim,
				fileName, rollperiod);
		f.setInputGenerator(ig);

		XCSFConstants.load("xcsf.ini");

		XCSF xcsf = new XCSF(f);

		RollingPopTracker rpt = new RollingPopTracker(null);
		xcsf.addListener(rpt);

		for (int i = 201; i < maxlength - rollperiod - 1; i++) {
			f.setStart(i);
			xcsf.runExperiments();
			RollingFunctionApproxToRules rulemaker = new RollingFunctionApproxToRules(
					rpt);
			rulemaker.parseRulesFromPopulation();
			fitness *= runOnePoint(i, trainOrTest, series, rulemaker);
			TimeTick today = new TimeTick();

			setTodaysValues(hist, today, stock, i);

			Date date = stock.getDate();

			outChart.addPoint(series, date, fitness);
		}	
		System.out.println("Series: " + seriesName + " Fitness: " + fitness);
		return series;

	}

	public double runOnePoint(int tickNum, String trainOrTest,
			TimeSeries series, RollingFunctionApproxToRules rm) {
//		TimeTick today = new TimeTick();
		double fitness = 1;
		double inputs[];
		double outputs[] = new double[1];

		Rule.RecType rec = Rule.RecType.DONOTHING;

		if (hist.getTrainTest(tickNum) == trainOrTest) {
			inputs = ig.generateInput(hist, tickNum);
//			setTodaysValues(hist, today, stock, tickNum);
			
			outputs[0] = tickNum < (hist.getLength() - 1) ? (hist
					.getAdjClose(tickNum + 1) / hist.getAdjClose(tickNum)) * 100
					: 100.0;

			rec = rm.getRuleset().getRecommendation(inputs, outputs);
			fitness = evalPrediction(hist, tickNum, rec);
					}

		return fitness;
	}
}
