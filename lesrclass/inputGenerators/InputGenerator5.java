package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator5 implements InputGenerator {

int dim = 2;
	
	public int getDim(){return dim;}
	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[dim];
		
		double pointInRange = (hist.getClose(currtick)-hist.getLow(currtick))/(hist.getHigh(currtick)-hist.getLow(currtick)) *100;
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = pointInRange;

		return input;	
	
	}
}
