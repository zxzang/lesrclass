package LESRData;
import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;


import java.math.*;
// holds an array of DayInfo and functions to read data from a space-delimited file
// and to calculate and set some DayInfo attributes

public class PriceData {
	
	String symbol;
	DayInfo[] theData;
	Double yesterdaysClose;
	Double[] lastTen;   // used to calc 10 day MA
	Double[] lastTwoHundred;  // used to calc 200 day MA
	Double[] lastFifty;
	Double[] lastTick;
	
	Double[] lastTenVolume;
	Double[] lastFiftyVolume;
	Double[] lastTwoHundredVolume;
	
	
	Date date;
	String fullPathName;
	int length;

	public PriceData(String nameIn, String fileName){
		// set up a PriceData object and read data in from a file.  No apportionment between train and test data.
			
			lastTen = new Double[10];
			lastTwoHundred = new Double[200];
			lastFifty = new Double[50];
	
			lastTenVolume = new Double[10];
			lastTwoHundredVolume = new Double[200];
			lastFiftyVolume = new Double[50];
			
			yesterdaysClose = 0.00;
			fullPathName = "./Data/" + fileName; 
			
			try{

				length = getNumPoints(fullPathName);

				Scanner input = new Scanner(new File(fullPathName));
						
				theData = new DayInfo[length];
				
				int counter = 0;

// skip header line
				input.nextLine();
//				counter +=1;
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
	
//					if(counter > 12176) System.out.println("PD76: getting " + counter + " " + theData[counter].getDate());
					
//					System.out.println("PD71: " + theData[counter].getDate() + " " + theData[counter].getClose());
					
					counter++;
				
						
				}while (input.hasNext());
//			System.out.println("PD81: done");
			} // end try
	
			
			catch (Exception ex){
				System.out.println("error: " + ex.toString());
			} // end catch
		}// end constructor

		public void setPoint(int tickNum, String date, double open, double high, double low, double close, int volume,
			double adjClose, String trainTest){
		// for use with the two-parameter constructor, when setting up a split data set 
			DayInfo today = new DayInfo();
			today.setDate(date);
			today.setOpen(open);
			today.setHigh(high);
			today.setLow(low);
			today.setClose(close);
			today.setVolume(volume);
			today.setAdjClose(adjClose);

			
//			today.setLastClose(lastClose);
			today.setMaTen(calcMaTen(close, tickNum));
					
			today.setMaTwoHundred(calcMaTwoHundred(close, tickNum));
				
			today.setMaFifty(calcMaFifty(close, tickNum));
			today.setTrainOrTest(trainTest);
			theData[tickNum]=today;
			
	}
		
	public Date getDate(){
		return date;	
	}
	
	public double getTodaysOpen(int tick){
		return theData[tick].getOpen();
	}
	public double calcMaTen(double price, int counter){
		counter %= 10;
		lastTen[counter] = price;
		
		return(calcMean(lastTen));
	}

	public double calcMaTwoHundred(double price, int counter){
		counter %= 200;
		lastTwoHundred[counter] = price;
		
		return(calcMean(lastTwoHundred));		
	}	
		
	public String getTrainTest(int tick){
		return theData[tick].getTrainOrTest();
	}
	
	
	public double getMaFifty(int tick){
		return theData[tick].getMaFifty();		
	}
	
	public double calcMaFifty(double price, int counter){
		counter %= 50;
		lastFifty[counter] = price;
		
		return(calcMean(lastFifty));		
	}	
	
	
	public double calcMaFiftyVolume(double volume, int counter){
		counter %= 50;
		lastFiftyVolume[counter] = volume;
		
		return(calcMean(lastFiftyVolume));		
	}	

	public double calcMaTenVolume(double volume, int counter){
		counter %= 10;
		lastTenVolume[counter] = volume;
		
		return(calcMean(lastTenVolume));		
	}	

	public double calcMaTwoHundredVolume(double volume, int counter){
		counter %= 200;
		lastTwoHundredVolume[counter] = volume;
		
		return(calcMean(lastTwoHundredVolume));		
	}	

	
	public double getMaTen(int tick){
		return theData[tick].getMaTen();		
	}
	
	public double getMaTwoHundred(int tick){
		return theData[tick].getMaTwoHundred();		
	}
	
	public double calcMean(Double[] arrayIn){
		double total = 0;
		
		for(int counter = 0; counter < arrayIn.length; counter++){
			Double value = arrayIn[counter];
			if(value != null) total += arrayIn[counter].doubleValue();
		}
		
		double mean = total/arrayIn.length;
		
		return mean;
		
	}
	
	public double getAdjClose(int tick){
		if(tick >= theData.length) JOptionPane.showMessageDialog(null, "tick too high: " + tick);
		return theData[tick].getClose();		
	}
	
	public double getHigh(int tick){
		return theData[tick].getHigh();		
	}

	public double getLow(int tick){
		return theData[tick].getLow();		
	}

	
		
	public double getClose(int tick){
//		System.out.println("setting tick " + tick + " " +theData[tick].getDate() + " " + theData[tick].getPrice());

		
//		System.out.println("PD 176: " + theData[tick].getDate());
		
		return theData[tick].getClose();		
	}
	
	public String getDate(int tick){
		return theData[tick].getDate();
	}
	
	public Date getCDate(int tick){
		return theData[tick].getdDate();
	}
	
	public Double getTickTenMA(int ticknum){
		return theData[ticknum].getMaTen();
	}	
	
	public Double getTickFiftyMA(int ticknum){
		return theData[ticknum].getMaFifty();
	}	
	
	public Double getTickTwoHundredMA(int ticknum){
		return theData[ticknum].getMaTwoHundred();
	}
	
	public int getLength(){
		return this.length;
	}
	
	public int getVolume(int ticknum){
		return theData[ticknum].getVolume();
	}	

	public int getYesterdaysVolume(int ticknum){
		return theData[ticknum].getYesterdaysVolume();
	}	
	
	public double getYesterdaysClose(int ticknum){
		return yesterdaysClose;
	}	

	
	public void outputToFile(String filename){
//  saves a data file with info on the training/testing split used 
	
		PrintWriter output;


		try{
			output = new PrintWriter(new File(filename));

			System.out.println("outputting from a " + theData.length + " array. " + "PD 205");


			try{

				output.print("Date" + " ");
			
				output.print("Open" + " ");
				output.print("High" + " " );
				output.print("Low" + " ");
				output.print("Close" + " ");
				output.print("Volume" + " ");
				output.print("AdjClose" + " ");
				output.print("TrainTest" + " ");
				output.print("\n");
				
				
			} catch(Exception ex){JOptionPane.showMessageDialog(null, ex + " counter 0.  PD227");}

			for(int counter = 1; counter < theData.length; counter++){
		
				output.print(theData[counter].getDate() + " " +  
					theData[counter].getOpen() + " " + 
					theData[counter].getHigh() + " " + 
					theData[counter].getLow() + " " +
					theData[counter].getClose() + " " +
					theData[counter].getVolume() + " " + 
					theData[counter].getAdjClose() + " " +
					theData[counter].getTrainOrTest() + "\n");
				if(counter > 12175) { 
					System.out.println(theData[counter].getDate() + " " +  
					theData[counter].getOpen() + " " + 
					theData[counter].getHigh() + " " + 
					theData[counter].getLow() + " " +
					theData[counter].getClose() + " " +
					theData[counter].getVolume() + " " + 
					theData[counter].getAdjClose() + " " +
					theData[counter].getTrainOrTest() + "\n");
				}
				
			} // end for
			output.close();
		} // end try
		
		catch (Exception ex){
			System.out.println("error: " + ex.toString());
		} // end catch
	}// end function


	int getNumPoints(String fullPathName){
		int length = 0;
		try{
			Scanner input = new Scanner(new File(fullPathName));

			while (input.hasNextLine()){
				length += 1;
				input.nextLine();
			} // end while

			input.close();
			}
			catch(Exception ex){}
		
		return length;
	}
	
	public PriceData(int sizeIn){
		theData = new DayInfo[sizeIn];
		lastTen = new Double[10];
		lastTwoHundred = new Double[200];
		lastFifty = new Double[50];
		length = sizeIn;
		yesterdaysClose = 0.00;

	
}// end constructor

	public double getMaTenVolume(int tick){
		return theData[tick].getMaTenVolume();		
	}
	
	public double getMaFiftyVolume(int tick){
		return theData[tick].getMaFiftyVolume();		
	}

	public double getMaTwoHundredVolume(int tick){
		return theData[tick].getMaTwoHundredVolume();		
	}
	

	
	
	/*
	public PriceData(String nameIn, int sizeIn, String fileName){
	// set up a PriceData object and read data in from a file.  No apportionment between train and test data.
		
		theData = new DayInfo[sizeIn];
		lastTen = new Double[10];
		lastTwoHundred = new Double[200];
		lastFifty = new Double[50];
		yesterdaysClose = 0.00;

		fullPathName = "c:\\droolsdata\\" + fileName; 
		length = sizeIn;
		
		try{
			Scanner input = new Scanner(new File(fullPathName));
	
			int counter = 0;
			
			while (input.hasNext() && counter < sizeIn){
				
				DayInfo today = new DayInfo();
				today.setDate(input.next());
								
				today.setOpen(input.nextDouble());
				today.setHigh(input.nextDouble());
				today.setLow(input.nextDouble());
				today.setClose(input.nextDouble());
				today.setVolume(input.nextLong());
				today.setAdjClose(input.nextDouble());

				today.setMaTen(calcMaTen(today.getAdjClose(), counter));
				
				today.setMaTwoHundred(calcMaTwoHundred(today.getAdjClose(), counter));
				
				today.setMaFifty(calcMaFifty(today.getAdjClose(), counter));
				
				theData[counter]=today;
				length++;
				counter++;
			} // end while
		} // end try
		catch (Exception ex){
			System.out.println("error: " + ex.toString());
		} // end catch
	}// end constructor
*/

	
} // end class

