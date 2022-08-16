package Command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import tp.p1.Game;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Excepciones.IlegalArgumentException;

public class SaveCommand extends Command{
	private String fileName;

	public SaveCommand(String filename) {
		super("SAVE", "", "USAGE: SAVE <FILENAME>", "SAVE THE PLAY IN THE FILE <FILENAME>");
		this.fileName = filename;
	}

	@Override
	public void execute(Game game) throws CommandExecuteException {
		if(fileName != null){
			try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))){
				out.write(game.stringify());
				System.out.println("Game successfully saved in file " + this.fileName + ". Use the load command to reload it");
			} catch (Exception e) {
				System.out.println("Error al escribir en filename");
			}
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		Command c = null;
		
		if(commandWords[0].equalsIgnoreCase("save")) {
			if(commandWords.length == 1) {
				throw new IlegalArgumentException("save command needs a filename argument");
			}else if(commandWords.length == 2){
					fileName = confirmFileNameStringForWrite(commandWords[1], new Scanner(System.in));
					c = new SaveCommand(fileName);
			}else{
				throw new IlegalArgumentException("Command save admit only one argument");
			}
		}
		return c;
	}

	//metodo que comprueba que exista el archivo o no
	private String confirmFileNameStringForWrite(String string, Scanner in) throws IlegalArgumentException {
		String loadName = null;
		boolean filename_confirmed = false;
		while (!filename_confirmed){
			File file = new File(string);
				if (!file.exists()){
					filename_confirmed = true;
					loadName = string;
				}
				else {
					filename_confirmed = true;
					loadName = getSaveName(string, in);
				}
		}
		return loadName;
	}

	//metodo que sobreescribe el archivo o no(a eleccion del jugador)
	private String getSaveName(String string, Scanner in) throws IlegalArgumentException {
		String newFilename = null;
		boolean yesOrNo = false;
		while (!yesOrNo) {
			System.out.print("The file already exists; do you want to overwrite it? (Y/N)" + ": ");
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1) {
				switch (responseYorN[0]) {
				case "y":
					yesOrNo = true;
					try (BufferedWriter out = new BufferedWriter(new FileWriter(string))){
						out.write("");
					} catch (Exception e){
						System.out.println("Error al sobreescribir el archivo");
					}
					newFilename = string;
					break;
				case "n":
					yesOrNo = true;
					System.out.println("Please, execute the command save and other filename");
					break;
				default:
					System.out.println("Wrong answer, please enter (Y/N)");
				}
			} else {
				throw new IlegalArgumentException("The answer must be (Y/N)" + "\n");
			}
		}
		return newFilename;
	}

}
