package Objetos;

import tp.p1.Game;

public class ExplosiveAlien extends AlienShip{

	public ExplosiveAlien(Game game, int x, int y, int life,String dirActual,String dirAnterior) {
		super(game, x, y, life);
		this.dirActual = dirActual;
		this.dirAnterior = dirAnterior;
	}
	
	@Override
	public void computerAction() {
		this.avanza();
	}

	@Override
	public void update() {
		this.cambiaDirAlienShip();
		if(!this.isAlive()){
			if(this.game.hayObjectInPos(x-1, y) != null){
				this.game.hayObjectInPos(x-1, y).getDamage(1);
			}
			if(this.game.hayObjectInPos(x+1, y) != null){
				this.game.hayObjectInPos(x+1, y).getDamage(1);
			}
			if(this.game.hayObjectInPos(x, y-1) != null){
				this.game.hayObjectInPos(x, y-1).getDamage(1);
			}
			if(this.game.hayObjectInPos(x, y+1) != null){
				this.game.hayObjectInPos(x, y+1).getDamage(1);
			}
		}
	}

	@Override
	public void onDelete() {
		this.game.receivePoints(5);
		this.game.AlienShipEliminada();
	}
	
	//metodo ue parsea las explosiveAlien para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		ExplosiveAlien c = null;
		
		if(words.length == 7){
			if(words[0].equals("e")){
				c = new ExplosiveAlien(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]),words[5],words[6]);
				juego.AlienShipAñadida();
			}
		}
			
		return c;
	}
	
	@Override
	public void dañoShockWave() {
		this.live--;
	}
	
	//metodo que realiza el string de las explosiveAlien para el save
	@Override
	public String stringify() {
		return "E" + "," + this.x + "," + this.y + "," + this.live + "," + this.NextCicloMoverse() + "," + this.dirActual + "," + this.dirAnterior +"\n";
	}

	@Override
	public String toString() {
		return "E[" + this.live + "]";
	}
}
