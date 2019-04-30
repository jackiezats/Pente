import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class PenteGameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int gWidth = 800;
		int gHeight = 800;
		int sbWidth = (int)(gWidth * 0.45);
		
		JFrame theGame = new JFrame("Play Pente!!");
		theGame.setLayout(new BorderLayout());
		
		
		theGame.setSize(gWidth + sbWidth, gHeight);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PenteScore sb = new PenteScore(sbWidth, gHeight);
		sb.setPreferredSize(new Dimension(sbWidth, gHeight));
		
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight-20, sb);
		gb.setPreferredSize(new Dimension(gWidth, gHeight));
		
		theGame.add(gb, BorderLayout.CENTER);
		theGame.add(sb, BorderLayout.EAST);
		
		sb.setGameBoard(gb);
		
		theGame.setVisible(true); //see display
		gb.startNewGame(true);
		

	}

}
