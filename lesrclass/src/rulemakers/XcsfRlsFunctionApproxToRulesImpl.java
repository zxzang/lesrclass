package rulemakers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

import xcsf.Population;

import LESRClass.Rule.RecType;
import LESRClass.RuleSet;
import LESRClass.RuleSetXCSFImpl;
import LESRData.DayInfo;

public class XcsfRlsFunctionApproxToRulesImpl implements FunctionApproxToRules {

	private RuleSet ruleset;
	private File xcsfOutputFile;
	private Population pop;

	public XcsfRlsFunctionApproxToRulesImpl() {
		xcsfOutputFile = chooseFile();
		readSource(xcsfOutputFile);		
		parseRulesFromPopulation();
	}

	public RuleSet getRuleset() {
		// TODO Auto-generated method stub
		return ruleset;
	}

	public void readSource() {
		if(!(xcsfOutputFile== null))
			readSource(xcsfOutputFile);
		else System.out.println("Error: no output file");
	}
	
	
	public void readSource(File xcsfOutputFile){
		
			System.out.println("file received by readSource: " 
					+ xcsfOutputFile.getName() + " " +
					xcsfOutputFile.length() + " bytes");
			Population tempPop = new Population();
			pop = new Population();
			try {
				pop = tempPop.parse(xcsfOutputFile);
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
	
	private File chooseFile() {
		List <File> popFiles = new ArrayList<File>();
		File dir = new File(System.getProperty("user.dir"));
		File inputFile = null;
		for(File file:dir.listFiles()){
			if(file.getName().contains(".population")) popFiles.add(file);
		}
		if(popFiles.size()>1) {
			
			String[] filenames = new String[popFiles.size()];
			for(int i = 0; i < popFiles.size(); i++) 
				filenames[i] = popFiles.get(i).getName();
			
			inputFile = new File((String)JOptionPane.showInputDialog(
					null, "Choose Input File", "Input File Choice", 
					1, null, filenames, 1));
					
			
			System.out.println("chose: " + inputFile.getName());
		}
		else inputFile = popFiles.get(0);
		return inputFile;
	}

	public void parseRulesFromPopulation(double[] inputs, double[] outputs) {
		ruleset = new RuleSetXCSFImpl(pop);
		RecType rec = ruleset.getRecommendation(inputs, outputs);
		System.out.print( "recommendation for ");
		for(int i = 0; i < inputs.length;i++) System.out.print (" " + inputs[i]);
		System.out.print (" is " + rec.toString());	
		
		
	}

	@Override
	public void parseRulesFromPopulation() {
		// TODO Auto-generated method stub
		
	}
}
