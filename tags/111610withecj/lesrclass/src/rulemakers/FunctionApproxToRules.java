package rulemakers;

import inputGenerators.InputGenerator;
import rules.RuleSet;




public interface FunctionApproxToRules {
//	Interface for parsing function approximations
	
	public void parseRulesFromPopulation();
	public void parseRulesFromPopulation(double[] inputs, double[] outputs);
	
	public RuleSet getRuleset();
	
	public void readSource();
	// parse the output from the function approximator to a RuleSet
	
	
}
