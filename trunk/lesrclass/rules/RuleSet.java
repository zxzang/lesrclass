package rules;

import java.util.List;



public interface RuleSet {

	public abstract void showRules();

	public abstract Rule.RecType getRecommendation(Double[] SMAsIn,
			String lastTick);

	Rule.RecType getRecommendation(double[] inputs, double[] outputs);

	
}