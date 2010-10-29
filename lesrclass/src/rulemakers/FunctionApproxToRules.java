package rulemakers;

import java.util.List;

import LESRClass.*;




public interface FunctionApproxToRules {
//	Interface for parsing function approximations
	
	public RuleSet getRules();
	
	public void readSource();
	// parse the output from the function approximator to a RuleSet
	
	
}
