package Command;

import Excepciones.OffWorldException;
import tp.p1.Game;

public class MoveCommand extends Command{
	private String dir;
	private int num;

	public MoveCommand(String dir,int num) {
		super("MOVE", "M", "USAGE: MOVE/M 1/2 LEFT/L OR RIGHT/R","MUEVE LA NAVE 1 o 2 POSICIONES A LA IZQUIERDA O DERECHA");
		this.dir = dir;
		this.num = num;
	}

	@Override
	public void execute(Game game) throws OffWorldException {
		game.move(num, dir);
		game.update();
		System.out.println(game.toString());
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 3){
			if(commandWords[0].equals("move") || commandWords[0].equals("m")){
				if(commandWords[1].equals("left") || commandWords[1].equals("l")){
					if(Integer.parseInt(commandWords[2]) > 0 && Integer.parseInt(commandWords[2]) < 3){
						c = new MoveCommand("left",Integer.parseInt(commandWords[2]));
					}
				}else if(commandWords[1].equals("right") || commandWords[1].equals("r")){
					if(Integer.parseInt(commandWords[2]) > 0 && Integer.parseInt(commandWords[2]) < 3){
						c = new MoveCommand("right",Integer.parseInt(commandWords[2]));
					}
				}
			}
		}
		
		return c;
	}
}
