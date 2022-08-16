package tp.p1;

import Excepciones.NoLevelParameterException;

public enum Level {
	
	EASY(4, 2, 0.1, 3, 0.5),
	HARD(8, 2, 0.3, 2, 0.2),
	INSANE(8, 4, 0.5, 1, 0.1);
	
	private int numRegularAliens;
	private int numDestroyerAliens;
	private double shootFrequency;
	private int numCyclesToMoveOneCell;
	private double ovniFrequency;
	
	private Level(
		int numRegularAliens,
		int numDestroyerAliens,
		double shootFrequency,
		int numCyclesToMoveOneCell,
		double ovniFrequency)
	{
		this. numRegularAliens = numRegularAliens;
		this. numDestroyerAliens = numDestroyerAliens;
		this. shootFrequency = shootFrequency;
		this. numCyclesToMoveOneCell = numCyclesToMoveOneCell;
		this. ovniFrequency = ovniFrequency;
	}
	
	//metodo estatico que parsea un string y lo convierte en level
	public static Level fromParam(String param) throws NoLevelParameterException {
		for (Level level : Level. values() )
		if (level.name().equalsIgnoreCase(param)) return level;
		throw new NoLevelParameterException();
	}
	
	public int getNumRegularAliens() {
		return numRegularAliens;
	}
		
	public int getNumDestroyerAliens() {
		return numDestroyerAliens;
	}
	
	public Double getShootFrequency() {
		return shootFrequency;
	}
	
	public int getNumCyclesToMoveOneCell() {
		return numCyclesToMoveOneCell;
	}
	
	public Double getOvniFrequency() {
		return ovniFrequency;
	}
	
	public int getNumDestroyerAliensPerRow() {
		return getNumDestroyerAliens();
	}
	
	public Double getTurnExplodeFreq(){
		return 0.05;
	}
}
