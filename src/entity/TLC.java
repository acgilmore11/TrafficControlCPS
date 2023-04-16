package entity;

public abstract class TLC implements Component {
	protected int tRemaining;
	protected CoupledIO outSwitch;
	protected CoupledIO inVNS, inVEW;
	
	public TLC(CoupledIO outSwitch, CoupledIO inVNS, CoupledIO inVEW) {
		this.outSwitch = outSwitch;
		this.inVNS = inVNS;
		this.inVEW = inVEW;
	}

	@Override
	public abstract void react();
	
	

}
