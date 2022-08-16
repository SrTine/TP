package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contaminationClass;
	private int totalContamination;
	private int travelledDistance;
	private int indiceCruce;
	private List<Junction> copiaItinerary;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		if(maxSpeed < 0){
			throw new IllegalArgumentException("maxSpeed  must be positive");
		}
		if(contClass < 0 || contClass > 10){
			throw new IllegalArgumentException("contClass must be a number between 0 and 10");
		}
		if(itinerary.size() < 2){
			throw new IllegalArgumentException("itinerary size must be more than 2");
		}
		
		this.itinerary = itinerary;
		this.maxSpeed = maxSpeed;
		this.currentSpeed = 0;
		this.status = VehicleStatus.PENDING;
		this.road = null;
		this.location = 0;
		this.contaminationClass = contClass;
		this.totalContamination = 0;
		this.travelledDistance = 0;
		this.indiceCruce = 1;//lo inicializamos a 1 porque para entrar a la primera carretera se consulta el estado si es pending
		copiaItinerary = Collections.unmodifiableList(new ArrayList<>(this.itinerary));
	}
	
	@Override
	void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING){
			int locAnterior = this.location;
			int contProducida;
			
			if((this.location + this.currentSpeed) >= this.road.getLength()){
				this.location = this.road.getLength();
				this.status = VehicleStatus.WAITING;
				this.setSpeed(0);
				this.road.getDestination().enter(this.road, this);
			}else{
				this.location += this.currentSpeed;
			}
			contProducida = ((this.location-locAnterior) * this.contaminationClass);
			this.totalContamination += contProducida;
			this.road.addContamination(contProducida);
			this.travelledDistance += this.location - locAnterior;
		}else if(this.status == VehicleStatus.WAITING){
			this.setSpeed(0);
		}
	}

	void moveToNextRoad(){
		if(this.status == VehicleStatus.PENDING){
			this.copiaItinerary.get(0).roadTo(this.copiaItinerary.get(1)).enter(this);
			this.road = this.copiaItinerary.get(0).roadTo(this.copiaItinerary.get(1));
			this.status = VehicleStatus.TRAVELING;
		}else if(this.status == VehicleStatus.WAITING){
			if(this.indiceCruce == this.copiaItinerary.size()-1){
				this.road.exit(this);
				this.status = VehicleStatus.ARRIVED;
			}else{
				this.road.exit(this);
				this.currentSpeed = 0;
				this.location = 0;
				this.copiaItinerary.get(this.indiceCruce).roadTo(this.copiaItinerary.get(this.indiceCruce+1)).enter(this);
				this.road = this.copiaItinerary.get(this.indiceCruce).roadTo(this.copiaItinerary.get(this.indiceCruce+1));
				this.status = VehicleStatus.TRAVELING;
				this.indiceCruce++;
			}
		}else{
			throw new IllegalArgumentException("El vehiculo" + this._id + " no esta pending ni waiting");
		}
		
	}
	
	@Override
	public int compareTo(Vehicle o) {
		if(this.location == o.getLocation()) return 0;
		else if(this.location < o.getLocation()) return 1;
		else return -1;
	}

	@Override
	public JSONObject report() {
		JSONObject result = new JSONObject();
		
		result.put("id", this.getId());
		result.put("speed", this.currentSpeed);
		result.put("distance", this.travelledDistance);
		result.put("co2", this.totalContamination);
		result.put("class", this.contaminationClass);
		result.put("status", this.status.toString());
		
		if(this.status.equals(VehicleStatus.TRAVELING) || this.status.equals(VehicleStatus.WAITING)){
			result.put("road",this.road);
			result.put("location", this.location);
		}
		
		return result;
	}
	
	//getters and setters-------------------------------------
	
	void setSpeed(int s){
		if (s < 0){
			throw new IllegalArgumentException("speed must be positive");
		}else{
			if(s >= this.maxSpeed){
				this.currentSpeed = this.maxSpeed;
			}else{
				this.currentSpeed = s;
			}
		}
	}
	
	void setContaminationClass(int c){
		if(c < 0 || c > 10){
			throw new IllegalArgumentException("c must be a number between 0 and 10 (method setContaminationClass)");
		}else{
			this.contaminationClass = c;
		}
	}
	
	public int getLocation() {
		return location;
	}

	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public int getContaminationClass() {
		return contaminationClass;
	}

	public List<Junction> getItinerary() {
		return copiaItinerary;
	}
}
