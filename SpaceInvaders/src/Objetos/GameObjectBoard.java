package Objetos;

import tp.p1.MyStringsUtils;

public class GameObjectBoard {
	private GameObject[] objects;
	private int currentObjects;
	private int remainingAliens;
	
	public GameObjectBoard () {
		objects = new GameObject[50];
		this.remainingAliens = 0;
		this.currentObjects = 0;
	}
	
	//metodos que se ejecutan en cada ciclo---------------------------------------------
	public void computerAction() {
		for(int i = 0; i < this.currentObjects;i++){
			if(this.objects[i].isAlive()){
				this.objects[i].computerAction();
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < this.currentObjects;i++){
			this.objects[i].update();
		}
		this.removeDead();
	}
	//----------------------------------------------------------------------------------
	
	public int getRemainingAliens() {
		return this.remainingAliens;
	}

	//metodo que se utiliza en el BoardInitializer
	public void AddRemainingAliens() {
		this.remainingAliens++;
	}
	
	//metodo que actualiza los aliens cuando se destruye uno
	public void LessRemainingAliens(){
		this.remainingAliens--;
	}

	//metodo que realiza el comando shockWave
	public void realizaShockWave() {
		for(int i = 0; i < this.currentObjects;i++){
			//solo se incrementan daño las AlienShip
			this.objects[i].dañoShockWave();
		}
	}
	
	//metodo que devuelve si quedan aliens
	public boolean allDead() {
		if(this.getRemainingAliens() == 0){
			return true;
		}
		return false;
	}

	public GameObject hayDistinctObjectSamePosition(GameObject object) {
		for(int i = 0; i < this.currentObjects;i++){
			if(this.objects[i] != object && this.objects[i].x == object.x && this.objects[i].y == object.y && this.objects[i].isAlive()){
				return this.objects[i];
			}
		}
		return null;
	}
	
	
	//metodos para añadir y eliminar objetos------------------------------------------
	
	public void add (GameObject object) {
		this.objects[this.currentObjects] = object;
		this.currentObjects++;
	}
	
	private void removeDead() {
		for(int i = 0; i < this.currentObjects;i++){
			if(!this.objects[i].isAlive()){
				this.objects[i].onDelete();
			
				if(this.objects[i].live != 999){
					this.objects[i] = null;
					
					for(int j = i; j < this.currentObjects-1;j++){
						this.objects[j] = this.objects[j+1];
					}
					
					this.currentObjects--;
					i--;
				}else{
					this.objects[i].live = 0;
				}
				
			}
		}
	}

	//--------------------------------------------------------------------------------------
	
	//metodos para obtener el objeto ------------------------------------------------
	
	public GameObject getObjectInPosition (int x, int y) {
		if(this.getIndex(x, y) != -1){
			return this.objects[this.getIndex(x, y)];
		}else{
			return null;
		}
	}
		
		//metodo que devuele el indice del elemento con posX y posY
		private int getIndex(int x, int y) {
			for(int i = 0; i < this.currentObjects;i++){
				if(this.objects[i].getX() == x && this.objects[i].getY() == y){
					return i;
				}
			}
			return -1;
		}
	//--------------------------------------------------------------------------------
	
	//Strings-------------------------------------------------------------------------------
	
	public String toString(){
		int numRows = 8; 
		int numCols = 9;
		String[][] board = new String[numRows][numCols];
		final String space = " ";
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
					board[i][j] = this.toString(i, j);
			}
		}
		
		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		String intersect = space;
		String vIntersect = space;
		String hIntersect = "-";
		String corner = space;
		
		String cellDelimiter = MyStringsUtils.repeat(hDelimiter, cellSize);
		
		String rowDelimiter = vIntersect + MyStringsUtils.repeat(cellDelimiter + intersect, numCols-1) + cellDelimiter + vIntersect;
		String hEdge =  corner + MyStringsUtils.repeat(cellDelimiter + hIntersect, numCols-1) + cellDelimiter + corner;
		
		String margin = MyStringsUtils.repeat(space, marginSize);
		String lineEdge = String.format("%n%s%s%n", margin, hEdge);
		String lineDelimiter = String.format("%n%s%s%n", margin, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineEdge);
		for(int i=0; i<numRows; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<numCols; j++)
					str.append( MyStringsUtils.centre(board[i][j], cellSize)).append(vDelimiter);
				if (i != numRows - 1) str.append(lineDelimiter);
				else str.append(lineEdge);	
		}
		
		return str.toString();
	}
	
	public String toString(int x, int y) {
		if(this.getIndex(x, y) != -1){
			return this.objects[this.getIndex(x, y)].toString();
		}else{
			return "";
		}
	}

	//metodo strings para serializar tablero-------------------------------------------------
	
	public String devuelveStringSerializado(/*String objeto*/) {
		String result = "";
		
		 for(int i = 0; i < this.currentObjects;i++){
			result += this.objects[i].stringify();
		 }
		
		
		return result;
	}

	//-----------------------------------------------------------------------------------------------

	public void AñadeRemainingAlien() {
		this.remainingAliens++;
	}

	public void reemplazaOject(GameObject object, int x, int y) {
		if(this.getIndex(x, y) != -1){
			this.objects[this.getIndex(x, y)] = object;
		}
	}
}
