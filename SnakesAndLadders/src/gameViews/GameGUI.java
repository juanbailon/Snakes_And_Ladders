package gameViews;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameControls.ControlGame;
import gameModels.Dice;
import gameModels.User;

public class GameGUI extends JFrame {
	
	//public static int FIRTS_SQUARE_X=95, FIRTS_SQUARE_Y=473, DELTA_X=47, DELTA_Y=47;
	
	private Escucha escucha;
	private JPanel leftPanel, rightPanel, bottomPanel;
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
		this.add(controlGame.getGameBoard(), BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		
		
		System.out.println("##########");
		
		Integer aux = null;
		System.out.println( " ¬¬¬¬¬¬¬¬¬¬ "+ aux );
		
		for (User user : controlGame.getGameBoard().getPlayers() ) {
			user.setDice( this.dice );
		}
		
		
		controlGame.setDice( dice );
		
	}	
	
	
	private class Escucha extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			if( eventMouse.getSource()==diceButton ) {
				
				if( controlGame.getCurrentTurn() == 1 ) {
			
					controlGame.startHumans();		
				}
					
				controlGame.startBots();
				
						
			}
		}
		
	}// END class Escucha
	
		

}// END	class GameGUI
