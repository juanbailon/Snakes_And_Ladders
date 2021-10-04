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

public class Dice extends JLabel {
	
	public int DICE_WIDTH;
	public int DICE_HEIGHT;
	
	private int currentFace;
	private ImageIcon diceIcon, rotatingDice;
	private Random random;
	private ControlGame controlGame = null;
	public ScheduledExecutorService scheduler;
	private ScheduledFuture<?> spinDiceTask;
	
	
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
	
	
	public synchronized int rollDice() {
		
		int num = random.nextInt(6)+1;
			
		return num;
	}
	
	public void setDiceValue(int value) {
		
		diceIcon = new ImageIcon("src/images/dice_"+ value +".png");
		diceIcon = resizeImg(DICE_WIDTH, DICE_HEIGHT, diceIcon, true);
		currentFace = value;					
		setIcon(diceIcon);
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


	public ControlGame getControlGame() {
		return controlGame;
	}

	public void setControlGame(ControlGame controlGame) {
		this.controlGame = controlGame;
	}


	public ScheduledFuture<?> getSpinDiceTask() {
		return spinDiceTask;
	}
	
	
}
