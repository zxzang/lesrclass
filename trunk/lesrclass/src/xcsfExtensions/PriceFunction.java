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
	int dim;
	
	double[][] bounds;

	
	int currtick = 0;
	
	public PriceFunction(double scale, double modifier, double noiseDeviation,
			int dim) {
		super(scale, modifier, noiseDeviation, dim);
		// TODO Auto-generated constructor stub
		this.dim = dim;
		bounds = new double [dim][2];
		/* for each dim
		 *  bounds[dim][0] = lowest value so far for input[dim]
		 * bounds[dim][1] = highest values so far for input[dim]
		*/	
		for(int i = 0; i < dim; i++) {
			bounds[i][0] = 0;
			bounds[i][1] = 0;
		}
		stock = new Stock(stockName, 0);

		fileName = "sp62-10Split.prn";
		stockName = "SP500";
		hist = new PriceData(stockName, fileName);

	}

	@Override
	protected double evaluate() {
		
		Double value = 0D;
			
		if (currtick < (hist.getLength() - 2))
			value = (hist.getAdjClose(currtick + 1))/(hist.getAdjClose(currtick))* 100;
		else value = 100.0;
	
		
//		System.out.println("Tick: " + currtick + " input: " + input[0] + 
//				" output: " + value);
		
		return value;
	}

	@Override
	protected void generateInput() {

		currtick = (int) (XCSFUtils.Random.uniRand() * (hist.getLength() - 1));
		input[0] = currtick > 0? (hist.getAdjClose(currtick) / hist.getAdjClose(currtick - 1))*100 : 100.0;
		input[1] = currtick > 49? (hist.getAdjClose(currtick)/hist.getMaFifty(currtick)) * 100: 100.0;
		input[2] = currtick > 199? (hist.getAdjClose(currtick)/hist.getMaTwoHundred(currtick))* 100: 100.0;
		
		for(int i = 0; i < dim; i++) {
			if(input[i] < bounds[i][0]|| bounds[i][0] == 0) bounds[i][0] = input[i];
			if(input[i] > bounds[i][1]) bounds[i][1] = input[i];
			
		}
	
	}

	public void showBounds(){
		System.out.println("lowest and highest input values: for " + dim + " dimensions");
		for(int i = 0; i < dim; i++){
			System.out.println("Dimension: " + i + " lowest = " + bounds[i][0] +
					" highest = " + bounds[i][1] + " center: " +
					(bounds[i][1]+bounds[i][0])/2);
			
			
			
		}
		
		
	}
}
