package gameControls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;

import gameModels.Dice;
import gameModels.GameBoard;
import gameModels.User;
import gameViews.GameGUI;

public class ControlGame {
	
	private GameBoard gameBoard;
	private Dice dice;
	private GameGUI gameGUI;
	private int humans, bots;
	private int currentTurn;
	private final ImageIcon boardImage = new ImageIcon( getClass().getResource("/images/playing_board.png") );
	private Lock lock = new ReentrantLock();
	private Condition waitForTurn = lock.newCondition();
	private ExecutorService playersThreads;
	
	
	public ControlGame(GameGUI gameGUI) {
		
		this.humans = 1;
		this.bots = 2;
		this.currentTurn = 1;
		this.gameBoard = new GameBoard(boardImage, 640, 590);
		this.gameGUI = gameGUI;
		
		gameBoard.fillPlayersLists(humans, bots);
		
		//gameBoard.addPlayerToBoard( gameBoard.getPlayers().get(0) , 1);
		
		/*
		gameBoard.setAvatarsSize(35, 55);
		gameBoard.addPlayerToBoard( gameBoard.getPlayers().get(0) , 0);
		
		System.out.println("~~~~~~~ "+ gameBoard.getPlayers().get(0).getBoardCoordinateX());
		
		gameBoard.addPlayerToBoard( gameBoard.getPlayers().get(1) , 2);
		gameBoard.addPlayerToBoard( gameBoard.getPlayers().get(2) , 56);
		*/
		
	}
	
	public void startHumans() {
		
		playersThreads = Executors.newFixedThreadPool(2);
		Future<?> handle;
		
		for (User myHuman : gameBoard.getPlayers() ) {
			
			if( !myHuman.isBot() ) {
				myHuman.setControlGame(this);
				handle =  playersThreads.submit( myHuman );
			}
		}
		
		playersThreads.shutdown();
	}
	
	public void startBots() {
		
		playersThreads = Executors.newFixedThreadPool( bots );
		Future<?> handle;
		
		for (User myBot : gameBoard.getPlayers() ) {			
			
			if( myBot.isBot() ) {
				myBot.setControlGame(this);
				handle =  playersThreads.submit( myBot );
			}
		}
		
		playersThreads.shutdown();
	}
	
	
	public void turns(User user) {
		
		lock.lock();
		
		try {
			while( user.getTurn() != currentTurn ) {
				waitForTurn.await();
			}
			
			if(!user.isVisible())
				user.setVisible(true);
				
			if( user.getTurn() == 1 ) {
				gameGUI.getDiceButton().removeMouseListener( gameGUI.getEscucha() );
			}
			else if( user.getTurn() == 2 ) {
				gameGUI.getArrow().setLocation( gameGUI.getArrow().getX() ,  gameGUI.getArrow().getY() + 83 );
			}
			else if( user.getTurn() == 3 ) {
				gameGUI.getArrow().setLocation( gameGUI.getArrow().getX() ,  gameGUI.getArrow().getY() + 83 );
			}
			
			
			if( user.getCurrentSquare() == 0 )			
				gameBoard.addPlayerToBoard(user, 1);
			
			
			int result = dice.rollDice();
			dice.spinDice();
			
			while( !dice.getSpinDiceTask().isDone() ) { }
			
			dice.setDiceValue(result);
			
			gameBoard.moveForward(user, result);
			
			while( !gameBoard.getLastPreformedMovementTask().isDone() ) { }
						
			currentTurn++;
			waitForTurn.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
			if( currentTurn > humans+bots ) {
				currentTurn=1;
				gameGUI.getArrow().setLocation( 5, 125 );
				gameGUI.getDiceButton().addMouseListener( gameGUI.getEscucha() );
			}
			lock.unlock();
		}
		
	}

	
	public void restart() {
		
		int ctr=0;
		
		for (User user : gameBoard.getPlayers() ) {
			 
			user.setVisible(false);
			gameBoard.addPlayerToBoard(user, 1);
			
			if(ctr==0)
				user.setVisible(true);
			
			ctr++;
		}
		
		if( currentTurn != 1 )
			gameGUI.getDiceButton().addMouseListener( gameGUI.getEscucha() );		
		
		currentTurn=1;
		gameGUI.getArrow().setLocation( 5, 125 );
		
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}
	

}
