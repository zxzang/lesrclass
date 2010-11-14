package LESRClass;
import javax.swing.JOptionPane;

import org.jfree.util.Log;
import LESRData.SplitData;
import java.util.*;

import ec.*;
import java.util.BitSet;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LESRClass {

	public static void main(String[] args) {
	
//Do a run with the GA		
//		Evolve test = new Evolve();
//		test.main(args); 

//		String genome = " 0 0 0 1 1 0 0 0 0 0 1 0 0 1 1 1 1 1 0 1 1 0 0 1 1 0 0 1 1 0 1 1 0 1 1 0 0 1 1 0 0 0 0 1 0 0 0 0 1 1 1 0 1 1 1 0 1 0 1 0 1 1 1 0 0 0 0 0 0 0 0 1 1 1 1 0 0 0 0 1 1 1 0 0 1 1 0 0 0 0 1 0 0 0 1 1 0 0 0 1 1 0 0 1 0 1 1 1 1 1 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 1 0 1 1 0 1 0 0 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 0 0 0 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 0 1 0 0 0 1 0 0 1 1 1 0 1 1 0 0 1 1 0 0 0 1 1 1 0 1 0 1 0 0 1 1 0 0 0 1 0 0 1 0 1 0 1 0 1 0 0 0 1 1 1 0 1 0 0 1 1 1 0 0 0 0 1 1 1 0 1 1 0 0 1 0 0 0 1 0 1 0 0 0 0 1 1 0 1 1 1 0 0 0 0 1 1 1 1 0 1 1 1 1 0 1 1 1 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 1 0 1 1 1 1 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 1 0 1 1 0 0 1 0 0 1 0 0 0 1 0 1 1 1 1 0 1 1 1 1 1 0 0 0 0 1 0 1 0 0 1 1 0 1 1 1 0 0 0 1 0 1 0 1 0 0 1 1 0 0 0 0 0 1 0 1 0 1 0 1 0 0 0 0 0 0 0 1 1 0 1";

//		String genome = " 1 1 1 0 0 1 0 1 0 1 0 1 0 0 1 0 1 1 0 1 0 1 1 0 1 1 1 1 1 0 0 1 0 0 1 0 0 0 1 1 1 0 0 0 1 1 1 1 1 0 1 0 1 1 0 1 1 0 1 1 0 1 0 1 0 1 1 1 1 1 1 0 0 1 0 0 0 1 0 0 1 1 1 1 0 0 0 0 0 1 0 0 1 1 0 1 1 0 0 1 1 0 1 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 1 1 0 1 0 1 0 0 1 1 0 1 0 0 1 1 0 1 0 0 0 0 1 1 0 0 1 1 0 1 1 1 1 0 1 0 0 0 1 0 0 0 0 1 1 1 0 0 0 0 0 1 0 1 0 0 1 1 1 0 1 0 1 0 1 1 0 1 0 0 0 1 1 0 1 1 0 0 1 1 0 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 0 1 0 1 0 1 1 1 0 0 0 1 1 1 1 0 0 1 0 0 0 0 1 1 0 0 0 1 0 0 0 0 1 1 0 1 0 0 1 1 1 1 1 1 0 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 1 1 1 0 0 1 1 1 0 1 0 0 1 1 0 1 1 1 0 0 0 0 0 1 0 1 1 1 0 0 0 0 1 0 1 1 1 1 1 1 0 0 1 1 1 0 0 1 0 0 0 0 1 1 1 1 1 1 0 1 0 0 0 1 0 0 1 0 0 1 1 0 0 0 1 0 1 0 0 1 1 0 1 1 1 1 1 0 1";
//String genome = " 0 0 0 1 0 0 1 1 1 1 1 1 1 0 0 0 1 1 1 0 1 1 1 0 0 0 1 0 0 1 1 1 1 1 0 1 1 0 1 1 0 1 0 0 1 0 0 0 0 0 0 1 0 0 0 0 1 1 1 0 1 1 1 1 1 1 1 1 0 0 1 1 0 1 0 1 1 1 1 0 0 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 0 0 0 0 0 0 1 0 1 0 1 1 0 1 1 1 0 1 1 1 0 0 1 1 0 0 1 0 0 1 0 0 1 1 0 0 1 1 0 0 0 0 1 0 0 0 1 0 0 0 0 1 0 0 0 1 0 0 0 0 1 0 0 1 1 0 0 0 1 0 0 1 1 0 1 1 0 0 0 0 0 1 0 1 1 0 1 1 0 0 1 1 1 0 0 0 0 0 1 0 1 0 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 1 0 1 0 0 0 0 1 1 1 0 1 1 1 0 1 0 1 0 0 1 0 0 0 1 1 1 0 1 1 1 0 0 0 1 1 1 1 0 1 1 0 0 1 1 1 0 0 0 0 0 1 1 1 0 0 1 0 0 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 0 1 1 0 0 0 1 0 0 0 1 1 1 0 0 0 1 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 1 0 1 1 0 1 0 1 0 0 1 1 1 1 0 1 0 0 1 1 0 0 0 0 0 1 1 1 0 0 0 0 1 0 1 1 0 1 0 1 1 1 0 1 1 0 0 0 1 0 0 0 1 1 0 0 0 1 1 0 0 1 1 0 0 1 0 1 1 1 0 0 1 1 1 0 1 0 0 1 0 0 1 1 1 0 0 1 0 0 1 0 0 1 1 0 0 0 0 1 1 0 1 0 1 0 0 1 1 1 1 1 0 0 1 0 0 0 0 0 1 0 1 1 1 1 0 1 0 1 0 1 0 0 0 1 0 0 1 1 0 0 0 1 0 1 0 1 0 0 0 0 0 0 0 1 1 0 0";
//		String trainOrTest = "train";

		// buy/hold:		
		
//		String genome ="0001 0010 1110 1111 0010 0001 1110 1111";
		String genome = "100011010000000100001010111110001001011110011010111111101110111110011000011001110110111110001101111111110011001001001101100000000010111011110111000101001011000001110101100111011101111100001000101001100011101101001110110001111000111101111110011011000111100000010110011011001100101100010111101010001100110011111001010110111110100000101011000000101110111110101100110000100011001101110100001000110011011101100110111110100110010011111010001101011111010001100100010011111001101011001101110010000011111000111001110100000111100010101100001110111101100010111010000000001101100100011000000111010010011100101010";
		
//		getRules(stringRemoveSpaces(genome));

		runOneGenomeWithBitString(stringRemoveSpaces(genome));
// split the data into training and test		
//		SplitData hist = new SplitData("Data", 12182, "table62to10.prn");

	}

	private static void runOneGenomeWithBitString(String bitString){
		boolean[] genome = convert(stringRemoveSpaces(bitString));
		InvestorTester it = new InvestorTesterChartImpl();
//		InvestorTester it = new InvestorTesterNoChartImpl();
		
//		it.test(genome);
	}
	
	private static void runOneGenome(boolean[] genome){
		InvestorTesterChartImpl it = new InvestorTesterChartImpl();
//		InvestorTester it = new InvestorTesterNoChartImpl();
		
		it.test(genome);
	}
	
	static boolean[] convert (String stringGenome){
		boolean[] boolArray = new boolean[stringGenome.length()];
		for(int a = 0; a < stringGenome.length(); a++){
			if(stringGenome.charAt(a) == '0') boolArray[a] = false;
			else if(stringGenome.charAt(a) == '1') boolArray[a] = true;
			else JOptionPane.showMessageDialog(null, "problem converting String to bool[].  Value at " + a + " is " + stringGenome.charAt(a));						
		}
		return boolArray;
		
	}

	static boolean[] convert (BitSet bGenome){
		boolean[] boolArray = new boolean[bGenome.length()];
		for(int a = 0; a < bGenome.length(); a++){
			boolArray[a] = bGenome.get(a);
		}
		return boolArray;
		
	}

	public static void getRules(String sGenomeIn){
		String stringGenome = stringRemoveSpaces(sGenomeIn);
		boolean[] genome = convert(stringGenome);
		
		RuleSet rs = new RuleSetGAImpl(genome);
		rs.showRules();
}

	public static String stringRemoveSpaces(String stringIn){
		StringBuilder returnString = new StringBuilder();
		for(int a = 0; a < stringIn.length(); a++){
			char c = stringIn.charAt(a);
			if(c != ' ') returnString.append(c);
		}
		return returnString.toString();
	}
}
