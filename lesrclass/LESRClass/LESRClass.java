package LESRClass;

import inputGenerators.InputGenerator;
import inputGenerators.*;
import rulemakers.FunctionApproxToRules;
import rulemakers.XcsfRlsFunctionApproxToRulesImpl;
import xcsf.XCSF;
import xcsf.XCSFConstants;
import xcsf.listener.PopulationWriter;
import xcsfExtensions.PriceFunction;

public class LESRClass {

	public static void main(String[] args) {
		// 17 and 18 best so far for non-rolling
		InputGenerator ig = new InputGenerator17();

		String fileName = "sp62-10SplitII.prn";
		runNonRolling(fileName, ig);
//		testOnlyNonRolling(fileName, ig);
		
		// 18 best so far for rolling
		int rollperiod = 250;
		int maxlength = 5000;
//		runRolling(fileName, ig, rollperiod, maxlength);

	}

	
	public static void runRolling(String fileName, InputGenerator ig, int rollperiod, int maxlength){
		RollingTesterSplit rt = new RollingTesterSplit(fileName, ig, rollperiod, maxlength);
		rt.test();
	}

	public static void runNonRolling(String fileName, InputGenerator ig) {
		int dim = ig.getDim();
		PriceFunction f = new PriceFunction(1, 0, 0, dim, fileName);
		f.setInputGenerator(ig);

		XCSFConstants.load("xcsf.ini");

		XCSF xcsf = new XCSF(f);

		xcsf.addListener(new PopulationWriter(null));

		xcsf.runExperiments();

		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		rulemaker.parseRulesFromPopulation();

		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.test(rulemaker.getRuleset());
	}
	
	public static void testOnlyNonRolling(String fileName, InputGenerator ig) {
		
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		rulemaker.parseRulesFromPopulation();

		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.test(rulemaker.getRuleset());
	}
}
