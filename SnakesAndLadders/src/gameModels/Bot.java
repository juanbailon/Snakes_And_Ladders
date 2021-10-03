package gameModels;

public class Bot extends User implements Runnable {

	public Bot(int avatarId, int turn) {
		
		super(avatarId, turn);
		super.isBot = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
