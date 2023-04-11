package entity;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

public class VehicleGenerator extends Component {
	Queue<Vehicle> newVehicles;
	private static int vgID = 0;
	private int id;
			
	public VehicleGenerator() {
		this.id = vgID++;
	}
	public void generate() {
		// TODO: this should eventually reference the arrival function based on traffic
		// statistics
		// int num = arrivalFunction();
		int num = (int) (Math.floor(Math.random() * (5 - 0 + 1) + 0));
		newVehicles = new LinkedList<Vehicle>();
		for (int i = 0; i < num; i++) {
			Vehicle newV = new Vehicle();
			System.out.println(newV.toString() + ", VG: " + this.id);
			newVehicles.add(newV);
		}
	}

	public Queue<Vehicle> getVehicles() {
		return this.newVehicles;
	}

	@Override
	public void react() {
		generate();
		
	}

}
