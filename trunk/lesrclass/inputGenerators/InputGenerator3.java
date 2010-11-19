package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator3 implements InputGenerator {
int dim;
	
	public int getDim(){return dim;}
	
	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[2];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = currtick > 0? (hist.getVolume(currtick)/hist.getVolume(currtick-1)) * 100: 100.0;
		
		
		return input;	
	
	}
}
