package entity;

public class TLCConstantTime extends TLC {

	public TLCConstantTime(CoupledIO outSwitch, CoupledIO inVNS, CoupledIO inVEW) {
		super(outSwitch, inVNS, inVEW);
		this.tRemaining = Global.T_RED;
		
		
	}
	@Override
	public void react() {
		tRemaining--;
		
		if (tRemaining == 0) {
			tRemaining = Global.T_RED;
			this.outSwitch.setOutput(true);
		} else {
			this.outSwitch.setOutput(false);
		}
	}
	
	

}
