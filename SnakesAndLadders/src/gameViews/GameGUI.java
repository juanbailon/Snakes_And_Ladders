package gameViews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameControls.ControlGame;
import gameModels.Dice;
import gameModels.GameBoard;

public class GameGUI extends JFrame {
	
	//public static int FIRTS_SQUARE_X=95, FIRTS_SQUARE_Y=473, DELTA_X=47, DELTA_Y=47;
	
	private Escucha escucha;
	private JPanel leftPanel, rightPanel, bottomPanel;
	//private JFrame boardPanel;
	private GameBoard gameBoard;
	private Dice dice;
	private JLabel startPlank, diceButton, restartButton;
	private JLabel avatarUser;
	private ImageIcon imageIcon;
	private ControlGame controlGame;
	
	
	public GameGUI() {
		
		imageIcon = new ImageIcon();
		
		controlGame = new ControlGame();
		
		intiGUI();
		
		
		
		
		this.setSize(900+10, 620+10);
		this.setTitle("Snakes and Ladders");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(true);
		//this.pack();
		//this.setLayout(new GridBagLayout() );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void intiGUI() {
		
		
		//escucha
		escucha = new Escucha();
		
		
		// left panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		//starting plank
		startPlank = new JLabel();
		imageIcon = new ImageIcon( this.getClass().getResource("/images/start_plank.png" ) );
		imageIcon = Dice.resizeImg(140, 84, imageIcon, true);
		startPlank.setIcon( imageIcon );
		leftPanel.add(startPlank, BorderLayout.PAGE_END);
		
		
		ImageIcon temp = new ImageIcon( getClass().getResource("/images/playing_board.png") );
		gameBoard = new GameBoard( temp, 640, 590 );

		
		avatarUser = new JLabel();
		imageIcon = new ImageIcon( this.getClass().getResource("/images/explosion-sin-bg.gif" ) );
		imageIcon = Dice.resizeImg(50, 57, imageIcon, false);
		
		avatarUser.setIcon( imageIcon );
		avatarUser.setSize( imageIcon.getIconWidth() , imageIcon.getIconHeight() );
		avatarUser.setLocation( 95*2-10, 473-(47*4) );
		//gameBoard.add(avatarUser,0);
																													
		
		//right panel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout() );
		
		//dice button
		diceButton = new JLabel();
		diceButton.setIcon( new ImageIcon( this.getClass().getResource("/images/boton_dado.png" ) ) );
		diceButton.setCursor( new Cursor(Cursor.HAND_CURSOR) );
		diceButton.addMouseListener(escucha);
		rightPanel.add(diceButton, BorderLayout.SOUTH);
		
		//dice
		dice = new Dice(100, 100);
		rightPanel.add(dice, BorderLayout.CENTER);
		
		
		this.add(leftPanel, BorderLayout.LINE_START);
		//this.add(gameBoard, BorderLayout.CENTER);
		this.add(controlGame.gameBoard, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		
		//gameBoard.setEggGifSize(50, 57);
		
		/*
		gameBoard.fillPlayersLists(1, 2);
		
		gameBoard.setAvatarsSize(35, 55);
		gameBoard.addPlayerToBoard( gameBoard.getHumanPlayers().get(0) , 76);
		gameBoard.addPlayerToBoard( gameBoard.getBotPlayers().get(0), 37);
		gameBoard.addPlayerToBoard( gameBoard.getBotPlayers().get(1), 1);
		*/
		//gameBoard.getPlayers().get(2).setLocation( gameBoard.f );
		
		//System.out.println( gameBoard.getPlayers().get(2).getX() );
		
		System.out.println("##########");
		
		//gameBoard.moveForward(gameBoard.getPlayers().get(2), 7);
		Integer aux = null;
		System.out.println( " ¬¬¬¬¬¬¬¬¬¬ "+ aux );
		
	}	
	
	
	private class Escucha extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			if( eventMouse.getSource()==diceButton ) {
				/*
				if( controlGame.getCurrentTurn() == 1 ) {
					
					dice.spinDice();
					int result = dice.rollDice();
					 
					dice.setDiceValue(result);					
					controlGame.turns( controlGame.getGameBoard().getHumanPlayers().get(0) , result);
					//gameBoard.moveForward(gameBoard.getBotPlayers().get(1), result); 
					
				}else { */
					
					
					controlGame.setCurrentTurn(2);
					dice.spinDice();
					int result = dice.rollDice();
					 
					dice.setDiceValue(result);
					controlGame.startBots();
				//}
					
				
				/*
				dice.spinDice();
				int result = dice.rollDice();
				 
				dice.setDiceValue(result);
				//if(result==5)
				gameBoard.moveForward(gameBoard.getBotPlayers().get(1), result);  */  
				
				//gameBoard.upTheLadder( gameBoard.getPlayers().get(2), 450, 25 );
				//gameBoard.downTheSnake( gameBoard.getPlayers().get(2), 450 );
				//gameBoard.movePlayerHorizontally(gameBoard.getPlayers().get(2), 3, 2000, 20);
						
			}
		}
		
	}// END class Escucha
	
		

}// END	class GameGUI
