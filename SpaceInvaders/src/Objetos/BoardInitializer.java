package Objetos;

import tp.p1.Game;
import tp.p1.Level;
import Interfaces.IExecuteRandomActions;
import Objetos.GameObjectBoard;

//clase que sirve para inicializar el tablero al inicio y cuando reset

public class BoardInitializer {
	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this. game = game;
		
		board = new GameObjectBoard();
		
		initializeOvni ();
		initializeRegularAliens ();
		initializeDestroyerAliens ();
		
		return board;
	}
	
	private void initializeOvni () {
		if(IExecuteRandomActions.canGenerateRandomOvni(game)){
			GameObject ovni = new Ovni(this.game,0,9,1);
			this.game.addObject(ovni);
		}
	}
	
	private void initializeRegularAliens () {

		if(this.level.name() == "EASY"){
			for(int i = 0; i < this.level.getNumRegularAliens();i++){
				int fila = 1;
				int columna = 3 + i;
				this.board.add(new RegularAlien(this.game,fila,columna,2));
				this.board.AddRemainingAliens();
			}
		}else if(this.level.name() == "HARD"){
			for(int i = 0; i < this.level.getNumRegularAliens()/2;i++){
				int fila = 1;
				int columna = 3 + i;
				this.board.add(new RegularAlien(this.game,fila,columna,2));
				this.board.AddRemainingAliens();
			}
			for(int i = 0; i < this.level.getNumRegularAliens()/2;i++){
				int fila = 2;
				int columna = 3 + i;
				this.board.add(new RegularAlien(this.game,fila,columna,2));
				this.board.AddRemainingAliens();
			}
		}else if(this.level.name() == "INSANE"){
			for(int i = 0; i < this.level.getNumRegularAliens()/2;i++){
				int fila = 1;
				int columna = 3 + i;
				this.board.add(new RegularAlien(this.game,fila,columna,2));
				this.board.AddRemainingAliens();
			}
			for(int i = 0; i < this.level.getNumRegularAliens()/2;i++){
				int fila = 2;
				int columna = 3 + i;
				this.board.add(new RegularAlien(this.game,fila,columna,2));
				this.board.AddRemainingAliens();
			}
		}
		
		
	}
	private void initializeDestroyerAliens () {
		
		if(this.level.name() == "EASY"){
			for(int i = 0; i < this.level.getNumDestroyerAliens();i++){
				int fila = 2;
				int columna = 4 + i;
				this.board.add(new DestroyerAlien(this.game,fila,columna,1));
				this.board.AddRemainingAliens();
			}
		}else if(this.level.name() == "HARD"){
			for(int i = 0; i < this.level.getNumDestroyerAliens();i++){
				int fila = 3;
				int columna = 4 + i;
				this.board.add(new DestroyerAlien(this.game,fila,columna,1));
				this.board.AddRemainingAliens();
			}
		}else if(this.level.name() == "INSANE"){
			for(int i = 0; i < this.level.getNumDestroyerAliens();i++){
				int fila = 3;
				int columna = 3 + i;
				this.board.add(new DestroyerAlien(this.game,fila,columna,1));
				this.board.AddRemainingAliens();
			}
		}
	}

}
