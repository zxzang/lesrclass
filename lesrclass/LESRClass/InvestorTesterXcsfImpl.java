package LESRClass;

import inputGenerators.InputGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.TimeSeries;

import rules.Rule;
import rules.RuleSet;
import rules.RuleSetBuyHold;
import rules.RuleSetDoNothing;
import Charting.TimeSeriesChart;
import LESRData.PriceData;
import LESRData.Stock;
import LESRData.TimeTick;

public class InvestorTesterXcsfImpl implements InvestorTester {

	String stockName;
	int numberPoints;
	String fileName;
	double fitness;
	Stock stock;
	RuleSet ruleset;

	TimeSeriesChart outChart;

	PriceData hist;
	private InputGenerator ig;

	public InvestorTesterXcsfImpl(InputGenerator ig, String fileName) {
		this.fileName = fileName;
		stockName = "SP500";
		stock = new Stock(stockName, 0);
		outChart = new TimeSeriesChart("./Data/NewChart.png");
		hist = new PriceData(stockName, fileName);
		this.ig = ig;
		TimeTick today = null;
				
	}

	
	@Override
	public void test(RuleSet ruleSetIn) {
		this.ruleset = ruleSetIn;
		List<TimeSeries> seriesList = runRuleSet();
		chart(seriesList);
	}

	private void chart(List<TimeSeries> seriesList) {
		for (TimeSeries t : seriesList) {
			outChart.addSeries(t);
			
		}
	
		outChart.printChart(stock.getSymbol());
	}

	private List<TimeSeries> runRuleSet() {
		List<TimeSeries> seriesList = new ArrayList<TimeSeries>();

		String strategyTrainSeriesName = "Apply Strategy On Training Data";
		TimeSeries strategyTrainSeries = outChart
				.createSeries(strategyTrainSeriesName);
		strategyTrainSeries = runSeries(ruleset, "train",
				strategyTrainSeriesName);

		String strategyTestSeriesName = "Apply Strategy On Test Data";
		TimeSeries strategyTestSeries = outChart
				.createSeries(strategyTestSeriesName);
		strategyTestSeries = runSeries(ruleset, "test", strategyTestSeriesName);

		RuleSet buyHoldRules = new RuleSetBuyHold();
		String buyHoldTrainSeriesName = "Buy and Hold With Training Data";
		TimeSeries buyHoldTrainSeries = outChart
				.createSeries(buyHoldTrainSeriesName);
		buyHoldTrainSeries = runSeries(buyHoldRules, "train",
				buyHoldTrainSeriesName);

		String buyHoldTestSeriesName = "Buy and Hold With Test Data";
		TimeSeries buyHoldTestSeries = outChart
				.createSeries(buyHoldTestSeriesName);
		buyHoldTestSeries = runSeries(buyHoldRules, "test",
				buyHoldTestSeriesName);

		
		seriesList.add(strategyTestSeries);
		seriesList.add(strategyTrainSeries);
		seriesList.add(buyHoldTestSeries);
		seriesList.add(buyHoldTrainSeries);
		
		return seriesList;
	}

	@Override
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

	private TimeSeries runSeries(RuleSet rules, String trainOrTest,
			String seriesName) {

		fitness = 1;
		TimeSeries series = new TimeSeries(seriesName);
		TimeTick today = new TimeTick();

		double inputs[];
		double outputs[] = new double[1];

		Rule.RecType rec = Rule.RecType.DONOTHING;

		for (int a = 1; a < hist.getLength(); a++) {

			inputs = ig.generateInput(hist, today.getTickNum());
			if (hist.getTrainTest(a) == trainOrTest) {
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

}
