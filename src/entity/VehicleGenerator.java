package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

public class VehicleGenerator extends Component {
	List<Vehicle> newVehicles;
	Lane acceptingLane;
	Accumulator acc;
	private static int vgID = 0;
	private int id;
	private ArrivalFunction arrivalFunction;
			
	public VehicleGenerator() {
		this.id = vgID++;
		this.arrivalFunction = new ArrivalFunction();
	}
	
	public VehicleGenerator(Lane lane, Accumulator acc) {
		this.id = vgID++;
		this.acceptingLane = lane;
		this.acc = acc;
		this.arrivalFunction = new ArrivalFunction();
	}
	public void generate() {
		// TODO: this should eventually reference the arrival function based on traffic
		// statistics
		// int num = arrivalFunction(Global.round);
		int num = arrivalFunction.generateCars();
//		int num = (int) (Math.random() * 2);
		newVehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < num; i++) {
			Vehicle newV = new Vehicle();
			newV.arrive();
//			System.out.println(newV.toString() + ", VG: " + this.id);
			newVehicles.add(newV);
		}
		
		// update lane with newly generated vehicles
		this.acceptingLane.addVehicles(newVehicles);
		this.acc.addVs(newVehicles);
	}

	public List<Vehicle> getVehicles() {
		return this.newVehicles;
	}

	@Override
	public void react() {
		generate();
		
	}

}
