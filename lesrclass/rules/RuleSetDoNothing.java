package rules;

import rules.Rule.RecType;


public class RuleSetDoNothing implements RuleSet {

	@Override
	public void showRules() {
		// TODO Auto-generated method stub

	}

	@Override
	public RecType getRecommendation(double[] inputs, double[] outputs) {
		// TODO Auto-generated method stub
		return RecType.DONOTHING;
	}
@Override
	public int getRuleSetLength(){
		return 0;
}

}
