package tp.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import tp.p1.Level;
import Excepciones.FileContentsException;
import Excepciones.MissileInFlightException;
import Excepciones.NoEnoughPointsException;
import Excepciones.NoEnoughSuperMissileException;
import Excepciones.NoLevelParameterException;
import Excepciones.NoShockWaveException;
import Excepciones.OffWorldException;
import Interfaces.IExecuteRandomActions;
import Interfaces.IPlayerController;
import Objetos.BoardInitializer;
import Objetos.GameObject;
import Objetos.GameObjectGenerator;
import Objetos.Ovni;
import Objetos.ShockWave;
import Objetos.UCMShip;
import Objetos.GameObjectBoard;

	public class Game implements IPlayerController{
		public final static int DIM_X = 8;
		public final static int DIM_Y = 9;
		
		private int currentCycle;
		private Random rand;
		private Level level;
		
		GameObjectBoard board;
		
		private UCMShip player;
		
		private boolean doExit;
		private BoardInitializer initializer ;
		
		private boolean isLanded;//argumento para saber si han llegado los aliens a la fila del jugador
		private boolean hayOvni;//argumento para saber si ya hay un ovni en el tablero
		private boolean choqueFrontera;//argumento para saber si los aliens han llegado a los extremos
		
		public Game (Level levl, Random random){
			rand = random;
			level = levl;
			initializer = new BoardInitializer();
			board = new GameObjectBoard();
			initGame();
		}
	
		//constructora empleada en el comando load para instanciar un game desde un archivo
		public Game(BufferedReader inStream, Random ran) throws FileContentsException, NoLevelParameterException {
			this.rand = ran;
			try {
				this.currentCycle = this.parseaCiclosLoad(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.level = Level.fromParam(this.parseaLevelLoad(inStream));
			
			this.board = new GameObjectBoard();
			this.setChoqueFrontera(false);
		}

		public void initGame () {
			currentCycle = 1;
			isLanded = false;
			hayOvni = false;
			board = initializer.initialize(this, level );
			player = new UCMShip(this,7,4,3);
			board.add(player);
			setChoqueFrontera(false);
		}
		
		public void update() {
			generaOvni();
			board.computerAction();
			board.update();
			setChoqueFrontera(false);
			currentCycle += 1;
		}
		
		private void generaOvni(){
			if(!this.hayOvni){
				if(IExecuteRandomActions.canGenerateRandomOvni(this)){
					GameObject ovni = new Ovni(this,0,9,1);
					this.addObject(ovni);
					hayOvni = true;
				}
			}
		}
		
		public void reset() {
			initGame();
		}
		
		//metodo que implementa el comando exit
		public void exit() {
			doExit = true;
		}
		
		//metodo que implementa el comando load
		public Game load(BufferedReader inStream) throws FileContentsException, NoLevelParameterException {
			Game aux = new Game(inStream,this.rand);
			String line = null;
			String[] words;
			
			try {
				line = inStream.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			while( line != null && !line.isEmpty() ) {
				words = line.toLowerCase().trim().split(",");
				GameObject gameObject = GameObjectGenerator.parse(words,aux);
				if (gameObject == null) {
					throw new FileContentsException();
				}
				aux.board.add(gameObject);
				try {
					line = inStream.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("load realizado correctamente");
			return aux;
		}
		
		//metodo que se utiliza en la constructora de load para crear un juego desde un archivo para instanciar el level
		private String parseaLevelLoad(BufferedReader inStream) throws FileContentsException {
			String line;
			String[] words = null;
			try {
				line = inStream.readLine().trim();
				words = line.toLowerCase().trim().split(",");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(words.length == 2){
				if(words[0].equals("l")){
					return (words[1]);
				}
			}
			throw new FileContentsException();
		}
	
		//metodo que se utiliza en la constructora de load para crear un juego desde un archivo para instanciar los ciclos
		private int parseaCiclosLoad(BufferedReader inStream) throws IOException, FileContentsException {
			String line;
			String[] words;
			line = inStream.readLine().trim();
			words = line.toLowerCase().trim().split(",");
			
			if(words.length == 2){
				if(words[0].equals("g")){
					return Integer.parseInt(words[1]);
				}
			}
			throw new FileContentsException();
		}
	
		//metodo que implementa el comando ComprarCommand
		public void compraSuperMisil() throws NoEnoughPointsException{
			if(this.player.getPuntos() >= 20){
				this.player.setPuntos(-20);
				this.player.setSuperMisiles(this.player.getSuperMisiles()+1);
			}else{
				throw new NoEnoughPointsException();
			}
		}
		
		//metodos que llaman al board para consultar o añadir objetos----------------------------------
		
		public void addObject(GameObject object) {
			board.add(object);
		}
		
		//metodo utilizado para reemplazar la regularAlien por la explosiveAlien
		public void reemplazaObject(GameObject object, int x, int y) {
			this.board.reemplazaOject(object,x,y);
		}
		
		//metodo que devuelve si la pos(x,y) esta en el tablero
		public boolean isOnBoard(int x, int y) {
			return x >= 0 && y >= 0 && x < DIM_X && y < DIM_Y;
		}
		
		//metodo que devuelve el GameObject en la pos(x,y)
		public GameObject hayObjectInPos(int x, int y){
			return board.getObjectInPosition(x, y);
		}
	
		//metodo que llaman las alienShip desspues de desplazarse para ver si se intercalan con el misil
		public GameObject hayDistinctObjectSamePosition(GameObject object) {
			return this.board.hayDistinctObjectSamePosition(object);
		}
		//----------------------------------------------------------------------------------
		
		//metodos que comprueban las condiciones para que termine el juego-----------------------------
			public boolean isFinished() {
				return playerWin() || aliensWin() || doExit;
			}
			
			public boolean aliensWin() {
				return !player.isAlive () || this.isLanded;
			}
			
			private boolean playerWin () {
				return board.allDead();
			}
		//----------------------------------------------------------------------------------------------
		
		//Strings que se muestran en el juego---------------------------------------
		
		public String infoToString() {
			return "Cycles: " + currentCycle + "\n" + player.stateToString() + "Remaining aliens: " + (board.getRemainingAliens()) + "\n";
		}
		
		public String toString() {
			return this.infoToString() + board.toString();
		}
		
		//metodo que se llama al salir del bucle del controler
		public String getWinnerMessage () {
			if (this.playerWin()) return "Player win!";
			else if (this. aliensWin()) return "Aliens win!";
			else return "Player exits the game";
		}
		
		//muestra info de naves (comando list)
		public void muestraList() {
			System.out.println(	"[R]egular ship: Points: 5 - Harm: 0 - Shield: 2" + "\n" +
								"[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1" + "\n" +
								"[O]vni: Points: 25 - Harm: 0 - Shield: 1" + "\n" + 
								"^__^: Harm: 1 - Shield: 3" + "\n") ;
			
		}
		
		//Metodo que implementa el comando stringify
		public String stringify() {
			String result = "";
			
			result += "— Space Invaders v2.0 —\n\n";
			result += "G" + "," + this.currentCycle + "\n";
			result += "L" + "," + this.level.name() + "\n";
			
			result += this.board.devuelveStringSerializado();
			
			return result;
		}
		
		// PLAYER ACTIONS----------------------------------------------
	
		@Override
		public void move(int numCells,String dir) throws OffWorldException {
			if(!player.canMove(numCells,dir)){
				throw new OffWorldException();
			}
		}
	
		@Override
		public void shootLaser(String tipo) throws MissileInFlightException, NoEnoughSuperMissileException {
			if(!this.player.isHayMisil()){
				switch(tipo){
					case "normal":{
						this.enableMissile("normal");
					}break;
					case "supermisil":{
						if(this.player.getSuperMisiles() > 0){
							this.enableMissile("supermisil");
						}else{
							throw new NoEnoughSuperMissileException();
						}
					}break;
				}
			}else{
				throw new MissileInFlightException();
			}
		}
	
		@Override
		public void shockWave() throws NoShockWaveException {
			if(this.player.isHayShockWave()){
				this.board.realizaShockWave();
			}else{
				throw new NoShockWaveException();
			}
		}
		
	
		
		//--------------------------------------------------------------
		
		// CALLBACKS---------------------------------------------------
	
		@Override
		public void receivePoints(int points) {
			this.player.setPuntos(points);
		}
	
		//habilita shockWave
		@Override
		public void enableShockWave() {
			if(!this.player.isHayShockWave()){
				this.player.setHayShockWave(true);
				GameObject shockWave = new ShockWave(this,-1,-1,this.player);
				this.board.add(shockWave);
			}
		}
	
		//habilita misil
		@Override
		public void enableMissile(String tipo) {
			player.dispara(tipo);
		}
		//---------------------------------------------------------------
		
		public Random getRandom() {
			return rand;
		}
		
		public Level getLevel() {
			return level;
		}
	
		public boolean isLanded() {
			return isLanded;
		}
	
		public void setLanded(boolean isLanded) {
			this.isLanded = isLanded;
		}
	
		public boolean isHayOvni() {
			return hayOvni;
		}
	
		public void setHayOvni(boolean hayOvni) {
			this.hayOvni = hayOvni;
		}
	
		public void AlienShipEliminada() {
			this.board.LessRemainingAliens();
		}
		
		public void AlienShipAñadida(){
			this.board.AñadeRemainingAlien();
		}
	
		public int getCurrentCycle() {
			return currentCycle;
		}
	
		public void setPlayer(UCMShip c) {
			this.player = c;
		}
	
		public UCMShip getPlayer() {
			return this.player;
		}

		public boolean isChoqueFrontera() {
			return choqueFrontera;
		}

		public void setChoqueFrontera(boolean choqueFrontera) {
			this.choqueFrontera = choqueFrontera;
		}
	}
