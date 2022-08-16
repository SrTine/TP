package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if(roads.size() == 0){
			return -1;
		}else if(currGreen == -1){
			int max = 0;
			int indice = 0;
			
			for(int i = 0; i < qs.size();i++){
				if(qs.get(i).size() > max){
					indice = i;
				}
			}
			
			return indice;
		}else if(currTime - lastSwitchingTime < timeSlot){
			return currGreen;
		}else {
			int max = 0;
			int indice = 0;
			
			for(int i = ((currGreen+1) % roads.size());i < ((currGreen+1) % roads.size())+qs.size();i++){
				if(qs.get(i).size() > max){
					indice = i;
				}
			}
			
			return indice;
		}
	}
}
