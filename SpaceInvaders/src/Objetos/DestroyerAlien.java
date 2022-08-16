package Objetos;

import Interfaces.IExecuteRandomActions;
import tp.p1.Game;

public class DestroyerAlien extends AlienShip{
	private boolean hayBomba;
	
	public DestroyerAlien(Game game,int x, int y, int life) {
		super(game,x,y,life);
		hayBomba = false;
		this.daño = 0;
	}
	
	@Override
	public void computerAction() {
		this.avanza();
		if(!hayBomba){
			if(IExecuteRandomActions.canGenerateRandomBomb(game)){
				GameObject bomba = new Bomb(game,this.x+1,this.y,this);
				game.addObject(bomba);
				this.hayBomba = true;
			}
		}
	}
	
	@Override
	public void onDelete() {
		this.game.receivePoints(10);
		this.game.AlienShipEliminada();
	}
	 
	@Override
	public void update() {
		this.cambiaDirAlienShip();
	}
	
	//metodo que parsea las destroyerAlien para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		DestroyerAlien c = null;
		
		if(words.length == 7){
			if(words[0].equals("d")){
				c = new DestroyerAlien(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]));
				c.setDirActual(words[5]);
				c.setDirAnterior(words[6]);
				juego.AlienShipAñadida();
			}
		}
			
		return c;
	}
	
	public void setHayBomba(boolean hayBomba) {
		this.hayBomba = hayBomba;
	}
	
	@Override
	public void dañoShockWave() {
		this.live--;
	}
	
	//metodo que realiza el string de las alienShip para el save
	@Override
	public String stringify() {
		return "D" + "," + this.x + "," + this.y + "," + this.live + "," + this.NextCicloMoverse() + "," + this.dirActual + "," + this.dirAnterior + "\n";
	}
	
	@Override
	public String toString() {
		return "D"+"["+this.live + "]";
	}
}
