package entity;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

public class Simulator {
	//physical
	private VehicleGenerator vgNS, vgEW;
	public Lane laneNS, laneEW;
	
	//simulation
	private TrafficLight tlNS, tlEW;
	private RoadCamera rcNS, rcEW;
	private CoupledIO lSwitch, vNS, vEW;
	private TLC tlc;
	public Accumulator acc;
	
	
	
	private List<Component> components = new ArrayList<Component>();
	private List<Thread> threads = new ArrayList<Thread>();
	
	// constructor. initializes all necessary components, physical and cyber
	public Simulator(int algType) {
		this.acc = new Accumulator();
		this.laneNS = new Lane(acc);
		this.laneEW = new Lane(acc);
		this.lSwitch = new CoupledIO();
		this.vNS = new CoupledIO();
		this.vEW = new CoupledIO();
		this.vgNS = new VehicleGenerator(laneNS, acc);
		this.vgEW = new VehicleGenerator(laneEW, acc);
		this.rcNS = new RoadCamera(laneNS, vNS);
		this.rcEW = new RoadCamera(laneEW, vEW);
		this.tlNS = new TrafficLight(laneNS, lSwitch, Global.RED);
		this.tlEW = new TrafficLight(laneEW, lSwitch, Global.GREEN);
		try {
			this.tlc = getTLCFromAlgType(algType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		components.add(rcNS);
		components.add(rcEW);
		components.add(tlNS);
		components.add(tlEW);
		components.add((Component) tlc);
		

		
	}
	
	// initialized specific TLC object based on input parameter
	private TLC getTLCFromAlgType(int algType) throws Exception {
		TLC res = null;
		if (algType == 0) {
			res = new TLCConstantTime(lSwitch, vNS, vEW); 
		} else if (algType == 1) {
			res = new TLCConstantTimeVehicleThreshold(lSwitch, vNS, vEW); 
		} else if (algType == 2) {
			res = new TLCConstantTimeVehicleThresholdModified(lSwitch, vNS, vEW); 
		} else if (algType == 3) {
			res = new TLCVarTime(lSwitch, vNS, vEW); 
		} else if (algType == 4) {
			res = new TLCVarTimeVehicleThreshold(lSwitch, vNS, vEW); 
		} else {
			throw new Exception("Exception: Invalid algorithm type");
		}
		return res;
	}
	
	// executes single round of simulation
	public void run() throws Exception {
		vgNS.generate();
		vgEW.generate();
		laneNS.move();
		laneEW.move();
		for (Component t : components) {
			ComponentThread t1 = new ComponentThread(t);
			threads.add(t1);
			t1.start();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		threads = new ArrayList<Thread>();
		lSwitch.reset();
		vNS.reset();
		vEW.reset();
		Global.round++;
	}
}


