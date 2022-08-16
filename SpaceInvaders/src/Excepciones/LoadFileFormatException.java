package Excepciones;

public class LoadFileFormatException extends CommandExecuteException{

	public LoadFileFormatException() {
		super("Error de formato al cargar el archivo");
	}

}
