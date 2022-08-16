package Command;

import Excepciones.NoEnoughPointsException;
import tp.p1.Game;

public class ComprarCommand extends Command{
	String compra;
	public ComprarCommand(String compra) {
		super("Comprar supermisil", "c sp/supermisil", "USAGE: C SUPERMISIL/SP","COMPRA UN SUPERMISIL POR 20 PUNTOS");
		this.compra = compra;
	}

	@Override
	public void execute(Game game) throws NoEnoughPointsException {
		if(compra.equals("supermisil")){
			game.compraSuperMisil();
			game.update();
			System.out.println(game.toString());
		}
	}

	@Override
	public Command parse(String[] commandWords){
	Command c = null;
		
		if(commandWords.length == 2){
			if(commandWords[0].equals("c") && commandWords[1].equals("sp") || commandWords[1].equals("supermisil")){
				c = new ComprarCommand("supermisil");
			}
		}
		
		return c;
	}

}
