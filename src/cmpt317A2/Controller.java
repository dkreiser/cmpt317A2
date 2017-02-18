package cmpt317A2;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
	
	/**we never want to make a duplicate copy of this class.*/
	private Controller(){}
	
	/**an instance of the game board*/
	public Board myBoard = new Board();
	
	/**a scanner created to handle user input*/
	private Scanner myScanner = new Scanner(System.in);
	
	/**
	 * instructions to make player ones turn.
	 * @return true if the game is in a draw state, false otherwise.
	 */
	private boolean playerOneTurn(){
		//variables used
		gamePiece[] myUnits = myBoard.getTeamOne();
		int xCoordinate;
		int yCoordinate;
		char pieceToMove;
		ArrayList<Tuple> moveList;

		//Step one: check if the game is a draw or can be continued.
		isDraw(myUnits);
		
		//Step two: print the board
		myBoard.printGameBoard();
		
		//Step three: list the units that the player has control over
		printUnitList(myUnits);
		
		//Step four: ask the player for which piece they would like to move.
		System.out.println("Enter the x and y coordinates of the unit you would like to move, separated by a space");
		xCoordinate = myScanner.nextInt();
		yCoordinate = myScanner.nextInt();
		myScanner.nextLine(); //this line is to force the scanner to ignore any other input
		
		//we will keep asking the player for a valid unit until we get one.
		while(true){ 
			pieceToMove = myBoard.getPiece(xCoordinate, yCoordinate);
			if (pieceToMove == 'K'){
				System.out.println("You are moving the King at position: " + "(" + xCoordinate + "," + yCoordinate + ")");
				//They've selected a valid unit: verify it can move before preceding.
				moveList = myBoard.availableMoves(xCoordinate,yCoordinate);
				if(moveList.size() != 0){
					break;
				} else {
					System.out.println("but the King has no available moves, please select another unit!");
				}
			}
			else if(pieceToMove == 'G'){
				System.out.println("You are moving the Guard at position: " + "(" + xCoordinate + "," + yCoordinate + ")");
				//They've selected a valid unit: verify it can move before preceding.
				moveList = myBoard.availableMoves(xCoordinate,yCoordinate);
				if(moveList.size() != 0){
					break;
				} else {
					System.out.println("but that Guard has no available moves, please select another unit!");
				}
			} else {
				System.out.println("Invalid input, can't move: " + pieceToMove);
				System.out.println("Please enter another coordinate");
			}
			xCoordinate = myScanner.nextInt();
			yCoordinate = myScanner.nextInt();
			myScanner.nextLine();
		}
		
		//Step five: ask the player where they would like to move that unit and then move it.
		moveUnit(myUnits, moveList, xCoordinate, yCoordinate, pieceToMove);
		
		return false;
	}
	
	/**
	 * instructions to make player twos turn.
	 * @return true if the game is in a draw state, false otherwise.
	 */
	private boolean playerTwoTurn(){
		//variables used
		gamePiece[] myUnits = myBoard.getTeamTwo();
		int xCoordinate;
		int yCoordinate;
		char pieceToMove;
		ArrayList<Tuple> moveList;
		
		//Step one: Check if the game is a draw or can be continued.
		isDraw(myUnits);
		
		//Step two: print the board.
		myBoard.printGameBoard();
		
		//Step three: list the units that the player has control over
		printUnitList(myUnits);
		
		//Step four: ask the player for which piece they would like to move.
		System.out.println("Enter the x and y coordinates of the unit you would like to move, separated by a space");
		xCoordinate = myScanner.nextInt();
		yCoordinate = myScanner.nextInt();
		myScanner.nextLine(); //this line is to force the scanner to ignore any other input
		
		//we will keep asking the player for a valid unit until we get one.
		while(true){ 
			pieceToMove = myBoard.getPiece(xCoordinate, yCoordinate);
			if (pieceToMove == 'D'){
				System.out.println("You are moving the Dragon at position: " + "(" + xCoordinate + "," + yCoordinate + ")");
				//They've selected a valid unit: verify it can move before preceding.
				moveList = myBoard.availableMoves(xCoordinate,yCoordinate);
				if(moveList.size() != 0){
					break;
				}
				else
				{
					System.out.println("but that dragon has no available moves, please select another one!");
				}
			}
			else
			{
				System.out.println("Invalid input, can't move: " + pieceToMove);
				System.out.println("Please enter another coordinate");
			}
			xCoordinate = myScanner.nextInt();
			yCoordinate = myScanner.nextInt();
			myScanner.nextLine();
		}
		
		//Step five: ask the player where they would like to move that unit and then move it.
		moveUnit(myUnits, moveList, xCoordinate, yCoordinate, pieceToMove);
		
		return false;
	}
	
	//Helper functions to make the player turns.
	private void moveUnit(gamePiece[] listOfUnits, ArrayList<Tuple> moveList, int xCoordinate, int yCoordinate, char piece){
		System.out.println("Here are the legal moves: " + moveList);
		System.out.println("Enter the index of the move you would like to make (starting from 0!)");
		int index = myScanner.nextInt();
		myScanner.nextLine();
		
		//Note, we might have to make this better.
		Tuple nextMove = moveList.get(index);
		for( gamePiece pieceToCheck : myBoard.getAllUnits()){
			if (pieceToCheck.checkPosition(new Tuple(xCoordinate,yCoordinate))){
				pieceToCheck.changePosition( nextMove );
			}
		}
	}
	
	private boolean isDraw(gamePiece[] listToCheck){
		for (gamePiece currentPiece : listToCheck){
			Tuple currentTuple = currentPiece.getPosition();
			if (myBoard.availableMoves(currentTuple.getX(), currentTuple.getY()).size() != 0){
				return false;
			}
		}
		return true;
	}
	
	private void printUnitList(gamePiece[] listToPrint){
		for (gamePiece currentPiece : listToPrint){
			System.out.println(currentPiece);
		}
	}
	
	/**
	 * a method to play the game until it results in a win or a draw.
	 */
	public void game(){
		boolean draw = false;
		boolean dragonsWin = false;
		while (true){
			if (playerTwoTurn()){ 
				draw = true;
				break;
			}
			if(myBoard.dragonsWin()){
				dragonsWin = true;
				break;
			}
			if(myBoard.kingWins()){
				break;
			}
			if (playerOneTurn()){
				draw = true;
				break;
			}
			if(myBoard.dragonsWin()){
				dragonsWin = true;
				break;
			}
			if(myBoard.kingWins()){
				break;
			}
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		myBoard.printGameBoard();
		if(draw){
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("~Nobody wins, it's a draw!~");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} else if(dragonsWin){
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("~The Dragons Win! Congratulations!~");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");			
		} else {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("~The King Wins! Congratulations!~");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");			
		}
	}
	
	/**
	 * where the game is played from for now.
	 * @param args not used.
	 */
	public static void main(String[] args){
		Controller testController = new Controller();
		
		testController.game();
	}
}
