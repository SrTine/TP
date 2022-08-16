package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private Factory<Event> factorias;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
		
		if(sim == null){
			throw new IllegalArgumentException("No se inicializo bien el simulador (controller)");
		}
		
		if(eventsFactory == null){
			throw new IllegalArgumentException("No se inicializo bien las factorias (controller)");
		}
		
		this.simulator = sim;
		this.factorias = eventsFactory;
	}

	public void loadEvents(InputStream in){
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if(!jo.has("events")){
			throw new IllegalArgumentException("El inputStream no contiene la clave events (controller)");
		}
		
		JSONArray arrayEvents = jo.getJSONArray("events");
		
		for(int i = 0; i < arrayEvents.length();i++){
			Event e = this.factorias.createInstance(arrayEvents.getJSONObject(i));
			if (e != null) this.simulator.addEvent(e);
			else
				throw new IllegalArgumentException("Evento desconocido (controller)");
		}
	}
	
	public void run(int n, OutputStream out) throws IOException{
		JSONObject states = new JSONObject();
		JSONArray statesArray = new JSONArray();
		
		for(int i = 0; i < n ;i++){
			this.simulator.advance();
			statesArray.put(i, this.simulator.report());
		}
		
		states.put("states", statesArray);
		out.write(states.toString().getBytes());
	}
	
	public void reset(){
		this.simulator.reset();
	}
}
