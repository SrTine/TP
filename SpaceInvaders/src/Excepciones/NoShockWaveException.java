package Excepciones;

public class NoShockWaveException extends CommandExecuteException{
	public NoShockWaveException(){
		super("Cannot release shockwave: no shockwave available");
	}
}
