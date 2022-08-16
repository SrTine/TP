package Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import tp.p1.Game;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Excepciones.FileNotFoundException;
import Excepciones.IlegalArgumentException;
import Excepciones.LoadFileFormatException;

public class LoadCommand extends Command{
	private String fileName;

	public LoadCommand(String filename) {
		super("LOAD", "", "USAGE: LOAD <FILENAME>", "LOAD THE PLAY IN THE FILE <FILENAME>");
		this.fileName = filename;
	}

	@Override
	public void execute(Game game) throws CommandExecuteException {
		if(fileName != null){
			try (BufferedReader inStream = new BufferedReader(new FileReader(fileName))) {
				String line = inStream.readLine();
				if (!line.equalsIgnoreCase("— Space Invaders v2.0 —")) throw new LoadFileFormatException();
					
				line = inStream.readLine();
	
				if(line.length() != 0) throw new LoadFileFormatException();
				
				game = game.load(inStream);
				
				System.out.println(game.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command c = null;
		
		if (commandWords[0].equalsIgnoreCase("load")) {
			if(commandWords.length == 1) {
				throw new IlegalArgumentException("load command needs a filename argument");
			}else if(commandWords.length == 2){
			fileName = confirmFileNameStringForRead(commandWords[1], new Scanner(System.in));
			c = new LoadCommand(fileName);
			}else{
				throw new IlegalArgumentException("Command load admit only one argument");
			}
		}
		return c;
	}
	
	//metodo que comprueba que exista el archivo a cargar
	private String confirmFileNameStringForRead(String filenameString, Scanner in) throws FileNotFoundException {
		String loadName = null;
		boolean filename_confirmed = false;
		
		while (!filename_confirmed) {
			File file = new File(filenameString);
			if(file.exists()){
				filename_confirmed = true;
				loadName = filenameString;
			}else {
				filename_confirmed = true;
			}
		}
		
		if(loadName == null){
			throw new FileNotFoundException();
		}
		return loadName;
	}

}
