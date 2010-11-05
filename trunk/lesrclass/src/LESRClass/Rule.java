package LESRClass;

import LESRClass.RuleGAImpl.RecType;

public interface Rule {

	public abstract RecType evalRule(Double[] SMAsIn, String lastTick);

	public abstract void setTests(Double varA, Double varB, int n, boolean same);

	public abstract String toString();

	public abstract int getWeight();

	public abstract void setWeight(int weight);

	public abstract void goShort();

	public abstract void goLong();

	public abstract void doNothing();

	public abstract RecType getRecommendation();

	public abstract void setRecommendation();

}