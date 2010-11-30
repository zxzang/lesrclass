package LESRClass;

import inputGenerators.*;
import rulemakers.FunctionApproxToRules;
import rulemakers.XcsfRlsFunctionApproxToRulesImpl;
import xcsf.XCSF;
import xcsf.XCSFConstants;
import xcsf.listener.PerformanceGUI;
import xcsf.listener.PopulationWriter;
import xcsfExtensions.PriceFunction;

public class LESRClass {

	public static void main(String[] args) {
		// set up the input generator
		InputGenerator ig = new InputGenerator6();

		// set dim to the number of dimensions used by the particular input
		// generator
		int dim = ig.getDim();

		// set the data file to read from
		String fileName = "sp62-10SplitII.prn";

		// construct the function and set its input generator
		PriceFunction f = new PriceFunction(1, 0, 0, dim, fileName);
		f.setInputGenerator(ig);

		// load settings used by JXCSF, such as the number of
		// iterations and the types of conditions and predictions
		XCSFConstants.load("xcsf.ini");

		// construct the top-level XCSF class
		XCSF xcsf = new XCSF(f);
		
		
		// PopulationWriter is a listener that saves the output to a file
		xcsf.addListener(new PopulationWriter(null));

		xcsf.runExperiments();

		// at this point, we have the output form JXCSF. The next several lines
		// of code
		// use separate code to test JXCSF's function approximations against
		// test data

		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		rulemaker.parseRulesFromPopulation();

		// note that we use the same input generator for testing that we
		// used for the training with JXCSF, else the results would be garbage.
		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.test(rulemaker.getRuleset());

	}
}
