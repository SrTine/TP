package Command;

import Excepciones.NoShockWaveException;
import tp.p1.Game;

public class ShockwaveCommand extends Command{

	public ShockwaveCommand() {
		super("SHOCKWAVE", "W", "USAGE: SHOCKWAVE/W","DECREMENTA UN PUNTO DE DAÑO A TODOS LOS ALIENS");
	}

	@Override
	public void execute(Game game) throws NoShockWaveException {
		game.shockWave();
		game.update();
		System.out.println(game.toString());
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("shockwave") || commandWords[0].equals("w")){
				c = new ShockwaveCommand();
			}
		}
		
		return c;
	}
}
