import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class PenteGameBoard extends JPanel implements MouseListener{

	
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int NUM_SQUARES_SIDE = 19;
	public static final int INNER_START = 7;
	public static final int INNER_END = 11;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	public static final int MAX_CAPTURES = 5;
	public static final int SLEEP_TIME = 50;
	
	//variables for play (p1 is dark stones & moves first)
	private int playerTurn;
	private boolean player1IsComputer = false;
	private boolean player2IsComputer = false;
	private String p1Name, p2Name;
	private boolean darkStoneMove2Taken = false;
	
	private boolean gameOver = false;
	
	//computer game players
	private ComputerMoveGenerator p1ComputerPlayer = null;
	private ComputerMoveGenerator p2ComputerPlayer = null;
	
	private int bWidth, bHeight;
	private PenteBoardSquare testSquare;
	private int squareW, squareH;
	
	//make data structure to hold the board pieces
	private PenteBoardSquare[][] gameBoard;
	private PenteScore myScoreBoard;
	private int p1Captures, p2Captures;

	
	public PenteGameBoard(int w, int h, PenteScore sb) {
		
		//storing the variables
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		
		p1Captures = 0;
		p2Captures = 0;
	
		this.setSize(w,h);
		this.setBackground(Color.pink);
		
		squareW = bWidth/this.NUM_SQUARES_SIDE;
		squareH = bHeight/this.NUM_SQUARES_SIDE;
		
		//testSquare = new PenteBoardSquare(0, 0, squareW, squareH);
		gameBoard = new PenteBoardSquare[NUM_SQUARES_SIDE][NUM_SQUARES_SIDE];
		
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
				gameBoard[row][col] = new PenteBoardSquare(col * squareW, row * squareH, squareW, squareH);
				
				if(col >= INNER_START && col <= INNER_END) {
					if(row >= INNER_START && row <= INNER_END) {
					gameBoard[row][col].setInner();
				}
			
			}
				//Testing
				//gameBoard[row][col].setState(BLACKSTONE);
				//gameBoard[row][col].setState(WHITESTONE);
				
				/*
				if(row+col % 2 == 0) {
					gameBoard[row][col].setState(BLACKSTONE);
				} else {
					gameBoard[row][col].setState(WHITESTONE);
				}
				*/
		}
	}
		
		//startNewGame();
		//this.resetBoard();
		display();
		repaint();
		addMouseListener(this);
		this.setFocusable(true);
	
}
	//Drawing Method (overriding)
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.pink);
		g.fillRect(0, 0, bWidth, bHeight);
		
		//do this 19x19 times
		//testSquare.drawMe(g);
		
		//columns
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
				
				gameBoard[row][col].drawMe(g);
				
				
			}
			
		}

	}
	
	public void resetBoard() {
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
				
				gameBoard[row][col].setState(EMPTY);
				gameBoard[row][col].setWinningSquare(false);
				
			}	
		} 
		//this.paintImmediately(0, 0, bWidth, bHeight); 
	}
	
	public void startNewGame(boolean firstGame) {
		
		//resetBoard();
		
		p1Captures = 0;
		p2Captures = 0;
		
	
		
		if(firstGame) {
			p1Name = JOptionPane.showInputDialog("Name of Player 1 (or type 'c' for computer");
				if(p1Name  != null && ( p1Name.toLowerCase().equals("c") || p1Name.toLowerCase().equals("computer") || p1Name.toLowerCase().equals("comp"))) {		
					player1IsComputer = true;
					p1ComputerPlayer = new ComputerMoveGenerator(this, BLACKSTONE);
				}
			p2Name = JOptionPane.showInputDialog("Name of Player 2 (or type 'c' for computer");
				if(p2Name != null && (p2Name.toLowerCase().equals("c") || p2Name.toLowerCase().equals("computer") || p2Name.toLowerCase().equals("comp"))){		
					player2IsComputer = true;
					p2ComputerPlayer = new ComputerMoveGenerator(this, WHITESTONE);
				}
		} 
			
		myScoreBoard.setName(p1Name, PenteGameBoard.BLACKSTONE);
		myScoreBoard.setCaptures(p1Captures, PenteGameBoard.BLACKSTONE);
		myScoreBoard.setName(p2Name, PenteGameBoard.WHITESTONE);
		myScoreBoard.setCaptures(p2Captures, PenteGameBoard.WHITESTONE);
		
			resetBoard();
			gameOver = false;
			//first stone placed
			playerTurn = this.PLAYER1_TURN;
			//sets the center square as a dark square
			this.gameBoard[NUM_SQUARES_SIDE/2][NUM_SQUARES_SIDE/2].setState(BLACKSTONE);
			darkStoneMove2Taken = false;
			changePlayerTurn();	
			checkForComputerMove(playerTurn);
			
			this.repaint();
			} 
		
	
	public void changePlayerTurn () {
		playerTurn *= -1;
		myScoreBoard.setPlayerTurn(playerTurn);
	}
	
	
	/*
	public void updateSizes() {
		if(myFrame.getWidth() ! = bWidth || myFrame.getHeight() ! = bHeight + 20) {
			bWidth = myFrame.getWidth();
			bHeight = myFrame.getHeight() - 20;
			
			squareW = bWidth/this.NUM_SQUARES_SIDE;
			squareH = bHeight/this.NUM_SQUARES_SIDE;
			
			resetSquares(squareW, squareH);
		}		
	*/  	
		
	public void resetSquares(int w, int h)	{
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
				gameBoard[row][col].setXLoc(col * w);
				gameBoard[row][col].setYLoc(row * h);
				gameBoard[row][col].setWidth(w);
				gameBoard[row][col].setHeight(h);
			}
		}
	}
	
	
	
	
	//the location of the click
	public void checkClick(int clickX, int clickY) {
		
		
		if(!gameOver) {
			for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
				
				for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
					
					boolean squareClicked = gameBoard[row][col].isClicked(clickX, clickY);
					if(squareClicked) {
						System.out.println("The square is clicked at [" + row + ", " + col + "]");
						if(gameBoard[row][col].getState() == EMPTY) {
							//check to see 2nd dark move
							if(!darkSquareProblem(row, col)) {
								gameBoard[row][col].setState(playerTurn);
								checkForAllCaptures(row, col, playerTurn);
								this.repaint();
								checkForWin(playerTurn);
								this.changePlayerTurn();
								checkForComputerMove(playerTurn);
								
							} else {
								JOptionPane.showMessageDialog(null, "The second dark stone has to be moved outside of the light square. Try again!");
							}
						} else {
								JOptionPane.showMessageDialog(null, "This square is taken, please chose another");
						}
					}
				}
			}
		}
	}
	
	public void checkForComputerMove(int whichPlayer) {
		
		if(whichPlayer == this.PLAYER1_TURN && this.player1IsComputer) {
			int [] nextMove = this.p1ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			//this.repaint();
			checkForAllCaptures(newR, newC, playerTurn);
			//this.repaint();
			checkForWin(playerTurn);
			if(!gameOver) {
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
			
		} else if(whichPlayer == this.PLAYER2_TURN && this.player2IsComputer) {
			int [] nextMove = this.p2ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			//this.repaint();
			checkForAllCaptures(newR, newC, playerTurn);
			this.repaint();
			checkForWin(playerTurn);

			if(!gameOver) {
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
			
			
			
		}
	}
	
	public boolean darkSquareProblem(int r, int c) {
		boolean dsp = false;
		
		if((darkStoneMove2Taken == false) && (playerTurn == BLACKSTONE)) {
			
			if((r >= INNER_START && r <= INNER_END) && (c >= INNER_START && c <= INNER_END)) {
						dsp = true;
					} else {
						darkStoneMove2Taken = true;
					}
		}
				
		return dsp;
	}
	
	public boolean darkSquareProblemComputerMoveList(int r, int c) {
		
		boolean dsp = false;
		
		if((darkStoneMove2Taken == false) && (playerTurn == BLACKSTONE)) {
			
			if((r >= INNER_START && r <= INNER_END) && (c >= INNER_START && c <= INNER_END)) {
						dsp = true;
					} 
		}
				
		return dsp;
	}
	
	public boolean fiveInARow(int whichPlayer) {
		
		
		boolean isFive = false;
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col< NUM_SQUARES_SIDE; col++) {
				
				
				for(int rL = -1; rL <= 1; rL++) {
					for(int uD = -1; uD <= 1; uD++) {
						if(fiveCheck(row, col, whichPlayer, rL, uD)) {
							isFive = true;
						}
							
					}
				}
				
			}
		}
		
		return isFive;
	}
	
	
	public boolean fiveCheck(int r, int c, int pt, int upDown, int rightLeft) {
		
		try {
			boolean win = false;
	
				if(!(upDown == 0 && rightLeft == 0)) {
					if(gameBoard[r][c].getState() == pt) {
						if(gameBoard[r + upDown][c + rightLeft].getState() == pt ) {
							if(gameBoard[r + (upDown*2)][c + (rightLeft*2)].getState() == pt) {
								if(gameBoard[r + (upDown*3)][c + (rightLeft*3)].getState() == pt ) {
									if(gameBoard[r + (upDown*4)][c + (rightLeft*4)].getState() == pt ) {
										
											
												System.out.println("Five! You win!");
												//gameBoard[r + upDown][c+rightLeft].setState(EMPTY);
												//gameBoard[r + (upDown*2)][c+(rightLeft * 2)].setState(EMPTY);
												
												win = true;
												gameBoard[r][c].setWinningSquare(true);
												gameBoard[r+upDown][c+rightLeft].setWinningSquare(true);
												gameBoard[r+(upDown*2)][c+(rightLeft*2)].setWinningSquare(true);
												gameBoard[r+(upDown*3)][c+(rightLeft*3)].setWinningSquare(true);
												gameBoard[r+(upDown*4)][c+(rightLeft*4)].setWinningSquare(true);
		//											if(pt == this.PLAYER1_TURN) {
		//												JOptionPane.showMessageDialog(null, "WIN!");
		//											
		//											} else {
		//												JOptionPane.showMessageDialog(null, "WIN!");
		//											}
											
										
									}
								}
							}
						}
					}
				}
				
				/*if(pt == this.PLAYER1_TURN && win == true) {
					JOptionPane.showMessageDialog(null, "WIN!");
				
				} else if(pt == this.PLAYER2_TURN && win == true){
					JOptionPane.showMessageDialog(null, "WIN!");
				}
				*/
				return win;
			} catch(ArrayIndexOutOfBoundsException e) {
				//System.out.println("error" /*+ e.toString() */);
				return false;
		}
		

		
		
	}
	
	
	
	
	//checking for 5 in a row
	public void checkForWin(int whichPlayer) {
		if(whichPlayer == PLAYER1_TURN) {
			if(this.p1Captures >= MAX_CAPTURES) {
				JOptionPane.showMessageDialog(null, "Congratulations " + p1Name + ", you win!!!");
				gameOver = true;
			} else {
				if(fiveInARow(whichPlayer)) {
					JOptionPane.showMessageDialog(null, "Congratulations " + p1Name + ", you win!!!");
					gameOver = true;
				}
				
			} 
				
		} else {
			if(this.p2Captures >= MAX_CAPTURES) {
				JOptionPane.showMessageDialog(null, "Congratulations " + p2Name + ", you win!!!");
				gameOver = true;
			} else {
				if(fiveInARow(whichPlayer)) {
					JOptionPane.showMessageDialog(null, "Congratulations " + p2Name + ", you win!!!");
					gameOver = true;
				}
			}
	}
			
}
	
	//This checks all of the captures
	public void checkForAllCaptures(int r, int c, int pt) {
		
		boolean didCapture;
		
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				didCapture = checkForCaptures(r, c, pt, rL, uD);
			}
		}
		
		/*
		//horizontal checks
		if(c <= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pt, 0, 1);
		if(c >= 3) didCapture = checkForCaptures(r, c, pt, 0, -1);
		//vertical
		if(r <= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pt, 1, 0);
		if(r >= 3) didCapture = checkForCaptures(r, c, pt, -1, 0);
		//diagonal down left
		if((r <= NUM_SQUARES_SIDE - 4) && (c >= 3)) didCapture = checkForCaptures(r, c, pt, 1, -1);
		if((c <= NUM_SQUARES_SIDE - 4) &&(r >= 3)) didCapture = checkForCaptures(r, c, pt, -1, 1);
		//diagonal down right
		if((r <= NUM_SQUARES_SIDE - 4) && (c >= 3)) didCapture = checkForCaptures(r, c, pt, -1, -1);
		if((c <= NUM_SQUARES_SIDE - 4) &&(r >= 3)) didCapture = checkForCaptures(r, c, pt, 1, 1);
		*/
	}
	
	public boolean checkForCaptures(int r, int c, int pt, int upDown, int rightLeft) {
		
		try {
			boolean cap = false;
		
				if(gameBoard[r + upDown][c + rightLeft].getState() == pt * -1) {
					if(gameBoard[r + (upDown*2)][c + (rightLeft*2)].getState() == pt * -1) {
						if(gameBoard[r + (upDown*3)][c + (rightLeft*3)].getState() == pt) {
							System.out.println("Horizontal Capture");
							gameBoard[r + upDown][c+rightLeft].setState(EMPTY);
							gameBoard[r + (upDown*2)][c+(rightLeft * 2)].setState(EMPTY);
							cap = true;
							if(pt == this.PLAYER1_TURN) {
								p1Captures++;
								myScoreBoard.setCaptures(p1Captures, playerTurn);
								
							} else {
								p2Captures++;
								myScoreBoard.setCaptures(p2Captures, playerTurn);
							}
						}
					}
				}
			
			
			return cap;
		} catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("error" + e.toString());
			return false;
		}
	}
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Clicked");
		//System.out.println("this.gameBoard[" + e.getX() 
			//	+ "]" + "[" + e.getY() + "].setState()");
		this.checkClick(e.getX(), e.getY());
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void display() {
		this.gameBoard[1][1].setState(BLACKSTONE);
		this.gameBoard[3][1].setState(BLACKSTONE);
		this.gameBoard[2][1].setState(WHITESTONE);
		this.gameBoard[4][1].setState(BLACKSTONE);
		this.gameBoard[5][1].setState(WHITESTONE);
		this.gameBoard[1][2].setState(BLACKSTONE);
		this.gameBoard[1][3].setState(WHITESTONE);
		this.gameBoard[2][3].setState(BLACKSTONE);
		this.gameBoard[3][3].setState(WHITESTONE);
		this.gameBoard[3][2].setState(BLACKSTONE);
		this.gameBoard[1][5].setState(WHITESTONE);
		this.gameBoard[2][5].setState(BLACKSTONE);
		this.gameBoard[3][5].setState(WHITESTONE);
		this.gameBoard[4][5].setState(BLACKSTONE);
		this.gameBoard[5][5].setState(WHITESTONE);
		this.gameBoard[5][6].setState(BLACKSTONE);
		this.gameBoard[5][7].setState(WHITESTONE);
		this.gameBoard[5][9].setState(BLACKSTONE);
		this.gameBoard[4][9].setState(WHITESTONE);
		this.gameBoard[3][9].setState(BLACKSTONE);
		this.gameBoard[2][9].setState(WHITESTONE);
		this.gameBoard[1][9].setState(BLACKSTONE);
		this.gameBoard[1][10].setState(WHITESTONE);
		this.gameBoard[1][11].setState(BLACKSTONE);
		this.gameBoard[2][11].setState(WHITESTONE);
		this.gameBoard[3][11].setState(BLACKSTONE);
		this.gameBoard[4][11].setState(WHITESTONE);
		this.gameBoard[5][11].setState(BLACKSTONE);
		this.gameBoard[3][10].setState(WHITESTONE);
		this.gameBoard[1][13].setState(BLACKSTONE);
		this.gameBoard[2][13].setState(WHITESTONE);
		this.gameBoard[2][14].setState(BLACKSTONE);
		this.gameBoard[2][15].setState(WHITESTONE);
		this.gameBoard[1][15].setState(BLACKSTONE);
		this.gameBoard[3][14].setState(WHITESTONE);
		this.gameBoard[4][14].setState(BLACKSTONE);
		this.gameBoard[5][14].setState(WHITESTONE);
		this.gameBoard[11][1].setState(BLACKSTONE);
		this.gameBoard[12][1].setState(WHITESTONE);
		this.gameBoard[13][1].setState(BLACKSTONE);
		this.gameBoard[14][1].setState(WHITESTONE);
		this.gameBoard[15][1].setState(BLACKSTONE);
		this.gameBoard[11][2].setState(WHITESTONE);
		this.gameBoard[11][3].setState(BLACKSTONE);
		this.gameBoard[12][3].setState(WHITESTONE);
		this.gameBoard[13][3].setState(BLACKSTONE);
		this.gameBoard[13][2].setState(WHITESTONE);
		this.gameBoard[11][5].setState(BLACKSTONE);
		this.gameBoard[12][5].setState(WHITESTONE);
		this.gameBoard[13][5].setState(BLACKSTONE);
		this.gameBoard[14][5].setState(WHITESTONE);
		this.gameBoard[15][5].setState(BLACKSTONE);
		this.gameBoard[15][6].setState(WHITESTONE);
		this.gameBoard[15][7].setState(BLACKSTONE);
		this.gameBoard[13][6].setState(WHITESTONE);
		this.gameBoard[11][6].setState(BLACKSTONE);
		this.gameBoard[11][7].setState(WHITESTONE);
		this.gameBoard[15][9].setState(BLACKSTONE);
		this.gameBoard[14][9].setState(WHITESTONE);
		this.gameBoard[13][9].setState(BLACKSTONE);
		this.gameBoard[12][9].setState(WHITESTONE);
		this.gameBoard[11][9].setState(BLACKSTONE);
		this.gameBoard[12][10].setState(WHITESTONE);
		this.gameBoard[13][11].setState(BLACKSTONE);
		this.gameBoard[15][12].setState(WHITESTONE);
		this.gameBoard[14][12].setState(BLACKSTONE);
		this.gameBoard[13][12].setState(WHITESTONE);
		this.gameBoard[12][12].setState(BLACKSTONE);
		this.gameBoard[11][12].setState(WHITESTONE);
		this.gameBoard[11][14].setState(BLACKSTONE);
		this.gameBoard[11][15].setState(WHITESTONE);
		this.gameBoard[11][16].setState(BLACKSTONE);
		this.gameBoard[12][15].setState(WHITESTONE);
		this.gameBoard[13][15].setState(BLACKSTONE);
		this.gameBoard[14][15].setState(WHITESTONE);
		this.gameBoard[15][15].setState(BLACKSTONE);
		this.gameBoard[11][17].setState(WHITESTONE);
		this.gameBoard[12][17].setState(BLACKSTONE);
		this.gameBoard[13][17].setState(WHITESTONE);
		this.gameBoard[14][17].setState(BLACKSTONE);
		this.gameBoard[15][17].setState(WHITESTONE);
		this.gameBoard[15][18].setState(BLACKSTONE);
		this.gameBoard[13][18].setState(WHITESTONE);
		this.gameBoard[11][18].setState(BLACKSTONE);

		
	
	}
	
	
	public PenteBoardSquare[][] getBoard() {
		return gameBoard;
	}
	
	public boolean getDarkStoneMove2Taken() {
		return darkStoneMove2Taken;
	}
	
}
	

