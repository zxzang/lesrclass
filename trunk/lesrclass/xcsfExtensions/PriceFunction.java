package xcsfExtensions;

import inputGenerators.InputGenerator;
import LESRData.PriceData;
import LESRData.Stock;
import xcsf.StateDescriptor;
import xcsf.XCSFUtils;
import xcsf.Function.SimpleFunction;
import LESRData.PriceData;
import LESRData.Stock;
import LESRData.TimeTick;
import javax.swing.*;

public class PriceFunction extends SimpleFunction {
	String stockName;
	String fileName;
	Stock stock;
	PriceData hist;
	int dim;
	InputGenerator ig;
	int currtick;
	
	public PriceFunction(double scale, double modifier, double noiseDeviation,
			int dim, String fileName) {
		super(scale, modifier, noiseDeviation, dim);
		currtick = 0;
		this.dim = dim;
		stock = new Stock(stockName, 0);
		this.fileName = fileName;
		stockName = "SP500";
		hist = new PriceData(stockName, fileName);
	}

	@Override
	protected double evaluate() {
		Double value = 0D;
			
		if (currtick < (hist.getLength() - 2))
			value = (hist.getAdjClose(currtick + 1))/(hist.getAdjClose(currtick))* 100;
		else value = 100.0;
		return value;
	}

	
	public void setInputGenerator(InputGenerator ig){
		this.ig=ig;
	}
	
	protected void generateInput() {
		Boolean trainSet = false;;
		do {
			currtick = (int) (XCSFUtils.Random.uniRand() * (hist.getLength() - 1));
			if(hist.getTrainTest(currtick) == "train")
				{
				trainSet = true;
				input = ig.generateInput(hist, currtick);
			}
		} while (trainSet == false);
	}
	
}
