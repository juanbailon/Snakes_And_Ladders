package gameModels;

import java.util.Random;

import javax.swing.ImageIcon;

import gameControls.ControlGame;

public class Bot extends User implements Runnable {
	
	private GameBoard gameBoard;
	private ControlGame controlGame;
	
	public Bot(int avatarId, int turn) {
		
		super(avatarId, turn);
		super.isBot = true;
		
		gameBoard = new GameBoard();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println( "BOT IS MOVING" );
		Random random = new Random();
		int steps = random.nextInt(6)+1;
		
		controlGame.turns(this, steps);
		
	}

	public ControlGame getControlGame() {
		return controlGame;
	}

	public void setControlGame(ControlGame controlGame) {
		this.controlGame = controlGame;
	}
	
	

}
