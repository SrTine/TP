package Command;

import tp.p1.Game;

public class ResetCommand extends Command{

	public ResetCommand() {
		super("RESET", "R", "USAGE: RESET/R","INICIALIZA EL JUEGO");
	}

	@Override
	public void execute(Game game) {
		game.reset();
		System.out.println(game.toString());
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("reset") || commandWords[0].equals("r")){
				c = new ResetCommand();
			}
		}
		
		return c;
	}
}
