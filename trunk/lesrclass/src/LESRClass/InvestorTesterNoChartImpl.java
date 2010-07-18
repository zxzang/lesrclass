package LESRClass;

import java.io.PrintWriter;

import LESRData.HighFitnessTracker;

import javax.swing.*;

import LESRData.PriceData;
import LESRData.Stock;
import LESRData.TimeTick;

import java.util.Date;
public class InvestorTesterNoChartImpl implements InvestorTester{

	boolean[] genome;
	String stockName;
	int numberPoints;
	String fileName;
	double fitness;
	Stock stock;
	PriceData hist;
	RuleSet rules;
	
	public InvestorTesterNoChartImpl(){
		fitness = 1;			
		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		stock = new Stock(stockName,0);
		hist = new PriceData(stockName, fileName);

	}

	public double test(boolean[] genomeIn){
		return test(genomeIn, "train");
		
	}
	
	public double test(boolean[] genomeIn, String trainOrTest) {
		genome = genomeIn;
		rules = new RuleSet(genomeIn);
		double fitness = runSet(trainOrTest);
				
		if(trainOrTest == "train" && (fitness > 500))  {
			double testPerf = runSet("test");
		
			double trainPerf = fitness;

			if( testPerf > 8){
				System.out.print("\ngenome: ");
				for(boolean b: genome) System.out.print(b?'1':'0');
				System.out.println("\nfitness: on training data: " + trainPerf + " vs. 4.716 for buy and hold. Fitness on test data: " +testPerf
					+ " vs. 4.087 for buy and hold");
				HighFitnessTracker hft  = new HighFitnessTracker(genome, trainPerf, testPerf);
			}
		}
	return	fitness;
	
	} // end function

	private double runSet(String trainOrTest){
		TimeTick today = new TimeTick();
		Rule.RecType rec = Rule.RecType.DONOTHING;
		double fitness = 1;
		int datasetCount = 0;

		for(int a = 1; a < hist.getLength(); a++){
			setTodaysValues(hist, today, stock, a);
			String lastTick = stock.getLastTick();

			if(hist.getTrainTest(a) == trainOrTest){
				if(a > 200){
					datasetCount++;
					
					fitness = fitness * (evalPrediction(hist, a, rec));
//					Date date = stock.getDate();
					
					Double[] smas = stock.getSmas();
					
					rec = rules.getRecommendation(smas, lastTick);
				}
			}
		}
		return fitness;
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
		
//		double changeTrigger = Math.abs(1-returnVal);

/*			if(changeTrigger > .06)
			JOptionPane.showMessageDialog(null, "IT 202: " + hist.getDate(tick) + " today: " + today + " yesterday " + yesterday + " rec: " + rec + " returning " + returnVal);
*/
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
