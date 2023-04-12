package entity;

public class TLCConstantTime extends Component {
	private int tRemaining = Global.T_RED;
	private CoupledIO lSwitch;

	public TLCConstantTime(CoupledIO lSwitch) {
		this.lSwitch =  lSwitch;
		
	}
	@Override
	public void react() {
		// TODO Auto-generated method stub
		tRemaining--;
		
		if (tRemaining == 0) {
			tRemaining = Global.T_RED;
			System.out.println("Producer: Switch set to true");
			this.lSwitch.setOutput(true);
		} else {
			System.out.println("Producer: Switch set to false");
			this.lSwitch.setOutput(false);
		}
	}
	
	

}
