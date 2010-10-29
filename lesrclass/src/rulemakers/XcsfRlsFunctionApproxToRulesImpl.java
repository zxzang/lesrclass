package rulemakers;

import java.io.File;
import LESRClass.RuleSet;

public class XcsfRlsFunctionApproxToRulesImpl implements FunctionApproxToRules {

	private RuleSet ruleset;

	public XcsfRlsFunctionApproxToRulesImpl() {
		File XcsfOutputFile = chooseFile();

		XcsfRlsFunctionApproxToRulesImpl(XcsfOutputFile);
	
	}

	public XcsfRlsFunctionApproxToRulesImpl(File XcsfOutputFile) {

	}

	public RuleSet getRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readSource() {
		// TODO Auto-generated method stub

	}

	private File chooseFile() {
		return null;
	}

}
