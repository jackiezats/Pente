
public class CMObject {
	
	//data
	private int priority = 0;
	private int  row = -1, col = -1;
	private int moveType = 0;
	
	//no constructor
	
	//set
	public void setPriority(int newP) {
		priority = newP;
	}
	public void setRow(int newR) {
		row = newR;
	}
	public void setCol(int newC) {
		col = newC;
	}
	public void setMoveType(int newT) {
		moveType = newT;
	}
	
	//get
	public int getPriority() {
		return priority;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public int getMoveType() {
		return moveType;
	}
	
	
}
