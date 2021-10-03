package gameModels;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameViews.GameGUI;

public class User extends JLabel {
	
	private static int AVATAR_WIDTH, AVATAR_HEIGHT;
	
	protected boolean isBot;
	private ImageIcon avatar;
	private int currentSquare, turn;
	private Dice dice;
	private int AvatarID, boardCoordinates[];
	
	public User( int avatarId, int turn ) {
		
		this.isBot = false;
		this.turn = turn;
		this.currentSquare = 0;
		this.AvatarID = avatarId;
		this.boardCoordinates = new int[2];
		
		selectAvatar( this.AvatarID );
		AVATAR_WIDTH = avatar.getIconWidth();
		AVATAR_HEIGHT = avatar.getIconHeight();
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
	
	public void moveForward(int steps) {
		
		
		
		
	}
	
	public boolean equalAvatars(User otherUser) {
		
		boolean flag = false;
		
		if( this.AvatarID == otherUser.getAvatarID() )
			flag=true;
		
		return flag;
	}
	
	@Override
	public boolean equals(Object otherUser) {
		
		boolean flag = false;
		
		if( this.AvatarID==((User) otherUser).getAvatarID() &&  this.isBot==((User) otherUser).isBot() ) {
			flag=true;
		}
		
		return flag;
	}

	public boolean isBot() {
		return isBot;
	}
	
	/*
	public void setBot(boolean isBot) {
		this.isBot = isBot;
	} */

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
