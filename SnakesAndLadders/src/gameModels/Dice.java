/*
 * Author:  JUAN JOSE BAILON
 * 		    JUAN JOSE REVELO
 * 		    ANGELO SALAZAR
 */
package gameModels;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameControls.ControlGame;

// TODO: Auto-generated Javadoc
/**
 * The Class Dice.
 */
public class Dice extends JLabel {
	
	/** The dice width. */
	public int DICE_WIDTH;
	
	/** The dice height. */
	public int DICE_HEIGHT;
	
	private int currentFace;
	private ImageIcon diceIcon, rotatingDice;
	private Random random;
	private ControlGame controlGame = null;
	
	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> spinDiceTask;
	
	
	/**
	 * Instantiates a new dice.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public Dice(int width, int height) {
	
		this.DICE_WIDTH = width;
		this.DICE_HEIGHT = height;
		this.currentFace=1;
		this.diceIcon= new ImageIcon("src/images/dice_"+ this.currentFace +".png");;
		this.rotatingDice = new ImageIcon("src/images/dice_moving.gif");
		this.random = new Random();
	
		this.diceIcon = resizeImg(DICE_WIDTH, DICE_HEIGHT, this.diceIcon, true);
		this.rotatingDice = resizeImg(DICE_WIDTH, DICE_HEIGHT, this.rotatingDice, false);
		
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
		
		this.setPreferredSize( new Dimension(DICE_WIDTH, DICE_HEIGHT) );
		this.setIcon( this.diceIcon );

	}
	
	
	/**
	 * Spin dice. sets the icon with a moving dice (gif) 
	 */
	public void spinDice() {
		
		
		Runnable myTask = new Runnable() {
			
			int ctr=0;
			@Override
			public void run() {
				
				if( ctr==0 ) {
					setIcon(rotatingDice);
				}
				else {
					setIcon(diceIcon);
					scheduler.notifyAll();	
				}
				ctr++;
			}
		};
		
		
		ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(myTask, 10, 530, TimeUnit.MILLISECONDS);
		spinDiceTask = taskHandle;
		
		
		Runnable canceler = new Runnable() {
			
			@Override
			public void run() {
				
				while( !taskHandle.isDone() ) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				taskHandle.cancel(false);
			}
		};
		
		scheduler.schedule(canceler, 1, TimeUnit.MICROSECONDS);
		
	}
	
	
	/**
	 * Roll dice. returns a value between 1 and 6
	 *
	 * @return the int
	 */
	public synchronized int rollDice() {
		
		int num = random.nextInt(6)+1;
			
		return num;
	}
	
	/**
	 * Sets the dice value. 
	 *
	 * @param value the new dice value
	 */
	public void setDiceValue(int value) {
		
		diceIcon = new ImageIcon("src/images/dice_"+ value +".png");
		diceIcon = resizeImg(DICE_WIDTH, DICE_HEIGHT, diceIcon, true);
		currentFace = value;					
		setIcon(diceIcon);
	}
	
	
	/**
	 * Resize img.
	 *
	 * @param newWidth the new width
	 * @param newHeight the new height
	 * @param img the img
	 * @param smooth the smooth
	 * @return the image icon
	 */
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
	

	/**
	 * Gets the current face.
	 *
	 * @return the current face
	 */
	public int getCurrentFace() {
		return currentFace;
	}

	/**
	 * Sets the current face.
	 *
	 * @param currentFace the new current face
	 */
	public void setCurrentFace(int currentFace) {
		this.currentFace = currentFace;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public ImageIcon getImage() {
		return diceIcon;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(ImageIcon image) {
		this.diceIcon = image;
		this.setIcon(image);
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
	 * Gets the spin dice task.
	 *
	 * @return the spin dice task
	 */
	public ScheduledFuture<?> getSpinDiceTask() {
		return spinDiceTask;
	}
	
	
}
