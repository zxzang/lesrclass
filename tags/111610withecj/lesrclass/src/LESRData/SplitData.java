package LESRData;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import java.math.*;

import javax.swing.JOptionPane;


// reads data from a space-delimited file and outputs a new one with a field for train/test
public class SplitData {
	
	String symbol;
	PriceData dataOut;
	double trainRatio = .75;

	String fullPathName;
	AllocationPoint[] allocation;
	int numTrainPoints;
	
	public SplitData(String nameIn, int sizeIn, String fileName){
		
//		numTrainPoints = (int) (sizeIn * trainRatio);
				
		dataOut = new PriceData(sizeIn);

//		System.out.println(sizeIn + " SD line 26");
		
	
		fullPathName = "./Data/" + fileName; 
		
		allocation = new AllocationPoint[sizeIn];
	
/* array of bools to indicate which set each point is assigned to.  
 * this is inefficient, but it makes it easier to monitor the random 
 * allocation.  This runs in advance, not at training time.
 */
		allocatePoints();
		
		int counter = 0;
		String errorDate = null;
		try{
			Scanner input = new Scanner(new File(fullPathName));
			
//			int trainCount = 0;
//			int testCount = 0;
			String date = null;
			double open;
			double high;
			double low;
			double close;
			int volume;
			double adjClose;
			String trainTest = null;			

			// skip header line in file
			input.nextLine();
			counter++;
	
//			System.out.println(sizeIn);
			while (input.hasNext() && counter < sizeIn ){

				//				System.out.println(counter + " of " + (sizeIn -1) + " SD 91");
						
	// choose which set and call setPoint(int tickNum, String date, double price)
			
				date = input.next();
	
				errorDate = date;
				
				open = input.nextDouble();
				high = input.nextDouble();
				
				low = input.nextDouble();


				close = input.nextDouble();

				if(counter > 12175) System.out.println(counter + " " + date + " SD 80");
				// skip vol
				input.next();
				
				// use vol in 10000s
				volume = input.nextInt();  

				
				adjClose = input.nextDouble();


//				System.out.println(counter + "  split data line 75");

//				if(allocation[counter] == null) System.out.println("no allocation SD 73");


				if(allocation[counter].usedForTraining == true ) {
					trainTest = "train";
//					trainCount +=1;
				}
				else if(allocation[counter].usedForTraining==false){
					trainTest = "test";
	//				testCount +=1;
				}		
				
				dataOut.setPoint(counter, date, open, high, low, close, volume, adjClose, trainTest);

				counter++;
			} // end while
	
		} // end try
		catch (Exception ex){
			System.out.println("error: " + ex.toString() +" date = " + errorDate + " Split Data l 109");

		} // end catch
		
		try{
			dataOut.outputToFile("./Data/SP62-10Split.prn");
			
		}catch(Exception ex) {System.out.println("SD 119" + ex.toString());
		}

	}// end constructor

	public void allocatePoints(){
		int trainCount = 0;
		int testCount = 0;
		double randDouble = 0;
		
		for(int i = 0; i < allocation.length; i++){
			
			randDouble = Math.random();

			allocation[i] = new AllocationPoint();
			
			if(randDouble <= trainRatio){
//					(randDouble <= trainRatio || testCount >= (allocation.length - numTrainPoints -1)) && 
//					(trainCount < (numTrainPoints))
//					){

				allocation[i].setUsedForTraining(true);
				trainCount += 1;
	
			}
			
			else if(testCount < (allocation.length)){
				allocation[i].setUsedForTraining(false);
				testCount += 1;			

			}// end else
			
			else JOptionPane.showMessageDialog(null, "more work on allocation!");

//			if(i > 9000) System.out.println(i + " " + allocation[i].getUsedForTraining() + " SD134");
//			System.out.println(i + " " + randDouble + " " + allocation[i].getUsedForTraining() + " SplitData l 105");

			
		}// end for
//		System.out.println(allocation[allocation.length-1].getUsedForTraining());
	} // end function

	
} // end class

	

