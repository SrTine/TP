package Command;

import tp.p1.Game;

public class StringifyCommand extends Command{

	public StringifyCommand() {
		super("STRINGIFY", "", "USAGE: STRINGIFY","MUESTRA EL ESTADO DEL JUEGO SERIALIZADO");
	}

	@Override
	public void execute(Game game) {
		System.out.println(game.stringify());
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("stringify")){
				c = new StringifyCommand();
			}
		}
		
		return c;
	}

}