import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComputerMoveGenerator {

	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	public static final int ONE_IN_ROW_DEF = 1;
	public static final int TWO_IN_ROW_DEF = 2;
	public static final int TWO_IN_ROW_OPEN = 3;
	public static final int TWO_IN_ROW_CAP = 4;
	public static final int THREE_IN_ROW_DEF = 7;
	public static final int THREE_IN_ROW_OPEN = 8;
	public static final int FOUR_IN_ROW_DEF = 9;
	public static final int FOUR_IN_ROW_OPEN = 10;
	public static final int THREE_ON_OFF_DEF = 6;
	public static final int THREE_ON_OFF_DEF2 = 7;
	public static final int BLOCK_CAP = 6;
	
	public static final int TWO_IN_ROW_OFF = 2;
	public static final int TWO_IN_ROW_OFF_OPEN = 3;
	public static final int THREE_IN_ROW_OFF = 4;
	public static final int THREE_IN_ROW_OPEN_OFF = 5;
	public static final int FOUR_IN_ROW_OFF = 6;
	public static final int FOUR_IN_ROW_OPEN_OFF = 7;
	// public static final int FIVE = 10;
	

	
	
	PenteGameBoard myGame;
	int myStone;
	
	ArrayList<CMObject> moves = new ArrayList<CMObject>();
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor) {
		
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player " + myStone);
		
	}
	
	public int[] getComputerMove() {
		int [] newMove = new int[2];
		newMove[0] = -1;
		newMove[1] = -1;
		
		moves.clear();
		
		//finding the moves
		findMove();
		sortPriorities();
		printPriorities();
		
		if(moves.size() > 0) {
			CMObject ourMove = moves.get(0);
			
			if(moves.get(0).getPriority() <= this.ONE_IN_ROW_DEF) {
				ourMove = moves.get((int)(Math.random()*moves.size()));
			} else {
				ourMove = moves.get(0);
			}
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
			
			if(myGame.darkSquareProblem(newMove[0], newMove[1]) == true) {
				System.out.print("Problem in getComputerMove");
			} else {
				
				if(myStone == PenteGameBoard.BLACKSTONE && myGame.getDarkStoneMove2Taken() == false) {
					System.out.println("In getComputerMove(), myStone is dark and dsp = true");
					int newBStoneProbRow = -1;
					int newBStoneProbCol = -1;
					int innerSafeSquareSideLen = PenteGameBoard.INNER_END - PenteGameBoard.INNER_START + 1;
						while(myGame.getDarkStoneMove2Taken() == false) {
							newBStoneProbRow = (int)(Math.random() * (innerSafeSquareSideLen + 2)) + 
									(innerSafeSquareSideLen + 1);
							
							newBStoneProbCol = (int)(Math.random() * (innerSafeSquareSideLen + 2)) +
									(innerSafeSquareSideLen + 1);
							
							myGame.darkSquareProblem(newBStoneProbRow, newBStoneProbCol);
						}
					newMove[0] = newBStoneProbRow;
					newMove[1] = newBStoneProbCol;
				}
			}
			
			
			
		} else {
			newMove = generateRandomMove();
		}
		
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return newMove;
	}
	
	public void findMove() {
		for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col++) {
				
				//defense
				findOneDef(row, col);
				findTwoDef(row, col);
				findThreeDef(row, col);
				findFourDef(row, col);
				findThreeOnOffDef(row, col);
				findThreeOneDef(row, col);
				blockCap(row, col);
				
				//offense
				findTwoOff(row, col);
				findThreeOff(row, col);
				findFourOff(row, col);
				//findFiveOff(row, col);
			}
		}
	}
	
	public boolean isOnBoard(int r, int c) {
		boolean isOn = false;
		if(r>=0 && r < PenteGameBoard.NUM_SQUARES_SIDE) {
			if(c>=0 && c < PenteGameBoard.NUM_SQUARES_SIDE) {
				isOn = true;
			}
		}
		
		return isOn;
	}
	
	public void findOneDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r + rL] [c + uD].getState() == PenteGameBoard.EMPTY) {
						CMObject newMove = new CMObject();
						newMove.setRow(r + rL);
						newMove.setCol(c + uD);
						newMove.setPriority(ONE_IN_ROW_DEF);
						newMove.setMoveType(DEFENSE);
						moves.add(newMove);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}

	}
		
	
	public void setDefMove(int r, int c, int p) {
		
		if(myStone == PenteGameBoard.BLACKSTONE && myGame.getDarkStoneMove2Taken() == false) {
			if(myGame.darkSquareProblem(r, c) == false) {
				CMObject newMove = new CMObject();
				newMove.setRow(r);
				newMove.setCol(c);
				newMove.setPriority(p);
				newMove.setMoveType(DEFENSE);
				moves.add(newMove);
			} else 
				System.out.println("Dark Square Problem");
		} else {
				CMObject newMove = new CMObject();
				newMove.setRow(r);
				newMove.setCol(c);
				newMove.setPriority(p);
				newMove.setMoveType(DEFENSE);
				moves.add(newMove);
			}
		}

	
	
	public void setOffMove(int r, int c, int p) {
		if(myStone == PenteGameBoard.BLACKSTONE && myGame.getDarkStoneMove2Taken() == false) {
			if(myGame.darkSquareProblem(r, c) == false) {
				CMObject newMove = new CMObject();
				newMove.setRow(r);
				newMove.setCol(c);
				newMove.setPriority(p);
				newMove.setMoveType(OFFENSE);
				moves.add(newMove);
			} else 
				System.out.println("Dark Square Problem");
		} else {
				CMObject newMove = new CMObject();
				newMove.setRow(r);
				newMove.setCol(c);
				newMove.setPriority(p);
				newMove.setMoveType(OFFENSE);
				moves.add(newMove);
			}
		}
	
	
	public void findTwoDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
				if(myGame.getBoard()[r][c].getState() == myStone * -1) {
					if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone * -1) {
						if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == PenteGameBoard.EMPTY) {
							//if r-rL is wall
							if(isOnBoard(r - rL, c - uD) == false) {
								setDefMove(r + (rL*2), c + (uD*2), TWO_IN_ROW_DEF);
							} else if(myGame.getBoard()[r - rL] [c - uD].getState() == PenteGameBoard.EMPTY) {
								setDefMove(r + (rL*2), c + (uD*2), TWO_IN_ROW_OPEN);
							} else if(myGame.getBoard()[r - rL] [c - uD].getState() == myStone) {
								setDefMove(r + (rL*2), c + (uD*2), TWO_IN_ROW_CAP);
							}
							
							
							CMObject newMove = new CMObject();
							newMove.setRow(r + (rL*2));
							newMove.setCol(c + (uD*2));
							newMove.setPriority(TWO_IN_ROW_DEF);
							newMove.setMoveType(DEFENSE);
							moves.add(newMove);
						}
					}
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
				}
			}
		}

	}
	
	public void findThreeDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone * -1) {
					if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone * -1) {
						if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone * -1) {
							if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == PenteGameBoard.EMPTY) {
								//if r-rL is wall
								if(isOnBoard(r - rL, c - uD) == false) {
									setDefMove(r + (rL*3), c + (uD*3), THREE_IN_ROW_DEF);
								} else {
									setDefMove(r + (rL*3), c + (uD*3), THREE_IN_ROW_OPEN);
								} 
							}
						}
					}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
	
	public void findFourDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone * -1) {
					if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone * -1) {
						if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone * -1) {
							if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == myStone * -1) {
								if(myGame.getBoard()[r + (rL*4)] [c + (uD*4)].getState() == PenteGameBoard.EMPTY) {
									//if r-rL is wall
									if(isOnBoard(r - rL, c - uD) == false) {
										setDefMove(r + (rL*4), c + (uD*4), FOUR_IN_ROW_DEF);
									} else {
										setDefMove(r + (rL*4), c + (uD*4), FOUR_IN_ROW_OPEN);
									} 
								}
							}
						}
					}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
	
	public void findThreeOnOffDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone * -1) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == PenteGameBoard.EMPTY) {
							if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone * -1) {
								if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == PenteGameBoard.EMPTY) {
									if(myGame.getBoard()[r + (rL*4)] [c + (uD*4)].getState() == myStone * -1) {
												
										    int whichWay = (int)(Math.random() * 100);
										    if(whichWay < 50) {
										    	setDefMove(r + (rL), c + (uD), THREE_ON_OFF_DEF);
										    } else {
										    	setDefMove(r + (rL*3), c + (uD*3), THREE_ON_OFF_DEF);
										    }
										
									}
								}
							}
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
	
	public void findThreeOneDef(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone ) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone * -1) {
							if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == PenteGameBoard.EMPTY) {
								if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == myStone * -1) {
									if(myGame.getBoard()[r + (rL*4)] [c + (uD*4)].getState() == myStone * -1) {
										if(myGame.getBoard()[r + (rL*5)] [c + (uD*5)].getState() == myStone * -1) {
											if(isOnBoard(r - rL, c - uD) == false) {
												setDefMove(r + (rL*2), c + (uD*2), THREE_ON_OFF_DEF);
											}
										}
									}
								}
							}
						}
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
		
	public void findOneOff(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == PenteGameBoard.EMPTY) {
							CMObject newMove = new CMObject();
							newMove.setRow(r + rL);
							newMove.setCol(c + uD);
							newMove.setPriority(ONE_IN_ROW_DEF);
							newMove.setMoveType(OFFENSE);
							moves.add(newMove);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
				}
			}
		}

	}
	
	public void findTwoOff(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone) {
							if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == PenteGameBoard.EMPTY) {
								//if r-rL is wall
								if(isOnBoard(r - rL, c - uD) == false) {
									setDefMove(r + (rL*2), c + (uD*2), this.TWO_IN_ROW_OFF);
								} else if(myGame.getBoard()[r - rL] [c - uD].getState() == PenteGameBoard.EMPTY) {
									setDefMove(r + (rL*2), c + (uD*2), this.TWO_IN_ROW_OFF_OPEN);
								} 
							
							
//							CMObject newMove = new CMObject();
//							newMove.setRow(r + (rL*2));
//							newMove.setCol(c + (uD*2));
//							newMove.setPriority(ONE_IN_ROW_DEF);
//							newMove.setMoveType(DEFENSE);
//							dMoves.add(newMove);
						}
					}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
				}
			}
		}

	}
	
	public void findThreeOff(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone) {
							if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone) {
								if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == PenteGameBoard.EMPTY) {
									//if r-rL is wall
									if(isOnBoard(r - rL, c - uD) == false) {
										setOffMove(r + (rL*3), c + (uD*3), this.THREE_IN_ROW_OFF);
									} else {
										setOffMove(r + (rL*3), c + (uD*3), this.THREE_IN_ROW_OPEN_OFF);
									} 
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
					}
				}
			}
		}
	
	public void findFourOff(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone) {
					if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone) {
						if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone) {
							if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == myStone) {
								if(myGame.getBoard()[r + (rL*4)] [c + (uD*4)].getState() == PenteGameBoard.EMPTY) {
									//if r-rL is wall
									if(isOnBoard(r - rL, c - uD) == false) {
										setOffMove(r + (rL*4), c + (uD*4), this.FOUR_IN_ROW_OFF);
									} else {
										setOffMove(r + (rL*4), c + (uD*4), this.FOUR_IN_ROW_OPEN_OFF);
									} 
								}
							}
						}
					}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
	
