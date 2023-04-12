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
	private SwitchControl lSwitch;
	private TLCConstantTime tlcct;
	
	public Accumulator acc;
	
	
	
	private List<Component> components = new ArrayList<Component>();
	private List<Thread> threads = new ArrayList<Thread>();
	
	public Simulator() {
		acc = new Accumulator();
		laneNS = new Lane(acc);
		laneEW = new Lane(acc);
		lSwitch = new SwitchControl();
		vgNS = new VehicleGenerator(laneNS, acc);
		vgEW = new VehicleGenerator(laneEW, acc);
		rcNS = new RoadCamera(laneNS);
		rcEW = new RoadCamera(laneEW);
		tlNS = new TrafficLight(laneNS, lSwitch, Global.RED);
		tlEW = new TrafficLight(laneEW, lSwitch, Global.GREEN);
		tlcct = new TLCConstantTime(lSwitch);
		
//		components.add(vgNS);
//		components.add(vgEW);
		components.add(tlNS);
		components.add(tlEW);
		components.add(tlcct);
//		components.add(rcNS);
//		components.add(rcEW);
		
	}
	
	public void run() throws Exception {
		Global.round++;
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
		
	}

	
	public static void main(String[] args) {
		Simulator sim = new Simulator();
		for (int i = 0; i < 100; i++) {
			System.out.println("\n\n**********ROUND " + Global.round + "**********\n\n");
			try {
				sim.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sim.laneNS.toString());
			System.out.println(sim.laneEW.toString());
			System.out.println(sim.acc.toString());
		}
		
//		for (int i = 0; i < 20; i++) {
//			System.out.println("\n\n**********ROUND " + Global.round + "**********\n\n");
//			sim.run();
//			System.out.println(sim.laneNS.toString());
//			System.out.println(sim.laneEW.toString());
//		}
		
		
		

		

	}

}


