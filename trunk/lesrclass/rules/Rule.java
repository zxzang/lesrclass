package rules;

import xcsf.StateDescriptor;
import xcsf.classifier.Classifier;


public abstract class Rule {

	public enum RecType{LONG, SHORT, DONOTHING}
	
	public abstract String toString();

	public abstract void goShort();

	public abstract void goLong();

	public abstract Classifier getClassifier();
	
	public abstract void doNothing();

	public abstract RecType getRecommendation();
	
	public abstract RecType getRecommendation(StateDescriptor state);
	
	public abstract double getPrediction(StateDescriptor state);
		

}