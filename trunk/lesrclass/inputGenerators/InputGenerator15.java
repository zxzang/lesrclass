package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator15 implements InputGenerator {

int dim = 2;
	
	public int getDim(){return dim;}
	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[dim];
		
		input[0] = currtick / (hist.getLength()-1)*100;
		input[1] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		

		return input;	
	
	}
}
