package cmpt317A2;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
	
	private Controller(){}
	
	public Board myBoard = new Board();
	
	private Scanner myScanner = new Scanner(System.in);
	
	@SuppressWarnings("static-access")
	private void playTurn(char player){
		boolean lock = false;
		int x;
		int y;
		char curPiece;
		if (player == '1') {
			myBoard.printGameBoard();
			System.out.println("King: " + myBoard.king.getPosition());
			System.out.println("Guard: " + myBoard.guardOne.getPosition());
			System.out.println("Guard: " + myBoard.guardTwo.getPosition());
			System.out.println("Guard: " + myBoard.guardThree.getPosition());
			System.out.println("Enter the x and y coordinates, separated by a space");
			x = myScanner.nextInt();
			y = myScanner.nextInt();
			myScanner.nextLine();
			while(true){
				curPiece = myBoard.getPiece(x, y);
				switch(curPiece){
				case 'K':
					System.out.println("You are moving the King");
					lock = true;
					break;
				case 'G':
					System.out.println("You are moving the Guard at position: " + "(" + x + "," + y + ")");
					lock = true;
					break;
				default:
					System.out.println("Invalid input, can't move: " + curPiece);
					x = myScanner.nextInt();
					y = myScanner.nextInt();
					myScanner.nextLine();
				}
				if(lock){
					break;
				}
			}
		}
		else
		{
			myBoard.printGameBoard();
			System.out.println("Dragon: " + myBoard.dragonOne.getPosition());
			System.out.println("Dragon: " + myBoard.dragonTwo.getPosition());
			System.out.println("Dragon: " + myBoard.dragonThree.getPosition());
			System.out.println("Dragon: " + myBoard.dragonFour.getPosition());
			System.out.println("Dragon: " + myBoard.dragonFive.getPosition());
			System.out.println("Enter the x and y coordinates, separated by a space");
			x = myScanner.nextInt();
			y = myScanner.nextInt();
			myScanner.nextLine();
			while(true){
				curPiece = myBoard.getPiece(x, y);
				switch(curPiece){
				case 'D':
					System.out.println("You are moving the Dragon at position: " + "(" + x + "," + y + ")");
					lock = true;
					break;
				default:
					System.out.println("Invalid input, can't move: " + curPiece);
					x = myScanner.nextInt();
					y = myScanner.nextInt();
					myScanner.nextLine();
				}
				if(lock){
					break;
				}
			}
		}
		//movement stuff
		ArrayList<Tuple> moveList = myBoard.availableMoves(x,y);
		System.out.println("Here are the legal moves: " + moveList);
		System.out.println("Enter the index of the move you would like to make");
		int index = myScanner.nextInt();
		myScanner.nextLine();
		Tuple nextMove = moveList.get(index);
		for( gamePiece piece : myBoard.pieceArray){
			if (piece.checkPosition(new Tuple(x,y))){
				piece.updateCurrentPosition( nextMove, curPiece);
			}
		}
		
	}
	
	public void game(){
		while (true){
			playTurn('2');
			if(myBoard.gameOver()){
				break;
			}
			playTurn('1');
			if(myBoard.gameOver()){
				break;
			}
		}
	}
	
	public static void main(String[] args){
		Controller testController = new Controller();
		
		testController.game();
	}
}
