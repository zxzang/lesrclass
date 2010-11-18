package LESRData;

public class AllocationPoint {
	
	public boolean getUsedForTraining() {
		return usedForTraining;
	}
	public void setUsedForTraining(boolean usedForTraining) {
		this.usedForTraining = usedForTraining;
	}
	public int getPointInSet() {
		return pointInSet;
	}
	public void setPointInSet(int pointInSet) {
		this.pointInSet = pointInSet;
	}
	
	boolean usedForTraining;
	int pointInSet;  // click number in whichever data set (training or test) this point is assigned to
	
}
