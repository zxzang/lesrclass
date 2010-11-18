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
/*		System.out.println("getting prediction for input: ");
		for(double d: input) System.out.print(d + " ");
	*/	
		 
		double[] centeredInput = state.getPredictionInput();
		for(int i = 0; i < centeredInput.length; i++)
			centeredInput[i] -= classifier.getCondition().getCenter()[i];
		
		double[] predChange = prediction.predict(centeredInput);
	
//		for(double pred: predChange) System.out.println("prediction: " + pred);
		if(predChange[0] > (100+ threshold)) goLong();
		else if(predChange[0] < (100-threshold)) goShort();
		else doNothing();
		
		return recommendation;
	}
	

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
	public void setRecommendation(Rule.RecType recommendation) {
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}
