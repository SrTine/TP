package Command;

import tp.p1.Game;

public class ListCommand extends Command{

	public ListCommand() {
		super("LIST", "L", "USAGE: LIST/L","LISTA LOS OBJETOS QUE SE PUEDEN AÑADIR AL JUEGO");
	}

	@Override
	public void execute(Game game) {
		game.muestraList();
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("list") || commandWords[0].equals("l")){
				c = new ListCommand();
			}
		}
		
		return c;
	}
}
