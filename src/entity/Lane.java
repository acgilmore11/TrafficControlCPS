package entity;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Lane {
	private List<Vehicle> vehicles;
	private int id;
	private Accumulator acc;
	private boolean dispensing = false;
	private int t_remainingDispense = -1;
	private static int laneId = 0;
	
	public Lane(Accumulator acc) {
		this.vehicles = new ArrayList<Vehicle>();
		this.id = laneId++;
		this.acc = acc;
	}
	
	public void move() throws Exception {
		if (dispensing) {
			t_remainingDispense--;
			if (t_remainingDispense == 0) {
				t_remainingDispense = Global.T_EXIT;
				if (vehicles.size() > 0) {
					acc.removeVs(vehicles.remove(0));
				}
			}
			
		}
	}
	
	public void addVehicles(List<Vehicle> newVs) {
		for (Vehicle v : newVs)
			vehicles.add(v);
	}
	
	public Vehicle remove() {
		return vehicles.remove(0);
	}
	
	public List<Integer> getVehicleIds() {
		List<Integer> vIDs = new ArrayList<Integer>();
		for (Vehicle v : vehicles) {
			vIDs.add(v.getID());
		}
		return vIDs;
	}
	
	// not sure yet if this will be used. removes first n vehicles from lane
	public List<Vehicle> remove(int n) throws Exception {
		if (n <= vehicles.size()) {
			List<Vehicle> removedVs = new ArrayList<Vehicle>();
			for (int i = 0; i < n; i++) {
				Vehicle v = vehicles.remove(0);
				v.exit();
				removedVs.add(v);
			}
			return removedVs;
			
		} else {
			throw new Exception("Exception: " + n + " exceeds size of list");
		}
	}
	
	
	// this should receive signal from traffic light and begin dispensing vehicles
	public void dispense() throws Exception {
		if (!dispensing) {
			dispensing = true;
			t_remainingDispense = Global.T_EXIT;
		}
	}
	
	public void stop() {
		if (dispensing) {
			dispensing = false;
			t_remainingDispense = -1;
		}
	}
	
	@Override
	public String toString() {
		String res = "Lane" + id + " [IS DISPENSING: " + dispensing +" | ";
		for (int i = 0; i < vehicles.size(); i++) {
			res += "[" + vehicles.get(i).toString() + "]";
			if (i < vehicles.size()-1)
				res += ",";
			
		}
		res += "]";
		return res;
		
	}

}
