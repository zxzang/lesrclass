package LESRData;

// keep track of which time tick we are using during transactions
public class TimeTick {
	int tickNum;
	
	public TimeTick(){
		
	}
	
	public void setTickNum(int tickNum) {
		this.tickNum = tickNum;
	}

	public TimeTick(int tickIn){
		tickNum = tickIn;		
	}
	public int getTickNum(){
		return tickNum;		
	}
	
}
