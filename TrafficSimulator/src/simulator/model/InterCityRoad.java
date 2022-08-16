package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		int result = 0;
		
		switch(this.getWeatherConditions().toString().toLowerCase()){
		
			case "sunny": x = 2;break;
			case "cloudy": x = 3;break;
			case "rainy": x = 10;break;
			case "windy": x = 15;break;
			case "storm": x = 20;break;
		}
		
		result = (int)(((100.0-x)/100.0)*this.getTotalContamination());
		
		this.setTotalContamination(result);
	}

	@Override
	void updateSpeedLimit() {
		if(this.getTotalContamination() >= this.getContaminationAlarmLimit()){
			this.setCurrentSpeedLimit((int)(this.getMaxSpeed()*0.5));
		}else{
			this.setCurrentSpeedLimit(this.getMaxSpeed());
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int result = this.getCurrentSpeedLimit();
		
		if(this.getWeatherConditions() == Weather.STORM){
			result = ((int)(result *0.8));
		}
		return result;
	}
}
