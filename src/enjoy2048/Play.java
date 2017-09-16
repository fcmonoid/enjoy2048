package enjoy2048;

import enjoy2048.grid.GridPlate;

public class Play {
	private static void play(){
		GridPlate c = GridPlate.getPlate();
		Game g = Game.getGame(c);
		g.show();	
	}

	public static void main(String[] args){
		play(); // TODO: if y/n dialog selects yes, another game will start.... todo! 9.13
	}
}
