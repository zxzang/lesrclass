package LESRClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.TimeSeries;

import xcsfExtensions.PriceFunction;
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

	public InvestorTesterXcsfImpl() {
		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		stock = new Stock(stockName, 0);
		outChart = new TimeSeriesChart("./Data/SP500Chart.png");
		hist = new PriceData(stockName, fileName);
	}

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

		String trainSeriesName = "Apply Strategy on Training Data";
		String testSeriesName = "Apply Strategy on Test Data";
		TimeSeries trainSeries = outChart.createSeries(trainSeriesName);
		TimeSeries testSeries = outChart.createSeries(testSeriesName);

		/*
		 * boolean[] buyHoldGenome = convert("0001000111101111"); RuleSet
		 * buyHoldRules = new RuleSetGAImpl(buyHoldGenome); String
		 * buyHoldTrainSeriesName = "Buy and Hold, Training Data"; String
		 * buyHoldTestSeriesName = "Buy and Hold, Test Data"; TimeSeries
		 * buyHoldTrainSeries = outChart.createSeries(buyHoldTrainSeriesName);
		 * TimeSeries buyHoldTestSeries =
		 * outChart.createSeries(buyHoldTestSeriesName);
		 */
		trainSeries = runSeries(ruleset, "train", trainSeriesName);
		testSeries = runSeries(ruleset, "test", testSeriesName);
		// buyHoldTrainSeries = runSeries(buyHoldRules, "train",
		// buyHoldTrainSeriesName);
		// buyHoldTestSeries = runSeries(buyHoldRules, "test",
		// buyHoldTestSeriesName);

		seriesList.add(trainSeries);
		seriesList.add(testSeries);
		// seriesList.add(buyHoldTrainSeries);
		// seriesList.add(buyHoldTestSeries);

		return seriesList;
	}
	
	public double evalPrediction(PriceData hist, int tick, RuleGAImpl.RecType rec){
		double today = hist.getAdjClose(tick);
		double yesterday = hist.getAdjClose(tick-1);
		double returnVal = 1;
		
		double changeVal = 
			((today - yesterday) / yesterday) + 1;
			
		returnVal = changeVal;
		if(rec == RuleGAImpl.RecType.SHORT) returnVal = 1/returnVal;
		if(rec == RuleGAImpl.RecType.DONOTHING) returnVal = 1;
		
		return returnVal;
	}

	private TimeSeries runSeries(RuleSet rules, String trainOrTest,
			String seriesName) {
		fitness = 1;
		TimeSeries series = new TimeSeries(seriesName);
		TimeTick today = new TimeTick();
		String lastTick = stock.getLastTick();

		double inputs[];
		
		RuleGAImpl.RecType rec = RuleGAImpl.RecType.DONOTHING;

		int datasetCount = 0;

		for (int a = 1; a < hist.getLength(); a++) {
			setTodaysValues(hist, today, stock, a);

			inputs = new double[3];
			if (hist.getTrainTest(a) == trainOrTest) {
				if (a > 200) {
					datasetCount++;
					
					// these must match the inputs in the function!  Abstract this out later!
					
					int currtick = today.getTickNum();
					inputs[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
					inputs[1] = currtick > 49? (hist.getAdjClose(currtick)/hist.getMaFifty(currtick)) * 100: 100.0;
					inputs[2] = currtick > 199? (hist.getAdjClose(currtick)/hist.getMaTwoHundred(currtick))* 100: 100.0;
					fitness = fitness * (evalPrediction(hist, a, rec));
					Date date = stock.getDate();

					if (trainOrTest == "train")
						outChart.addPoint(series, date, fitness);
					if (trainOrTest == "test")
						outChart.addPoint(series, date, fitness);

					Double[] smas = stock.getSmas();
					
					
					rec = rules.getRecommendation(smas, lastTick);
				}
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

	@Override
	public double test(boolean[] boolArray) {
		// TODO Auto-generated method stub
		return 0;
	}

}
