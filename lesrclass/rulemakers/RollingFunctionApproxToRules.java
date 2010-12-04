package rulemakers;

import java.io.File;

import rules.Rule.RecType;
import rules.RuleSet;
import rules.RuleSetXCSFImpl;
import xcsf.Population;
import xcsfExtensions.RollingPopTracker;

public class RollingFunctionApproxToRules{

	private RuleSet ruleset;
	private Population pop;
	RollingPopTracker rpt;

	public RollingFunctionApproxToRules(RollingPopTracker rpt) {
		this.rpt = rpt;
		pop = rpt.getFinalPop();
		parseRulesFromPopulation();
	}

	public void setRollingPopTracker(RollingPopTracker rpt){
		this.rpt = rpt;
		pop = rpt.getFinalPop();
		parseRulesFromPopulation();
	
	}
	public void parseRulesFromPopulation(double[] inputs, double[] outputs) {
		ruleset = new RuleSetXCSFImpl(pop);
		RecType rec = ruleset.getRecommendation(inputs, outputs);
	}

	public void parseRulesFromPopulation() {
		ruleset = new RuleSetXCSFImpl(pop);
	}

	public RuleSet getRuleset() {
		return ruleset;
	}

}
