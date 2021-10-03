package gameModels;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Dice extends JLabel {
	
	public static int DICE_WIDTH;
	public static int DICE_HEIGHT;
	
	private int currentFace;
	private ImageIcon diceIcon, rotatingDice;
	private Random random;
	
	public Dice(int width, int height) {
	
		Dice.DICE_WIDTH = width;
		Dice.DICE_HEIGHT = height;
		this.currentFace=1;
		this.diceIcon= new ImageIcon("src/images/dice_"+ this.currentFace +".png");;
		this.rotatingDice = new ImageIcon("src/images/dice_moving.gif");
		this.random = new Random();
	
		this.diceIcon = resizeImg(DICE_WIDTH, DICE_HEIGHT, this.diceIcon, true);
		this.rotatingDice = resizeImg(DICE_WIDTH, DICE_HEIGHT, this.rotatingDice, false);
		
		this.setPreferredSize( new Dimension(DICE_WIDTH, DICE_HEIGHT) );
		this.setIcon( this.diceIcon );
		//this.setVisible(false);
	}
	
	
	public void spinDice() {
		
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setIcon(rotatingDice);
				
			}
		} );
		
		
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			int ctr=0;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub									
				if(ctr==1) {
					
					
					SwingUtilities.invokeLater( new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							setIcon(diceIcon);
							
						}
					} );
					//setIcon(diceIcon);
					timer.cancel();
				}		
				ctr++;					
			}
		};
		
		//SwingUtilities.invokeLater(timerTask);
		
		timer.scheduleAtFixedRate(timerTask, 10, 530);
	}
	
	
	public int rollDice() {
		
		int num = random.nextInt(6)+1;
		diceIcon = new ImageIcon("src/images/dice_"+ num +".png");
		diceIcon = resizeImg(DICE_WIDTH, DICE_HEIGHT, diceIcon, true);
		//this.setVisible(false);
		
		//spinDice(this);		
		currentFace = num;		
		setIcon(diceIcon);	
		
		return currentFace;
	}
	
	
	public static ImageIcon resizeImg(int newWidth, int newHeight, ImageIcon img, boolean smooth) {
		
		Image tempImage;
		
		if(smooth) {
			tempImage = img.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		}
		else {
			tempImage = img.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_DEFAULT);
		}
		
		img = new ImageIcon(tempImage);		
		
		return img;
	}
	

	public int getCurrentFace() {
		return currentFace;
	}

	public void setCurrentFace(int currentFace) {
		this.currentFace = currentFace;
	}

	public ImageIcon getImage() {
		return diceIcon;
	}

	public void setImage(ImageIcon image) {
		this.diceIcon = image;
		this.setIcon(image);
	}
	
	

}
