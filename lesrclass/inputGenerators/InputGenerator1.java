package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator1 implements InputGenerator {
	int dim;
	
	public int getDim(){return dim;}
	public double[] generateInput(PriceData hist, int currtick) {
		
		dim = 1;
		double[] input = new double[dim];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		
		return input;	
	
	}
}
