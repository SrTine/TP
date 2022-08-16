package Excepciones;

public class MissileInFlightException extends CommandExecuteException{
	public MissileInFlightException(){
		super("Cannot fire missile: missile already exists on board");
	}
}
