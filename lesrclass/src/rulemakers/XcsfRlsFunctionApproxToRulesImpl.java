package rulemakers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import javax.swing.JOptionPane;

import LESRClass.RuleSet;
import LESRData.DayInfo;

public class XcsfRlsFunctionApproxToRulesImpl implements FunctionApproxToRules {

	private RuleSet ruleset;
	private File xcsfOutputFile;
	/*	
	public XcsfRlsFunctionApproxToRulesImpl() {
		xcsfOutputFile = chooseFile();

		readSource(xcsfOutputFile);
	
	}

	public XcsfRlsFunctionApproxToRulesImpl(File fileIn) {
		xcsfOutputFile = fileIn;
		readSource(xcsfOutputFile);
	}

	public RuleSet getRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readSource() {
		if(!(xcsfOutputFile== null))
			readSource(xcsfOutputFile);
		else System.out.println("Error: no output file");
	}
	
	public void readSource(File outputFile){
			try{


			Scanner input = new Scanner(new File(outputFile));
					
			theData = new DayInfo[length];
			
			int counter = 0;

//skip header line
			input.nextLine();
//			counter +=1;
			do{
				
				DayInfo today = new DayInfo();
				today.setDate(input.next());
				today.setOpen(input.nextDouble());
				today.setHigh(input.nextDouble());
				today.setLow(input.nextDouble());
				today.setClose(input.nextDouble());

				// use volume in 10000s


				today.setVolume(input.nextInt());
				
				today.setAdjClose(input.nextDouble());
				today.setTrainOrTest(input.next());

			
				double close = today.getClose();
				today.setMaTen(calcMaTen(close, counter));
				
				today.setMaTwoHundred(calcMaTwoHundred(close, counter));
				
				today.setMaFifty(calcMaFifty(close, counter));


				double volume = today.getVolume();
				today.setMaTenVolume(calcMaTenVolume(volume, counter));
				
				today.setMaTwoHundredVolume(calcMaTwoHundredVolume(volume, counter));
				
				today.setMaFiftyVolume(calcMaFiftyVolume(volume, counter));

				
				theData[counter]=today;

//				if(counter > 12176) System.out.println("PD76: getting " + counter + " " + theData[counter].getDate());
				
//				System.out.println("PD71: " + theData[counter].getDate() + " " + theData[counter].getClose());
				
				counter++;
			
					
			}while (input.hasNext());
//		System.out.println("PD81: done");
		} // end try

		
		catch (Exception ex){
			System.out.println("error: " + ex.toString());
		} // end catch
	}// end function

		
		
		
		
		
		
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
			inputFile = new File( (String) JOptionPane.showInputDialog(
					null, "Choose Input File", "Input File Choice", 
					1, null, filenames, 1));
					
			
			System.out.println("chose: " + inputFile.getName());
		}
		else inputFile = popFiles.get(0);
		return inputFile;
	}
*/
	@Override
	public RuleSet getRules() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void readSource() {
		// TODO Auto-generated method stub
		
	}
}
