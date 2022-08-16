package simulator.model;

import java.util.List;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap mapa;
	private List<Event> eventos;
	private int contadorTiempo;
	
	
	public TrafficSimulator(){
		this.mapa = new RoadMap();
		this.contadorTiempo = 0;
		this.eventos = new SortedArrayList<Event>();
	}

	public void addEvent(Event e){
		eventos.add(e);
	}
	
	public void advance(){
		this.contadorTiempo++;
	
		for(int i = 0; i < eventos.size();i++){
			if(eventos.get(i).getTime() == this.contadorTiempo){
				eventos.get(i).execute(mapa);
				eventos.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < mapa.getJunctions().size();i++){
			mapa.getJunctions().get(i).advance(this.contadorTiempo);
		}
		
		for(int i = 0; i < mapa.getRoads().size();i++){
			mapa.getRoads().get(i).advance(this.contadorTiempo);
		}
	}
	
	
	public void reset(){
		this.mapa.reset();
		
		for (int i = this.eventos.size()-1; i >= 0;i--){
			this.eventos.remove(i);
		}
	}
	
	public JSONObject report(){
		JSONObject result = new JSONObject();
		
		result.put("time", this.contadorTiempo);
		result.put("state", this.mapa.report());
		
		return result;
	}
}
