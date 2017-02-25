package board;

import cmpt317A2.Tuple;

public class State {

	private char[][] potentialBoard = new char[5][5];
	private boolean dragonsJustMoved;
	private boolean potentialBoardWins;
	public Tuple oldPosition = null;
	public Tuple newPosition = null;

	// Initial static board constructor, used once in Board.java
	public State(char[][] newBoard){
		potentialBoard = newBoard;
		dragonsJustMoved = false;
		potentialBoardWins = false;
	}
	
	// Constructor, used for cloning
	public State(char[][] newBoard, boolean djm, boolean pbw, Tuple oldPos, Tuple newPos){
		potentialBoard = newBoard;
		dragonsJustMoved = djm;
		potentialBoardWins = pbw;
		if(oldPos != null){
			oldPosition = oldPos.clone();
		}
		if(newPos != null){
			newPosition = newPos.clone();
		}
	}
	
	// Build a new state from a previous state
	public State(State s) {
		for (int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				setChar(x, y, s.getChar(x, y));
			}
		}
		dragonsJustMoved = !s.dragonsJustMoved();
		potentialBoardWins = false;
	}
	
	// Constructor used in successor function
	public State(State s, Tuple oldPosition, Tuple newPosition, char letter){
		for (int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				setChar(x, y, s.getChar(x, y));
			}
		}
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
		
		// Need to account for dragons being eaten here
		potentialBoard[oldPosition.getX()][oldPosition.getY()] = '_';
		potentialBoard[newPosition.getX()][newPosition.getY()] = letter;
		dragonsJustMoved = !s.dragonsJustMoved();
		potentialBoardWins = false;
	}
	
	public char[][] getBoard(){
		return potentialBoard.clone();
	}
	
	public char getChar(int x, int y){
		return potentialBoard[x][y];
	}
	
	public char getChar(Tuple x){
		return potentialBoard[x.getX()][x.getY()];
	}
	
	public void setChar(int x, int y, char newChar){
		potentialBoard[x][y] = newChar;
	}
	
	public void setChar(Tuple x, char newChar){
		potentialBoard[x.getX()][x.getY()] = newChar;
	}
	
	public boolean dragonsJustMoved() {
		return dragonsJustMoved;
	}

	public boolean potentialBoardWins() {
		return potentialBoardWins;
	}

	public void stateIsWinner() {
		this.potentialBoardWins = true;
	}
	
	public State clone(){
		char[][] newBoard = this.getBoard();
		return new State(newBoard, dragonsJustMoved, potentialBoardWins, oldPosition, newPosition);
	}
	
	public String toString(){
		String returnString = "";
		returnString += "  0 1 2 3 4\n";
		for (int i = 0; i < 5; i++) {
			returnString += (i + " ");
			for (int k = 0; k < 5; k++) {
				returnString += (potentialBoard[i][k] + " ");
			}
			returnString += "\n";
		}
		
		returnString += "Dragons Just Moved: " + dragonsJustMoved;
		returnString += "\nPotential Board wins: " + 	potentialBoardWins;
		returnString += "\nOld Position is: " + oldPosition;
		returnString += "\nNew Position is: " + newPosition;
		returnString += "\n~~~~~~~~~~\n";
		
		return returnString;
	}
}
