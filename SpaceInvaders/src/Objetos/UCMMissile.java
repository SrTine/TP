package Objetos;

import tp.p1.Game;

public class UCMMissile extends Weapon{
	protected UCMShip nave;
	private boolean primerMove;

	public UCMMissile(Game game, int x, int y,int daño, UCMShip nave) {
		super(game, x, y);
		this.live = 1;
		this.nave = nave;
		this.daño = daño;
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
			if(this.game.hayObjectInPos(this.x-1, this.y) != null){
				this.game.hayObjectInPos(this.x-1, this.y).getDamage(daño);
				this.live = 0;
				this.nave.setHayMisil(false);
			}else{
				move(1,"arriba");
				if(!this.game.isOnBoard(x, y)){
					this.live = 0;
				}
			}
		}
	}
	
	@Override
	public void onDelete() {
		this.nave.setHayMisil(false);
	}

	@Override
	public void update() {
		//el metodo update de misil no hace nada
	}
	
	//metodo que parsea el misil para el load
	@Override
	public GameObject parse(String[] words,Game juego) {
		UCMMissile c = null;
		
		if(words.length == 6){
			if(words[0].equals("m")){
				c = new UCMMissile(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]),juego.getPlayer());
				c.nave.setHayMisil(true);
			}
		}
			
		return c;
	}
	
	//metodo que realiza el string para el save
	@Override
	public String stringify() {
		return "M" + "," + this.x + "," + this.y + "," + this.daño + "," + this.nave.x + "," + this.nave.y + "\n";
	}
	
	@Override
	public String toString() {
		return "oo";
	}

	@Override
	public void dañoShockWave() {
		// Al misil no le hace daño shockWave
	}

}
