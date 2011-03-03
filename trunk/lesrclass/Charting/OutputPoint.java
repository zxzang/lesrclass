package Charting;


// data point used in the output array
public class OutputPoint {
	String date;	

	double closePrice;

	double[] investorWealth;
	
	public void setValues()
	{
		
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
