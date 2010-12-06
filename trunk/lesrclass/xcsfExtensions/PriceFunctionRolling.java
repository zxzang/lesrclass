package xcsfExtensions;

import inputGenerators.InputGenerator;
import xcsf.Function.SimpleFunction;
import xcsf.XCSFUtils;
import LESRData.PriceData;
import LESRData.Stock;

public class PriceFunctionRolling extends SimpleFunction {
	String stockName;
	String fileName;
	Stock stock;
	PriceData hist;
	int dim;
	InputGenerator ig;
	int currtick;
	int rollperiod;
	int start;

	public void setCurr(int currIn) {
		currtick = currIn;
	}

	public PriceFunctionRolling(double scale, double modifier,
			double noiseDeviation, int dim, String fileName, int rollperiod) {
		super(scale, modifier, noiseDeviation, dim);
		currtick = 0;
		this.dim = dim;
		stock = new Stock(stockName, 0);
		this.fileName = fileName;
		stockName = "SP500";
		hist = new PriceData(stockName, fileName);
		this.rollperiod = rollperiod;
		start = 0;
	}

	@Override
	protected double evaluate() {
		Double value = 0D;

		if (currtick < (hist.getLength() - 2)) {
			value = (hist.getAdjClose(currtick + 1))
					/ (hist.getAdjClose(currtick)) * 100;
		} else
			value = 100.0;
		return value;
	}

	public void setRollperiod(int rollPeriodIn) {
		rollperiod = rollPeriodIn;
	}

	public void setInputGenerator(InputGenerator ig) {
		this.ig = ig;
	}

	public void setStart(int startIn) {
		start = startIn;
	}

	protected void generateInput() {
		currtick = (int) (XCSFUtils.Random.uniRand() * rollperiod) + start;
		input = ig.generateInput(hist, currtick);
	}

	public int getHistLength() {
		return hist.getLength();
	}
}