//	public void findFiveOff(int r, int c) {
//		for(int rL = -1; rL <= 1; rL++) {
//			for(int uD = -1; uD <= 1; uD++) {
//				try {
//					if(myGame.getBoard()[r][c].getState() == myStone) {
//					if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone) {
//						if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone) {
//							if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == myStone) {
//								if(myGame.getBoard()[r + (rL*4)] [c + (uD*4)].getState() == myStone) {
//									if(myGame.getBoard()[r + (rL*5)] [c + (uD*5)].getState() == PenteGameBoard.EMPTY) {
//										//if r-rL is wall
//										if(isOnBoard(r - rL, c - uD) == false) {
//											setOffMove(r + (rL*5), c + (uD*4), FIVE);
//										} else {
//											setOffMove(r + (rL*5), c + (uD*4), FIVE);
//										} 
//									}
//								}
//							}
//						}
//					}
//					}
//				} catch (ArrayIndexOutOfBoundsException e) {
//					System.out.println("Checking off the board...");
//					}
//				}
//			}
//		}
	
	public void blockCap(int r, int c) {
		for(int rL = -1; rL <= 1; rL++) {
			for(int uD = -1; uD <= 1; uD++) {
				try {
					if(myGame.getBoard()[r][c].getState() == myStone * -1) {
						if(myGame.getBoard()[r + rL] [c + uD].getState() == myStone) {
							if(myGame.getBoard()[r + (rL*2)] [c + (uD*2)].getState() == myStone) {
								if(myGame.getBoard()[r + (rL*3)] [c + (uD*3)].getState() == PenteGameBoard.EMPTY) {
											//if r-rL is wall
									
										setDefMove(r + (rL*3), c + (uD*3), BLOCK_CAP);
									 
								}
							}
						}
					}
						
			
		
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Checking off the board...");
					}
				}
			}
		}
	
	public void printPriorities() {
		for(CMObject m: moves) {
			System.out.println(m);
		}
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
	
	public void sortPriorities() {
		Collections.sort(moves);
	}
	
	public void sleepForAMove() throws InterruptedException {
		Thread currThread = Thread.currentThread();
		currThread.sleep(PenteGameBoard.SLEEP_TIME);
	}
	
}
