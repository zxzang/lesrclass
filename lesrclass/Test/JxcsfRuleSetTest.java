package Test;

import static org.junit.Assert.assertNotNull;
import inputGenerators.InputGenerator;
import inputGenerators.InputGenerator2InputsImpl;
import inputGenerators.InputGenerator3InputsImpl;
import inputGenerators.InputGenerator4InputsImpl;

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
		PriceFunction f = new PriceFunction(1, 0, 0, 3);
		InputGenerator ig = new InputGenerator3InputsImpl();
		
		XCSFConstants.load("xcsf.ini");
		XCSF xcsf = new XCSF(f);
		f.setInputGenerator(ig);
		
		xcsf.addListener(new PopulationWriter(null));
		xcsf.runExperiments();
		
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		rulemaker.parseRulesFromPopulation();
		InvestorTester test = new InvestorTesterXcsfImpl(ig);
		test.test(rulemaker.getRuleset());
		assertNotNull(rulemaker.getRuleset());
	}
	
	@Test
	public void testRules(){
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		InputGenerator ig = new InputGenerator3InputsImpl();
		rulemaker.parseRulesFromPopulation();
//		System.out.println("rules " + (rulemaker.getRuleset()==null? "are null": "are not null"));
		InvestorTester test = new InvestorTesterXcsfImpl(ig);
		test.test(rulemaker.getRuleset());
		
		assertNotNull(rulemaker.getRuleset());
	
	}
	
	@Ignore
	@Test
	public void testDoNothing(){
		RuleSet rules = new RuleSetDoNothing();
		System.out.println("rules " + (rules==null? "are null": "are not null"));
		InvestorTester test = new InvestorTesterXcsfImpl(new InputGenerator4InputsImpl());
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
