package entity;

public class TrafficLight extends Component{
	private int remaining = 0;
	private int status = Global.GREEN;
	private int id;
	private SwitchControl lSwitch;
	private boolean switchIn;
	private Lane lane;
	private static int tlID = 0;
	
	public TrafficLight(Lane lane, SwitchControl lSwitch, int status) {
		this.id = tlID++;
		this.lSwitch = lSwitch;
		this.status = status;
		this.lane = lane;
	}
	@Override
	public void react() {
		if (status < Global.RED) {
			try {
				lane.dispense();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			lane.stop();
		}
		
		//TODO: this is only temporary. this eventually needs to receive input from TLC
		
		System.out.println("TL" + id + ": Waiting for switch");
//		int switchIn = (int) (Math.random() * 2);
		try {
			switchIn = lSwitch.waitForSwitch();
			System.out.println("TL" + id + ": Switch set to " + switchIn);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (switchIn) {
			if (status == Global.GREEN) {
				status++;
				remaining = 5;
			} else if (status == Global.RED) {
				status = Global.GREEN;
			}
		}
		
		if (status == Global.YELLOW) {
			remaining--;
			if (remaining == 0) {
				status++;
			}
		}
	}
	
//	public void run

}
