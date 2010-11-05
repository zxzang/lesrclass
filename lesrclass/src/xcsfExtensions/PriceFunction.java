package xcsfExtensions;

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

	int currtick = 0;
	
	public PriceFunction(double scale, double modifier, double noiseDeviation,
			int dim) {
		super(scale, modifier, noiseDeviation, dim);
		// TODO Auto-generated constructor stub
		stock = new Stock(stockName, 0);

		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		hist = new PriceData(stockName, fileName);

	}

	@Override
	protected double evaluate() {
		
		Double value = 0D;
			
		if (currtick < (hist.getLength() - 2))
			value = (hist.getAdjClose(currtick + 1))/(hist.getAdjClose(currtick));

		else value = 1.0;
	
		return value;
	}

	@Override
	protected void generateInput() {

		currtick = (int) (XCSFUtils.Random.uniRand() * (hist.getLength() - 1));
//		input[0] = currtick > 49? (hist.getAdjClose(currtick)/hist.getMaFifty(currtick)): 1;
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1)) : 1;
		
	}

}
