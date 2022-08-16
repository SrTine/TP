package Objetos;

import tp.p1.Game;

public class Ovni extends EnemyShip {

	public Ovni(Game game,int posX,int posY,int life) {
		super(game,posX,posY,life);//se inicializa a la pos (0,9) porque en el computerAction se pone en tablero
		this.daño = 0;
	}

	@Override
	public void computerAction() {
		move(1,"izquierda");
		if(!this.game.isOnBoard(x, y)){
			this.live = 0;
		}
	}
	
	@Override
	public void onDelete() {
		if(this.game.isOnBoard(x, y)){
			this.game.receivePoints(25);
			this.game.enableShockWave();
		}
		this.game.setHayOvni(false);
	}

	@Override
	public void update() {
		//el metodo update de Ovni no hace nada
	}
	
	//metodo que parsea el ovni para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		Ovni c = null;
		
		if(words.length == 4){
			if(words[0].equals("o")){
				c = new Ovni(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]));
				juego.setHayOvni(true);
			}
		}
			
		return c;
	}
	
	@Override
	public void dañoShockWave() {
		//Al ovni no le daña sockWave
		
	}
	
	//metodo que realiza el string del ovni para el save
	@Override
	public String stringify() {
		return "O" + "," + this.x + "," + this.y + "," + this.live + "\n";
	}
	
	@Override
	public String toString() {
		return "O"+"["+this.live + "]";
	}
}
