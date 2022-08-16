package Command;

import tp.p1.Game;

public class NoneCommand extends Command{

	public NoneCommand() {
		super("NONE", "N", "USAGE: NONE/N","DEJA AVANZAR UN CICLO DE JUEGO");
	}

	@Override
	public void execute(Game game) {
		game.update();
		System.out.println(game.toString());
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("none") || commandWords[0].equals("n") || commandWords[0].equals("")){
				c = new NoneCommand();
			}
		}
			
		return c;
	}
}
