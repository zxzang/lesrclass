package LESRClass;

import LESRData.PriceData;

public interface InvestorTester {

	public abstract double test(boolean[] genome);
	public abstract double test(boolean[] genome, String trainOrTest);
	
	public abstract double evalPrediction(PriceData hist, int tick,
			RuleGAImpl.RecType rec);

	public abstract boolean checkPrediction(String lastTick,
			RuleGAImpl.RecType lastRec);

}