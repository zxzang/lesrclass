package Test;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.matchers.*;

import LESRClass.InvestorTester;
import LESRClass.InvestorTesterNoChartImpl;
import LESRClass.InvestorTesterChartImpl;

public class ItTester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTestBooleanArrayString() {
		String stringGenome ="00010010111011110010000111101111";
		boolean[] boolArray = new boolean[stringGenome.length()];
		for(int a = 0; a < stringGenome.length(); a++){
			if(stringGenome.charAt(a) == '0') boolArray[a] = false;
			else if(stringGenome.charAt(a) == '1') boolArray[a] = true;
			else JOptionPane.showMessageDialog(null, "problem converting String to bool[].  Value at " + a + " is " + stringGenome.charAt(a));						
		}

			InvestorTester it = new InvestorTesterNoChartImpl();
			
			double fitness = it.test(boolArray);
			assertEquals(4.7, fitness, .1);
		
		}
	
	@Test
	public void testLogChartAxis() {
		String stringGenome ="00010010111011110010000111101111";
		boolean[] boolArray = new boolean[stringGenome.length()];
		for(int a = 0; a < stringGenome.length(); a++){
			if(stringGenome.charAt(a) == '0') boolArray[a] = false;
			else if(stringGenome.charAt(a) == '1') boolArray[a] = true;
			else JOptionPane.showMessageDialog(null, "problem converting String to bool[].  Value at " + a + " is " + stringGenome.charAt(a));						
		}

			InvestorTester it = new InvestorTesterChartImpl();
			
			double fitness = it.test(boolArray);
			assertEquals(0.00, fitness, .1);
		
		}
}