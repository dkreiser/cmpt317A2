package cmpt317A2;

import java.util.ArrayList;

public class Board {
	final static int boardSize = 5;
	
	public static char[][] Gameboard = {
			{'_','_','K','_','_'},
			{'_','G','G','G','_'},
			{'_','_','_','_','_'},
			{'D','D','D','D','D'},
			{'_','_','_','_','_'}};

	public Board(){}

	static King king = new King();
	
	static Guard guardOne   = new Guard(1,1);
	static Guard guardTwo   = new Guard(1,2);
	static Guard guardThree = new Guard(1,3);
	
	static Dragon dragonOne   = new Dragon(3,0);
	static Dragon dragonTwo   = new Dragon(3,1);
	static Dragon dragonThree = new Dragon(3,2);
	static Dragon dragonFour  = new Dragon(3,3);
	static Dragon dragonFive  = new Dragon(3,4);
	
	static gamePiece[] pieceArray = {king,
			guardOne, guardTwo, guardThree,
			dragonOne, dragonTwo, dragonThree, dragonFour, dragonFive};

	public void printGameBoard(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		for (int i = 0; i < 5; i++){
			for( int k = 0; k < 5; k++){
				System.out.print(Gameboard[i][k] + " ");
			}
			System.out.println();
		}
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}
	
	public char getPiece(int x, int y){
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)){
			return ' ';
		}
		else
		{
			return Gameboard[x][y];
		}
		
	}
	
	public ArrayList<Tuple> availableMoves(int x, int y){
		char piece = Gameboard[x][y];
		ArrayList<Tuple> returnList = new ArrayList<Tuple>();
		
		if (piece == '_'){
			return returnList;
		}

		if(isLegalMove(x, y+1)){
			returnList.add(new Tuple(x, y+1));
		}
		if(isLegalMove(x, y-1)){
			returnList.add(new Tuple(x, y-1));
		}
		if(isLegalMove(x+1, y)){
			returnList.add(new Tuple(x+1,y));
		}
		if(isLegalMove(x-1, y)){
			returnList.add(new Tuple(x-1,y));
		}
		
		switch(piece){
		case('G'):
			break;
		case('K'):
			if(isGuard(x+1, y)){
				if(isLegalMove(x+2,y)){
					returnList.add(new Tuple(x+2, y));
				}
			}
			if(isGuard(x-1, y)){
				if(isLegalMove(x-2,y)){
					returnList.add(new Tuple(x-2, y));
				}
			}
			if(isGuard(x, y+1)){
				if(isLegalMove(x,y+2)){
					returnList.add(new Tuple(x, y+2));
				}
			}
			if(isGuard(x, y-1)){
				if(isLegalMove(x,y-2)){
					returnList.add(new Tuple(x, y-2));
				}
			}
			break;
		case('D'):
			if(isLegalMove(x+1, y+1)){
				returnList.add(new Tuple(x+1, y+1));
			}
			if(isLegalMove(x-1, y-1)){
				returnList.add(new Tuple(x-1, y-1));
			}
			if(isLegalMove(x+1, y-1)){
				returnList.add(new Tuple(x+1,y-1));
			}
			if(isLegalMove(x-1, y+1)){
				returnList.add(new Tuple(x-1,y+1));
			}
			break;
		default:
			System.out.println("No Piece there");
			break;
		}
		return returnList;
	}
	
	private boolean isGuard(int x, int y){
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) return false;
		if(Gameboard[x][y] == 'G') return true;
		return false;
	}
	
	private boolean isLegalMove(int x, int y){
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) return false;
		if(Gameboard[x][y] == '_') return true;
		return false;
	}
	
	public boolean gameOver(){
		if( draw() || dragonsWin() || kingWins() ){
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean draw(){
		return false;
	}
	
	private boolean dragonsWin(){
		if (king.isAlive()){
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean kingWins(){
		if(king.getPosition().getY() == 4){
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String[] args){
		Board newBoard = new Board();
		newBoard.printGameBoard();
		System.out.println(newBoard.availableMoves(3,2));
	}
}
