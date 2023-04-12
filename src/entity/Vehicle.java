package entity;

public class Vehicle {
	private int id;
	private int arrivalTime;
	private int departureTime;
	private int waitTime = -1;
	private static int currentID = 0;
	
	public Vehicle() {
		this.id = currentID++;
	}
	
	public Vehicle(int arrivalTime) {
		this.id = currentID++;
		this.arrivalTime = arrivalTime;
	}
	
	public void arrive() {
		this.arrivalTime = Global.round;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public void exit() {
		this.departureTime = Global.round;
		this.waitTime = this.departureTime - this.arrivalTime;
	}
	
	public int getDepartureTime() {
		return this.departureTime;
	}
	
	public int getWaitTime() {
		if (this.waitTime == -1) {
			return Global.round - this.arrivalTime;
		} else {
			return this.waitTime;
		}
			
	}
	
	public int getID() {
		return this.id;
	}
	
	@Override
	public String toString() {
//		return "Vehicle ID: " + this.id + ", Arrival time: " + this.arrivalTime + ", Wait time: " + getWaitTime();
		return "VID: " + this.id; 
	}
	
}