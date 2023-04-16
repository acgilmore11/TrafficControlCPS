package entity;

public class TrafficLight implements Component {
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
				e.printStackTrace();
			}
		} else {
			lane.stop();
		}

		switchIn = (boolean) inSwitch.waitForOutput();


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

}
