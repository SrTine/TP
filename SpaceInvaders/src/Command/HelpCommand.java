package Command;

import tp.p1.Game;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("HELP", "H", "USAGE: HELP/H","MUESTRA UN MENSAJE DE AYUDA");
	}

	@Override
	public void execute(Game game) {
		 Command[] availableCommands = {
				new ListCommand(),
				new HelpCommand(),
				new ResetCommand(),
				new ExitCommand(),
				new ListCommand(),
				new ListPrintersCommand(),
				new MoveCommand("",0),
				new ShockwaveCommand(),
				new ShootCommand(""),
				new ComprarCommand(""),
				new StringifyCommand(),
				new SaveCommand(""),
				new LoadCommand("")
		};
		
			for (Command c: availableCommands) {
				System.out.println(c.helpText() + "\n");
			}
	}

	@Override
	public Command parse(String[] commandWords) {
		Command c = null;
		
		if(commandWords.length == 1){
			if(commandWords[0].equals("help") || commandWords[0].equals("h")){
				c = new HelpCommand();
			}
		}
		
		return c;
	}
}
