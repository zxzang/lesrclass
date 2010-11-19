package rules;

import xcsf.StateDescriptor;
import xcsf.classifier.Classifier;
import xcsf.classifier.Prediction;


public class RuleXcsfImpl extends Rule {
	RecType recommendation;
	double weight;	
	double threshold;
	Classifier classifier;
		
	public RuleXcsfImpl(double thresholdIn){
		threshold = thresholdIn;
	}

	public RuleXcsfImpl(Classifier classifier){
		this.classifier = classifier;
		threshold = 0;
	}

	public Classifier getClassifier(){return classifier;}
	

	public RecType getRecommendation(StateDescriptor state){
		classifier.predict(state);
		Prediction prediction = classifier.getPrediction();
		double[] input = state.getPredictionInput();
		 
		double[] centeredInput = state.getPredictionInput();
		for(int i = 0; i < centeredInput.length; i++)
			centeredInput[i] -= classifier.getCondition().getCenter()[i];
		
		double[] predChange = prediction.predict(centeredInput);
	
		if(predChange[0] > (100+ threshold)) goLong();
		else if(predChange[0] < (100-threshold)) goShort();
		else doNothing();
		
		return recommendation;
	}
	

	@Override
	public void goShort(){
		recommendation = RecType.SHORT;
		}

	@Override
	public void goLong(){
		recommendation = RecType.LONG;
		}

	@Override
	public void doNothing(){
		recommendation = RecType.DONOTHING;
	}

	@Override
	public RecType getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Rule.RecType recommendation) {
		this.recommendation = recommendation;
	}
	
	@Override
	public String toString() {
		return null;
	}
	
}
