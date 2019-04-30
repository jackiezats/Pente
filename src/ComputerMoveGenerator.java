import java.util.ArrayList;

public class ComputerMoveGenerator {

	public static int OFFENSE = 1;
	public static int DEFENSE = -1;
	
	
	PenteGameBoard myGame;
	int myStone;
	
	ArrayList<CMObject> oMoves = new ArrayList<CMObject>();
	ArrayList<CMObject> dMoves = new ArrayList<CMObject>();
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor) {
		
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player " + myStone);
		
	}
	
	public int[] getComputerMove() {
		int [] newMove;
		
		findDefMoves();
		findOffMoves();
		
		
		
		newMove = generateRandomMove();
		
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return newMove;
	}
	
	public void findDefMoves() {
		findOneDef();
		//findTwoDef();
		//findThreeDef();
		//findFourDef();
	}
	
	public void findOneDef() {
		//wed
	}
	
	public void findOffMoves() {
		
	}
	
	public int[] generateRandomMove() {
		int[] move = new int[2];
		
		boolean done = false;
		int newR, newC;
		
		do {
			newR = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			newC = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			
			if(myGame.getBoard()[newR][newC].getState() == PenteGameBoard.EMPTY) {
				done = true;
				move[0] = newR;
				move[1] = newC;
			}
		} while(!done);
		
		return move;
	}
	
	public void sleepForAMove() throws InterruptedException {
		Thread currThread = Thread.currentThread();
		currThread.sleep(PenteGameBoard.SLEEP_TIME);
	}
	
}
