package Interfaces;

import Excepciones.CommandExecuteException;
import Excepciones.NoShockWaveException;
import Excepciones.OffWorldException;

public interface IPlayerController {
	
	// PLAYER ACTIONS
	public void move (int numCells,String dir) throws OffWorldException;
	public void shootLaser(String tipo) throws CommandExecuteException;
	public void shockWave() throws NoShockWaveException;
	// CALLBACKS
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableMissile(String tipo);

}
