import javax.swing.JFrame;

public class PenteGameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int gWidth = 800;
		int gHeight = 800;
		
		JFrame theGame = new JFrame("Play Pente!!");
		theGame.setSize(gWidth, gHeight);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight-20);
		theGame.add(gb);
		
		theGame.setVisible(true);
		gb.startNewGame();

	}

}
