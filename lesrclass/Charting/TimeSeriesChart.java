package Charting;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

public class TimeSeriesChart {

	TimeSeriesCollection xyDataset;
	String outFileName;
	
	public TimeSeriesChart(String nameIn){
		xyDataset = new TimeSeriesCollection();
		outFileName = nameIn;
	}

	public TimeSeries createSeries(String nameIn){
		TimeSeries currentSeries = new TimeSeries(nameIn, Day.class);
		return currentSeries;		
	}	
	
	public void addPoint(TimeSeries series, Date date, double wealthIn){
//		double logWealth = Math.log10(wealthIn);
		double logWealth = wealthIn;
		TimeSeriesDataItem dataItem = new TimeSeriesDataItem(new Day(date), logWealth);
	
		series.add(dataItem);
	}
	
	
	public void addSeries(TimeSeries series){
		xyDataset.addSeries(series);		
	}
		
				
		public void printChart(String stockName){
		String Title = "Results for Investment in " + stockName;
//		JFreeChart xyChart = ChartFactory.createXYLineChart(
//				Title, "Trading Day", "Wealth (log 10 scale, start = 0)", xyDataset, PlotOrientation.VERTICAL,
//				true, false, false);	
	
		JFreeChart xyChart = ChartFactory.createTimeSeriesChart(
				Title, " ", "Wealth (log 10 scale, start = 0)", xyDataset,
				true, false, false);	

		LogAxis va = new LogAxis();
		va.setAutoRange(true);
		va.setAutoTickUnitSelection(true);
		DecimalFormat logFormatter = new DecimalFormat();
		logFormatter.applyPattern("#0");
		va.setNumberFormatOverride(logFormatter); 
		NumberTickUnit ntu = new NumberTickUnit(1);
		va.setTickUnit(ntu);
		va.setLabel("Multiples of Starting Investment");
		xyChart.getXYPlot().setRangeAxis(va);
		
		TickUnits source = new TickUnits();
		DateTickUnit ti = new DateTickUnit(DateTickUnitType.YEAR, 3);
		source.add(ti);
		DateAxis d = new DateAxis();
		d.setTickUnit(ti);
		DateFormat formatter = new SimpleDateFormat("yyyy");
		d.setDateFormatOverride(formatter);
		d.setMinimumDate(new Date("1/1/1962"));
		d.setMaximumDate(new Date("1/1/2011"));
		
		
		xyChart.getXYPlot().setDomainAxis(d);

		File outFile = new File(outFileName);
			
		try{
			ChartUtilities.saveChartAsJPEG(outFile, xyChart, 1280, 790);
		}


		catch(Exception ex) {}
		try{
			ChartUtilities.saveChartAsPNG(new File ("c:\\droolsdata\\outChart.png"), xyChart, 700, 500);
		}

		catch(Exception ex) {}

		ChartViewer view = new ChartViewer(xyChart);
		

	
	} // end function
	
	
}// end class
	
