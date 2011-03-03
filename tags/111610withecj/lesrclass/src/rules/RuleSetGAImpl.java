package rules;
import java.util.ArrayList;
import java.util.List;

import rules.Rule.RecType;



public class RuleSetGAImpl implements RuleSet {
	private ArrayList<RuleGAImpl> rules;
	private ArrayList<RuleGAImpl.RecType> ruleRecommendations;
	RuleGAImpl.RecType recommendation;
	int[] intGenome;
	
	public RuleSetGAImpl(boolean[] genome){
		int intGenomeLength = genome.length/4;
		intGenome = new int[intGenomeLength];
		boolean[] genSeg = new boolean[4];

		for(int a = 0; a < intGenomeLength; a++){
			int b = a*4;
			genSeg[0] = genome[b];
			genSeg[1] = genome[b+1];
			genSeg[2] = genome[b+2];	
			genSeg[3] = genome[b+3];
			intGenome[a] = parseInt(genSeg);
		}

//		checkGenomeTranslation(genome, intGenome);
		
		parseRules(intGenome);
	}
	
	public String getIntGenomeString(){
		StringBuilder sb = new StringBuilder();
		for(int i: intGenome) sb.append(i);
		return sb.toString();
		
	}
	
	/* (non-Javadoc)
	 * @see LESRClass.RuleSet#showRules()
	 */
	@Override
	public void showRules(){
		System.out.println("\nRULES: \n");
		int a = 0;
		for(Rule rule: rules){
			System.out.println("Rule # " + a);
			System.out.println(rule.toString());
			a++;
		}
	}
	
	private int[] alToArray(ArrayList<Integer> alIn){
		int[] arrayOut = new int[alIn.size()];
		
		int counter = 0;
		while(counter < alIn.size()){
			arrayOut[counter] = alIn.get(counter);
			counter++;
		}
		
		return arrayOut;
	}
	
	private void parseRules(int[] intGenome){
		rules = new ArrayList<RuleGAImpl>();

		int counter = 0;
		ArrayList<Integer> currRuleGenome = new ArrayList<Integer>();
		boolean flagNewRule = false;
		do{
			// if we are out of bits, current is discarded when the while breaks			

			// if we have just finalized a rule, start a new rule genome
			if(flagNewRule == true)	currRuleGenome = new ArrayList<Integer>(); 
			flagNewRule = false;
			
			// add this value to the rule genome			
			currRuleGenome.add(intGenome[counter]);
			
			// if current value is 14 or 15, add the rule to the array list and set a flag
			if(intGenome[counter] > 13) {
				// add one more 4-bit value for the weight
				counter++;
				currRuleGenome.add(intGenome[counter]);
				
				RuleGAImpl currRule = new RuleGAImpl(alToArray(currRuleGenome));
				rules.add(currRule);
//				System.out.println("RS 90: Added new rule: " + currRule.toString());
				flagNewRule = true;
			}
			
		} while(counter++ < intGenome.length-2);
 
	}

	public void checkWeights(Double[] SMAsIn, String lastTick){

		Double recDouble = 0.0;
		ruleRecommendations = new ArrayList<RuleGAImpl.RecType>();

		for(int counter = 0; counter < rules.size(); counter++){
			rules.get(counter).evalRule(SMAsIn, lastTick);
			RuleGAImpl.RecType currRec = rules.get(counter).getRecommendation();
			ruleRecommendations.add(currRec);
			System.out.println("RS 113: " + ruleRecommendations.get(counter).toString());
			System.out.println("RS 113: " + rules.get(counter).getWeight());

		}

		for(int counter = 0; counter < rules.size(); counter++){
			if(ruleRecommendations.get(counter) == RuleGAImpl.RecType.LONG) recDouble += rules.get(counter).getWeight();
			if(ruleRecommendations.get(counter) == RuleGAImpl.RecType.SHORT) recDouble -= rules.get(counter).getWeight();
		}
		
		if(recDouble > 0) recommendation = RuleGAImpl.RecType.LONG;
		else if(recDouble < 0) recommendation = RuleGAImpl.RecType.SHORT;
		else recommendation = RuleGAImpl.RecType.DONOTHING;

		System.out.println("R118 total recommendation: "  + recommendation.toString());
	}	
	
	/* (non-Javadoc)
	 * @see LESRClass.RuleSet#getRecommendation(java.lang.Double[], java.lang.String)
	 */
	@Override
	public RuleGAImpl.RecType getRecommendation(Double[] SMAsIn, String lastTick){
		Double recDouble = 0.0;
		ruleRecommendations = new ArrayList<RuleGAImpl.RecType>();

		for(int counter = 0; counter < rules.size(); counter++){
			rules.get(counter).evalRule(SMAsIn, lastTick);
			RuleGAImpl.RecType currRec = rules.get(counter).getRecommendation();
			ruleRecommendations.add(currRec);
		}

/*		while(rules.listIterator().hasNext()) {
			Rule.RecType currRec = rules.listIterator().next().getRecommendation();
			ruleRecommendations.add(currRec);
		}
	*/	
		
		// get the ruleset recommendation by comparing the total weightings of rules that recommend
		// long or short.  Note that a rule whose antecedents are not met recommends DONOTHING, which 
		// we ignore here.
		for(int counter = 0; counter < rules.size(); counter++){
			if(ruleRecommendations.get(counter) == RuleGAImpl.RecType.LONG) recDouble += rules.get(counter).getWeight();
			if(ruleRecommendations.get(counter) == RuleGAImpl.RecType.SHORT) recDouble -= rules.get(counter).getWeight();
			
		}		

		if(recDouble > 0) recommendation = RuleGAImpl.RecType.LONG;
		else if(recDouble < 0) recommendation = RuleGAImpl.RecType.SHORT;
		else recommendation = RuleGAImpl.RecType.DONOTHING;
		
		return recommendation;
	}

	/* (non-Javadoc)
	 * @see LESRClass.RuleSet#getAllRecs()
	 */
	
	public List<RuleGAImpl.RecType> getAllRecs(){
		// used in testing
		List<RuleGAImpl.RecType> reclist = new ArrayList<RuleGAImpl.RecType>();
		for(Rule r: rules) reclist.add(r.getRecommendation());
		return reclist;
	}	
	
	private int parseInt(boolean[] genSeg){
		int returnValue = 0;
		int counter = 0;

		int numPlaces = genSeg.length;
		for(counter = 0; counter < numPlaces; counter++){
			if(genSeg[counter] == true)
				returnValue += (Math.pow(2,(numPlaces - counter-1)));
		}
		
		return returnValue;
	}

	private void checkGenomeTranslation(boolean[] genome, int[]intGenome){
		// diagnostic
		for(boolean b: genome) System.out.print((b?'1':'0') + " ");
		System.out.println();
		
		for(int i: intGenome){
			System.out.print(i + "      ");
			if(i < 10) System.out.print(" ");
		}
		System.out.println();
		
	}

	@Override
	public RecType getRecommendation(double[] inputs, double[] outputs) {
		// TODO Auto-generated method stub
		return null;
	}
}