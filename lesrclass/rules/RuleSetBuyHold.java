package rules;

import rules.Rule.RecType;


public class RuleSetBuyHold implements RuleSet {

	@Override
	public void showRules() {
		// TODO Auto-generated method stub

	}

	@Override
	public RecType getRecommendation(double[] inputs, double[] outputs) {
		// TODO Auto-generated method stub
		return RecType.LONG;
	}
@Override
	public int getRuleSetLength(){
		return 0;
}

}
