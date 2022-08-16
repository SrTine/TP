package Objetos;

import tp.p1.Game;

public abstract class Ship extends GameObject{
	
	public Ship(Game game, int x, int y, int live) {
		super(game, x, y);
		this.live = live;
	}
}
