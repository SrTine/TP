package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		
		List<Pair<String,Integer>> cs = new ArrayList<Pair<String,Integer>>();
		JSONArray infoList = data.getJSONArray("info");
		
		for(int i = 0; i < infoList.length();i++){
			JSONObject elem = infoList.getJSONObject(i);
			String id = elem.getString("vehicle");
			int contClass = elem.getInt("class");
			
			Pair<String,Integer> p = new Pair<String, Integer>(id, contClass);
			
			cs.add(p);
		}
		
		return new NewSetContClassEvent(time,cs);
	}
}
