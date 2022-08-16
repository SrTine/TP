package Excepciones;

public class UnknownCommandException extends CommandParseException{

	public UnknownCommandException() {
		super("The inserted command doesnt exists");
	}
}
