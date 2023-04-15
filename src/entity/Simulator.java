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
	private TLCConstantTime tlc_ct;
	private TLCConstantTimeVehicleThreshold tlc_ctvt;
	private TLCConstantTimeVehicleThresholdModified tlc_ctvtm;
	private TLCVarTime tlc_vt;
	private TLCVarTimeVehicleThreshold tlc_vtvt;
	public Accumulator acc;
	
	
	
	private List<Component> components = new ArrayList<Component>();
	private List<Thread> threads = new ArrayList<Thread>();
	
	public Simulator() {
		acc = new Accumulator();
		laneNS = new Lane(acc);
		laneEW = new Lane(acc);
		lSwitch = new CoupledIO();
		vNS = new CoupledIO();
		vEW = new CoupledIO();
		vgNS = new VehicleGenerator(laneNS, acc);
		vgEW = new VehicleGenerator(laneEW, acc);
		rcNS = new RoadCamera(laneNS, vNS);
		rcEW = new RoadCamera(laneEW, vEW);
		tlNS = new TrafficLight(laneNS, lSwitch, Global.RED);
		tlEW = new TrafficLight(laneEW, lSwitch, Global.GREEN);
		tlc_ctvt = new TLCConstantTimeVehicleThreshold(lSwitch,vNS,vEW);
		tlc_ctvtm = new TLCConstantTimeVehicleThresholdModified(lSwitch,vNS,vEW);
		tlc_vt = new TLCVarTime(lSwitch, vNS, vEW);
		tlc_vtvt = new TLCVarTimeVehicleThreshold(lSwitch,vNS,vEW);
		tlc_ct = new TLCConstantTime(lSwitch);
		
		
		
//		components.add(vgNS);
//		components.add(vgEW);
		components.add(rcNS);
		components.add(rcEW);
		components.add(tlNS);
		components.add(tlEW);
		
		// constant time
		components.add(tlc_ct);
		
		// constant time, vehicle threshold
//		components.add(tlc_ctvt);
		
		// constant time, vehicle threshold mod
//		components.add(tlc_ctvtm);
		
		// var time
//		components.add(tlc_vt);
		
		// var time, vehicle threshold
//		components.add(tlc_vtvt);


//		components.add(rcNS);
//		components.add(rcEW);
		
	}
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		threads = new ArrayList<Thread>();
		
		// must reset at the end of each round
		// traffic lights await this output from TLC
		lSwitch.reset();
		Global.round++;
		
	}

	
	public static void main(String[] args) {
		Simulator sim = new Simulator();
		for (int i = 0; i < 86400; i++) {
			try {
				sim.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i % 60 == 0) {
				System.out.println("\n\n**********ROUND " + Global.round + "**********\n\n");
				System.out.println(sim.laneNS.toString());
				System.out.println(sim.laneEW.toString());
				System.out.println(sim.acc.toString());
			}
			
		}
		System.out.println(sim.acc.toString());
		
//		for (int i = 0; i < 20; i++) {
//			System.out.println("\n\n**********ROUND " + Global.round + "**********\n\n");
//			sim.run();
//			System.out.println(sim.laneNS.toString());
//			System.out.println(sim.laneEW.toString());
//		}
		
		
		

		

	}

}


