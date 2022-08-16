package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	Factory<LightSwitchingStrategy> lssFactory;
	Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int xCoor = data.getJSONArray("coor").getInt(0);
		int yCoor = data.getJSONArray("coor").getInt(1);
		LightSwitchingStrategy lss = this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJunctionEvent(time,id,lss,dqs,xCoor,yCoor);
	}
}
