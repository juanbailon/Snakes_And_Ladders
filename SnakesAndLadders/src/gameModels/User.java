package gameModels;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameControls.ControlGame;
import gameViews.GameGUI;

public class User extends JLabel  implements Runnable {
	
	private static int AVATAR_WIDTH, AVATAR_HEIGHT;
	
	protected boolean isBot;
	private ImageIcon avatar;
	private int currentSquare, turn;
	private Dice dice;
	private int AvatarID, boardCoordinates[];
	private ControlGame controlGame;
	
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
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	
		
		controlGame.turns(this);
	}
	
	
	public void selectAvatar(int avatID) {
		
		this.AvatarID = avatID;
		this.avatar = new ImageIcon( getClass().getResource("/images/avatar_"+ avatID +".png") );
		this.setIcon(avatar);
	}
	
	public void setAvatarSize(int width, int height) {
		
		AVATAR_WIDTH = width;
		AVATAR_HEIGHT = height;
		
		avatar = Dice.resizeImg(35, 55, avatar, true);
		this.setSize(avatar.getIconWidth(), avatar.getIconHeight());
		this.setIcon(avatar);
	}
	
	
	public boolean equalAvatars(User otherUser) {
		
		boolean flag = false;
		
		if( this.AvatarID == otherUser.getAvatarID() )
			flag=true;
		
		return flag;
	}
	
	
	public ControlGame getControlGame() {
		return controlGame;
	}

	public void setControlGame(ControlGame controlGame) {
		this.controlGame = controlGame;
	}
	

	public Dice getDice() {
		return dice;
	}


	public void setDice(Dice dice) {
		this.dice = dice;
	}


	public boolean isBot() {
		return isBot;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}

	public int getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(int currentSquare) {
		this.currentSquare = currentSquare;
	}

	public int getAvatarID() {
		return AvatarID;
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}


	public int[] getBoardCoordinates() {
		return boardCoordinates;
	}

	public void setBoardCoordinates(int x, int y) {
		this.boardCoordinates[0] = x;
		this.boardCoordinates[1] = y;
	}
	
	public void setBoardCoordinateX(int x) {
		this.boardCoordinates[0] = x;
	}
	
	public int getBoardCoordinateX() {
		return this.boardCoordinates[0];
	}
	
	public void setBoardCoordinateY(int y) {
		this.boardCoordinates[1] = y;
	}
	
	public int getBoardCoordinateY() {
		return this.boardCoordinates[1];
	}


}
