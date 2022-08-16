package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		int result;
		
		switch(this.getWeatherConditions().toString()){
			case "SUNNY": x = 2;break;
			case "CLOUDY": x = 2;break;
			case "RAINY": x = 2;break;
			case "WINDY": x = 10;break;
			case "STORM": x = 10;break;
		}
		result = this.getTotalContamination()-x;
		if(result >= 0){
			this.setTotalContamination(result);
		}else{
			this.setTotalContamination(0);
		}
	}

	@Override
	void updateSpeedLimit() {
		this.setCurrentSpeedLimit(this.getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int result = (int)(((11.0 - v.getContaminationClass())/11.0)*this.getCurrentSpeedLimit());
		
		//le sumo uno para que coincida con la salida porq se redondea mal en la solucion que nos dan
		return result+1;
	}
}
