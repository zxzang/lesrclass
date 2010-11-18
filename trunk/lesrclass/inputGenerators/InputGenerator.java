package inputGenerators;

import LESRData.PriceData;


public interface InputGenerator {
	double[] generateInput(PriceData hist, int currtick);
}
