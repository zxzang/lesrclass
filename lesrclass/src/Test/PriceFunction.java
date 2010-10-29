package Test;

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
		// if(input[0] < 0) JOptionPane.showMessageDialog(null,"input[0] < 0!: "
		// + input[0]);

		Double value = 0D;
		if(input[0] < (hist.getLength() -2))
			value = hist.getAdjClose((int)input[0]+1);
		// System.out.println(" tick " + input[0] + " value: " + value);

		// if(value == null)
		// JOptionPane.showMessageDialog(null,"null value for adjClose at tick "
		// + input[0]);
		// return (value == null? 0: value);
		
		return value;
	}

	@Override
	protected void generateInput() {

		int tick = (int) (XCSFUtils.Random.uniRand() * (hist.getLength() - 1));
	
		input[0] = tick;
			
			//hist.getAdjClose(tick);

		input[1] = XCSFUtils.Random.uniRand();
		
		//if (tick >= 1) input[1] = XCSFUtils.Random.uniRand();
			
			
	//		hist.getAdjClose(tick); 
		/*{
			
		
			double outcome = hist.getAdjClose(tick)
					/ hist.getAdjClose(tick - 1);
			input[1] = outcome;
		}*/
		
	}
}
