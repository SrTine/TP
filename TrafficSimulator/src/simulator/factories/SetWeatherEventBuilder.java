package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {	
		int time = data.getInt("time");
		
		List<Pair<String,Weather>> ws = new ArrayList<Pair<String,Weather>>();
		JSONArray infoList = data.getJSONArray("info");
		
		for(int i = 0; i < infoList.length();i++){
			JSONObject elem = infoList.getJSONObject(i);
			String id = elem.getString("road");
			
			Weather weather = Weather.valueOf(elem.getString("weather"));
			
			Pair<String,Weather> p = new Pair<String, Weather>(id, weather);
			
			ws.add(p);
		}
		
		return new SetWeatherEvent(time,ws);
	}
}
