package LESRClass;

import java.util.List;


public interface RuleSet {

	public abstract void showRules();

	public abstract RuleGAImpl.RecType getRecommendation(Double[] SMAsIn,
			String lastTick);

	Rule.RecType getRecommendation(double[] inputs, double[] outputs);

	
}