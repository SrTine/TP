package simulator.model;

import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject{
	private Junction source;
	private Junction destination;
	private int length;
	private int maxSpeed;
	private int currentSpeedLimit;
	private int contaminationAlarmLimit;
	private Weather weatherConditions;
	private int totalContamination;
	private List<Vehicle> vehicles;
	private List<Vehicle> copiaLecturaVehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed < 0) {
			throw new IllegalArgumentException("maxSpeed must be positive");
		}
		
		if(contLimit < 0){
			throw new IllegalArgumentException("contLimit must be positive");
		}
		
		if(length < 0){
			throw new IllegalArgumentException("length must be positive");
		}
		
		if(srcJunc == null || destJunc == null || weather == null){
			throw new IllegalArgumentException("srcJunc, destJunc and weather must be diferent than null");
		}
		
		this.source = srcJunc;
		this.destination = destJunc;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.currentSpeedLimit = this.maxSpeed;
		this.contaminationAlarmLimit = contLimit;
		this.weatherConditions = weather;
		this.vehicles = new SortedArrayList<Vehicle>();	
		this.source.addOutGoingRoad(this);
		this.destination.addIncommingRoad(this);
		this.copiaLecturaVehicles = Collections.unmodifiableList(new SortedArrayList<Vehicle>());
	}
	
	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(int i = 0; i < this.vehicles.size();i++){
			this.vehicles.get(i).setSpeed(this.calculateVehicleSpeed(this.vehicles.get(i)));
			this.vehicles.get(i).advance(time);
		}
		Collections.sort(this.vehicles);
	}
	
	@Override
	public JSONObject report() {
		JSONObject result = new JSONObject();
		
		result.put("id", this.getId());
		result.put("speedlimit", this.currentSpeedLimit);
		result.put("weather", this.weatherConditions.toString());
		result.put("co2", this.totalContamination);
		
		JSONArray vehicles = new JSONArray();
		
		for(int i = 0; i < this.vehicles.size();i++){
			vehicles.put(i, this.vehicles.get(i).getId());
		}
		
		result.put("vehicles", vehicles);
		
		return result;
	}
	
	void enter(Vehicle v){
		if(v.getLocation() != 0 || v.getCurrentSpeed() != 0){
			throw new IllegalArgumentException("La velocidad y la localizacion del vehiculo al entrar en la carretera debe ser 0");
		}else{
			this.vehicles.add(v);
		}
	}
	
	void exit(Vehicle v){
		boolean encontrado = false;
		
		for(int i = 0;(i<this.vehicles.size()) && !encontrado;i++){
			if(this.vehicles.get(i).getId() == v.getId()){
				encontrado = true;
				this.vehicles.remove(i);
				//Collections.sort(this.vehicles);
			}
		}
	}
	
	void setWeather(Weather w){
		if(w == null){
			throw new IllegalArgumentException("el weather no puede ser null");
		}else{
			this.weatherConditions = w;
		}
	}
	
	void addContamination(int c){
		if(c < 0){
			throw new IllegalArgumentException("No se puede añadir co2 negativo");
		}else{
			this.totalContamination += c;
		}
	}
	
	
	//METODOS ABSTRACTOS QUE IMPLEMENTAN SUBCLASES DE ROAD
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
	//getters and setters
	
	public Weather getWeatherConditions() {
		return weatherConditions;
	}

	protected void setTotalContamination(int totalContamination) {
		this.totalContamination = totalContamination;
	}

	public int getTotalContamination() {
		return totalContamination;
	}

	public int getCurrentSpeedLimit() {
		return currentSpeedLimit;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getContaminationAlarmLimit() {
		return contaminationAlarmLimit;
	}

	protected void setCurrentSpeedLimit(int currentSpeedLimit) {
		this.currentSpeedLimit = currentSpeedLimit;
	}

	public Junction getSource() {
		return source;
	}

	public List<Vehicle> getVehicles() {
		return this.copiaLecturaVehicles;
	}

	public Junction getDestination() {
		return destination;
	}

	public int getLength() {
		return length;
	}
}
