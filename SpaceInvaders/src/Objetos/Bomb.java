package Objetos;

import tp.p1.Game;

public class Bomb extends Weapon {
	private DestroyerAlien nave;
	private boolean primerMove;
	
	public Bomb(Game game, int x, int y,DestroyerAlien nave) {
		super(game, x, y);
		this.daño = 1;
		this.live = 1;
		this.nave = nave;
		primerMove = true;
	}

	@Override
	public void computerAction() {
		if(primerMove){
			if(this.game.hayDistinctObjectSamePosition(this) != null){
				this.game.hayDistinctObjectSamePosition(this).getDamage(this.daño);
				this.live = 0;
			}
			primerMove = false;
		}else{
			if(game.hayObjectInPos(this.x +1, this.y) != null){
				game.hayObjectInPos(this.x +1, this.y).getDamage(this.daño);
				this.live = 0;
			}else{
				move(1,"abajo");
				if(!this.game.isOnBoard(x, y)){
					this.live = 0;
				}
			}
		}
	}

	@Override
	public void onDelete() {
		this.nave.setHayBomba(false);
	}

	@Override
	public void update() {
		//el metodo update de bomb no hace nada
	}
	
	@Override
	public void dañoShockWave() {
		//A las bombas no le daña el shockwave
		
	}
	
	//metodo que parsea las bombas para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		Bomb c = null;
		
		if(words.length == 5){
			if(words[0].equals("b")){
				c = new Bomb(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), (DestroyerAlien) juego.hayObjectInPos(Integer.parseInt(words[3]), Integer.parseInt(words[4])));
				c.nave.setHayBomba(true);
			}
		}
		
		return c;
	}
	
	//metodo que realiza el string de las bombas para el save
	@Override
	public String stringify() {
		return "B" + "," + this.x + "," + this.y + "," + this.nave.x + "," + this.nave.y + "\n";
	}
	
	@Override
	public String toString() {
		return ".";
	}
}
