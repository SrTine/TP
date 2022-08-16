package Command;

import tp.p1.Game;

public class ExitCommand extends Command{

	public ExitCommand() {
		super("EXIT", "E", "USAGE: EXIT/E","TERMINA EL JUEGO");
	}

	@Override
	public void execute(Game game) {
		game.exit();
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
			if(commandWords.length == 1){
				if(commandWords[0].equals("exit") || commandWords[0].equals("e")){
					c = new ExitCommand();
				}
			}
		
		return c;
	}
}
