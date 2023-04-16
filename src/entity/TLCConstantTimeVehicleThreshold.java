package entity;

public class TLCConstantTimeVehicleThreshold extends TLC {
	private int vSwitch = 0;
	private int numV;

	public TLCConstantTimeVehicleThreshold(CoupledIO outSwitch, CoupledIO inVNS, CoupledIO inVEW) {
		super(outSwitch, inVNS, inVEW);
		this.tRemaining = Global.T_RED;
		
	}
	@Override
	public void react() {
		// TODO Auto-generated method stub
		
		Thread t1 = new Thread(() -> {
			tRemaining--;
		});
		
		Thread t2 = new Thread(() -> {
			int vns = (int) inVNS.waitForOutput();
			int vew = (int) inVEW.waitForOutput();
			if (vSwitch == 0) {
				this.numV = vns;
			} else {
				this.numV = vew;
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// this might need to change to factor in yellow lights
		if (tRemaining == 0 || numV >= Global.MAX_VS) {
			outSwitch.setOutput(true);
			vSwitch = (vSwitch + 1) % 2;
			tRemaining = Global.T_RED;
//			System.out.println("TLC: Switch set to true");
			this.outSwitch.setOutput(true);
		} else {
//			System.out.println("TLC: Switch set to false");
			this.outSwitch.setOutput(false);
		}
	}
	
	

}
