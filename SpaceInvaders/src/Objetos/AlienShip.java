package Objetos;

import tp.p1.Game;

public abstract class AlienShip extends EnemyShip{
	public String dirActual;
	public String dirAnterior;
	
	public AlienShip(Game game, int x, int y, int life) {
		super(game,x,y,life);
		dirActual = "izquierda";
		dirAnterior = "derecha";
	}
	
	//metodo comun que mueve las alienship
	public boolean avanza(){
		boolean result = false;
		
		if(this.NextCicloMoverse() == 0){
			move(1,this.dirActual);
			if(this.y == 0 || this.y == 8){
				this.game.setChoqueFrontera(true);
			}
			if(this.haveLanded()){
				this.game.setLanded(true);
			}
			result = true;
		}
		
		return result;
	}
	
	//metodo que comprueba si el alien ha llegado a la linea del UCMShip
	public boolean haveLanded(){
		if(this.x >= 7 ){
			return true;
		}else{
			return false;
		}
	}
	
	//metodo que decide si se mueven los aliens(dependiendo del level y su ciclo de moimiento)
	public int NextCicloMoverse() {
		return this.game.getCurrentCycle()%this.game.getLevel().getNumCyclesToMoveOneCell();
	}
	
	//metodo que actualiza la dir de los aliens
	public void cambiaDirAlienShip(){
		if(this.NextCicloMoverse() == 0){
			if(this.game.isChoqueFrontera()){
				 if(!dirActual.equals("abajo") ){
				 	dirAnterior = dirActual;
				 	dirActual = "abajo";
				 }else{
				 	if(dirAnterior.equals("izquierda")){
				 		dirActual = "derecha";
				 	}
				 	else{
				 		dirActual = "izquierda";
				 	}
				 }
			}
		}
	}
	
	public void setDirActual(String dir){
		this.dirActual = dir;
	}
	
	public void setDirAnterior(String dir){
		this.dirAnterior = dir;
	}
}
