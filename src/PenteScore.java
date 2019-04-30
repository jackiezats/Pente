import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel implements ActionListener{

	//data
	private JLabel p1Name, p2Name;
	private JTextField p1Captures, p2Captures;
	private JButton resetButton;
	private Color backColor = new Color (198, 220, 255);
	private int spWidth;
	private int spHeight;
	private Font myFont = new Font("Ariel", Font.BOLD, 24);
	private JTextField whoseTurnField;
	private Color bBlack = new Color(5, 11, 91);
	private PenteGameBoard myBoard = null;
	private boolean firstGame = true;
	
	
	public PenteScore(int w, int h) {
		
		spWidth = w;
		spHeight = h;
		
		this.setSize(spWidth, spHeight);
		this.setBackground(backColor);
		
		this.setVisible(true);
		
		addInfoPlaces();
		
	}
	
	public void addInfoPlaces() {
		
		//player 1
		JPanel p1Panel = new JPanel();
		p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.Y_AXIS));
		p1Panel.setSize(spWidth, (int)(spHeight * 0.45));
		p1Panel.setBackground(new Color(137, 156, 249)); //change to a lighter blue
		//p1Panel.setOpaque(false);
			
			p1Name = new JLabel("Name: ");
			p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Name.setFont(myFont);
			p1Name.setForeground(bBlack);
			p1Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			p1Captures = new JTextField("Player1 Captures:");
			p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Captures.setFont(myFont);
			p1Captures.setForeground(Color.WHITE);
			p1Captures.setBackground(bBlack);
			p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
			p1Captures.setFocusable(false);//cant edit score
			
			//place and space the labels
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,70)));
			p1Panel.add(p1Name);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,20)));
			p1Panel.add(p1Captures);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,40)));
			
			Border b = BorderFactory.createLineBorder(Color.BLUE, 4, true);
			p1Panel.setBorder(b);
			
			this.add(p1Panel);
			
			
			//player 2
			JPanel p2Panel = new JPanel();
			p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.Y_AXIS));
			p2Panel.setSize(spWidth, (int)(spHeight * 0.45));
			p2Panel.setBackground(new Color(137, 156, 249));
			//p2Panel.setOpaque(false);
			
				p2Name = new JLabel("Name: ");
				p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
				p2Name.setFont(myFont);
				p2Name.setForeground(Color.WHITE);
				p2Name.setHorizontalAlignment(SwingConstants.CENTER);
				
				p2Captures = new JTextField("Player2 Captures:");
				p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
				p2Captures.setFont(myFont);
				p2Captures.setForeground(bBlack);
				p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
				p2Captures.setFocusable(false);
				
				//place and space the labels
				p2Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,70)));
				p2Panel.add(p2Name);
				p2Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,20)));
				p2Panel.add(p2Captures);
				p2Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,40)));
				
	
				p2Panel.setBorder(b);
				
				this.add(p2Panel);
			
				//whose turn info
				JPanel whoseTurn = new JPanel();
				whoseTurn.setLayout(new BoxLayout(whoseTurn, BoxLayout.Y_AXIS));
				whoseTurn.setSize(spWidth, (int)(spHeight*0.45));
				whoseTurn.setOpaque(false);
				
				whoseTurnField = new JTextField("Whose Turn?");
				whoseTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
				whoseTurnField.setFont(myFont);
				whoseTurnField.setForeground(bBlack);
				whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);
				
				whoseTurn.add(Box.createRigidArea(new Dimension(spWidth - 40,20)));
				whoseTurn.add(whoseTurnField);
				whoseTurn.add(Box.createRigidArea(new Dimension(spWidth - 40,20)));
				
				//whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);

				Border b3 = BorderFactory.createLineBorder(Color.BLUE, 4, true);
				whoseTurn.setBorder(b3);
				
				this.add(Box.createRigidArea(new Dimension(spWidth-40, 30)));
				this.add(whoseTurn);
				
			//Add a button
			resetButton = new JButton("New Game");
			resetButton.setFont(myFont);
			resetButton.addActionListener(this);
			this.add(resetButton);
			
			
	}
	
	public void setName(String n, int whichPlayer) {
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			p1Name.setText("Player 1: " + n);
		} else {
			p2Name.setText("Player 2: " + n);
		}
		
		repaint();
	
	}
	
	public void setCaptures(int c, int whichPlayer) {
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			p1Captures.setText(Integer.toString(c));
			Rectangle r = p1Captures.getVisibleRect();
			p1Captures.paintImmediately(r);
		} else {
			p2Captures.setText(Integer.toString(c));
			Rectangle r = p2Captures.getVisibleRect();
			p2Captures.paintImmediately(r);
		}
		
		//repaint();
	}
	
	public void setPlayerTurn(int whichPlayer) {
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			whoseTurnField.setBackground(bBlack);
			whoseTurnField.setForeground(Color.WHITE);
			// fix this p1Name.getText();
			int cLoc = p1Name.getText().indexOf(":");
			String n = p1Name.getText().substring(cLoc + 2, p1Name.getText().length());
			
			
			//whoseTurnField.setText("It's " + n + "'s Turn Now");
			whoseTurnField.setText(n);
			
		} else {
			whoseTurnField.setBackground(Color.WHITE);
			whoseTurnField.setForeground(bBlack);
			
			int cLoc = p2Name.getText().indexOf(":");
			String n = p2Name.getText().substring(cLoc + 2, p2Name.getText().length());
			//whoseTurnField.setText("It's " + n + "'s Turn Now");
			whoseTurnField.setText(n);
		}
		if(firstGame) {
			whoseTurnField.repaint();
		} else {
			Rectangle r = whoseTurnField.getVisibleRect();
			whoseTurnField.paintImmediately(r);
		}
	}
	
	public void setGameBoard(PenteGameBoard gb) {
		myBoard = gb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println("Hi you clicked me!");
		JOptionPane.showMessageDialog(null, "Starting new game...");
		firstGame = false;
		if(myBoard != null) myBoard.startNewGame(false);
	}
}
 