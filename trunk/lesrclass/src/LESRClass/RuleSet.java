package LESRClass;

import java.util.List;

import LESRClass.RuleGAImpl.RecType;

public interface RuleSet {

	public abstract void showRules();

	public abstract RuleGAImpl.RecType getRecommendation(Double[] SMAsIn,
			String lastTick);

	
}