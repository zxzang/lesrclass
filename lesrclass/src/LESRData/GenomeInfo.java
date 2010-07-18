package LESRData;

public class GenomeInfo {
	private boolean[] genome;
	private double trainFitness;
	private double testFitness;
	
	public GenomeInfo(boolean[] genome, double trainFitness, double testFitness){
		this.genome = genome;
		this.trainFitness = trainFitness;
		this.testFitness = testFitness;
		
	}
	
	public boolean[] getGenome() {
		return genome;
	}
	public void setGenome(boolean[] genome) {
		this.genome = genome;
	}
	public double getTrainFitness() {
		return trainFitness;
	}
	public void setTrainFitness(double trainFitness) {
		this.trainFitness = trainFitness;
	}
	public double getTestFitness() {
		return testFitness;
	}
	public void setTestFitness(double testFitness) {
		this.testFitness = testFitness;
	}

	public String getGenomeAsString(){
		StringBuilder sb = new StringBuilder();
		for(boolean b: genome) {
			if(b == true) sb.append("1");
			else if(b == false) sb.append("0");
			else System.out.println("GenomeInfo: Error converting genome for file output");
		}
		return sb.toString();
		
	}
	public int getGenomeLength(){
		return genome.length;		
	}
}
	

