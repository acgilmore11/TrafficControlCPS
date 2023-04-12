package entity;

public class TrafficLight extends Component {
	private int t_remaining = 0;
	private int status = Global.GREEN;
	private int id;
	private CoupledIO inSwitch;
	private boolean switchIn;
	private Lane lane;
	private static int tlID = 0;

	public TrafficLight(Lane lane, CoupledIO inSwitch, int status) {
		this.id = tlID++;
		this.inSwitch = inSwitch;
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

		// TODO: this is only temporary. this eventually needs to receive input from TLC

		System.out.println("TL" + id + ": Waiting for switch");
		switchIn = (boolean) inSwitch.waitForOutput();
		System.out.println("TL" + id + ": Switch set to " + switchIn);

		if (switchIn) {
			t_remaining = Global.T_YELLOW;
			if (status == Global.GREEN) {
				status++;
			}
		}

		if (t_remaining > 0) {
			t_remaining--;
			if (t_remaining == 0) {
				status = (status + 1) % 3;
			}
		}

	}

//	public void run

}
