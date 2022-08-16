package Excepciones;

public class NoEnoughSuperMissileException extends CommandExecuteException{

	public NoEnoughSuperMissileException() {
		super("Cannot fire supperMissile: not have supperMissile to fire");
	}

}
