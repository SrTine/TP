package Objetos;

import tp.p1.Game;
import Interfaces.IExecuteRandomActions;

public class RegularAlien extends AlienShip{
	
	public RegularAlien(Game game, int fila, int columna, int life) {
		super(game,fila,columna,life);
		this.daño = 0;
	}

	@Override
	public void computerAction(){
		this.avanza();
		if(IExecuteRandomActions.canTransformateExplosiveShip(game)){
			game.reemplazaObject(new ExplosiveAlien(this.game,this.x,this.y,this.live,this.dirActual,this.dirAnterior),this.x,this.y);
		}
	}
	
	@Override
	public void onDelete() {
		this.game.receivePoints(5);
		this.game.AlienShipEliminada();
	}

	@Override
	public void update() {
		this.cambiaDirAlienShip();
	}
	
	//metodo que parsea las regularAlien para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		RegularAlien c = null;
		
		if(words.length == 7){
			if(words[0].equals("r")){
				c = new RegularAlien(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]));
				c.setDirActual(words[5]);
				c.setDirAnterior(words[6]);
				juego.AlienShipAñadida();
			}
		}
			
		return c;
	}
	
	@Override
	public void dañoShockWave() {
		this.live--;
	}
	
	//metodo que realiza el string de las regularAlien para el save
	@Override
	public String stringify() {
		return "R" + "," + this.x + "," + this.y + "," + this.live + "," + this.NextCicloMoverse() + "," + this.dirActual + "," + this.dirAnterior +"\n";
	}
	
	@Override
	public String toString() {
		return "C"+"["+this.live + "]";
	}
}
