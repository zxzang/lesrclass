package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator13 implements InputGenerator {

	int dim = 8;

	public int getDim() {
		return dim;
	}

	public double[] generateInput(PriceData hist, int currtick) {

		double[] input = new double[dim];

		input[0] = currtick > 0 ? (hist.getAdjClose(currtick) / hist
				.getAdjClose(currtick - 1)) * 100 : 100.0;
		input[1] = currtick > 9 ? (hist.getAdjClose(currtick) / hist
				.getMaTen(currtick)) * 100 : 100.0;
		input[2] = currtick > 49 ? (hist.getAdjClose(currtick) / hist
				.getMaFifty(currtick)) * 100 : 100.0;
		input[3] = currtick > 199 ? (hist.getAdjClose(currtick) / hist
				.getMaTwoHundred(currtick)) * 100 : 100.0;
		input[4] = currtick > 0 ? (hist.getVolume(currtick) / hist
				.getVolume(currtick -1)) * 100 : 100.0;
		input[5] = currtick > 9 ? (hist.getVolume(currtick) / hist
				.getMaTenVolume(currtick)) * 100 : 100.0;
		input[6] = currtick > 49 ? (hist.getVolume(currtick) / hist
				.getMaFiftyVolume(currtick)) * 100 : 100.0;
		input[7] = currtick > 199 ? (hist.getVolume(currtick) / hist
				.getMaTwoHundredVolume(currtick)) * 100 : 100.0;
		
		return input;

	}
}
