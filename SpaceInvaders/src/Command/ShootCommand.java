package Command;

import Excepciones.MissileInFlightException;
import Excepciones.NoEnoughSuperMissileException;
import tp.p1.Game;

public class ShootCommand extends Command{
	private String tipe;

	public ShootCommand(String tipo) {
		super("SHOOT", "S", "USAGE: SHOOT/S (supermisil/sp)","DISPARA UN MISIL (o un supermisil)");
		tipe = tipo;
	}

	@Override
	public void execute(Game game) throws MissileInFlightException, NoEnoughSuperMissileException{
		game.shootLaser(tipe);
		game.update();
		System.out.println(game.toString());
	}

	@Override
	public Command parse(String[] commandWords) {
	Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("shoot") || commandWords[0].equals("s")){
				c = new ShootCommand("normal");
			}
		}else if(commandWords.length == 2){
			if((commandWords[0].equals("shoot") || commandWords[0].equals("s")) && commandWords[1].equals("supermisil") || commandWords[1].equals("sp")){
				c = new ShootCommand("supermisil");
			}
		}
		
		return c;
	}
}
