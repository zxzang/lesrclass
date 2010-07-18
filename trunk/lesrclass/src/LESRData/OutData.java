package LESRData;
// sets up an array of OutputPoints and provides methods to populate it and 
// to save it to a spreadsheet-readable file.  
import java.io.File;
import java.io.PrintWriter;

import LESRData.Investor;

import Charting.OutputPoint;

public class OutData {

	OutputPoint[] theData;
	
	public OutData(int sizeIn){		
		theData = new OutputPoint[sizeIn];
	}
	
	
	public void setPointValue(int pointNum, String date, double closePriceIn, Investor[] investors){
			
		theData[pointNum] = new OutputPoint();
		theData[pointNum].setValues(date, closePriceIn, investors);
	}
		
	public void outputToFile(String outFileName, Investor[] investors){
		String header = "Date " + "Close ";
		
		for(int counter = 0; counter < investors.length; counter++){
			header += " ";
			header += investors[counter].getName();
			
		}
		PrintWriter output;
		try{
			output = new PrintWriter(new File(outFileName));
		
			output.println(header);
		
			for(int counter = 0; counter < theData.length; counter++){
				output.println(theData[counter].getValues());		
			} // end for
			output.close();
		} // end try
		
		catch (Exception ex){
			System.out.println("error: " + ex.toString());
		} // end catch
	}// end function
	
} // end class
