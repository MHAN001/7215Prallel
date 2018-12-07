public class Messages {
	
	private int actualCount;
	private int estimatedCount;
	private double accuracy;
	private int index;
	
	public Messages(int actualCount, int estimatedCount, double accuracy, int fileName){
		this.actualCount = actualCount;
		this.estimatedCount = estimatedCount;
		this.accuracy = accuracy;
		this.index = fileName;
	}

	@Override
	public String toString(){
		return "File No." + index + "."+ "Actual count: " + actualCount + " "
				+ "Estimated count: " + estimatedCount + " "
				+ "Accuracy: " + accuracy;
	}

}