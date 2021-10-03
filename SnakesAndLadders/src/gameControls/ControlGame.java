package gameControls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.crypto.spec.DHPrivateKeySpec;
import javax.swing.ImageIcon;

import gameModels.Bot;
import gameModels.Dice;
import gameModels.GameBoard;
import gameModels.User;

public class ControlGame {
	
	public GameBoard gameBoard;
	//private Dice dice;
	private int humans, bots, currentTurn;
	private final ImageIcon boardImage = new ImageIcon( getClass().getResource("/images/playing_board.png") );
	private Lock lock = new ReentrantLock();
	private Condition waitForTurn = lock.newCondition();
	private List< Future<?> > botsFuturesList;
	private ExecutorService botsThreads;
	
	
	public ControlGame() {
		
		this.humans = 1;
		this.bots = 2;
		this.currentTurn = 1;
		//this.dice = dice;
		this.gameBoard = new GameBoard(boardImage, 640, 590);
		this.botsFuturesList = new ArrayList<>();
		
		gameBoard.fillPlayersLists(humans, bots);
		
		gameBoard.setAvatarsSize(35, 55);
		gameBoard.addPlayerToBoard( gameBoard.getHumanPlayers().get(0) , 15);
		gameBoard.addPlayerToBoard( gameBoard.getBotPlayers().get(0) , 2);
		gameBoard.addPlayerToBoard( gameBoard.getBotPlayers().get(1) , 56);
		
	}
	
	public void startBots() {
		
		botsThreads = Executors.newFixedThreadPool(2);
		Future<?> handle;
		
		for (Bot myBot : gameBoard.getBotPlayers() ) {
			
			System.out.println("bot square -> "+ myBot.getCurrentSquare() );
			System.out.println("bot turn -> "+ myBot.getTurn() );
			
			if( myBot.isBot() ) {
				myBot.setControlGame(this);
				handle =  botsThreads.submit( myBot );
				botsFuturesList.add(handle);
			}
		}
		
		botsThreads.shutdown();
	}
	
	
	public void turns(User user, int steps) {
		
		lock.lock();
		System.out.println("el bot hizo lock... "+ user.getTurn());
		
		try {
			while( user.getTurn() != currentTurn ) {
				System.out.println("el bot esta esperando.... "+ user.getTurn());
				waitForTurn.await();
			}
			
			System.out.println("el bot paso el while  ___ "+ user.getTurn());
			
			gameBoard.moveForward(user, steps);
			
			while( !gameBoard.getLastPreformedMovementTask().isDone() ) {
				
			}
			
			currentTurn++;
			waitForTurn.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if( currentTurn > humans+bots )
				currentTurn=1;
			lock.unlock();
		}
		
	}

	
	public List<Future<?>> getBotsFuturesList() {
		return botsFuturesList;
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
	
	

}
