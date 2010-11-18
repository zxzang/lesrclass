package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator4InputsImpl implements InputGenerator {

	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[4];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = currtick > 49? (hist.getAdjClose(currtick)/hist.getMaFifty(currtick)) * 100: 100.0;
		input[2] = currtick > 199? (hist.getAdjClose(currtick)/hist.getMaTwoHundred(currtick))* 100: 100.0;
		input[3] = currtick > 199?
				(hist.getMaFifty(currtick)/hist.getMaTwoHundred(currtick))* 100: 100.0;
		
		return input;	
	
	}
}
