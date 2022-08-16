package Excepciones;

public class NoEnoughPointsException extends CommandExecuteException{

	public NoEnoughPointsException() {
		super("Cannot buy superMissile: not have enough points to buy");
	}

}
