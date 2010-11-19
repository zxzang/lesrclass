package rules;

import java.util.List;



public interface RuleSet {

	public abstract void showRules();

	
	Rule.RecType getRecommendation(double[] inputs, double[] outputs);

	
}