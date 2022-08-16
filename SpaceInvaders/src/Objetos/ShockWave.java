package Objetos;

import tp.p1.Game;

public class ShockWave extends Weapon{
	private UCMShip nave;

	public ShockWave(Game game, int x, int y,UCMShip nave) {
		super(game, x, y);
		this.daño = 1;
		this.live = 1;
		this.nave = nave;
	}

	@Override
	public void computerAction() {
		//el metodo computerAction de shockWave no hace nada
	}

	@Override
	public void onDelete() {
		this.nave.setHayShockWave(false);
	}

	@Override
	public void update() {
		//el metodo update de shockwave no hace nada
	}
	
	@Override
	public GameObject parse(String[] words,Game juego) {
		//El shockWave no tiene parse de load porque no se pasa un objeto
		//si no que se pasa un argumento en el objeto Player
		return null;
	}
	
	@Override
	public String stringify() {
		//El string serializado de shockWave no devuelve nada
		//porque se pasa como un argumento de player
		return "";
	}
	
	@Override
	public String toString() {
		return "";
	}

	@Override
	public void dañoShockWave() {
		this.live = 0;
		//como unicamente se nos permite tener una shockwave, le asignamos su vida a 0 para que se borre del G0B
	}

}
