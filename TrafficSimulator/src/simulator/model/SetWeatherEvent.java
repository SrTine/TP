package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	private List<Pair<String,Weather>> wS;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		
		if(ws == null){
			throw new IllegalArgumentException("El evento setWeather contiene una lista vacia de pares");
		}else{
			this.wS = ws;
		}
	}

	@Override
	void execute(RoadMap map) {
		for(int i = 0; i < wS.size();i++){
			if(map.getRoad(wS.get(i).getFirst()) != null){
				map.getRoad(wS.get(i).getFirst()).setWeather(wS.get(i).getSecond());
			}else{
				throw new IllegalArgumentException("La carretera a la que se quiere instanciar un weather no existe");
			}
		}
	}
}
