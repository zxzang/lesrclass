package LESRClass;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.BitVectorIndividual;
import javax.swing.*;
// most of this is direct from ECJ, with my project-specific additions 

public class Fitness extends Problem implements SimpleProblemForm{
	private static final long serialVersionUID = 1L;

	public void describe(Individual ind, EvolutionState state,
			int subpopulation, int threadnum, int log, int verbosity) {
	}

	public void evaluate(EvolutionState state, Individual ind,
			int subpopulation, int threadnum) {
	
	    if (ind.evaluated) return;   //don't evaluate the individual if it's already evaluated
	    if (!(ind instanceof BitVectorIndividual))
	    	state.output.fatal("Whoa!  It's not a BitVectorIndividual!!!",null);

	    BitVectorIndividual ind2 = (BitVectorIndividual)ind;

	    boolean chart = false;
//	    InvestorTester test = new InvestorTesterChartImpl(chart);
	    InvestorTesterNoChartImpl test = new InvestorTesterNoChartImpl();

	    double fitness= test.test(ind2.genome, "train");

//ecj wants it as a float
	    float fitFloat = (float) fitness;
	    
/*	    ((SimpleFitness)ind2.fitness).setFitness(state, fitFloat,
	    	       ///... is the individual ideal?  Indicate here...
	    		(fitFloat == 1));
*/
	    // changed this to avoid abort on fitness = 1.0
	    ((SimpleFitness)ind2.fitness).setFitness(state, fitFloat,
	       ///... is the individual ideal?  Indicate here...
		(false));

	    ind2.evaluated = true;
	}
}
