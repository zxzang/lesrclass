package rules;

import java.util.List;



public interface RuleSet {

	public abstract void showRules();

	int getRuleSetLength();
	Rule.RecType getRecommendation(double[] inputs, double[] outputs);

	
}