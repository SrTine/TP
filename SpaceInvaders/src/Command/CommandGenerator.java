package Command;

import Excepciones.CommandParseException;
import Excepciones.UnknownCommandException;

public class CommandGenerator {
	private static Command[] availableCommands = {
		new ListCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new ListCommand(),
		new ListPrintersCommand(),
		new MoveCommand("",0),
		new ShockwaveCommand(),
		new NoneCommand(),
		new ShootCommand(""),
		new ComprarCommand(""),
		new StringifyCommand(),
		new SaveCommand(""),
		new LoadCommand("")
		};
	
	public static Command parseCommand(String[ ] commandWords) throws CommandParseException{
		Command command = null;
		for (Command c: availableCommands) {
				command = c.parse(commandWords) ;
			if (command != null)
				return command;
		}
		throw new UnknownCommandException();
	}
	
	public static String commandHelp(){
		String str = "";
		for (Command command: availableCommands) {
			str += command.helpText();
		}
		return str;
	}
}
