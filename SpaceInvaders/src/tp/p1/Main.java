package tp.p1;

import java.util.Random;

import Command.Controller;
import Excepciones.NoLevelParameterException;

/*
Practica 2(Space Invaders)
Pablo Martinez Domingo
Grupo: Gul'dan
 */

public class Main {

	public static void main(String[] args) {
		String nivel  = null;
		long seed = 123;
		Level level = null;
		try{
			if(args.length == 2){
				nivel = args[0];
				try{
					seed = Long.parseLong(args[1]);
				}catch(RuntimeException ex){
					System.out.println("Usage: Main <EASY|HARD|INSANE> [seed]: the seed must be a number");
					System.out.println("Se usara como semilla: 123");
					seed = 123;
				}
				
				try {
					level = Level.fromParam(nivel);
				} catch (NoLevelParameterException e) {
					System.out.println(e.getMessage());
					System.out.println("Se usara como nivel: EASY");
					level = Level.EASY;
				}
				
			}else{
				seed = 123;
				level = Level.EASY;
				throw new RuntimeException("Usage: Main <EASY|HARD|INSANE> [seed]");
			}
		}catch(RuntimeException ex){
			System.out.println(ex.getMessage());
			System.out.println("Se usara como valor de semilla: 123 y como nivel: EASY");
		}finally{
			Random ran = new Random(seed);
			Game game = new Game(level,ran);
			Controller control = new Controller(game);
			
			control.run();
		}
	}
}
