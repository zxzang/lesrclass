package xcsfExtensions;

import xcsf.MatchSet;
import xcsf.Population;
import xcsf.StateDescriptor;
import xcsf.XCSFListener;
import java.io.File;
import java.io.FileNotFoundException;
import xcsf.XCSFConstants;
import xcsf.XCSFUtils;

public class RollingPopTracker implements XCSFListener {

	private int interval;
	private int exp;
	private String path;
	private String name;
	private Population finalPop;

	public RollingPopTracker(String path) {
		this(path, -1);
	}

	public RollingPopTracker(String path, int interval) {
		this.path = XCSFUtils.checkPath(path);
		this.interval = interval;
	}

	public void nextExperiment(int experiment, String functionName) {
		this.exp = experiment;
		this.name = functionName;
	}

	public void stateChanged(int iteration, Population population,
			MatchSet matchSet, StateDescriptor state, double[][] performance) {
		if (interval == -1 && iteration != XCSFConstants.maxLearningIterations) {
			return;
		} else if (iteration % interval != 0) {
			return;
		}
		// build filename
		String expString, itString = String.valueOf(iteration / 1000);
		if (this.exp < 10) {
			expString = "0" + this.exp;
		} else {
			expString = "" + this.exp;
		}
		while (itString.length() < String.valueOf(
				XCSFConstants.maxLearningIterations / 1000).length()) {
			itString = "0" + itString;
		}
		finalPop = population;
		String filename = this.path + this.name + "-exp" + expString + "-it"
				+ itString + "k";
		// write to file
		try {
			population.writePopulation(new File(filename + ".population"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Population getFinalPop(){return finalPop;}
}
