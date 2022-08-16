package Objetos;

import tp.p1.Game;
import Excepciones.FileContentsException;

public class GameObjectGenerator {
	private static GameObject[] availableGameObjects = {
		new UCMShip(null, 0, 0, 0),
		new Ovni(null, 0, 0, 0),
		new RegularAlien(null, 0, 0, 0),
		new DestroyerAlien(null, 0, 0, 0),
		new ExplosiveAlien(null, 0, 0, 0, null, null),
		new ShockWave(null, 0, 0, null),
		new Bomb(null, 0, 0, null),
		new UCMMissile(null, 0, 0, 0, null),
		new UCMSuperMissile(null, 0, 0, 0, null)
		};
	
	public static GameObject parse(String[] stringFromFile,Game game) throws FileContentsException {
		GameObject gameObject = null;
		
		for (GameObject go: availableGameObjects) {
			gameObject = go.parse(stringFromFile,game);
			if (gameObject != null) break;
		}
		
		if( gameObject == null){
			throw new FileContentsException();
		}
		return gameObject;
	}
}
