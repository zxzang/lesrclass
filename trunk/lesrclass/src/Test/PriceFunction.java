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
	

	public PriceFunction(double scale, double modifier, double noiseDeviation,
			int dim) {
		super(scale, modifier, noiseDeviation, dim);
		// TODO Auto-generated constructor stub
		stock = new Stock(stockName,0);
		
		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		hist = new PriceData(stockName, fileName);
					
	}

	@Override
	protected double evaluate() {
	//	if(input[0] < 0) JOptionPane.showMessageDialog(null,"input[0] < 0!: " + input[0]);

		Double value = hist.getAdjClose((int) input[0]);
	//	if(value == null) JOptionPane.showMessageDialog(null,"null value for adjClose at tick " + input[0]);
		return (value == null? 0: value);

		
	}
	
	@Override
	protected void generateInput() {
      
		int tick = (int) XCSFUtils.Random.uniRand() * (hist.getLength()-1);
		input[0] = tick;
//		input[1] = (hist.getMaFifty(tick) > hist.getYesterdaysClose(tick)?1:0);
		input[1] = 0;
		if(tick >= 2) input[1] = hist.getAdjClose(tick-1)/hist.getAdjClose(tick-2);
		
		
		
	
	}
}
