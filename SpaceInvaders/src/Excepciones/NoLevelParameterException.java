package Excepciones;

public class NoLevelParameterException extends Exception{
	public NoLevelParameterException(){
		super("Usage: Main <EASY|HARD|INSANE> [seed]: the level must be a string like <EASY|HARD|INSANE>");
	}
}
