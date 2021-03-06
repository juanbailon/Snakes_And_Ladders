/*
 * Author: JUAN JOSE BAILON
 */
package gameViews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gameControls.ControlGame;
import gameModels.Dice;
import gameModels.User;

// TODO: Auto-generated Javadoc
/**
 * The Class GameGUI.
 */
public class GameGUI extends JFrame {
	
	private Escucha escucha;
	private JPanel leftPanel, rightPanel, playersPanel, players_SubPanel;
	private Dice dice;
	private JLabel diceButton, restartButton, trophy;
	private JLabel humanAvatar, bot1Avatar, bot2Avatar, arrow;
	private ImageIcon imageIcon;
	private ControlGame controlGame;
	
	private int avatarsInPlayersPanelSize[] = new int[] {40, 64};
	
	
	/**
	 * Instantiates a new game GUI.
	 */
	public GameGUI() {
		
		imageIcon = new ImageIcon();
		
		controlGame = new ControlGame(this);
		controlGame.getGameBoard().setGameGUI(this);
		controlGame.getGameBoard().setAvatarsSize(35, 55);
		
		controlGame.getGameBoard().addPlayerToBoard( controlGame.getGameBoard().getPlayers().get(0), 1);
				
		intiGUI();
		
		this.setSize(900+25, 620+10);
		this.setTitle("Snakes and Ladders");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Inti GUI.
	 */
	public void intiGUI() {
				
		//escucha
		escucha = new Escucha();
		
		
		// left panel
		leftPanel = new JPanel();
		leftPanel.setLayout( new BorderLayout() );		
		leftPanel.setMinimumSize( new Dimension(200, 600));
		leftPanel.setSize(200, 650);
		leftPanel.setBackground(Color.red);
		
		
		JLabel auxLabel = new JLabel("_________________________");
		//auxLabel.setSize(500, 10);
		leftPanel.add(auxLabel, BorderLayout.PAGE_START );
		
		
		
		//players panel
		playersPanel = new JPanel();
		playersPanel.setBackground(Color.CYAN.darker());
		playersPanel.setLayout(null);
		playersPanel.setSize(500, 200);
		playersPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
		leftPanel.add( playersPanel, BorderLayout.CENTER );
		
		//players SUB panel
		players_SubPanel = new JPanel();
		players_SubPanel.setSize( avatarsInPlayersPanelSize[0] + 50 , avatarsInPlayersPanelSize[1]*3 + 70 );
		players_SubPanel.setLocation(50, 100);
		players_SubPanel.setBackground(Color.orange);
		players_SubPanel.setLayout( new BoxLayout(players_SubPanel, BoxLayout.Y_AXIS) );
		playersPanel.add(players_SubPanel);
		
		
		//human avatar
		humanAvatar = new JLabel();
		imageIcon = controlGame.getGameBoard().getPlayers().get(0).getAvatar();		
		imageIcon = Dice.resizeImg(avatarsInPlayersPanelSize[0], avatarsInPlayersPanelSize[1], imageIcon, true);
		humanAvatar.setIcon( imageIcon );
		humanAvatar.setText("YOU");
		humanAvatar.setOpaque(false);
		humanAvatar.setVerticalTextPosition( SwingConstants.BOTTOM );
		humanAvatar.setHorizontalTextPosition( SwingConstants.CENTER );
		humanAvatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		players_SubPanel.add(humanAvatar);
		
		
		//bot1 avatar
		bot1Avatar = new JLabel();
		imageIcon = controlGame.getGameBoard().getPlayers().get(1).getAvatar();
		imageIcon = Dice.resizeImg(avatarsInPlayersPanelSize[0], avatarsInPlayersPanelSize[1], imageIcon, true);
		bot1Avatar.setIcon(imageIcon);
		bot1Avatar.setText("BOT_1");
		bot1Avatar.setVerticalTextPosition( SwingConstants.BOTTOM );
		bot1Avatar.setHorizontalTextPosition( SwingConstants.CENTER );
		bot1Avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		players_SubPanel.add(bot1Avatar);
		
		
		//bot2 avatar
		bot2Avatar = new JLabel();
		imageIcon = controlGame.getGameBoard().getPlayers().get(2).getAvatar();
		imageIcon = Dice.resizeImg(avatarsInPlayersPanelSize[0], avatarsInPlayersPanelSize[1], imageIcon, true);
		bot2Avatar.setIcon(imageIcon);
		bot2Avatar.setText("BOT_2");
		bot2Avatar.setVerticalTextPosition( SwingConstants.BOTTOM );
		bot2Avatar.setHorizontalTextPosition( SwingConstants.CENTER );
		bot2Avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		players_SubPanel.add(bot2Avatar);
		
		
		// arrow
		arrow = new JLabel();
		imageIcon =  new ImageIcon( getClass().getResource("/images/arrows-sin-bg-.gif") );
		imageIcon = Dice.resizeImg(50, 30, imageIcon, false);
		arrow.setIcon( imageIcon );
		arrow.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight() );
		arrow.setLocation(5, 125+83*0 );
		playersPanel.add(arrow,0);
		
		//trophy
		trophy = new JLabel("WINNER -> BOT_1");
		imageIcon =  new ImageIcon( getClass().getResource("/images/trophy-sin-bg.gif") );
		imageIcon = Dice.resizeImg(140, 140, imageIcon, false);
		trophy.setIcon(imageIcon);
		trophy.setSize(imageIcon.getIconWidth()+10, imageIcon.getIconHeight()+30 );
		trophy.setLocation(5, 400);
		trophy.setVerticalTextPosition( SwingConstants.BOTTOM );
		trophy.setHorizontalTextPosition( SwingConstants.CENTER );
		trophy.setVisible(false);
		playersPanel.add(trophy,0);
		
		
		
		//right panel
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.cyan.darker());
		rightPanel.setLayout(new BorderLayout() );
		
