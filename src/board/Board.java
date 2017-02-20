package board;

import java.util.ArrayList;

import cmpt317A2.Tuple;
import gamepiece.Dragon;
import gamepiece.Guard;
import gamepiece.King;
import gamepiece.gamePiece;

public class Board {

	// Variables
	/** hard coded game board */
	static char[][] startingArray = {{ '_', '_', 'K', '_', '_' },
			   				  	 	 { '_', 'G', 'G', 'G', '_' },
			   				  		 { '_', '_', '_', '_', '_' },
			   				  		 { 'D', 'D', 'D', 'D', 'D' },
			   				  		 { '_', '_', '_', '_', '_' }};
	public static State gameBoard = new State(startingArray);

	// player one's objects
	King king = new King();
	Guard guardOne = new Guard(1, 1);
	Guard guardTwo = new Guard(1, 2);
	Guard guardThree = new Guard(1, 3);
	ArrayList<gamePiece> teamOne = new ArrayList<gamePiece>();

	// player two's objects
	Dragon dragonOne = new Dragon(3, 0);
	Dragon dragonTwo = new Dragon(3, 1);
	Dragon dragonThree = new Dragon(3, 2);
	Dragon dragonFour = new Dragon(3, 3);
	Dragon dragonFive = new Dragon(3, 4);
	ArrayList<gamePiece> teamTwo = new ArrayList<gamePiece>();

	// constructor, populates each team's initial list
	public Board() {
		teamOne.add(king);
		teamOne.add(guardOne);
		teamOne.add(guardTwo);
		teamOne.add(guardThree);

		teamTwo.add(dragonOne);
		teamTwo.add(dragonTwo);
		teamTwo.add(dragonThree);
		teamTwo.add(dragonFour);
		teamTwo.add(dragonFive);
	}

	// Getters and setters (only the ones that are relevant
	/**
	 * @return teamOne: The units associated with team one
	 */
	public ArrayList<gamePiece> getTeamOne() {
		return teamOne;
	}

	/**
	 * @return teamTwo: The units associated with team two
	 */
	public ArrayList<gamePiece> getTeamTwo() {
		return teamTwo;
	}

