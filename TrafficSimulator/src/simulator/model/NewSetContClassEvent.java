package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{
	private List<Pair<String,Integer>> cS;

	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		
		if(cs == null){
			throw new IllegalArgumentException("El evento newSetContClass contiene una lista vacia de pares");
		}else{
			this.cS = cs;
		}
	}

	@Override
	void execute(RoadMap map) {
		for(int i = 0; i < cS.size();i++){
			if(map.getVehicle(cS.get(i).getFirst()) != null){
				map.getVehicle(cS.get(i).getFirst()).setContaminationClass(cS.get(i).getSecond());
			}else{
				throw new IllegalArgumentException("El vehiculo al que se intenta instanciar una contaminacion no existe");
			}
		}
	}
}
