package LESRClass;

import LESRClass.RuleGAImpl.RecType;
import LESRData.Stock;
import java.util.List;
import java.util.ArrayList;

public class RuleXcsfImpl implements Rule {
	RecType recommendation;
	double weight;	
	List<Boolean> tests;
	List<Double> inputs;
	List<Double> coefficients;
	double prediction;
	double threshold;

		
	public RuleXcsfImpl(double thresholdIn){
		tests = new ArrayList<Boolean>();
		inputs = new ArrayList<Double>();
		coefficients = new ArrayList<Double>();
		threshold = thresholdIn;
	}
	

	public RuleGAImpl.RecType evalRule(List<Double> inputsIn, List<Double> coefficientsIn){
		this.inputs = inputsIn;
		boolean allTestsTrue = true;  // to be checked by iterating through the array 
	
		
		for(boolean test: tests){
			if(test == false) allTestsTrue = false;
		}
	
		if (allTestsTrue){
		
			if(prediction > (1+ threshold)) goLong();
			else if(prediction < (1-threshold)) goShort();
			else doNothing();
		}

		else doNothing();

		return recommendation;
	}
	
/*	
	@Override
	public void setTests(Double varA, Double varB, int n, boolean same)	{

//		double changeIncrement = 15;
		if(same) tests[n] = true;
		// always pass if we are comparing two copies of the same variable		
		
		else {
			if(varA <= varB) tests[n] = true;
			else tests[n] = false;
		}
	}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#toString()
	 */
	/*
	@Override
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
	
	@Override
	public int getWeight() {
		return weight;
	}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#setWeight(int)
	 */
	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public void goShort(){
//		System.out.println("recommend short");
		recommendation = RecType.SHORT;
		}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#goLong()
	 */
	@Override
	public void goLong(){
//		System.out.println("recommend long");
		recommendation = RecType.LONG;
		}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#doNothing()
	 */
	@Override
	public void doNothing(){
//		System.out.println("dead rule");
		recommendation = RecType.DONOTHING;
	}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#getRecommendation()
	 */
	@Override
	public RecType getRecommendation() {
		return recommendation;
	}

	/* (non-Javadoc)
	 * @see LESRClass.Rule#setRecommendation(LESRClass.GARuleImpl.RecType)
	 */
	public void setRecommendation(RuleGAImpl.RecType recommendation) {
		this.recommendation = recommendation;
	}

	@Override
	public void setRecommendation() {
		// TODO Auto-generated method stub
		
	}
	public void setWeight(double fitnessIn){
		weight = 1 - fitnessIn;
	
	}


	@Override
	public RecType evalRule(Double[] SMAsIn, String lastTick) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setTests(Double varA, Double varB, int n, boolean same) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	
}
