import java.awt.EventQueue;

import gameViews.GameGUI;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		EventQueue.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GameGUI gui = new GameGUI();
			}
		} );
		
	}	

}
