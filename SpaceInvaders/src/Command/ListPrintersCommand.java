package Command;

import tp.p1.Game;

public class ListPrintersCommand extends Command{

	public ListPrintersCommand() {
		super("LISTPRINTERS", "", "USAGE: LISTPRINTERS","LISTA LOS TIPOS DE TABLERO O PINTADO DISPONIBLES");
	}

	@Override
	public void execute(Game game) {
		System.out.println("boardPrinter : prints the game formatted as a board of dimension: 9x8" + "\n" +
							"stringifier : prints the game as plain text" + "\n");
	}

	@Override
	public Command parse(String[] commandWords) {
	Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("listprinters")){
				c = new ListPrintersCommand();
			}
		}
		
		return c;
	}

}
