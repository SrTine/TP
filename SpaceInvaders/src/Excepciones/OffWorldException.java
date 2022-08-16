package Excepciones;

public class OffWorldException extends CommandExecuteException{
	public OffWorldException(){
		super("Cannot perform move: ship too near border");
	}
}
