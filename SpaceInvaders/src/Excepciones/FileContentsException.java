package Excepciones;

public class FileContentsException extends CommandExecuteException{

	public FileContentsException() {
		super("Error al cargar un objeto de un archivo");
	}
}
