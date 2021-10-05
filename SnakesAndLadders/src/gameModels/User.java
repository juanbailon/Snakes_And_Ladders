/*
 * Author:  JUAN JOSE BAILON
 * 		    JUAN JOSE REVELO
 * 		    ANGELO SALAZAR
 */
package gameModels;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameControls.ControlGame;


// TODO: Auto-generated Javadoc
/**
 * The Class User. represents a user
 */
public class User extends JLabel  implements Runnable {
	
	private static int AVATAR_WIDTH, AVATAR_HEIGHT;
	
	/** The is bot. */
	protected boolean isBot;
	private ImageIcon avatar;
	private int currentSquare, turn;
	private Dice dice;
	private int AvatarID, boardCoordinates[];
	private ControlGame controlGame;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param avatarId the avatar id
	 * @param turn the turn
	 * @param isBot the is bot
	 */
	public User( int avatarId, int turn, boolean isBot ) {
		
		this.isBot = isBot;
		this.turn = turn;
		this.currentSquare = 0;
		this.AvatarID = avatarId;
		this.boardCoordinates = new int[2];
		
		this.dice = null;
		this.controlGame = null;
		
		selectAvatar( this.AvatarID );
		AVATAR_WIDTH = avatar.getIconWidth();
		AVATAR_HEIGHT = avatar.getIconHeight();
	}
	
	
	/**
	 * Run.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		controlGame.turns(this);
	}
	
	
	/**
	 * Select avatar.
	 *
	 * @param avatID the avat ID
	 */
	public void selectAvatar(int avatID) {
		
		this.AvatarID = avatID;
		this.avatar = new ImageIcon( getClass().getResource("/images/avatar_"+ avatID +".png") );
		this.setIcon(avatar);
	}
	
	/**
	 * Sets the avatar size. resize the avatar image
	 *
	 * @param width the width
	 * @param height the height
	 */
	public void setAvatarSize(int width, int height) {
		
		AVATAR_WIDTH = width;
		AVATAR_HEIGHT = height;
		
		avatar = Dice.resizeImg(35, 55, avatar, true);
		this.setSize(avatar.getIconWidth(), avatar.getIconHeight());
		this.setIcon(avatar);
	}
	
	
	/**
	 * Equal avatars.
	 *
	 * @param otherUser the other user
	 * @return true, if successful
	 */
	public boolean equalAvatars(User otherUser) {
		
		boolean flag = false;
		
		if( this.AvatarID == otherUser.getAvatarID() )
			flag=true;
		
		return flag;
	}
	
	
	/**
	 * Gets the control game.
	 *
	 * @return the control game
	 */
	public ControlGame getControlGame() {
		return controlGame;
	}

	/**
	 * Sets the control game.
	 *
	 * @param controlGame the new control game
	 */
	public void setControlGame(ControlGame controlGame) {
		this.controlGame = controlGame;
	}
	

	/**
	 * Gets the dice.
	 *
	 * @return the dice
	 */
	public Dice getDice() {
		return dice;
	}


	/**
	 * Sets the dice.
	 *
	 * @param dice the new dice
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}


	/**
	 * Checks if is bot.
	 *
	 * @return true, if is bot
	 */
	public boolean isBot() {
		return isBot;
	}

	/**
	 * Gets the avatar.
	 *
	 * @return the avatar
	 */
	public ImageIcon getAvatar() {
		return avatar;
	}

	/**
	 * Sets the avatar.
	 *
	 * @param avatar the new avatar
	 */
	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}

	/**
	 * Gets the current square.
	 *
	 * @return the current square
	 */
	public int getCurrentSquare() {
		return currentSquare;
	}

	/**
	 * Sets the current square.
	 *
	 * @param currentSquare the new current square
	 */
	public void setCurrentSquare(int currentSquare) {
		this.currentSquare = currentSquare;
	}

	/**
	 * Gets the avatar ID.
	 *
	 * @return the avatar ID
	 */
	public int getAvatarID() {
		return AvatarID;
	}
	
	/**
	 * Gets the turn.
	 *
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Sets the turn.
	 *
	 * @param turn the new turn
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}


	/**
	 * Gets the board coordinates.
	 *
	 * @return the board coordinates
	 */
	public int[] getBoardCoordinates() {
		return boardCoordinates;
	}

	/**
	 * Sets the board coordinates.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setBoardCoordinates(int x, int y) {
		this.boardCoordinates[0] = x;
		this.boardCoordinates[1] = y;
	}
	
	/**
	 * Sets the board coordinate X.
	 *
	 * @param x the new board coordinate X
	 */
	public void setBoardCoordinateX(int x) {
		this.boardCoordinates[0] = x;
	}
	
	/**
	 * Gets the board coordinate X.
	 *
	 * @return the board coordinate X
	 */
	public int getBoardCoordinateX() {
		return this.boardCoordinates[0];
	}
	
	/**
	 * Sets the board coordinate Y.
	 *
	 * @param y the new board coordinate Y
	 */
	public void setBoardCoordinateY(int y) {
		this.boardCoordinates[1] = y;
	}
	
	/**
	 * Gets the board coordinate Y.
	 *
	 * @return the board coordinate Y
	 */
	public int getBoardCoordinateY() {
		return this.boardCoordinates[1];
	}


}
