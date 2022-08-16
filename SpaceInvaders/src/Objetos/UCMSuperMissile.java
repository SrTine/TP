package Objetos;

import tp.p1.Game;

public class UCMSuperMissile extends UCMMissile{

	public UCMSuperMissile(Game game, int x, int y, int daño, UCMShip nave) {
		super(game, x, y, daño, nave);
	}

	@Override
	public String stringify() {
		return "SPM" + "," + this.x + "," + this.y + "," + this.daño + "," + this.nave.x + "," + this.nave.y + "\n";
	}

	@Override
	public GameObject parse(String[] words, Game juego) {
		UCMSuperMissile c = null;
		
		if(words.length == 6){
			if(words[0].equals("spm")){
				c = new UCMSuperMissile(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]),juego.getPlayer());
				c.nave.setHayMisil(true);
			}
		}
			
		return c;
	}

}
