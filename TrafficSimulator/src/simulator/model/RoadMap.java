package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> listaCruces;
	private List<Road> listaCarreteras;
	private List<Vehicle> listaVehiculos;
	private Map<String,Junction> mapaCruces;
	private Map<String,Road> mapaCarreteras;
	private Map<String,Vehicle> mapaVehiculos;
	
	RoadMap(){
		listaCarreteras = new ArrayList<Road>();
		listaCruces = new ArrayList<Junction>();
		listaVehiculos = new ArrayList<Vehicle>();
		mapaCarreteras = new HashMap<String,Road>();
		mapaCruces = new HashMap<String,Junction>();
		mapaVehiculos = new HashMap<String,Vehicle>();
	}
	
	void addJunction(Junction j){
		if(!mapaCruces.containsKey(j.getId())){
			listaCruces.add(j);
			mapaCruces.put(j.getId(), j);
		}else{
			throw new IllegalArgumentException("El cruce que se quiere añadir ya existe en el mapa "+ j.getId());
		}
	}
	
	void addRoad(Road r){
		if(!mapaCarreteras.containsKey(r.getId())){
			if(mapaCruces.containsKey(r.getSource().getId()) && mapaCruces.containsKey(r.getDestination().getId())){
				listaCarreteras.add(r);
				mapaCarreteras.put(r.getId(), r);
			}else{
				throw new IllegalArgumentException("No existe alguno de los cruces de la carretera "+ r.getId());
			}
		}else{
			throw new IllegalArgumentException("La carretera que se quiere añadir ya existe en el mapa "+ r.getId());
		}

	}
	
	void addVehicle(Vehicle v){
		if(!mapaVehiculos.containsKey(v.getId())){
			boolean excepcion = false;
			for(int i = 0; (i < v.getItinerary().size()-1) && !excepcion ;i++){
				if(!this.mapaCarreteras.containsKey(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)).getId())){
					excepcion = true;
					throw new IllegalArgumentException("El vehiculo que se quiere añadir tiene un par de cruces asociados a una carretera que no existe "+ v.getId());
				}
			}
			listaVehiculos.add(v);
			mapaVehiculos.put(v.getId(), v);
		}else{
			throw new IllegalArgumentException("El vehiculo que se quiere añadir ya existe en el mapa "+ v.getId());
		}
	}
	
	public Junction getJunction(String id){
		return this.mapaCruces.get(id);
	}
	
	public Road getRoad(String id){
		return this.mapaCarreteras.get(id);
	}
	
	public Vehicle getVehicle(String id){
		return this.mapaVehiculos.get(id);
	}
	
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(this.listaCruces));
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(new ArrayList<>(this.listaCarreteras));
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(this.listaVehiculos));
	}
	
	void reset(){
		listaCarreteras.clear(); 
		listaCruces.clear();
		listaVehiculos.clear();
		mapaCarreteras.clear();
		mapaCruces.clear();
		mapaVehiculos.clear();
	}
	
	public JSONObject report(){
		JSONObject result = new JSONObject();
		
		JSONArray arrayJunctions = new JSONArray();
		
		for(int i = 0; i < this.getJunctions().size();i++){
			arrayJunctions.put(i, this.getJunctions().get(i).report());
		}
		
		JSONArray arrayRoads = new JSONArray();
		
		for(int i = 0; i < this.getRoads().size();i++){
			arrayRoads.put(i, this.getRoads().get(i).report());
		}
		
		JSONArray arrayVehicles = new JSONArray();
		
		for(int i = 0; i < this.getVehicles().size();i++){
			arrayVehicles.put(i, this.getVehicles().get(i).report());
		}
		
		result.put("junctions", arrayJunctions);
		
		result.put("roads", arrayRoads);
		
		result.put("vehicles", arrayVehicles);
		
		return result;
	}
}
