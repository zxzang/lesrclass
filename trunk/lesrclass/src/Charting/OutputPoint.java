package Charting;

import LESRData.Investor;

// data point used in the output array
public class OutputPoint {
	String date;	

	Double closePrice;

	Double[] investorWealth;
	
	public void setValues(String dateIn, double closePriceIn, Investor[] investors)
	{
		date = dateIn;
		closePrice = closePriceIn;
		investorWealth = new Double[investors.length];
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
		
		String outString = date + " " + closePrice.toString();
		for(Double inv: investorWealth){
			outString += " ";
			outString += inv.toString();			
		}
		return outString;
	}
}
