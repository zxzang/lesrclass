package LESRData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
// info on one security.  Uses data from a PriceData object.


public class Stock {


	String symbol;
	double price;
	double todaysOpen;
	double twoDaysAgoPrice;
	double yesterdaysPrice;

	double todaysHigh;
	double todaysLow;
	String lastTick;
	double maTen;
	double maFifty;
	double maTwoHundred;

	double maTenVolume;
	double maFiftyVolume;
	double maTwoHundredVolume;
		
	double volume;
	double yesterdaysVolume;
	
	Date date;
	int month;
	int year;
	
	String trainTest;
	
	public Date getDate(){
		return date;
		
	}
	
	public Double[] getSmas(){
		Double[] smas = {twoDaysAgoPrice, yesterdaysPrice, price, todaysOpen, todaysHigh, todaysLow, maTen, maFifty, maTwoHundred, yesterdaysVolume,
				volume, maTenVolume, maFiftyVolume, maTwoHundredVolume};
/*		0				1			   2		3			4		5			6		7				8		9		10			
 * twoDaysAgoPrice, yesterdaysPrice, price, todaysOpen todaysHigh, todaysLow, maTen, maFifty, maTwoHundred, volume, yesterdaysVolume, 
				11				12				13							
				maTenVolume, maFiftyVolume, maTwoHundredVolume
 * 
 * */
//		System.out.println("Stock 39.  SMAS: ");
//		for(double d:smas) System.out.print(d + " ");
		
		String[] smaNames = {"twoDaysAgoPrice", "yesterdaysPrice", "price", "todaysOpen", "todaysHigh", "todaysLow",
				"maTen", "maFifty", "maTwoHundred", "yesterdaysVolume", "volume", "maTenVolume", "maFiftyVolume", "maTwoHundredVolume"};


		return smas;
		
	}
	
	
	public static String getSmaName(int a){
		String[] smaNames = {"twoDaysAgoPrice", "yesterdaysPrice", "price", "todaysOpen", "todaysHigh", "todaysLow",
				"maTen", "maFifty", "maTwoHundred", "yesterdaysVolume", "volume", "maTenVolume", "maFiftyVolume", "maTwoHundredVolume"};

		return smaNames[a];
		
		
	}
	
	public Map getSmaMap(){
		Double[] smas = {twoDaysAgoPrice, yesterdaysPrice, price, todaysOpen, todaysHigh, todaysLow, maTen, maFifty, maTwoHundred, yesterdaysVolume,
				volume, maTenVolume, maFiftyVolume, maTwoHundredVolume};
/*		0				1			   2		3			4		5			6		7				8		9		10			
 * twoDaysAgoPrice, yesterdaysPrice, price, todaysOpen todaysHigh, todaysLow, maTen, maFifty, maTwoHundred, volume, yesterdaysVolume, 
				11				12				13							
				maTenVolume, maFiftyVolume, maTwoHundredVolume
 * 
 * */
//		System.out.println("Stock 39.  SMAS: ");
//		for(double d:smas) System.out.print(d + " ");
		
		String[] smaNames = {"twoDaysAgoPrice", "yesterdaysPrice", "price", "todaysOpen", "todaysHigh", "todaysLow",
				"maTen", "maFifty", "maTwoHundred", "yesterdaysVolume", "volume", "maTenVolume", "maFiftyVolume", "maTwoHundredVolume"};


		Map<String, Double> smaMap = new HashMap<String, Double>();
		for(int a = 0; a < smas.length; a++){
			smaMap.put(smaNames[a], smas[a]);
			
		}
		
		return smaMap;
		
	}
	
	public Stock(String symbolIn, double priceIn){
		symbol = symbolIn;
		price = priceIn;
		yesterdaysPrice = 0;
		twoDaysAgoPrice = 0;
		todaysHigh = 0;
		todaysLow = 0;
		
		lastTick = "flat"; 
		maTen = 0;
		maFifty = 0;
		maTwoHundred = 0;
		maTenVolume = 0;
		maFiftyVolume = 0;
		maTwoHundredVolume = 0;
		
		volume = 0;
		yesterdaysVolume = 0;
	}

	
	public double getTodaysHigh() {
		return todaysHigh;
	}

