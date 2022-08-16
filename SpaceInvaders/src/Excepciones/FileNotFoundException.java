package Excepciones;

public class FileNotFoundException extends CommandParseException{

	public FileNotFoundException() {
		super("The file doesnt exists");
	}

}
