package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> itinerarioCruces = new ArrayList<Junction>();
		
		for(int i = 0; i < this.itinerary.size();i++){
			if(map.getJunction(this.itinerary.get(i)) != null){
				itinerarioCruces.add(map.getJunction(this.itinerary.get(i)));
			}
		}
		
		Vehicle v = new Vehicle(id,maxSpeed,contClass,itinerarioCruces);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
}
