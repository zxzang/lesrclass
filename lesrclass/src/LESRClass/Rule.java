package LESRClass;

import javax.swing.JOptionPane;

import LESRData.Stock;

public class Rule {

	public enum RecType{LONG, SHORT, DONOTHING}
	RecType recommendation;
	int[] intGenome;
	Double[] SMAs;
	boolean[] tests;
	int weight;
	
	public Rule(int[] intGenomeIn){
		this.intGenome = intGenomeIn;
		int numTests = (intGenome.length-2)/2;
		tests = new boolean[numTests];
		weight = intGenome[intGenome.length-1];
	}

	public RecType evalRule(Double[] SMAsIn, String lastTick){
		this.SMAs = SMAsIn;
		boolean allTestsTrue = true;  // to be checked by iterating through the array 

		int a = 0;
		int b = 0;
		for(int testNum = 0; testNum < tests.length; testNum++){

			a = testNum * 2;
			b = a + 1;

			boolean same = false;
			
			// if we are comparing two copies of the same variable, same = true
			if(intGenome[a] == intGenome[b]) same = true;
			setTests(SMAs[intGenome[a]], SMAs[intGenome[b]], testNum, same);
		}

		for(boolean test: tests){
			if(test == false) allTestsTrue = false;
		}
	
		if (allTestsTrue){
		// test int (second to last) will be 14 or 15.  If 14, make this a long rule.  If 15, make it a short rule
		
			if(intGenome[intGenome.length-2] == 14) goLong();
			else if (intGenome[intGenome.length-2] == 15) goShort();
			else System.out.println("Error setting rule type");
		}

		else doNothing();

		return recommendation;
	}
	
	
	public void setTests(Double varA, Double varB, int n, boolean same)	{

//		double changeIncrement = 15;
		if(same) tests[n] = true;
		// always pass if we are comparing two copies of the same variable		
		
		else {
			if(varA <= varB) tests[n] = true;
			else tests[n] = false;
		}
	}

	public String toString(){

		for(int i: intGenome) System.out.print(i+ " ");
		System.out.println();
		StringBuilder sb = new StringBuilder();

		int a = 0, b = 0;
		for(int testNum = 0; testNum < tests.length; testNum++){
			a = testNum * 2;
			b = a + 1;

			if(testNum > 0) System.out.print(" AND ");
			else if (tests.length > 0) System.out.print("IF ");
			System.out.print("(" + Stock.getSmaName(intGenome[a]) + " <= " + Stock.getSmaName(intGenome[b]) +")");
		}

		if(tests.length>0) System.out.print("\nTHEN ");
		if(intGenome[intGenome.length-2] == 14) System.out.print("go long");
		else if (intGenome[intGenome.length-2] == 15) System.out.print("go short");	
		System.out.print(". Weight: " + weight);
		System.out.println();
		
		return sb.toString();
	}

	public void setIntGenomePosition(int posIn, int numIn){
		intGenome[posIn] = numIn;		
	} 
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void goShort(){
//		System.out.println("recommend short");
		recommendation = RecType.SHORT;
		}

	public void goLong(){
//		System.out.println("recommend long");
		recommendation = RecType.LONG;
		}

	public void doNothing(){
//		System.out.println("dead rule");
		recommendation = RecType.DONOTHING;
	}

	public RecType getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(RecType recommendation) {
		this.recommendation = recommendation;
	}
}
