package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator14 implements InputGenerator {

int dim = 5;
	
	public int getDim(){return dim;}
	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[dim];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = currtick >= 0? (hist.getAdjClose(currtick) / hist.getHigh(currtick))*100 : 100.0;
		input[2] = currtick >= 0? (hist.getAdjClose(currtick) / hist.getLow(currtick))*100 : 100.0;
		input[3] = currtick > 0? (hist.getVolume(currtick) / hist.getVolume(currtick-1))*100 : 100.0;
		input[4] = currtick > 199? (hist.getVolume(currtick) / hist.getMaTwoHundredVolume(currtick))*100 : 100.0;


		return input;	
	
	}
}
