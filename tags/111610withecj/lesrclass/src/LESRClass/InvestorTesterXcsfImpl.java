package LESRClass;

import inputGenerators.InputGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.TimeSeries;

import rules.RuleGAImpl;
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

	public InvestorTesterXcsfImpl(InputGenerator ig) {
		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		stock = new Stock(stockName, 0);
		outChart = new TimeSeriesChart("./Data/NewChart.png");
		hist = new PriceData(stockName, fileName);
		this.ig = ig;
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


		String strategySeriesName = "Apply Strategy";
		TimeSeries strategySeries = outChart.createSeries(strategySeriesName);
		strategySeries = runSeries(ruleset, "train", strategySeriesName);


		RuleSet buyHoldRules = new RuleSetBuyHold(); 
		String buyHoldSeriesName = "Buy and Hold";
		TimeSeries buyHoldSeries = outChart.createSeries(buyHoldSeriesName);
		buyHoldSeries = runSeries(buyHoldRules, "train", buyHoldSeriesName);

		RuleSet cashRules = new RuleSetDoNothing(); 
		String cashSeriesName = "Cash";
		TimeSeries cashSeries = outChart.createSeries(buyHoldSeriesName);
		cashSeries = runSeries(cashRules, "cash", cashSeriesName);

		
		seriesList.add(strategySeries);
		seriesList.add(buyHoldSeries);
		seriesList.add(cashSeries);

		
		/*

		String trainSeriesName = "Apply Strategy on Training Data";
		String testSeriesName = "Apply Strategy on Test Data";
		TimeSeries trainSeries = outChart.createSeries(trainSeriesName);
		TimeSeries testSeries = outChart.createSeries(testSeriesName);

		 * boolean[] buyHoldGenome = convert("0001000111101111"); RuleSet
		 * buyHoldRules = new RuleSetGAImpl(buyHoldGenome); String
		 * buyHoldTrainSeriesName = "Buy and Hold, Training Data"; String
		 * buyHoldTestSeriesName = "Buy and Hold, Test Data"; TimeSeries
		 * buyHoldTrainSeries = outChart.createSeries(buyHoldTrainSeriesName);
		 * TimeSeries buyHoldTestSeries =
		 * outChart.createSeries(buyHoldTestSeriesName);
		 
		trainSeries = runSeries(ruleset, "train", trainSeriesName);

//		testSeries = runSeries(ruleset, "test", testSeriesName);
		// buyHoldTrainSeries = runSeries(buyHoldRules, "train",
		// buyHoldTrainSeriesName);
		// buyHoldTestSeries = runSeries(buyHoldRules, "test",
		// buyHoldTestSeriesName);

		seriesList.add(trainSeries);
		seriesList.add(testSeries);
		// seriesList.add(buyHoldTrainSeries);
		// seriesList.add(buyHoldTestSeries);
*/
		return seriesList;
	}
	
	public double evalPrediction(PriceData hist, int tick, RuleGAImpl.RecType rec){
		
		double tomorrow = (tick < (hist.getLength()-1))? hist.getAdjClose(tick+1): hist.getAdjClose(tick);
		double today = hist.getAdjClose(tick);
		double returnVal = 1;
		
		returnVal = tomorrow / today;
			
		if(rec == RuleGAImpl.RecType.SHORT) returnVal = 1/returnVal;
		if(rec == RuleGAImpl.RecType.DONOTHING) returnVal = 1.00;

/*		if(tick%100 ==0) System.out.println(tick+ " today:" + 
				today + " tomorrow:" + tomorrow + "return: " + returnVal);
*/
		
		return returnVal;
	}

	private TimeSeries runSeries(RuleSet rules, String trainOrTest,
			String seriesName) {
		
		fitness = 1;
		TimeSeries series = new TimeSeries(seriesName);
		TimeTick today = new TimeTick();
//		String lastTick = stock.getLastTick();

		double inputs[];
		double outputs[] = new double[1];
		
		RuleGAImpl.RecType rec = RuleGAImpl.RecType.DONOTHING;

		int datasetCount = 0;

		for (int a = 1; a < hist.getLength(); a++) {
			
			setTodaysValues(hist, today, stock, a);

			inputs = ig.generateInput(hist, today.getTickNum());
//			if (hist.getTrainTest(a) == trainOrTest) {
				if (a > 200) {
					datasetCount++;
					
					// these must match the inputs in the function!  Abstract this out later!
					
					int currtick = today.getTickNum();
					inputs = ig.generateInput(hist, currtick);
					Date date = stock.getDate();

					outputs[0] = currtick < (hist.getLength() -1)? 
							(hist.getAdjClose(currtick+1)/hist.getAdjClose(currtick))*100:100.0;
					if (trainOrTest == "train")
						outChart.addPoint(series, date, fitness);
					if (trainOrTest == "test")
						outChart.addPoint(series, date, fitness);

//					Double[] smas = stock.getSmas();
/*					for(int i = 0; i < inputs.length;i++) {
						System.out.println("inputs[" + i + "] = " + inputs[i]);	
					}

					for(int i = 0; i < outputs.length;i++) {
						System.out.println("outputs[" + i + "] = " + outputs[i]);	
					}
	*/				

					rec = rules.getRecommendation(inputs, outputs);
					fitness *= evalPrediction(hist, currtick, rec);
				}
			}
//		}
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
