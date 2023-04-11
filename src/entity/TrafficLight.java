package entity;

public class TrafficLight extends Component{
	private int remaining = 0;
	private int status = 0;
	private int id;
	private static int tlID = 0;
	
	public TrafficLight() {
		this.id = tlID++;
	}
	@Override
	public void react() {
		if (status < 2) {
			System.out.println("isDeparting: true" + " (tl: " + id + ")");
		} else {
			System.out.println("isDeparting: false" + " (tl: " + id + ")");
		}
		
		//TODO: this is only temporary. this eventually needs to receive input from TLC
		int switchIn = (int) (Math.random() * 2);
		
		if (switchIn == 1) {
			if (status == 0) {
				status++;
				remaining = 5;
			} else if (status == 2) {
				status = 0;
			}
		}
		
		if (status == 1) {
			remaining--;
			if (remaining == 0) {
				status--;
			}
		}
	}
	
//	public void run

}
