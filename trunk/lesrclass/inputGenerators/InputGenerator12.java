package inputGenerators;

import xcsf.XCSFUtils;
import LESRData.PriceData;

public class InputGenerator12 implements InputGenerator {

	int dim = 3;

	public int getDim() {
		return dim;
	}

	public double[] generateInput(PriceData hist, int currtick) {

		double[] input = new double[dim];

		input[0] = currtick > 9 ? (hist.getVolume(currtick) / hist
				.getMaTenVolume(currtick)) * 100 : 100.0;
		input[1] = currtick > 49 ? (hist.getVolume(currtick) / hist
				.getMaFiftyVolume(currtick)) * 100 : 100.0;
		input[2] = currtick > 199 ? (hist.getVolume(currtick) / hist
				.getMaTwoHundredVolume(currtick)) * 100 : 100.0;
		
		return input;

	}
}