	public void setTodaysHigh(double todaysHigh) {
		this.todaysHigh = todaysHigh;
	}

	public double getTodaysLow() {
		return todaysLow;
	}

	public void setTodaysLow(double todaysLow) {
		this.todaysLow = todaysLow;
	}

	public void setPrice(double priceIn){
		twoDaysAgoPrice = yesterdaysPrice;
		yesterdaysPrice = price;
		if(price == 0 || price == priceIn) lastTick = "flat";
		else {
			if(priceIn > price) lastTick = "up";
			if(priceIn < price) lastTick = "down";
		}
		price = priceIn;				
	}
	
	public double getPrice(){
		return price;
	}

	public void setDate(Date dateIn){
		date = dateIn;
		month = date.getMonth()+1;
		year = date.getYear();
	}
	
	public int getVolume() {
		return (int) volume;
	}

	public void setVolume(int volumeIn) {
		yesterdaysVolume = volume;
		this.volume = volumeIn;
//		System.out.println("set volume to : " + volume + " and yesterday's volume to " + yesterdaysVolume);
		
	}

	public int getYesterdaysVolume() {
		return (int) yesterdaysVolume;
	}

	public void setYesterdaysVolume(int yesterdaysVolumeIn) {
		this.yesterdaysVolume = yesterdaysVolumeIn;
	}

	public int getMonth(){
		return month; 
	}
	
	private void setMaTen(double valueIn){
		maTen = valueIn;		
	}
	
	private void setMaFifty(double valueIn){
		maFifty = valueIn;		
	}
	
	private void setMaTwoHundred(double valueIn){
		maTwoHundred = valueIn;		
	}
	public double getMaTen(){
		return maTen;
	}

	public double getMaFifty(){
		return maFifty;
	}
	
	public double getMaTwoHundred(){
		return maTwoHundred;
	}
	
	double getmaFifty(){
		return maFifty;
	}
	
	double getmaTwoHundred(){
		return maTwoHundred;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public double getTodaysOpen(){
		return todaysOpen;		
	}
	
	public void setTodaysOpen(double open){
		this.todaysOpen = open;		
	}
		
	public String getLastTick(){
		return lastTick;		
	}

	public double getMaTenVolume() {
		return maTenVolume;
	}

	public void setMaTenVolume(double maTenVolume) {
		this.maTenVolume = maTenVolume;
	}

	public double getMaFiftyVolume() {
		return maFiftyVolume;
	}

	public void setMaFiftyVolume(double maFiftyVolume) {
		this.maFiftyVolume = maFiftyVolume;
	}

	public double getMaTwoHundredVolume() {
		return maTwoHundredVolume;
	}

	public void setMaTwoHundredVolume(double maTwoHundredVolume) {
		this.maTwoHundredVolume = maTwoHundredVolume;
	}

	public void setCrossover(PriceData dataSet, int tickNum){

		// these are the values as of yesterday's close
		setMaTen(dataSet.getMaTen(tickNum));
		setMaFifty(dataSet.getMaFifty(tickNum));
		setMaTwoHundred(dataSet.getMaTwoHundred(tickNum));
		
		setMaTenVolume(dataSet.getMaTenVolume(tickNum));
		setMaFiftyVolume(dataSet.getMaFiftyVolume(tickNum));
		setMaTwoHundredVolume(dataSet.getMaTwoHundredVolume(tickNum));
		
	}

	public String getTrainTest(){
		return trainTest;		
	}
	
	public void setTrainTest(String valIn){
		if(valIn == "train" || valIn == "test") trainTest = valIn;
		else JOptionPane.showMessageDialog(null, "error setting trainTest in Stock received value " + valIn);
	}
}
