package Objetos;

import tp.p1.Game;

public class UCMShip extends Ship{
	private int puntos;
	private int superMisiles;
	private boolean hayMisil;
	private boolean hayShockWave;

	public UCMShip(Game game, int x, int y, int live) {
		super(game, x, y, live);
		puntos = 0;
		hayMisil = false;
		hayShockWave = false;
		superMisiles = 0;
	}
	
	//realiza el comando shoot
	public void dispara(String tipo) {
		setHayMisil(true);
		if(tipo.equals("normal")){
			GameObject misil = new UCMMissile(this.game,this.getX()-1,this.getY(),1,this);
			game.addObject(misil);
		}else if(tipo.equals("supermisil")){
			GameObject misil = new UCMSuperMissile(this.game,this.getX()-1,this.getY(),2,this);
			game.addObject(misil);
			this.superMisiles--;
		}
	}
	
	//realiza el comando move
	public boolean canMove(int numCells , String dir) {
		if(dir == "left"){
			if(game.isOnBoard(this.x,this.getY()-numCells)){
				this.y -= numCells;
				return true;
			}
		}else if(dir == "right"){
			if(game.isOnBoard(this.x,this.getY()+numCells)){
				this.y += numCells;
				return true;
			}
		}
		return false;
	}
	
	//metodo que parsea el player para load
	@Override
	public GameObject parse(String[] words,Game juego) {
		UCMShip c = null;
		
		if(words.length == 7){
			if(words[0].equals("p")){
				c = new UCMShip(juego, Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]));
				juego.setPlayer(c);
				c.setPuntos(Integer.parseInt(words[4]));
				if(words[5].equals("si")){
					c.setHayShockWave(true);
					juego.addObject(new ShockWave(game, -1, -1, c));
				}
				c.setSuperMisiles(Integer.parseInt(words[6]));
			}
		}
			
		return c;
	}
	
	@Override
	public void computerAction() {
		//el computer action del UCMShip no hace nada
	}
	
	@Override
	public void onDelete() {
		this.live = 999;//para que el UCMShip no se borre y se puede mostrar la XX, se restablece a 0 en el removeDead
	}
	
	@Override
	public void update() {
		//el metodo update de UCMShip no hace nada
	}
	
	@Override
	public void dañoShockWave() {
		// El shockWave no le hace daño a UCMShip
		
	}
	
	//Strings---------------------------------------------------------------------------
	
	@Override
	public String toString() {
		if(this.isAlive()){
			return "^__^";
		}else{
			return "XX";
		}
	}
	
	public String stateToString() {
		return "Life: " + this.live + "\n" + "Points: " + this.puntos + "\n" + "ShockWave: " + this.stringShockWave() +"\n" + "SuperMisiles: " + this.superMisiles + "\n";
	}
	
	public String stringShockWave(){
		if(hayShockWave){
			return "SI";
		}else{
			return "NO";
		}
	}
	
	//metodo que realiza el string para el save
	@Override
	public String stringify() {
		return "P" + "," + this.x + "," + this.y + "," + this.live + "," + this.puntos + "," + this.stringShockWave() + "," + this.superMisiles + "\n";
	}
	
	//------------------------------------------------------------------------------------------
	
	//getters and setters-------------------------------------------------------------

	public int getPuntos(){
		return this.puntos;
	}
	
	public void setPuntos(int puntos) {
		this.puntos += puntos;
	}

	public boolean isHayMisil() {
		return hayMisil;
	}

	public void setHayMisil(boolean hayMisil) {
		this.hayMisil = hayMisil;
	}

	public boolean isHayShockWave() {
		return hayShockWave;
	}

	public void setHayShockWave(boolean hayShockWave) {
		this.hayShockWave = hayShockWave;
	}

	public int getSuperMisiles() {
		return superMisiles;
	}

	public void setSuperMisiles(int superMisiles) {
		this.superMisiles = superMisiles;
	}
}
