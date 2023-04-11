package entity;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

public class Simulator {
	private VehicleGenerator vgNS, vgEW;
	private TrafficLight tlNS, tlEW;
	
	private List<Component> components = new ArrayList<Component>();
	
	public Simulator() {
		vgNS = new VehicleGenerator();
		vgEW = new VehicleGenerator();
		tlNS = new TrafficLight();
		tlEW = new TrafficLight();
		components.add(vgNS);
		components.add(vgEW);
		components.add(tlNS);
		components.add(tlEW);
		
	}
	
	public void run() {
		Global.round++;
		for (Component t : components) {
			ComponentThread t1 = new ComponentThread(t);
			t1.start();
		}
	}
	
	public static void main(String[] args) {
		Simulator sim = new Simulator();
		sim.run();
		sim.run();

	}

}


