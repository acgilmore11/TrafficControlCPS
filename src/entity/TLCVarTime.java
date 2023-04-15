package entity;

public class TLCVarTime extends Component {
	private int tRemaining = Global.T_RED_MIN;
	private CoupledIO outSwitch;
	private CoupledIO inVNS, inVEW;
	private int vSwitch = 0;
	private int numV;
	private int currTimeInt = Global.T_RED_MIN;

	public TLCVarTime(CoupledIO outSwitch, CoupledIO inVNS, CoupledIO inVEW) {
		this.outSwitch =  outSwitch;
		this.inVNS = inVNS;
		this.inVEW = inVEW;
		
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
		if (tRemaining == 0) {
			outSwitch.setOutput(true);
			vSwitch = (vSwitch + 1) % 2;
			currTimeInt = Math.min(Global.T_RED_MIN + (this.numV * Global.T_EXIT), Global.T_RED_MAX);
			tRemaining = currTimeInt;
//			System.out.println("TLC: Switch set to true");
			this.outSwitch.setOutput(true);
		} else {
//			System.out.println("TLC: Switch set to false");
			this.outSwitch.setOutput(false);
		}
	}
	
	

}
