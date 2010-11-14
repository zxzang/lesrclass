package rulemakers;

import java.io.File;
import java.util.List;

import xcsf.Population;

import LESRClass.*;




public interface FunctionApproxToRules {
//	Interface for parsing function approximations
	
	public void parseRulesFromPopulation();
	public void parseRulesFromPopulation(double[] inputs, double[] outputs);
	
	public RuleSet getRuleset();
	
	public void readSource();
	// parse the output from the function approximator to a RuleSet
	
	
}
