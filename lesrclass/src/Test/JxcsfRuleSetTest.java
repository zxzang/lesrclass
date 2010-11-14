package Test;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rulemakers.FunctionApproxToRules;
import rulemakers.XcsfRlsFunctionApproxToRulesImpl;
import xcsf.Population;

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
	
	@Test
	public void testRules(){
		FunctionApproxToRules rulemaker = new XcsfRlsFunctionApproxToRulesImpl();
		
		
		
		
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
