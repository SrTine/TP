package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event>{

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String srcJunc = data.getString("src");
		String destJunc = data.getString("dest");
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));
		
		return new NewCityRoadEvent(time,id,srcJunc,destJunc,length,co2Limit,maxSpeed,weather);
	}
}
