package Charting;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Date;

public class ResultsChart {

	XYSeriesCollection xyDataset;
	String outFileName;

	public ResultsChart(String nameIn) {
		xyDataset = new XYSeriesCollection();
		outFileName = nameIn;
	}

	public XYSeries createSeries(String nameIn) {
		XYSeries currentSeries = new XYSeries(nameIn);
		return currentSeries;
	}

	public XYSeries getSeries(String nameIn) {
		return xyDataset.getSeries(nameIn);
	}

	public void addPoint(XYSeries series, int click, double wealthIn) {
		double logWealth = Math.log10(wealthIn);
		series.add(click, logWealth);
	}

	public void addSeries(XYSeries series) {
		xyDataset.addSeries(series);
	}

	public void printChart(String stockName) {
		String Title = "Results for Investment in " + stockName;
		JFreeChart xyChart = ChartFactory.createXYLineChart(Title,
				"Trading Day", "Wealth (log 10 scale, start = 0)", xyDataset,
				PlotOrientation.VERTICAL, true, false, false);
	
	
		// display

		File outFile = new File(outFileName);

		try {
			ChartUtilities.saveChartAsJPEG(outFile, xyChart, 1000, 700);
		}

		catch (Exception ex) {
		}
		try {
			ChartUtilities.saveChartAsPNG(new File(
					"c:\\droolsdata\\outChart.png"), xyChart, 700, 500);
		}

		catch (Exception ex) {
		}

		ChartViewer view = new ChartViewer(outFileName);

	} // end function

}// end class

