package entity;

import java.util.List;
import java.util.ArrayList;

public class Accumulator {
	public List<Vehicle> activeVs;
	
	private double throughput;
	private double avgWait;
	
	// only used in average calculation
	private int totalWait;
	
	
	public Accumulator() {
		activeVs = new ArrayList<Vehicle>();
	}
	
	public void addVs(List<Vehicle> newVs) {
		activeVs.addAll(newVs);
	}
	
	public void removeVs(Vehicle v) throws Exception {
		if (activeVs.contains(v)) {
			activeVs.remove(v);
			throughput++;
			totalWait += v.getWaitTime();
			avgWait = totalWait/throughput;
			
		} else {
			throw new Exception("Exception: Exiting vehicle not active in intersection");
		}
	}
	
	public double getThroughput() {
		return this.throughput;
	}
	
	public double getAvgWait() {
		return this.avgWait;
	}
	
	public String toString() {
		return "Avg Wait: " + this.avgWait + ", Vehicle throughput: " + throughput;
	}

}