	// Handy Print method
	/**
	 * print the game board in its current state adds some nice formatting.
	 */
	public void printGameBoard() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("  0 1 2 3 4");
		for (int i = 0; i < 5; i++) {
			System.out.print(i + " ");
			for (int k = 0; k < 5; k++) {
				System.out.print(gameBoard.getChar(i, k) + " ");
			}
			System.out.println();
		}
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	/**
	 * return the piece at a given tile, if it exists
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return a space if the coordinates were invalid, otherwise the character
	 *         on the tile
	 */
	// check if the coordinates given are valid
	// if they are valid, return the gamepiece at that location
	// if they aren't valid, return a space
	public char getPiece(int x, int y) {
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) {
			return ' ';
		} else {
			return gameBoard.getChar(x,y);
		}
	}

	/**
	 * return a list of moves that a unit at a given tile is able to make
	 * 
	 * @param x
	 *            the x coordinate of the tile we are evaluating
	 * @param y
	 *            the y coordinate of the tile we are evaluating
	 * @return an arrayList of tuples that contain all of the (x,y) pairs of the
	 *         valid moves we can make.
	 */

	// Given an x and y coordinate, return all of the moves that can be made
	// by the unit on that tile.
	public ArrayList<Tuple> availableMoves(int x, int y) {
		char piece = gameBoard.getChar(x,y);
		ArrayList<Tuple> returnList = new ArrayList<Tuple>();

		if (piece == '_') {
			return returnList;
		}

		if (isLegalMove(x, y + 1)) {
			returnList.add(new Tuple(x, y + 1));
		}
		if (isLegalMove(x, y - 1)) {
			returnList.add(new Tuple(x, y - 1));
		}
		if (isLegalMove(x + 1, y)) {
			returnList.add(new Tuple(x + 1, y));
		}
		if (isLegalMove(x - 1, y)) {
			returnList.add(new Tuple(x - 1, y));
		}

		switch (piece) {
		case ('K'): // the king has to additionally check if it can jump a guard
			if (isGuard(x + 1, y)) {
				if (isLegalMove(x + 2, y)) {
					returnList.add(new Tuple(x + 2, y));
				}
			}
			if (isGuard(x - 1, y)) {
				if (isLegalMove(x - 2, y)) {
					returnList.add(new Tuple(x - 2, y));
				}
			}
			if (isGuard(x, y + 1)) {
				if (isLegalMove(x, y + 2)) {
					returnList.add(new Tuple(x, y + 2));
				}
			}
			if (isGuard(x, y - 1)) {
				if (isLegalMove(x, y - 2)) {
					returnList.add(new Tuple(x, y - 2));
				}
			}
			break;
		case ('D'): // the dragons have to additionally check the diagonal
					// movements
			if (isLegalMove(x + 1, y + 1)) {
				returnList.add(new Tuple(x + 1, y + 1));
			}
			if (isLegalMove(x - 1, y - 1)) {
				returnList.add(new Tuple(x - 1, y - 1));
			}
			if (isLegalMove(x + 1, y - 1)) {
				returnList.add(new Tuple(x + 1, y - 1));
			}
			if (isLegalMove(x - 1, y + 1)) {
				returnList.add(new Tuple(x - 1, y + 1));
			}
			break;
		default:
			break;
		}
		
		// Finally, a guard or king must check if it is eligible to capture a dragon
		if (piece == 'K' || piece == 'G') {
			// Check if horizontally or vertically adjacent to a dragon. If so, add option to capture dragon to move list
			// Note that none of the capture logic is in this function. As far as this function is concerned, the
			// king or guard is simply 'moving' to a space where there is already a dragon. Logic to kill the dragon
			// is implemented elsewhere
			if (isDragon(x + 1,y)) {
				if (canCaptureDragon(x + 1,y)) {
					returnList.add(new Tuple(x + 1,y));
				}
			}
			if (isDragon(x,y + 1)) {
				if (canCaptureDragon(x,y + 1)) {
					returnList.add(new Tuple(x,y + 1));
				}
			}
			if (isDragon(x - 1,y)) {
				if (canCaptureDragon(x - 1,y)) {
					returnList.add(new Tuple(x - 1,y));
				}
			}
			if (isDragon(x,y - 1)) {
				if (canCaptureDragon(x,y-1)) {
					returnList.add(new Tuple(x,y - 1));
				}
			}
		}
		
		return returnList;
	}
	
	/**
	 * check if the dragon at the provided x,y position can be captured
	 * 
	 * @return true if surrounded by at least 2 guards (king counted as a "guard"), false otherwise.
	 */

	private boolean canCaptureDragon(int x, int y) {
		int numGuards = 0;

		if (isKingOrGuard(x - 1, y)) {
			numGuards++;
		}

		if (isKingOrGuard(x, y - 1)) {
			numGuards++;
		}

		if (isKingOrGuard(x + 1, y)) {
			numGuards++;
		}

		if (isKingOrGuard(x, y + 1)) {
			numGuards++;
		}

		// Uncomment to allow diagonal captures
		/*
		 * if (isKingOrGuard(x-1,y-1)) { numGuards++; }
		 * 
		 * if (isKingOrGuard(x+1,y+1)) { numGuards++; }
		 * 
		 * if (isKingOrGuard(x-1,y+1)) { numGuards++; }
		 * 
		 * if (isKingOrGuard(x+1,y-1)) { numGuards++; }
		 */

		// Make the final decision if the dragon is able to be captured
		if (numGuards >= 2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * checks a given tile to see if it is a king or guard
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'K' or 'G', false otherwise.
	 */
	private boolean isKingOrGuard(int x, int y) {
		return checkPiece(x, y, 'K') || checkPiece(x, y, 'G');
	}

	/**
	 * checks a given tile to see if it is a dragon
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'D', false otherwise.
	 */
	private boolean isDragon(int x, int y) {
		return checkPiece(x, y, 'D');
	}

	/**
	 * checks a given tile to see if it is a guard
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'G', false otherwise.
	 */
	private boolean isGuard(int x, int y) {
		return checkPiece(x, y, 'G');
	}

	/**
	 * check a given tile to see if it is empty
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a '_', false otherwise
	 */
	private boolean isLegalMove(int x, int y) {
		return checkPiece(x, y, '_');
	}

	/**
	 * check a given tile to see if it has the required character on it
	 * 
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @param letter
	 *            the letter to check for
	 * @return true if the tile contains "letter", false otherwise
	 */
	private boolean checkPiece(int x, int y, char letter) {
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4))
			return false;
		if (gameBoard.getChar(x,y) == letter) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check the dragons' win condition
	 * 
	 * @return true if the king is surrounded by at least 3 dragons in
	 *         horizontal/vertical directions, false otherwise.
	 */
	public boolean dragonsWin() {
		return surroundedByDragons(king);
	}

	/**
	 * check the king's win condition
	 * 
	 * @return true if the king is at the dragon's "home row", false otherwise.
	 */
	public boolean kingWins() {
		if (king.getPosition().getX() == 4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if any guards have been captured
	 */
	public void checkGuardCapture() {
		for (gamePiece currentPiece : teamOne) {
			// Only consider alive guards
			if ((currentPiece.getMyLetter() != 'G') || (currentPiece.isAlive() == false)) {
				continue;
			}

			int x = currentPiece.getPosition().getX();
			int y = currentPiece.getPosition().getY();

			// If guard is surrounded by at least 3 dragons, the guard dies and
			// is replaced with a dragon
			if (surroundedByDragons(currentPiece)) {
				Dragon newDragon = new Dragon(x, y);
				teamTwo.add(newDragon);
				currentPiece.kill();
				// For now, deciding not to remove dead pieces from teamOne's
				// list. Uncomment line below if this is an issue
				// teamOne.remove(currentPiece);

				// The dragon must be replacing a guard, otherwise there is a
				// serious problem
				gameBoard.setChar(x,y, 'D');
			}
		}
	}
	
	/**
	 * Kills the dragon at a given x and y position provided a dragon exists at that x,y
	 * 
	 * Returns true on success, false otherwise
	 */
	public boolean killDragon(int x, int y) {
		// Note, we might have to make this better. Finds the object which represents the piece we are killing
		for (gamePiece pieceToCheck : teamTwo) {
			if (pieceToCheck.checkPosition(new Tuple(x, y ))) {
				pieceToCheck.kill();
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the current piece is surrounded by at least 3 dragons. Per the
	 * game rules, a piece can only be "surrounded" horizontally or vertically
	 * NOT diagonally.
	 * 
	 * @return true if surrounded by at least 3 dragons, false otherwise.
	 */

	private boolean surroundedByDragons(gamePiece currentPiece) {
		int numDragons = 0;
		int x = currentPiece.getPosition().getX();
		int y = currentPiece.getPosition().getY();

		if (isDragon(x - 1, y)) {
			numDragons++;
		}

		if (isDragon(x, y - 1)) {
			numDragons++;
		}

		if (isDragon(x + 1, y)) {
			numDragons++;
		}

		if (isDragon(x, y + 1)) {
			numDragons++;
		}

		// Uncomment to allow diagonal surroundings

		/*
		 * if (isDragon(x-1,y-1)) { numDragons++; }
		 * 
		 * if (isDragon(x+1,y+1)) { numDragons++; }
		 * 
		 * if (isDragon(x-1,y+1)) { numDragons++; }
		 * 
		 * if (isDragon(x+1,y-1)) { numDragons++; }
		 */

		// Make the final decision if the piece was captured by a dragon
		if (numDragons >= 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method used for testing purposes hopefully.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// we should probably write some testing code here.
		Board testBoard = new Board();
		testBoard.printGameBoard();
	}
}
