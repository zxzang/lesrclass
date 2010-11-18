package LESRData;
import java.util.Date;

import javax.swing.JOptionPane;
import java.math.*;
// info on one day tick
public class DayInfo {
	String date;	
	Date dDate;
	double open;
	double high;
	double low;
	double close;
	
	int yesterdaysVolume;
	int volume;
	double adjClose;
	double lastClose;

	double maTen;
	double maTwoHundred;
	double maFifty;

	double maTenVolume;
	double maTwoHundredVolume;
	double maFiftyVolume;

	
	int month;
	int year;
	
	String trainOrTest;  
	
	public void setTrainOrTest(String valIn){
		if(valIn.equalsIgnoreCase("train")) trainOrTest = "train";
		else if(valIn.equalsIgnoreCase("test")) trainOrTest = "test";
		else JOptionPane.showMessageDialog(null, "DayInfo received invalid setting for TrainOrTest received " + valIn);
	}
	
	public String getTrainOrTest(){return trainOrTest;}
	
	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	public void setdDate(Date dDate) {
		this.dDate = dDate;
	}

	public void setDate(String dateIn){
		date=dateIn;		
		dDate = new Date(date);
		month = dDate.getMonth()+1;
		year = dDate.getYear();
		
//		System.out.println(date + " " + month);
	}

	public int getMonth(){
		return month;		
	}
	
	public Date getdDate(){
		return dDate;
	}
	
	public int getTenVsTwoHundred(){
		int returnVal = 0;
		if(maTen < maTwoHundred) returnVal = -1;
		if(maTen == maTwoHundred) returnVal = 0;
		if(maTen > maTwoHundred) returnVal = 1;	
		
		return returnVal;
	}
	
	public int getTenVsFifty(){
		int returnVal = 0;
		if(maTen < maFifty) returnVal = -1;
		if(maTen == maFifty) returnVal = 0;
		if(maTen > maFifty) returnVal = 1;	
		
		return returnVal;
	}
	
	public int getFiftyVsTwoHundred(){
		int returnVal = 0;
		if(maFifty < maTwoHundred) returnVal = -1;
		
		if(maFifty > maTwoHundred) returnVal = 1;	
		
		return returnVal;
	}
	
	public String getDate(){
		return date;		
	}
	
	public void setMaFifty(double maFiftyIn){
		maFifty = maFiftyIn;		
		}
	
	public double getMaFifty(){
		return maFifty;		
		}

	
	public void setMaTen(double maTenIn){
		maTen = maTenIn;		
		}
	
	public double getMaTen(){
		return maTen;		
		}
	
	
	public void setMaTwoHundred(double maTwoHundredIn){
		maTwoHundred = maTwoHundredIn;		
		}
		
	public double getMaTwoHundred(){
		return maTwoHundred;		
		}

	public int getYesterdaysVolume(){
		return yesterdaysVolume;
		
	}

	public void setMaTenVolume(double maTenVolumeIn) {
		maTenVolume = maTenVolumeIn;
		}
	
	public void setMaFiftyVolume(double maFiftyVolumeIn) {
		maFiftyVolume = maFiftyVolumeIn;
		}

	public void setMaTwoHundredVolume(double maTwoHundredVolumeIn) {
		maTwoHundredVolume = maTwoHundredVolumeIn;
		}

	public double getLastClose() {
		return lastClose;
	}

	public double getMaTenVolume() {
		return maTenVolume;
	}

	public double getMaTwoHundredVolume() {
		return maTwoHundredVolume;
	}

	public double getMaFiftyVolume() {
		return maFiftyVolume;
	}

	public int getYear() {
		return year;
	}

	
}
