package entity;

import java.util.List;
import java.util.ArrayList;

public class RoadCamera implements Component{
	private static int rcID = 0;
	private int id;
	private List<Integer> inV = null;
	private Lane observingLane;
	private CoupledIO outV;
	
	// important: this represents the vehicles the Road Camera has spotted and is aware of
	private List<Integer> awareV = new ArrayList<Integer>();
	

	public RoadCamera(Lane lane, CoupledIO outv) {
		this.id = rcID++;
		this.observingLane = lane;
		this.outV = outv;
	}
	@Override
	public void react() {
//		System.out.println("RC" + id + " aware of " + awareV.size() + " vehicles");
		this.outV.setOutput(awareV.size());
		
		// in terms of SRC, this represents receiving input from Lane
		// in terms of physical, this represents camera spotting vehicles in lane
		inV = observingLane.getVehicleIds();
		this.awareV = inV;
		
		
	}

}
