package LESRData;

import org.jfree.data.xy.XYSeries;

import Charting.ResultsChart;

// investor class.  The other investor classes are derived from this one.
import javax.swing.JOptionPane;
public class Investor {
	double cash;
	public void setCash(double cash) {
		this.cash = cash;
	}

	int shares;
	String name, rulesFile;
	XYSeries invSeries;
	
	public Investor(){}
	public Investor(String nameIn, double cashIn, String rulesFileIn, ResultsChart chartIn){
		// start with specified cash stake, no shares
		name = nameIn;
		cash = cashIn;			
		rulesFile = rulesFileIn;
		shares = 0;
		
//		System.out.println(nameIn + " " + cashIn + " " + rulesFileIn + " " + chartIn);
		invSeries = chartIn.createSeries(name);
	
	}
	
		
	public void buy(int incrementIn, double StockPrice){
		// if investor has enough cash for the specified purchase
		if(cash >= (StockPrice * incrementIn)){
			shares += incrementIn;
			double totalPrice = incrementIn * StockPrice; 
			cash -= totalPrice;
			System.out.println(name + " bought " + incrementIn + " share(s) " 
				+ " at " + StockPrice +" total price: " + totalPrice );
		
		}
		// cancel transaction if there is not enough cash
		else JOptionPane.showMessageDialog(null, name + " does not have enough cash to buy " + incrementIn + " shares!");
	}
	
	public void buyHundred(double price){
		// buy least of 100 shares, $100,000 worth, or the max amount investor can buy with all available cash
		
	 	int sharesToBuy = (int) (100000/price);
 		if(sharesToBuy > 100) sharesToBuy = 100; 	

		double totalPrice = sharesToBuy * price; 

		if(totalPrice > cash){
			sharesToBuy = (int) (cash / price);
			totalPrice = sharesToBuy * price;			
		}
		
		buy(sharesToBuy, price);
	}
		
	public void buyAll(double price){
		// buy shares with all available cash
		
		int numToBuy = (int) (cash / price);
	
		buy(numToBuy, price);
		}
	
	
	public void sell(int decrementIn, double price){
		// no short selling in this version.  Investor can only sell as many shares as s/he owns.

		if( shares < decrementIn) JOptionPane.showMessageDialog(null,"ERROR: " + name + " trying to sell " + decrementIn + 
				", only has " + shares + " available");
		
		
		if((shares > 0) && (shares >= decrementIn)){
			shares -= decrementIn;
			cash += decrementIn * price;
			System.out.println(name + " sold " + decrementIn + " share(s) at " + price);
			}
		
		if (decrementIn < 0) 
			JOptionPane.showMessageDialog(null,"ERROR: " + name + " trying to sell negative number of shares");
		

	}
	public void sellAll(double price){
		// Sell all shares
//		JOptionPane.showMessageDialog(null, name + " sell "  +shares + " at " + price);
		if(shares > 0) sell(shares, price);
	}

	public void shortMax(double price){
		// go short as many shares as can be covered by current cash
		int shortSharesToSell = (int) (cash / price);
		if(shares >= 0)	sellShort(shortSharesToSell, price);
	
	}	

	
	public void sellShort(int sharesToSellShort, double price){
		if(sharesToSellShort > 0){
			shares -= sharesToSellShort;
			cash += sharesToSellShort * price;
			System.out.println(name + " sold short " + sharesToSellShort + " share(s) at " + price);
		}
//		else JOptionPane.showMessageDialog(null,"ERROR: can't sell short a negative number of shares!");
		
	}
		
	public void sellHundred(double price){
		// sell least of 100 shares, $100,000 worth, or all shares
		
	 	int sharesToSell = (int) (100000/price);
 		if(sharesToSell > 100) sharesToSell = 100; 	
 		if(sharesToSell > shares) sharesToSell = shares;

 		sell(sharesToSell, price);			
	}
	
			
	public double getWealth(double price){
		double wealth = cash + (shares * price);
		return wealth;		
	}
	
	public void printStatement(double price){
	 	System.out.printf ("%10s has: %5d shares @ $ %7.2f value" +
	 			" $ %11.2f; $ %11.2f Cash; Total: $ %12.2f\n", name, getShares(), price, 
	 			(getShares() * price),	getCash(), getWealth(price));
	 	
//	 	if(getWealth(price) < 0.01) {
//	 		String bankruptcyNotice = name + " is insolvent!";
//	 		JOptionPane.showMessageDialog(null, bankruptcyNotice);
//	 	}
//	 	System.out.println(outString);
	
	}
	public void payInterest(double rate){
		double dailyrate = rate / 250;
//		JOptionPane.showMessageDialog(null, name + " has " + cash + "in cash. Earning"
//				+ dailyrate + "today's interest = " + cash*dailyrate);
		cash += (cash * dailyrate);		
		
	}
	
	public void payDividend(double rate, double price){
		double dailyrate = rate / 250;
		double dividend = 0;
		if(shares > 0) dividend = dailyrate * (shares * price); 
		cash += dividend;			
		
	}

	String getRulesFile(){
		
		return rulesFile;		
	}
	
	public XYSeries getSeries(){
		return invSeries;		
	}
	
	
	public String getName(){
		return name;
		
	}
	public double getCash(){
		return cash;			
	}
	
	public int getShares(){
		return shares;		
	}
	
	public void setShares(int sharesIn){
		shares = sharesIn;		
	}
	
	public double getTotalValue(double priceIn){
		double value = cash + (shares*priceIn);
		return value;				
	}
	

}
