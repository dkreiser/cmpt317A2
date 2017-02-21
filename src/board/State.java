package board;

import cmpt317A2.Tuple;

public class State {

	private char[][] gameBoard;
	private boolean dragonsMoved;
	private boolean wasWin;
	



	public State(char[][] newBoard){
		gameBoard = newBoard;
		dragonsMoved = false;
		wasWin = false;
	}
	
	
	// Constructor used in successor function
	public State(State s, Tuple oldPosition, Tuple newPosition, char letter){
		gameBoard = s.getBoard();
		gameBoard[oldPosition.getX()][oldPosition.getY()] = '_';
		gameBoard[newPosition.getX()][newPosition.getY()] = letter;
		dragonsMoved = !s.dragonsMoved;
		wasWin = false;
	}
	
	public char[][] getBoard(){
		return gameBoard;
	}
	
	public char getChar(int x, int y){
		return gameBoard[x][y];
	}
	
	public void setChar(int x, int y, char newChar){
		gameBoard[x][y] = newChar;
	}
	
	public boolean dragonsJustMoved() {
		return dragonsMoved;
	}


	public boolean isCurrentStateAWin() {
		return wasWin;
	}


	public void stateIsWin() {
		this.wasWin = true;
	}
}
