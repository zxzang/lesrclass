package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator1InputImpl implements InputGenerator {

	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[1];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		
		return input;	
	
	}
}
