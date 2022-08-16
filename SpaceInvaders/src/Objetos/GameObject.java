package Objetos;

import tp.p1.Game;

public abstract class GameObject{
	protected int x;
	protected int y;
	protected int live;
	protected int daño;
	protected Game game;

	public GameObject( Game game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	//metodo comun a todos los gameObject que mueve el objeto
	public  void move(int i, String dir){
		switch(dir){
			case "izquierda":this.y -= i;break;
			case "derecha":this.y += i;break;
			case "abajo":this.x += i;break;
			case "arriba":this.x -= i;break;
		default:
		}
	}
	
	//getters------------------------------------------------------
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getLive() {
		return this.live;
	}
	
	public void getDamage (int damage) {
		if(damage > this.live){
			this.live = 0;
		}else{
			this.live = this.live - damage;
		}
	}
	//-----------------------------------------------------------------
	
	//Booleanos para comprobar el objeto--------------------------------
	
	public boolean isAlive() {
		return this.live > 0;
	}
	
	public boolean isOnPosition(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public boolean isOut() {
		return !game.isOnBoard(x, y);
	}
	//-------------------------------------------------------------------
	
	//metodos abstractos que implementan las subclases
	public abstract void update();//metodo llamado en el update del board (en el se actualizan ciertas cosas de ciertos objetos)
	public abstract void computerAction();//metodo llamado en el computerAction de board ( se realizan las acciones intutivas de los objetos)
	public abstract void onDelete();//metodo llamado en el update de board (realiza ciertas acciones del objeto al eliminarse)
	public abstract String toString();//metodo llamado en el toSring de board para mostrar la interfaz de string del objeto
	public abstract String stringify();//metodo llamado en el toStringSerializado de board para mostrar el string serializado del objeto
	public abstract GameObject parse(String[] stringFromFile, Game game);//metodo llamado en el parse de GameObjectGenerator para verificar si es ese obeto
	public abstract void dañoShockWave();//metodo que daña añ objeto por el shockwaave dependiendo del objeto
}
