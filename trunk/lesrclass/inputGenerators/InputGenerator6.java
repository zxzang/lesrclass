package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator6 implements InputGenerator {
int dim;
	
	public int getDim(){return dim;}
	public double[] generateInput(PriceData hist, int currtick) {
		
		double[] input = new double[4];
		
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = currtick > 49? (hist.getAdjClose(currtick) / hist.getMaTen(currtick))*100 : 100.0;
		input[2] = currtick > 0? (hist.getVolume(currtick)/hist.getVolume(currtick-1)) * 100: 100.0;
		input[3] = currtick > 49? (hist.getVolume(currtick) / hist.getMaTenVolume(currtick))*100 : 100.0;
		
		
		return input;	
	
	}
}
