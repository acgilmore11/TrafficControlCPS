package entity;

import java.util.List;
import java.util.ArrayList;

public class Accumulator {
	public List<Vehicle> activeVs;
	
	private double throughput;
	private double avgWait;
	private double peakWait = 0;
	private double maxCarsInLane = 0;
	
	// only used in average calculation
	private int totalWait;
	
	
	public Accumulator() {
		activeVs = new ArrayList<Vehicle>();
	}
	
	public void addVs(List<Vehicle> newVs) {
		activeVs.addAll(newVs);
	}
	
	public void receiveExitingVs(List<Vehicle> vs) throws Exception {
		for (Vehicle v: vs) {
			if (activeVs.contains(v)) {
				activeVs.remove(v);
				throughput++;
				double currWait = v.getWaitTime();
				totalWait += currWait;
				if (currWait > this.peakWait) {
					this.peakWait = currWait;
				}
				
				avgWait = totalWait/throughput;
				
			} else {
				throw new Exception("Exception: Exiting vehicle not active in intersection");
			}
		}
	}
	
	public void observeLaneTraffic(int num) {
		if (num > maxCarsInLane) {
			maxCarsInLane = num;
		}
		
	}
	
	public double getThroughput() {
		return this.throughput;
	}
	
	public double getAvgWait() {
		return this.avgWait;
	}
	
	public String toString() {
		return "Avg Wait: " + this.avgWait + ", Peak Wait: "+ this.peakWait +  ", Vehicle throughput: " + throughput + ", Max Cars In Lane: " + this.maxCarsInLane;
	}

}
