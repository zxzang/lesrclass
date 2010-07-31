package LESRClass;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;


import LESRData.HighFitnessTracker;

import javax.swing.*;

import org.jfree.data.xy.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import Charting.TimeSeriesChart;
import Charting.ResultsChart;
import LESRData.PriceData;
import LESRData.Stock;
import LESRData.TimeTick;

import java.util.Date;
public class InvestorTesterChartImpl implements InvestorTester {

	boolean[] genome;
	String stockName;
	int numberPoints;
	String fileName;
	double fitness;
	Stock stock;
	
	TimeSeriesChart outChart; 
	
	PriceData hist;
	
	public InvestorTesterChartImpl(){
		fitness = 1;			
		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		stock = new Stock(stockName,0);
		outChart = new TimeSeriesChart("./Data/SP500Chart.png"); 
		hist = new PriceData(stockName, fileName);
	}
	
	public double test(boolean[] genomeIn, String trainOrTest) {
		return test(genomeIn);
		
	}

	
	public double test(boolean[] genomeIn) {
		genome = genomeIn;
		RuleSet rules = new RuleSet(genomeIn);
		List <TimeSeries> seriesList = runGenome(genomeIn);
		chart(seriesList);
		return 0.00;
	}

	private void chart(List<TimeSeries> seriesList){
		for(TimeSeries t: seriesList){
			outChart.addSeries(t);
		}
		outChart.printChart(stock.getSymbol());
	}
	
	
	private List<TimeSeries> runGenome(boolean[] genomeIn){
		List<TimeSeries> seriesList = new ArrayList<TimeSeries>();

		RuleSet strategyRules = new RuleSet(genomeIn);
		String trainSeriesName = "Apply Strategy on Training Data";
		String testSeriesName = "Apply Strategy on Test Data";
		TimeSeries trainSeries = outChart.createSeries(trainSeriesName);
		TimeSeries testSeries = outChart.createSeries(testSeriesName);
			
		boolean[] buyHoldGenome = convert("0001000111101111");
		RuleSet buyHoldRules = new RuleSet(buyHoldGenome);
		String buyHoldTrainSeriesName = "Buy and Hold, Training Data";
		String buyHoldTestSeriesName = "Buy and Hold, Test Data";
		TimeSeries buyHoldTrainSeries = outChart.createSeries(buyHoldTrainSeriesName);
		TimeSeries buyHoldTestSeries = outChart.createSeries(buyHoldTestSeriesName);
		
		trainSeries = runSeries(strategyRules, "train", trainSeriesName);
		testSeries = runSeries(strategyRules, "test", testSeriesName);
		buyHoldTrainSeries = runSeries(buyHoldRules, "train", buyHoldTrainSeriesName);
		buyHoldTestSeries = runSeries(buyHoldRules, "test", buyHoldTestSeriesName);

		seriesList.add(trainSeries);
		seriesList.add(testSeries);
		seriesList.add(buyHoldTrainSeries);
		seriesList.add(buyHoldTestSeries);

		return seriesList;
	}
		
	private TimeSeries runSeries(RuleSet rules, String trainOrTest, String seriesName){
		fitness = 1;
		TimeSeries series = new TimeSeries(seriesName);
		TimeTick today = new TimeTick();
		String lastTick = stock.getLastTick();

		Rule.RecType rec = Rule.RecType.DONOTHING;
		
		int datasetCount = 0;
		
		for(int a = 1; a < hist.getLength(); a++){
			setTodaysValues(hist, today, stock, a);

			if(hist.getTrainTest(a) == trainOrTest){
				if(a > 200){
					datasetCount++;
					
					fitness = fitness * (evalPrediction(hist, a, rec));
					Date date = stock.getDate();
					
					if(trainOrTest == "train") outChart.addPoint(series, date, fitness);
					if(trainOrTest == "test") outChart.addPoint(series, date, fitness);
	
					Double[] smas = stock.getSmas();
					rec = rules.getRecommendation(smas, lastTick);
				}			
			}
		}
		System.out.println("Series: " + 
				seriesName + " Fitness: " + fitness);
		return series;
	}
		
	
	public double evalPrediction(PriceData hist, int tick, Rule.RecType rec){
		double today = hist.getAdjClose(tick);
		double yesterday = hist.getAdjClose(tick-1);
		double returnVal = 1;
		
		double changeVal = 
			((today - yesterday) / yesterday) + 1;
			
		returnVal = changeVal;
		if(rec == Rule.RecType.SHORT) returnVal = 1/returnVal;
		if(rec == Rule.RecType.DONOTHING) returnVal = 1;
		
		return returnVal;
	}

	private void setTodaysValues(PriceData hist, TimeTick today, Stock stock, int tickCounter){
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


	static boolean[] convert (String stringGenome){
		boolean[] boolArray = new boolean[stringGenome.length()];
		for(int a = 0; a < stringGenome.length(); a++){
			if(stringGenome.charAt(a) == '0') boolArray[a] = false;
			else if(stringGenome.charAt(a) == '1') boolArray[a] = true;
			else JOptionPane.showMessageDialog(null, "problem converting String to bool[].  Value at " + a + " is " + stringGenome.charAt(a));						
		}
		return boolArray;
	
	}
	
	public boolean checkPrediction(String lastTick, Rule.RecType lastRec){
		boolean goodPrediction = false;
		if(lastRec == Rule.RecType.DONOTHING){
			if(lastTick == "flat")goodPrediction = true;			
		}
		
		else if(lastRec ==Rule.RecType.LONG){
			if(lastTick == "up")goodPrediction = true;			
		}
		
		else if(lastRec == Rule.RecType.SHORT){
			if(lastTick == "down") goodPrediction = true;			
		}
		else System.out.println("Mismatch in checkPrediction");
//		if(goodPrediction) JOptionPane.showMessageDialog(null, "hit!");
		return goodPrediction;
	}
}
