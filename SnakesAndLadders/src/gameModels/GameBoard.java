package gameModels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameBoard extends JPanel {

	public static int FIRTS_SQUARE_X=95, FIRTS_SQUARE_Y=473, DELTA_X=47, DELTA_Y=47;
	
	private final int  totalAvatarIcons=5;
	private ImageIcon backgroundImage;
	private JLabel playingBoard, eggGif, explosionGif;
	private  List< User > players;
	public ScheduledExecutorService scheduler;
	private int squaresWithLadders[][] , squaresWithSnakes[][];
	private int delay=450, period=20, movesFor1Square=24;
	private final int durationEggGif = 1000, durationExplosionGif = 1440; // time in miliseconds
	private ScheduledFuture<?> lastPreformedMovementTask;
	
	
	
	public GameBoard() {
		
		this.backgroundImage = new ImageIcon( getClass().getResource( "/images/playing_board.png" ) );
		backgroundImage = Dice.resizeImg( 640, 590, backgroundImage, true);
		
		initGameBoard();
		 
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(Color.CYAN);		
	}
	
	public GameBoard(ImageIcon img, int _width, int _height) {
	
		this.backgroundImage = img;
		backgroundImage = Dice.resizeImg(_width, _height, backgroundImage, true);
		
		initGameBoard();
											 
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(Color.CYAN);		
	}
	
	public void initGameBoard() {
		
		playingBoard = new JLabel();
		playingBoard.setLocation(0, 0);
		playingBoard.setSize( backgroundImage.getIconWidth() , backgroundImage.getIconHeight() );
		playingBoard.setVisible(true);
		playingBoard.setIcon( backgroundImage );
		this.add(playingBoard, 0);
		
		this.eggGif = new JLabel();
		eggGif.setVisible(false);
		eggGif.setIcon( new ImageIcon( getClass().getResource( "/images/egg.gif" ) ) );
		eggGif.setSize( eggGif.getIcon().getIconWidth(), eggGif.getIcon().getIconHeight()  );
		this.add( eggGif, 0 );
		this.setEggGifSize(50, 57);
		
		this.explosionGif = new JLabel();
		explosionGif.setVisible(false);
		explosionGif.setIcon( new ImageIcon( getClass().getResource( "/images/explosion-sin-bg.gif" ) ) );
		explosionGif.setSize( explosionGif.getIcon().getIconWidth() , explosionGif.getIcon().getIconHeight() );		
		this.add( explosionGif, 0 );
		this.setExplosionGifSize(55, 55);
		
		
		players = new ArrayList<>();
		
		this.scheduler = Executors.newScheduledThreadPool(1);
		
		
		this.squaresWithLadders = new int[][] { {4, 14}, {8, 32}, {20, 38},
												{28, 84}, {40, 59}, {58, 83},
												{72, 93}
											  };
											  
											  
		this.squaresWithSnakes = new int[][] { {15, 3}, {31, 9}, {44, 26},
											   {62, 19}, {74, 70}, {85, 33},
											   {91, 71}, {98, 80}
											 };
		
	}
	
	public void setAvatarsSize(int width, int height) {
		
		for (User user : players) {
			user.setAvatarSize(width, height);
		}				
	}
	
	public void setEggGifSize(int width, int height) {
		
		ImageIcon eggIcon = (ImageIcon) eggGif.getIcon();
		eggIcon = Dice.resizeImg(width, height, eggIcon, false);
		eggGif.setSize(eggIcon.getIconWidth(), eggIcon.getIconHeight());
		eggGif.setIcon( eggIcon );		
	}
	
	public void setExplosionGifSize(int width, int height) {
		
		ImageIcon explosionIcon = (ImageIcon) explosionGif.getIcon();				
		explosionIcon = Dice.resizeImg(width, height, explosionIcon, false);
		explosionGif.setSize(explosionIcon.getIconWidth(), explosionIcon.getIconHeight());
		explosionGif.setIcon( explosionIcon );
	}
	
	
	public void addPlayerToBoard(int playersIndex, int square) {
		
		int temp[] = determineSquareCoords(square);
		
		User user = players.get( playersIndex );
		user.setBoardCoordinates( temp[0], temp[1] );
		user.setCurrentSquare(square);				
		user.setSize( user.getAvatar().getIconWidth() , user.getAvatar().getIconHeight() );
		
		temp = squareCoordInPixels(square);
		
		user.setLocation( temp[0] , temp[1] ); 
		this.add(user, 0);						 
	}
	
	
	public void addPlayerToBoard(User user, int square) {
		
		int temp[] = determineSquareCoords(square);
		
		user.setBoardCoordinates( temp[0], temp[1] );
		user.setCurrentSquare(square);
		user.setSize( user.getAvatar().getIconWidth() , user.getAvatar().getIconHeight() );
		
		temp = squareCoordInPixels(square);
		
		user.setLocation( temp[0] , temp[1] ); 
		this.add(user, 0);						 
	}
	
	public List<User> getPlayers() {
		return players;
	}
	

	public void fillPlayersLists( int humans, int bots ) {		
		
		Random random = new Random();
		ArrayList<Integer> avatarsIdArray = new ArrayList<>();
		
		for (int i=0; i<humans+bots; i++) {
			
			int num = random.nextInt( this.totalAvatarIcons )+1;
			
			while( avatarsIdArray.contains(num) ) {
				num = random.nextInt( this.totalAvatarIcons )+1;
			}
			
			if(i<humans) {
				players.add( new User(num, i+1, false) );
				avatarsIdArray.add(num);		
			}
			else {
				players.add( new User(num, i+1, true) );
				avatarsIdArray.add(num);			
			}
		}
			
	}
	
	
	public  void movePlayerHorizontally(User user, int steps, int delay, int period) {		
		
		int current_x = FIRTS_SQUARE_X + DELTA_X*user.getBoardCoordinateX() ;
		int current_y = FIRTS_SQUARE_Y - DELTA_Y*user.getBoardCoordinateY() ;
		int final_x = current_x+(DELTA_X*steps);
		int final_y = current_y;
		
		user.setBoardCoordinateX( user.getBoardCoordinateX() + steps );
		
		
		Runnable myTask = new Runnable() {
						
			int ctr=1;			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if( user.getX() < final_x && steps>0 ) {
					user.setLocation( current_x + (ctr*2), final_y );
					ctr++;
				}
				else if( user.getX() > final_x && steps<0  ) {
					user.setLocation( current_x - (ctr*2), final_y );
					ctr++;
				}
				else {
					System.out.println("%%% new x_coord: "+ user.getBoardCoordinateX());					
					
					scheduler.notifyAll();											
				}
				
			}			
		};
			
		
		ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(myTask, delay, period, TimeUnit.MILLISECONDS);
		lastPreformedMovementTask = taskHandle;	
		
		Runnable canceler = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub	

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
			
			
		scheduler.schedule(canceler, 1, TimeUnit.MILLISECONDS);		
	}
	
	
	public void movePlayerVertically(User user, int steps, int delay, int period) {
		
		int current_x = FIRTS_SQUARE_X + DELTA_X*user.getBoardCoordinateX() ;
		int current_y = FIRTS_SQUARE_Y - DELTA_Y*user.getBoardCoordinateY() ;
		int final_x = current_x;
		int final_y = current_y - (DELTA_Y*steps); 		
		
		user.setBoardCoordinateY( user.getBoardCoordinateY() + steps );
		
		Runnable task = new Runnable() {
			
			int ctr=1;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if( user.getY() > final_y && steps>0 ) {
					user.setLocation(final_x, current_y - (ctr*2) );					
					ctr++;
				}
				else if( user.getY() < final_y && steps<0  ) {
					user.setLocation(final_x, current_y + (ctr*2) );
					ctr++;
				}
				else {
					System.out.println("$$$ new Y_coord: "+ user.getBoardCoordinateY());
					
					scheduler.notifyAll();
				}
				
			}
		};
		
		
		ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
		lastPreformedMovementTask = taskHandle;
		
		Runnable canceler = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

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
		
		scheduler.schedule(canceler, 1, TimeUnit.MILLISECONDS);
	}
	
	
	
	public void moveForward(User user,int steps) {		
		
		int finalSquare = user.getCurrentSquare() + steps;
		int currentSquare = user.getCurrentSquare();
		
		if( currentSquare+steps >= 100 ) {
			
			steps = ( user.getBoardCoordinateY()%2==0 )? 100-currentSquare : -(100-currentSquare);
			movePlayerHorizontally(user, steps, this.delay, this.period);
			user.setCurrentSquare(100);
			
			System.out.println("GAME FINISH");
		}
		else if( currentSquare+steps <= (user.getBoardCoordinateY()+1)*10 ) {
			
			user.setCurrentSquare(currentSquare+steps);
			
			steps = ( user.getBoardCoordinateY()%2==0 )? steps : -steps;
			movePlayerHorizontally(user, steps, this.delay, this.period);
			
			System.out.println("single dimension move (HORIZONTAL MOVE)");
			System.out.println("currentsquare: -> "+user.getCurrentSquare());
			
			steps = ( steps>=0 )? steps : -steps;
			int newDelay = this.delay + ( this.movesFor1Square*this.period )*steps;
			
			if( containsALadder( user.getCurrentSquare() ) ) {
				
				System.out.println("YOU ARE IN A LADDER");
				upTheLadder(user, newDelay, this.period);
			}
			else if( containsASnake( user.getCurrentSquare() ) ) {
				
				System.out.println("YOU ARE IN A SNAKE");
				downTheSnake(user, newDelay);
			}
			
			
		}
		else {
			
			System.out.println("BI-dimensional move (HORIZONTAL+VERTICAL MOVES)");
			
			int temp = (user.getBoardCoordinateY()+1)*10 - currentSquare;			
			
			steps = ( user.getBoardCoordinateY()%2==0 )? temp : -temp;
			movePlayerHorizontally(user, steps, this.delay, this.period);			
			user.setCurrentSquare(currentSquare+temp);

			int newDelay = this.delay + ( this.movesFor1Square*this.period )*temp;
			movePlayerVertically(user, 1, newDelay, this.period);			
			user.setCurrentSquare(user.getCurrentSquare()+1);
				
			steps = finalSquare - user.getCurrentSquare();
			System.out.println("steps: -> "+steps);			
			user.setCurrentSquare(user.getCurrentSquare()+steps);
			
			newDelay = this.delay + ( this.movesFor1Square*this.period )*(temp+1) ;
			steps = ( user.getBoardCoordinateY()%2==0 )? steps : -steps;
						
			movePlayerHorizontally(user, steps, newDelay, this.period);
			
			System.out.println("currentsquare: -> "+user.getCurrentSquare());
			
			steps = ( steps>=0 )? steps : -steps;
			newDelay = this.delay + ( this.movesFor1Square*this.period )*(temp+1+steps) ;
			
			if( containsALadder( user.getCurrentSquare() ) ) {
				
				System.out.println("YOU ARE IN A LADDER");
				upTheLadder(user, newDelay, this.period);
			}
			else if( containsASnake( user.getCurrentSquare() ) ) {
				
				System.out.println("YOU ARE IN A SNAKE");
				downTheSnake(user, newDelay);
			}
			
			
		}
		
	}
	
	
	public boolean containsALadder(int square) {
		
		boolean flag=false;
		
		for (int[] myArray : squaresWithLadders) {
			
			if( myArray[0]==square )
				flag=true;
		}
		
		return flag;
	}
	
	
	public int[] determineSquareCoords(int square) {
		
		int coords[] = new int[2]; 		
		
		for(int i=0; i<10; i++) {
			
			if( square>0 && square<=10*(i+1) ) {
				
				if(i==0) {
					coords[0]= square-1;
					coords[1]= 0;
					break;
				}
				else if( i%2!=0 ) {
					coords[0]= (10*(i+1))-square;
					coords[1]= i;
					break;
				}
				else {
					coords[0]= square-(10*i)-1;
					coords[1]= i;
					break;
				}
				
			}
		}		
		
		return coords;
	}
	
	
	public int[] squareCoordInPixels( int square ) {
		
		int coordInPx[] = new int[2];
		int boardCoords[] = determineSquareCoords(square);		
		
		coordInPx[0] = FIRTS_SQUARE_X + DELTA_X*boardCoords[0] ;
		coordInPx[1] = FIRTS_SQUARE_Y - DELTA_Y*boardCoords[1];
		
		return coordInPx;
	}
	
	
	
	public void upTheLadder(User user, int delay, int period) {
		
		Integer[] ladderArray = new Integer[2];
		
		for (int[] i : squaresWithLadders) {
			
			if( i[0] == user.getCurrentSquare() ) {
				ladderArray[0] = i[0];
				ladderArray[1] = i[1];
				break;
				
			}else {
				ladderArray[0] = null;
				ladderArray[1] = null;
			}
		}
		
		int pointA[] = squareCoordInPixels( ladderArray[0] );
		int pointB[] = squareCoordInPixels( ladderArray[1] );
		
		System.out.println( "pointA-> "+ pointA[0]+", "+pointA[1] );
		System.out.println( "pointB-> "+ pointB[0]+", "+pointB[1] );
		
		double m;
		boolean verticalLine;
		
		if( pointB[0]==pointA[0] ) {
			verticalLine=true;
			m=0;
		}else {
			verticalLine = false;
			m = ((double) (pointB[1]-pointA[1]) ) / (pointB[0]-pointA[0]) ;
		}

		
		// creationn of the tasks (Runnable) that are goona move the avatar
		
		if( verticalLine ) {
			
			int temp[] = determineSquareCoords( ladderArray[0] );
			int temp2[] = determineSquareCoords( ladderArray[1] );
			
			int steps = temp2[1] - temp[1];
			System.out.println( "___ AVATR CLIMING UP THE LADDER ___" );
			System.out.println( "_____currentSquare -> "+ user.getCurrentSquare() );
			
			user.setCurrentSquare( ladderArray[1] );
			movePlayerVertically(user, steps, delay, period);
		}
		else {
			
			int final_x = pointB[0], final_y = pointB[1];
			
			Runnable myTask = new Runnable() {
				
				int ctr=1;
				@Override
				public void run() {
					// TODO Auto-generated method stub					
					
					if( m<0 && user.getX()<final_x && user.getY()>final_y ) {
						
						int x= user.getX() + (ctr*2);
						double y = m*(x - pointA[0]) + pointA[1];				
						
						user.setLocation( x, (int) y);
						
						if( user.getX()>final_x || user.getY()<final_y ) {
							
							user.setLocation( pointB[0], pointB[1] );
						}
						ctr++;
					}
					else if ( m>0 && user.getX()>final_x && user.getY()>final_y ) {
						
						int x= user.getX() - (ctr*2);
						double y = m*(x - pointA[0]) + pointA[1];
						
						user.setLocation( x, (int) y);
						
						if( user.getX()<final_x || user.getY()<final_y ) {
							
							user.setLocation( pointB[0], pointB[1] );
						}
						ctr++;						
					}
					else {
						
						int temp[] = determineSquareCoords( ladderArray[1] );
						user.setBoardCoordinates( temp[0], temp[1] );
						user.setCurrentSquare( ladderArray[1] );

						System.out.println( "___ AVATAR CLIMING UP THE LADDER ___" );
						System.out.println( "_____currentSquare -> "+ user.getCurrentSquare() );
						
						scheduler.notifyAll();
					}
					
					
				}//END of run() method of myTask
			};// END of runnable myTask
			
			
			ScheduledFuture<?> taskHandle = scheduler.scheduleAtFixedRate(myTask, delay, period, TimeUnit.MILLISECONDS);
			lastPreformedMovementTask = taskHandle;			
			
			Runnable canceler = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub					
					while( !taskHandle.isDone() ) { 
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}	
					}
					taskHandle.cancel(false);				
				}
			};//END Runnable canceler
			
			scheduler.schedule(canceler, 1, TimeUnit.MILLISECONDS);						
			
		} // END of the else
		
	}
	
	
	
	public boolean containsASnake(int square) {
		
		boolean flag=false;
		
		for (int[] myArray : squaresWithSnakes) {
			
			if( myArray[0] == square )
				flag = true;		
		}
		
		return flag;
	}
	
	
	public void downTheSnake(User user, int delay) {
				
		Integer snakeArray[] = new Integer[2];
		
		for (int[] i : squaresWithSnakes) {
			
			if( i[0] == user.getCurrentSquare() ) {
				snakeArray[0] = i[0];
				snakeArray[1] = i[1];
				break;
			}
			else {
				snakeArray[0] = null;
				snakeArray[1] = null;
			}
		}
		
		int  inicialPoint[] = squareCoordInPixels( snakeArray[0] );
		int  finalPoint[] = squareCoordInPixels( snakeArray[1] );
		
		System.out.println("______ marca antes de explosion task ______");
		
		Runnable explosionTask = new Runnable() {
			
			int ctr=0;
			@Override
			public void run() {
								
				if( ctr==0 ) {
					user.setVisible(false);
					user.setLocation( finalPoint[0] , finalPoint[1] );
					explosionGif.setLocation( inicialPoint[0] , inicialPoint[1] );
					explosionGif.setVisible(true);							
				}
				else {
					explosionGif.setVisible(false);
					System.out.println("__ EXPLOSION TASK COMPLETED __");
					scheduler.notifyAll();
				}						
				ctr++;														
			}
		}; // END explosionTask
		
		
		ScheduledFuture<?> explosionHandle = scheduler.scheduleAtFixedRate(explosionTask, delay, this.durationExplosionGif, TimeUnit.MILLISECONDS);
		lastPreformedMovementTask = explosionHandle;
		
		Runnable eggTask = new Runnable() {
			
			int ctr=0;
			@Override
			public void run() {				
					
				while( !explosionHandle.isDone()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				explosionHandle.cancel(false);
				
				if( ctr==0 ) {							
					eggGif.setLocation( finalPoint[0]-10 , finalPoint[1] );
					eggGif.setVisible(true);							
				}
				else {			
					int temp[] = determineSquareCoords( snakeArray[1] );
												
					eggGif.setVisible(false);
					user.setBoardCoordinates( temp[0], temp[1] );
					user.setCurrentSquare( snakeArray[1] );
					user.setVisible(true);
					
					System.out.println("__ EGG TASK COMPLETED __");
					System.out.println("____ currentSquare -> "+ user.getCurrentSquare() );
					scheduler.notifyAll();
				}
				ctr++; 				
			}
		};// END eggTask
		
		int eggTaskDelay = delay + this.durationExplosionGif + this.durationEggGif;
		
		ScheduledFuture<?> eggHandle = scheduler.scheduleAtFixedRate(eggTask, eggTaskDelay,  this.durationEggGif, TimeUnit.MILLISECONDS); 
		lastPreformedMovementTask = eggHandle;
		
		Runnable canceler = new Runnable() {
			
			@Override
			public void run() {

				while( !eggHandle.isDone() ) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				eggHandle.cancel(false);				
			}
		}; // END canceler
		
		
		scheduler.schedule(canceler, 1, TimeUnit.MILLISECONDS);
				
	}// END method downTheSnake

	
	
	public ScheduledFuture<?> getLastPreformedMovementTask() {
		return lastPreformedMovementTask;
	}
		
	
}// END class GameBoard
