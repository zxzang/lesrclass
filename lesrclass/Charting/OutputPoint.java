package Charting;

import LESRData.Investor;

// data point used in the output array
public class OutputPoint {
	String date;	

	double closePrice;

	double[] investorWealth;
	
	public void setValues(String dateIn, double closePriceIn, Investor[] investors)
	{
		date = dateIn;
		closePrice = closePriceIn;
		investorWealth = new double[investors.length];
		for(int counter = 0; counter < investors.length; counter++){	
			investorWealth[counter] = investors[counter].getWealth(closePrice);
					
		}
	}
	
	public double getPrice(){
		return closePrice;
	}
	
	public String getDate(){
		return date;		
	}
	
	public String getValues(){
		
		String outString = date + " " + String.valueOf(closePrice);
		for(double inv: investorWealth){
			outString += " ";
			outString += String.valueOf(inv);			
		}
		return outString;
	}
}
