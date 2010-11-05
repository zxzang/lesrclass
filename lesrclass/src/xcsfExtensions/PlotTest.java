package xcsfExtensions;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import xcsf.MatchSet;
import xcsf.Population;
import xcsf.StateDescriptor;
import xcsf.XCSFListener;
import xcsf.XCSFUtils.Gnuplot;
import xcsf.listener.ConditionsGUI2D3D;

/**
 * Implements a time-consuming listener to visualize the current prediction of
 * xcsf. gnuplot is used to plot the 3D graph.
 * 
 * @see xcsf.XCSFUtils.Gnuplot
 * @author Patrick O. Stalph, Martin V. Butz
 */
public class PlotTest implements XCSFListener {

    // 21 tics, interval [0:1] - if computed via for-loop: precision problems!
    private final static double[] TICS = { 0, .05, .1, .15, .2, .25, .3, .35,
            .4, .45, .5, .55, .6, .65, .7, .75, .8, .85, .9, .95, 1 };

    // initial gnuplot commands
    final static String[] GNUPLOT_CMD = { "set grid", //
            "set xrange[0:1]", //
            "set yrange[0:1]", //
            "set xlabel 'x'", //
            "set ylabel 'y'", //
            "set zlabel 'f(x,y)'", //
            "set style data lines", //
            "set contour", //
            "set surface", //
            "set hidden3d", //
            "set dgrid3d " + TICS.length + "," + TICS.length, //
    };

    private static MatchSet ms = new MatchSet(false);
    private static double[][][] samples = new double[TICS.length][TICS.length][3];

    private Gnuplot console;
    private String tmpFilename;

    /**
     * Default constructor.
     * 
     * @throws IOException
     *             if the system fails to execute gnuplot
     */
    public PlotTest() throws IOException {
        this.console = new Gnuplot();
        for (String cmd : GNUPLOT_CMD) {
            this.console.execute(cmd);
        }
        File tmpfile = File.createTempFile(this.getClass().getSimpleName(),
                "gnuplot");
        tmpfile.deleteOnExit();
        tmpFilename = tmpfile.getAbsolutePath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see xcsf.XCSFListener#nextExperiment(int, java.lang.String)
     */
    public void nextExperiment(int experiment, String functionName) {
        // ignore
    }

    /*
     * (non-Javadoc)
     * 
     * @see xcsf.listener.XCSFListener#stateChanged(int, java.util.Vector,
     * java.util.Vector, xcsf.StateDescriptor)
     */
    public void stateChanged(int iteration, Population population,
            MatchSet matchSet, StateDescriptor state, double[][] performance) {
        if (iteration % ConditionsGUI2D3D.visualizationSteps != 0
                || state.getConditionInput().length != 2) {
            return;
        }
        // create 2D samples and plot
        createIsoamples(population, tmpFilename);
        this.console.execute("set title 'iteration " + iteration + "'");
        this.console.execute("splot '" + tmpFilename + "' title 'prediction'");
    }

    /**
     * Calculates isosamples for use with gnuplot.
     * 
     * @param population
     *            the population
     * @param filename
     *            the filename to write the samples to
     */
    static void createIsoamples(Population population, String filename) {
        // calculate samples by matching all 441 states...
        int n = TICS.length;
        double[] output = new double[1]; // dummy output
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                double[] input = { TICS[x], TICS[y] };
                StateDescriptor state = new StateDescriptor(input, output);
                ms.match(state, population);
                if (ms.size() == 0) {
                    ms.setNumClosestMatching(true);
                    ms.match(state, population);
                    ms.setNumClosestMatching(false);
                }
                samples[x][y][0] = input[0];
                samples[x][y][1] = input[1];
                samples[x][y][2] = ms.getWeightedPrediction()[0];
            }
        }
        // write to file
        try {
            PrintStream ps = new PrintStream(filename);
            for (double[][] element : samples) {
                for (int y = 0; y < samples[0].length; y++) {
                    ps.println(element[y][0] + " " + element[y][1] + " "
                            + element[y][2]);
                }
            }
            ps.flush();
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {
   //     this.console.close();
//        super.finalize();
    }
}