		//starting plank
		restartButton = new JLabel("RESTART");
		imageIcon = new ImageIcon( this.getClass().getResource("/images/start_plank.png" ) );
		imageIcon = Dice.resizeImg(110, 44, imageIcon, true);
		restartButton.setIcon( imageIcon );
		restartButton.setSize(140, 100 );
		restartButton.setLocation(18, 470);
		restartButton.setVerticalTextPosition( SwingConstants.CENTER );
		restartButton.setHorizontalTextPosition( SwingConstants.CENTER);
		restartButton.setHorizontalAlignment( SwingConstants.RIGHT );
		restartButton.addMouseListener(escucha);
		rightPanel.add(restartButton, BorderLayout.PAGE_START);
		
		//dice button
		diceButton = new JLabel();
		diceButton.setIcon( new ImageIcon( this.getClass().getResource("/images/boton_dado.png" ) ) );
		diceButton.setCursor( new Cursor(Cursor.HAND_CURSOR) );
		diceButton.setHorizontalAlignment(SwingConstants.CENTER);
		diceButton.addMouseListener(escucha);
		rightPanel.add(diceButton, BorderLayout.SOUTH);
		
		//dice
		dice = new Dice(100, 100);
		dice.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(dice, BorderLayout.CENTER);
		
		
		this.add(leftPanel, BorderLayout.LINE_START);	
		this.add(controlGame.getGameBoard(), BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		
		
		for (User user : controlGame.getGameBoard().getPlayers() ) {
			user.setDice( this.dice );
		}
		
		controlGame.setDice( dice );
	}	

	
	/**
	 * Gets the dice button.
	 *
	 * @return the dice button
	 */
	public JLabel getDiceButton() {
		return diceButton;
	}


	/**
	 * Gets the arrow.
	 *
	 * @return the arrow
	 */
	public JLabel getArrow() {
		return arrow;
	}

	/**
	 * Gets the escucha.
	 *
	 * @return the escucha
	 */
	public Escucha getEscucha() {
		return escucha;
	}

	/**
	 * Gets the trophy.
	 *
	 * @return the trophy
	 */
	public JLabel getTrophy() {
		return trophy;
	}


	private class Escucha extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			// TODO Auto-generated method stub
			if( eventMouse.getSource()==diceButton ) {				
				
				if( controlGame.getCurrentTurn() == 1 ) {
					
					controlGame.startHumans();		
					controlGame.startBots();
				}		
						
			}
			else if ( eventMouse.getSource() == restartButton ) {
				controlGame.restart();
			}
		}
		
	}// END class Escucha
	
		

}// END	class GameGUI
