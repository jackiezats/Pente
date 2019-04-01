import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PenteBoardSquare {
	
	private int xLoc, yLoc;
	private int sWidth, sHeight;
	
	private int sState; //Open, Player1, Player2
	
	private Color sColor; //square color
	private Color lColor; //line color
	private Color bColor; //boarder color
	private Color innerC;
	
	private Color darkStoneColor = new Color(21, 7, 51);
	private Color darkStoneTop = new Color(46, 31, 104);
	private Color darkStoneHighlight = new Color(166, 142, 255);
	
	
	private Color whiteStoneTop = new Color(250, 250, 250);
	private Color whiteStoneColor = new Color(243, 237, 255);
	
	private Color shadowGrey = new Color(169, 173, 142);

	boolean isInner = false;
	
	//constructor
	public PenteBoardSquare(int x, int y, int w, int h) {
		
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
		
		sColor = new Color(255, 252, 183);
		//lColor = new Color(204, 223, 255);
		bColor = new Color(55, 55, 61) ;
		innerC = new Color(255, 250, 155);
		sState = PenteGameBoard.EMPTY;
		lColor = new Color(255, 235, 193);
	
	}
	
	public void setInner() {
		System.out.println("Setting Inner for " + xLoc + ", " + yLoc);
		isInner = true;
	}
	
	public void drawMe(Graphics g) {
		
		//draws the board square
		if(isInner) {
			g.setColor(innerC);
		} else {
			g.setColor(sColor);
		}
		
		
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		//boarder color
		g.setColor(bColor);
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		//the shadow
		if(sState != PenteGameBoard.EMPTY) {
			g.setColor(shadowGrey);
			g.fillOval(xLoc, yLoc+6, sWidth - 8, sHeight - 8);
		}
		
		
		
		
		g.setColor(lColor);
		
		//horizontal line
		g.drawLine(xLoc, yLoc + sHeight/2, xLoc + sWidth, yLoc + sHeight/2);
		
		//vertical line
		g.drawLine(xLoc + sWidth/2, yLoc, xLoc + sWidth/2, yLoc + sHeight);
		
		
		//stones
		if(sState == PenteGameBoard.BLACKSTONE) {
			
			g.setColor(darkStoneColor);
			g.fillOval(xLoc + 4, yLoc + 4, sWidth - 7, sHeight - 7);
			
			//top
			g.setColor(darkStoneTop);
			g.fillOval(xLoc + 8, yLoc + 6, sWidth - 12, sHeight - 10);
			
			//REFLECTION
			//g.setColor(darkStoneHighlight);
			//g.fillOval(xLoc + (int)(sWidth * 0.55), yLoc + 10, (int)(sWidth * 0.15), (int)(sHeight * 0.25));
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			
			g2.setColor(darkStoneHighlight);
			g2.setStroke(new BasicStroke(1));
			
			g2.drawArc(xLoc + (int)(sWidth * 0.45),
					yLoc + 10, 
					(int)(sWidth * 0.30), 
					(int)(sHeight * 0.35), 
					0, 
					90);
			
		}
		
		if(sState == PenteGameBoard.WHITESTONE) {
			g.setColor(whiteStoneColor);
			g.fillOval(xLoc + 3, yLoc + 3, sWidth - 6, sHeight - 6);
			
			//top
			g.setColor(whiteStoneTop);
			g.fillOval(xLoc + 8,
					yLoc + 6,
					sWidth - 12,
					sHeight - 10);
			
		}
		
		
	
	
	
	}
	
	//Methods
	//Only 3 states
	
	/*
	EMPTY = 0;
	BLACKSTONE = 1;
	WHITESTONE = -1;
	*/
	
	public void setState(int newState) {
		if(newState < -1 || newState > 1) {
			System.out.println(newState + "is an illegal");
		} else {
			sState = newState;
		}
		
		
		
		
	}

	public void setXLoc(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setYLoc(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setWidth(int w) {
		// TODO Auto-generated method stub
		
	}

	public void setHeight(int h) {
		// TODO Auto-generated method stub
		
	}

}
