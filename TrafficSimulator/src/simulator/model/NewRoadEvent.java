package simulator.model;

public class NewRoadEvent  extends Event{
	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	private int type;//0 = intercity, 1 = city

	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather,int type) {
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
		this.type = type;
	}

	@Override
	void execute(RoadMap map) {
		Junction srcJunction = null;
		Junction destJunction = null;
		
		if(map.getJunction(srcJunc) != null){
			srcJunction = map.getJunction(srcJunc);
		}
		
		if(map.getJunction(destJunc) != null){
			destJunction = map.getJunction(destJunc);
		}
		
		Road r = null;
		if(this.type == 0){
			r = new InterCityRoad(id,srcJunction,destJunction,maxSpeed,co2Limit,length,weather);
		}else if(this.type == 1){
			r = new CityRoad(id,srcJunction,destJunction,maxSpeed,co2Limit,length,weather);
		}
		
		map.addRoad(r);
	}
}
