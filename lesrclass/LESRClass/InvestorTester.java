package LESRClass;

import rules.Rule;
import rules.RuleSet;
import LESRData.PriceData;

public interface InvestorTester {

	public abstract void test(RuleSet ruleSetIn);

	public double evalPrediction(PriceData hist, int tick, Rule.RecType rec);
		

}