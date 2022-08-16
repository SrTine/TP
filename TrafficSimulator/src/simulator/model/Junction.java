package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	private List<Road> listaDeCarreterasEntrantes;
	private Map<Junction,Road> mapaDecarreterasSalientes;
	private List<List<Vehicle>> listaDeColasDeVehiculosCarreterasEntrantes;
	private Map<Road,List<Vehicle>> mapaDeCarreterasColas;
	private int indiceSemaforoVerde;
	private int ultimoPasoDeCambioDeSemaforoVerde;
	private LightSwitchingStrategy estrategiaSemaforo;
	private DequeuingStrategy estrategiaVehiculos;
	private int x;
	private int y;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		
		if(lsStrategy == null || dqStrategy == null){
			throw new IllegalArgumentException("Las estrategias de semaforo y vehiculos no pueden ser nulas");
		}
		if(xCoor < 0 || yCoor < 0){
			throw new IllegalArgumentException("Las coordenaadas del cruce no pueden ser negativas");
		}
		
		this.indiceSemaforoVerde = -1;
		this.ultimoPasoDeCambioDeSemaforoVerde = 0;
		this.estrategiaSemaforo = lsStrategy;
		this.estrategiaVehiculos = dqStrategy;
		this.x = xCoor;
		this.y = yCoor;
		this.listaDeCarreterasEntrantes = new ArrayList<Road>();
		this.listaDeColasDeVehiculosCarreterasEntrantes = new ArrayList<List<Vehicle>>();
		this.mapaDeCarreterasColas = new HashMap<Road,List<Vehicle>>();
		this.mapaDecarreterasSalientes = new HashMap<Junction,Road>();
	}
	
	@Override
	void advance(int time) {
		
		for(int i = 0; i < this.listaDeColasDeVehiculosCarreterasEntrantes.size();i++){
			List<Vehicle> res = this.estrategiaVehiculos.dequeue(this.listaDeColasDeVehiculosCarreterasEntrantes.get(i));
			
			for(int j = 0; (j < res.size()) && (this.indiceSemaforoVerde == i);j++){
				res.get(j).moveToNextRoad();
				this.mapaDeCarreterasColas.get(this.listaDeCarreterasEntrantes.get(i)).remove(res.get(j));
			}
		}
		
		if(this.estrategiaSemaforo.chooseNextGreen(listaDeCarreterasEntrantes, listaDeColasDeVehiculosCarreterasEntrantes, indiceSemaforoVerde, ultimoPasoDeCambioDeSemaforoVerde, time) != this.indiceSemaforoVerde){
			this.indiceSemaforoVerde = this.estrategiaSemaforo.chooseNextGreen(listaDeCarreterasEntrantes, listaDeColasDeVehiculosCarreterasEntrantes, indiceSemaforoVerde, ultimoPasoDeCambioDeSemaforoVerde, time);
			this.ultimoPasoDeCambioDeSemaforoVerde = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject result = new JSONObject();
		
		result.put("id", this.getId());
		
		if(this.indiceSemaforoVerde != -1){
			result.put("green", this.listaDeCarreterasEntrantes.get(this.indiceSemaforoVerde).getId());
		}else{
			result.put("green", "none");
		}
		
		JSONArray queues = new JSONArray();
		JSONObject elem = new JSONObject();
		JSONArray vehicles = new JSONArray();
		
		for(int i = 0; i < this.listaDeCarreterasEntrantes.size();i++){
			
			elem.put("road",this.listaDeCarreterasEntrantes.get(i).getId());
			
			for(int j = 0;  j < this.listaDeColasDeVehiculosCarreterasEntrantes.get(i).size();j++){
				vehicles.put(j, this.listaDeColasDeVehiculosCarreterasEntrantes.get(i).get(j).getId());
			}
			elem.put("vehicles", vehicles);
			queues.put(elem);
			
			elem = new JSONObject();
			vehicles = new JSONArray();
		}
		
		result.put("queues", queues);
		
		return result;
	}

	void addIncommingRoad(Road r){
		if(r.getDestination() != this){
			throw new IllegalArgumentException("El cruce destino de la carretera entrante no coincide con este cruce");
		}
		
		this.listaDeCarreterasEntrantes.add(r);
		List<Vehicle> cola = new LinkedList<Vehicle>();
		this.listaDeColasDeVehiculosCarreterasEntrantes.add(cola);
		this.mapaDeCarreterasColas.put(r, cola);
	}
	
	void addOutGoingRoad(Road r){
		if(r.getSource() != this || (this.mapaDecarreterasSalientes.containsValue(r))){
			throw new IllegalArgumentException("El cruce origen de la carretera saliente no coincide con este cruce o ya existe una carretera saliente para ese cruce");
		}else{
			this.mapaDecarreterasSalientes.put(r.getDestination(), r);
		}
	}
	
	void enter (Road r, Vehicle v){
		this.mapaDeCarreterasColas.get(r).add(v);
	}
	
	Road roadTo(Junction j){
		return this.mapaDecarreterasSalientes.get(j);
	}
}
