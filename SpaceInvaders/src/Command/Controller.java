package Command;

import java.util.Scanner;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import tp.p1.Game;;

public class Controller {
	private Game game;
	private Scanner sc;

	public Controller(Game juego) {
		this.game = juego;
		this.sc = new Scanner(System.in);
	}	
	
	public void run() {		
		System.out.println(game.toString());
		
		while (!game.isFinished()){
			System.out.print("Command > ");
			String[] words = sc.nextLine().toLowerCase().trim().split ("\\s+");
			try {
				Command command = CommandGenerator.parseCommand(words);
				if (command != null) {
					command.execute(game);
				}
			}catch(CommandParseException | CommandExecuteException ex){
				System.out.println(ex);
			}
		}
		System.out.println(game.getWinnerMessage());
	}
}
