package inputGenerators;

import LESRData.PriceData;

//comment 1-21
public interface InputGenerator {
	double[] generateInput(PriceData hist, int currtick);
	int getDim();
}
