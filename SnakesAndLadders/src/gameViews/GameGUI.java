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

import gameModels.Dice;
import gameModels.GameBoard;

public class GameGUI extends JFrame {
	
	//public static int FIRTS_SQUARE_X=95, FIRTS_SQUARE_Y=473, DELTA_X=47, DELTA_Y=47;
	
	private JButton restart, rollDice;
	private Escucha escucha;
	private JPanel leftPanel, boardPanel, rightPanel, bottomPanel;
	//private JFrame boardPanel;
	private GameBoard gameBoard;
	private Dice dice;
	private JLabel startPlank, playingBoard, diceButton, restartButton;
	private JLabel avatarUser;
	private ImageIcon imageIcon;
	
	
	public GameGUI() {
		
		imageIcon = new ImageIcon();
		
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
		
		//set up container - layout
		//this.getContentPane().setLayout( new GridBagLayout() );
		//GridBagConstraints constraints = new GridBagConstraints();
		
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
		
		// Board panel
		boardPanel = new JPanel();
		//boardPanel.setLocation(0,0);
		boardPanel.setLayout(null);
		boardPanel.setBackground(Color.blue);
		
		ImageIcon temp = new ImageIcon( getClass().getResource("/images/playing_board.png") );
		gameBoard = new GameBoard( temp, 640, 590 );
		//gameBoard.setSize(700, 600);
		//gameBoard.setLayout(null);
	
		//playing board
		playingBoard = new JLabel();
		
		imageIcon = new ImageIcon( this.getClass().getResource("/images/playing_board.png" ) );
		imageIcon = Dice.resizeImg(640, 590, imageIcon, true);
		
		playingBoard.setLocation(0, 0);
		playingBoard.setSize( imageIcon.getIconWidth() , imageIcon.getIconHeight() );
		playingBoard.setVisible(true);
		playingBoard.setIcon( imageIcon );
		boardPanel.setSize( imageIcon.getIconWidth() , imageIcon.getIconHeight() );
		boardPanel.add(playingBoard,0);
		
		avatarUser = new JLabel();
		imageIcon = new ImageIcon( this.getClass().getResource("/images/explosion-sin-bg.gif" ) );
		imageIcon = Dice.resizeImg(50, 57, imageIcon, false);
		
		avatarUser.setIcon( imageIcon );
		avatarUser.setSize( imageIcon.getIconWidth() , imageIcon.getIconHeight() );
		avatarUser.setLocation( 95*2-10, 473-(47*4) );
		//gameBoard.add(avatarUser,0);
		
		/*
		avatarUser = new JLabel();
		
		imageIcon = new ImageIcon( this.getClass().getResource("/images/avatar_1.png" ) );
		imageIcon = Dice.resizeImg(35, 55, imageIcon, true);
		
		avatarUser.setIcon( imageIcon );
		avatarUser.setSize( imageIcon.getIconWidth() , imageIcon.getIconHeight() );
		avatarUser.setLocation(95+(47*5), 473-(47*9)); //79, 397
		gameBoard.add(avatarUser,0);
		boardPanel.add(avatarUser,0);
		*/																											
		
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
		//this.add(boardPanel, BorderLayout.CENTER);
		this.add(gameBoard, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		
		//gameBoard.setEggGifSize(50, 57);
		
		gameBoard.setAvatarsSize(35, 55);
		gameBoard.addPlayerToBoard(0, 76);
		gameBoard.addPlayerToBoard(1, 37);
		gameBoard.addPlayerToBoard(2, 1);
		
		//gameBoard.getPlayers().get(2).setLocation( gameBoard.f );
		
		System.out.println( gameBoard.getPlayers().get(2).getX() );
		
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
				dice.spinDice();
				int result = dice.rollDice();
				 
				//if(result==5)
				gameBoard.moveForward(gameBoard.getPlayers().get(2), result);  
				
				//gameBoard.upTheLadder( gameBoard.getPlayers().get(2), 450, 25 );
				//gameBoard.downTheSnake( gameBoard.getPlayers().get(2), 450 );
				//gameBoard.movePlayerHorizontally(gameBoard.getPlayers().get(2), 3, 2000, 20);
						
			}
		}
		
	}// END class Escucha
	
		

}// END	class GameGUI
