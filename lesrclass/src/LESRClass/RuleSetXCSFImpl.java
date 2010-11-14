package LESRClass;

import java.util.ArrayList;
import java.util.List;

import xcsf.Population;
import xcsf.StateDescriptor;
import xcsf.classifier.Classifier;
import LESRClass.Rule.RecType;

public class RuleSetXCSFImpl implements RuleSet {
	List<Classifier> classifiers;
	List<Rule> rules;
	
	public RuleSetXCSFImpl(Population pop){
		classifiers = new ArrayList<Classifier>();
		
//		System.out.println("population received by RuleSet Constructor? " + (pop == null? "no": "yes"));
		if(pop.size() > 0){
			for(int i = 0; i < pop.size(); i++)
		
			classifiers.add(pop.get(i));
				
		}
//		for(Classifier classifier: classifiers) System.out.println(classifier.toString());
		makeRules();
		
	}
	
	public void makeRules(){
		rules = new ArrayList<Rule> ();
		for(Classifier classifier: classifiers) 
			rules.add(new RuleXcsfImpl(classifier)); 
	}
	
	
	@Override
	public void showRules() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RecType getRecommendation(double[] inputs, double[] outputs) {
		RecType recommendation;	
		int totalRec = 0;
		List<RecType> recs = new ArrayList<RecType>();
		StateDescriptor state = new StateDescriptor(inputs, outputs);
		
		for (Rule rule:rules) {
			System.out.println("\n\nNew Classifier.  Condition center: ");
			for(double d: rule.getClassifier().getCondition().getCenter())
				System.out.print(" " + d);
			System.out.println("volume: " + rule.getClassifier().getCondition().getVolume());
			System.out.println("match? " + rule.getClassifier().doesMatch(state));
			
			
			if(rule.getClassifier().doesMatch(state)){
				
				recs.add(rule.getRecommendation(state));
			}			
		}
		
		for(RecType rec: recs){
			System.out.println(rec.toString());
			if(rec == RecType.LONG) totalRec += 1;
			else if(rec == RecType.SHORT) totalRec -= 1;
		}
		if(totalRec > 0) recommendation = RecType.LONG;
		else if (totalRec < 0) recommendation = RecType.SHORT;
		else recommendation = RecType.DONOTHING;
		System.out.println("totalRec: " + totalRec + " recommendation: " + recommendation. toString());
		return recommendation;
	}

	@Override
	public RecType getRecommendation(Double[] SMAsIn, String lastTick) {
		// TODO Auto-generated method stub
		return null;
	}

}