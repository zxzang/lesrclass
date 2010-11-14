package LESRClass;

import xcsf.StateDescriptor;
import xcsf.classifier.Classifier;


public abstract class Rule {

	public enum RecType{LONG, SHORT, DONOTHING}
	
	public abstract RecType evalRule(Double[] SMAsIn, String lastTick);

	public abstract void setTests(Double varA, Double varB, int n, boolean same);

	public abstract String toString();

	public abstract int getWeight();

	public abstract void setWeight(int weight);

	public abstract void goShort();

	public abstract void goLong();

	public abstract Classifier getClassifier();
	
	public abstract void doNothing();

	public abstract RecType getRecommendation();
	
	public abstract RecType getRecommendation(StateDescriptor state);

	public abstract void setRecommendation();

}