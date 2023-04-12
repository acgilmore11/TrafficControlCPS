package entity;

public class TLCConstantTime extends Component {
	private int tRemaining = Global.T_RED;
	private SwitchControl lSwitch;

	public TLCConstantTime(SwitchControl lSwitch) {
		this.lSwitch = lSwitch;
		
	}
	@Override
	public void react() {
		// TODO Auto-generated method stub
		tRemaining--;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tRemaining == 0) {
			tRemaining = Global.T_RED;
			System.out.println("Producer: Switch set to true");
			this.lSwitch.setSwitch(true);
		} else {
			System.out.println("Producer: Switch set to false");
			this.lSwitch.setSwitch(false);
		}
	}
	
	

}
