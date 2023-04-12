package entity;

import java.util.List;
import java.util.ArrayList;

public class RoadCamera extends Component{
	private static int rcID = 0;
	private int id;
	private List<Integer> inV = null;
	private Lane observingLane;
	
	// important: this represents the vehicles the Road Camera has spotted and is aware of
	private List<Integer> awareV = new ArrayList<Integer>();
	
	// not sure about this one yet
	private List<Integer> leaving;

	public RoadCamera(Lane lane) {
		this.id = rcID++;
		this.observingLane = lane;
	}
	@Override
	public void react() {
		// TODO Auto-generated method stub
		System.out.println("RC" + id + " aware of " + awareV.size() + " vehicles");
		
		// in terms of SRC, this represents receiving input from Lane
		// in terms of physical, this represents camera spotting vehicles in lane
		inV = observingLane.getVehicleIds();
		this.awareV = inV;
		
		
	}

}
