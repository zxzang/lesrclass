package Test;

import static org.junit.Assert.assertNotNull;
import inputGenerators.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rulemakers.FunctionApproxToRules;
import rulemakers.XcsfRlsFunctionApproxToRulesImpl;
import rules.RuleSet;
import rules.RuleSetDoNothing;
import xcsf.Population;
import xcsf.XCSF;
import xcsf.XCSFConstants;
import xcsf.listener.PopulationWriter;
import xcsfExtensions.PriceFunction;
import LESRClass.InvestorTester;
import LESRClass.InvestorTesterXcsfImpl;

public class JxcsfRuleSetTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testMakeRules(){
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		
		double[] outputs = {100.0};
		int min = 97;
		int max = 103;
		for(int a = min; a < (max + 1); a++)
			for(int b = min; b < (max + 1); b++)
				for(int c = min; c < (max + 1); c++){
					double[] inputs = {a, b, c};
					rulemaker.parseRulesFromPopulation(inputs, outputs);
				}
		assertNotNull(rulemaker.getRuleset());
			
	}
	
	@Ignore
	@Test
	public void testFunctionAndRules(){
		
		// set up the input generator
		InputGenerator ig = new InputGenerator13();
		
		// set dim to the number of dimensions used by the particular input generator
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
		
		// at this point, we have the output form JXCSF.  The next several lines of code
		// use separate code to test JXCSF's function approximations against test data
		
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		rulemaker.parseRulesFromPopulation();
		
		// note that we use the same input generator for testing that we
		// used for the training with JXCSF, else the results would be garbage.
		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.test(rulemaker.getRuleset());
		
		assertNotNull(rulemaker.getRuleset());
	}

	
	@Test
	public void testRules(){
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		
		InputGenerator ig = new InputGenerator6();
		rulemaker.parseRulesFromPopulation();
		
		String fileName = "sp62-10SplitII.prn";
		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.test(rulemaker.getRuleset());
		
		assertNotNull(rulemaker.getRuleset());
	}

	
	@Ignore
	@Test
	public void testRulesNoTTSplit(){
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		InputGenerator ig = new InputGenerator5();
		rulemaker.parseRulesFromPopulation();
				
		String fileName = "sp62-10SplitII.prn";
		InvestorTester test = new InvestorTesterXcsfImpl(ig, fileName);
		test.testNoSplit(rulemaker.getRuleset());
		
		assertNotNull(rulemaker.getRuleset());
	}
	
	
	
	@Ignore
	@Test
	public void testDoNothing(){
		RuleSet rules = new RuleSetDoNothing();
		String fileName = "sp62-10Split.prn";

		System.out.println("rules " + (rules==null? "are null": "are not null"));
		InvestorTester test = new InvestorTesterXcsfImpl(new InputGenerator5(), fileName);
		test.test(rules);
		
		
	}
	
	
	@Ignore
	@Test
	public void testGetFiles() {
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		
		assertNotNull(rulemaker.getRuleset());
	}

@Ignore
@Test
public void testpopParser() {
	Population pop1 = new Population();
	Population pop2 = new Population();
	try {
		pop2 = pop1.parse(new File("PriceFunction-exp00-it100k.population"));
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertNotNull(pop2);
	
}

}
