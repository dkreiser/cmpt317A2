package board;

public class State {

	private static char[][] gameBoard;
	
	public State(char[][] newBoard){
		gameBoard = newBoard;
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
}
