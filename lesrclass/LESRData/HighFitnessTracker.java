package LESRData;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class HighFitnessTracker {
	File outFile;
	
	// not using the list yet, just appending to a file
	private List<GenomeInfo> highFitnessGenomes;


	public HighFitnessTracker(boolean[] genome, double trainFitness, double testFitness){
		String fileName = "c:\\users\\john\\documents\\CS499\\output\\HighFitness.dat";
		getFile(fileName);
		GenomeInfo gi = new GenomeInfo(genome, trainFitness, testFitness);
		//highFitnessGenomes.add(gi);
		appendToFile(outFile, gi);
		
	}
	
	public void appendToFile(File outFile, GenomeInfo gi){
		PrintWriter output;
		try {
		   output = new PrintWriter(new FileWriter(outFile, true));

		   output.write("\n" +"Genome Length: " + gi.getGenomeLength());
		   output.write("\n " + gi.getGenomeAsString());
		   output.write("\n" + "Fitness on training set: " + gi.getTrainFitness());
		   output.write("\n" + "Fitness on test set: " + gi.getTestFitness());
		   output.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void getFile(String fileName){
		File file = new File(fileName);
	    if(!(file.exists()))
	    	try{
	    		file.createNewFile();
	    		}
	    catch(Exception ex) {System.out.println(ex.toString());}
	    outFile = file;
	}
	
	public List<GenomeInfo> getList(){
		return highFitnessGenomes;
	}

	public void add(GenomeInfo gi){
		highFitnessGenomes.add(gi);
	}
	
}
