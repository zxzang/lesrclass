package Test;

import inputGenerators.InputGenerator5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import xcsf.Function;
import xcsf.XCSF;
import xcsf.Population;

import xcsf.XCSFConstants;
import xcsf.examples.Linear;
import xcsf.examples.Sine;
import xcsf.listener.ConditionsGUI2D3D;


import xcsf.listener.OutputWriter2D;
import xcsf.listener.PerformanceGUI;
import xcsf.listener.PopulationWriter;
import xcsf.listener.PredictionPlot;
import xcsfExtensions.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;


public class JavaXCSFFunctionTest {

	@Before
	public void setUp() throws Exception {
		xcsf.XCSFUtils.Gnuplot.executable = "c:/gnuplot/gnuplot/binary/pgnuplot.exe";
	}
	
	@Ignore
	@Test
	public void testLinear() {
//		Function f = new Sine(1, 4, 0, 2);
		
		Function f = new Linear(-15.0, 1.0, 0.03, 2);
		XCSFConstants.load("xcsf.ini");
		XCSF xcsf = new XCSF(f);

//		
//		xcsf.addListener(new OutputWriter2D("",false,0,1,1));
//		xcsf.addListener(new PopulationWriter(null));
		try {
			xcsf.addListener(new PlotTest());
//			xcsf.addListener(new PredictionPlot());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		xcsf.runExperiments();
		
	}
	
	@Ignore
	@Test
	public void testSine() {
		Function f = new Sine(1, 4, 0, 2);
		
		XCSFConstants.load("xcsf.ini");
		XCSF xcsf = new XCSF(f);
//	xcsf.addListener(new ConditionsGUI2D3D());
		xcsf.addListener(new OutputWriter2D("",false,0,1,1));
//		xcsf.addListener(new PopulationWriter(null));
		try {
			xcsf.addListener(new PlotTest());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
		xcsf.runExperiments();
	}

	
	@Test
	public void testPriceFunction() {
		String fileName = "sp62-10SplitI.prn";
		PriceFunction f = new PriceFunction(1, 0, 0, 4, fileName);
		
		XCSFConstants.load("xcsf.ini");
		XCSF xcsf = new XCSF(f);
		f.setInputGenerator(new InputGenerator5());
//		xcsf.addListener(new PerformanceGUI(true));
//		xcsf.addListener(new ConditionsGUI2D3D());
//		xcsf.addListener(new OutputWriter2D("",false,0,1,1));
		
		xcsf.addListener(new PopulationWriter(null));
/*		try {
			xcsf.addListener(new PlotTest());
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		*/
		xcsf.runExperiments();
		
	}
	
	@Ignore
	@Test 
	public void testPopWriterParse(){
		Population pop = new Population();

		try {
			pop.parse(new File("PriceFunction-exp00-it50k.population"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			
	}
	

}

