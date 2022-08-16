package Interfaces;

import tp.p1.Game;

public interface IExecuteRandomActions {
	static boolean canGenerateRandomOvni(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getOvniFrequency();
	}
	
	static boolean canGenerateRandomBomb(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
	}
	
	static boolean canTransformateExplosiveShip(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getTurnExplodeFreq();
	}
}
