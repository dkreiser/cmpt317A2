package board;

import java.util.ArrayList;
import java.util.LinkedList;

import cmpt317A2.Tuple;
import gamepiece.Dragon;
import gamepiece.Guard;
import gamepiece.King;
import gamepiece.gamePiece;

public class Board {

	// Variables
	/** hard coded game board */
	private static char[][] startingArray = { { '_', '_', 'K', '_', '_' }, { '_', 'G', 'G', 'G', '_' },
			{ '_', '_', '_', '_', '_' }, { 'D', 'D', 'D', 'D', 'D' }, { '_', '_', '_', '_', '_' } };
	public static State actualGameState = new State(startingArray);

	// player one's objects
	private King king = new King();
	private Guard guardOne = new Guard(1, 1);
	private Guard guardTwo = new Guard(1, 2);
	private Guard guardThree = new Guard(1, 3);
	private ArrayList<gamePiece> teamOne = new ArrayList<gamePiece>();

	// player two's objects
	private Dragon dragonOne = new Dragon(3, 0);
	private Dragon dragonTwo = new Dragon(3, 1);
	private Dragon dragonThree = new Dragon(3, 2);
	private Dragon dragonFour = new Dragon(3, 3);
	private Dragon dragonFive = new Dragon(3, 4);
	private ArrayList<gamePiece> teamTwo = new ArrayList<gamePiece>();

	// constructor, populates each team's initial list. This should only be
	// called by Controller once
	protected Board() {
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

	/**
	 * @return teamOne: The units associated with team one (King's team)
	 */
	protected ArrayList<gamePiece> getTeamOne() {
		return teamOne;
	}

	/**
	 * @return returns the king's gamePiece
	 */
	protected gamePiece getKing() {
		return king;
	}

	/**
	 * @return teamTwo: The units associated with team two (Dragons' team)
	 */
	protected ArrayList<gamePiece> getTeamTwo() {
		return teamTwo;
	}

	/**
	 * Prints the game board in its current state with some nice formatting.
	 */
	protected void printGameBoard() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("  0 1 2 3 4");
		for (int i = 0; i < 5; i++) {
			System.out.print(i + " ");
			for (int k = 0; k < 5; k++) {
				System.out.print(actualGameState.getChar(i, k) + " ");
			}
			System.out.println();
		}
		System.out.println("Dragons just moved? " + actualGameState.dragonsJustMoved());
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
	protected char getPiece(int x, int y) {
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) {
			return ' ';
		} else {
			return actualGameState.getChar(x, y);
		}
	}

	/**
	 * return a list of moves that a unit at a given tile is able to make
	 * 
	 * @param x
	 *            the x coordinate of the tile we are evaluating
	 * @param y
	 *            the y coordinate of the tile we are evaluating
	 * @param s
	 *            the state we are evaluating
	 * @return an arrayList of tuples that contain all of the (x,y) pairs of the
	 *         valid moves we can make.
	 */
	protected ArrayList<Tuple> availableMoves(State s, int x, int y) {
		char piece = s.getChar(x, y);
		ArrayList<Tuple> returnList = new ArrayList<Tuple>();

		if (piece == '_') {
			return returnList;
		}

		if (isLegalMove(s, x, y + 1)) {
			returnList.add(new Tuple(x, y + 1));
		}
		if (isLegalMove(s, x, y - 1)) {
			returnList.add(new Tuple(x, y - 1));
		}
		if (isLegalMove(s, x + 1, y)) {
			returnList.add(new Tuple(x + 1, y));
		}
		if (isLegalMove(s, x - 1, y)) {
			returnList.add(new Tuple(x - 1, y));
		}

		switch (piece) {
		case ('K'): // the king has to additionally check if it can jump a guard
			if (isGuard(s, x + 1, y)) {
				if (isLegalMove(s, x + 2, y)) {
					returnList.add(new Tuple(x + 2, y));
				}
			}
			if (isGuard(s, x - 1, y)) {
				if (isLegalMove(s, x - 2, y)) {
					returnList.add(new Tuple(x - 2, y));
				}
			}
			if (isGuard(s, x, y + 1)) {
				if (isLegalMove(s, x, y + 2)) {
					returnList.add(new Tuple(x, y + 2));
				}
			}
			if (isGuard(s, x, y - 1)) {
				if (isLegalMove(s, x, y - 2)) {
					returnList.add(new Tuple(x, y - 2));
				}
			}
			break;
		case ('D'): // the dragons have to additionally check the diagonal
					// movements
			if (isLegalMove(s, x + 1, y + 1)) {
				returnList.add(new Tuple(x + 1, y + 1));
			}
			if (isLegalMove(s, x - 1, y - 1)) {
				returnList.add(new Tuple(x - 1, y - 1));
			}
			if (isLegalMove(s, x + 1, y - 1)) {
				returnList.add(new Tuple(x + 1, y - 1));
			}
			if (isLegalMove(s, x - 1, y + 1)) {
				returnList.add(new Tuple(x - 1, y + 1));
			}
			break;
		default:
			break;
		}

		// Finally, a guard or king must check if it is eligible to capture a
		// dragon
		if (piece == 'K' || piece == 'G') {
			// Check if horizontally or vertically adjacent to a dragon. If so,
			// add option to capture dragon to move list
			// Note that none of the capture logic is in this function. As far
			// as this function is concerned, the
			// king or guard is simply 'moving' to a space where there is
			// already a dragon. Logic to kill the dragon
			// is implemented elsewhere
			if (isDragon(s, x + 1, y)) {
				if (getNumSurroundingGuards(s, x + 1, y) >= 2) {
					returnList.add(new Tuple(x + 1, y));
				}
			}
			if (isDragon(s, x, y + 1)) {
				if (getNumSurroundingGuards(s, x, y + 1) >= 2) {
					returnList.add(new Tuple(x, y + 1));
				}
			}
			if (isDragon(s, x - 1, y)) {
				if (getNumSurroundingGuards(s, x - 1, y) >= 2) {
					returnList.add(new Tuple(x - 1, y));
				}
			}
			if (isDragon(s, x, y - 1)) {
				if (getNumSurroundingGuards(s, x, y - 1) >= 2) {
					returnList.add(new Tuple(x, y - 1));
				}
			}
		}

		return returnList;
	}

	/**
	 * Successor function for the AI searches
	 * 
	 * @param s
	 *            A given game state
	 * 
	 * @return All the possible state successors from that particular state
	 */
	public LinkedList<State> successors(State s) {
		LinkedList<State> successors = new LinkedList<State>();

		/* Get all the piece positions for the team of interest */
		LinkedList<Tuple> teamPositions = new LinkedList<Tuple>();
		char[][] stateBoard = s.getBoard();

		if (s.dragonsJustMoved()) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					char curPiece = stateBoard[x][y];
					if (curPiece == 'K') {
						teamPositions.addFirst(new Tuple(x, y));
					} else if (curPiece == 'G') {
						teamPositions.addLast(new Tuple(x, y));
					}
				}
			}
		} else {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					if (stateBoard[x][y] == 'D') {
						teamPositions.add(new Tuple(x, y));
					}
				}
			}
		}

		ArrayList<Tuple> possibleMoves;
		for (Tuple currentPosition : teamPositions) {
			possibleMoves = availableMoves(s, currentPosition.getX(), currentPosition.getY());
			for (Tuple move : possibleMoves) {
				successors.addLast(new State(s, currentPosition, move, s.getChar(currentPosition)));
			}
		}

		return successors;
	}

	/**
	 * Terminal function for AI searches - indicates if the game is over
	 * 
	 * @param s
	 *            A given game state
	 * @return True if the state indicates game is over, false otherwise.
	 */
	public boolean terminalState(State s) {
		// To know if there is a winner, need to know where the king is

		Tuple kingPosition = findKing(s.getBoard());
		if (kingPosition == null) {
			throw new IllegalStateException("There was no king on the board when checking terminal state!");
		}

		// Check if the dragons or kings have achieved the win state
		if (dragonsWin(s, kingPosition) || kingWins(kingPosition)) {
			// Set the state's win indicator to true
			s.stateIsWinner();
			return true;
		} else if (successors(s).isEmpty()) {
			// If there are no possible moves from the given state, it is a
			// draw. There is no winner.
			return true;
		} else {
			// Otherwise, the game is not over yet
			return false;
		}
	}

	/**
	 * Finds the king on a given gameBoard
	 * 
	 * @param gameBoard
	 *            a 5x5 game board
	 * @return a position Tuple with the king's position
	 */
	private Tuple findKing(char[][] gameBoard) {
		char curPiece;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				curPiece = gameBoard[x][y];
				if (curPiece == 'K') {
					return new Tuple(x, y);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the utility value of a current state. Not necessarily a terminal
	 * state. When not a terminal state, attempts to estimate who is closer to
	 * winning
	 *
	 * @param s
	 *            A given game state
	 *
	 */
	public double utility(State s) {
		if (s.potentialBoardWins()) {
			if (!s.dragonsJustMoved()) {
				return -1000;
			} else {
				return 1000;
			}
		} else {
			LinkedList<Tuple> kingTeam = new LinkedList<Tuple>();
			LinkedList<Tuple> dragonTeam = new LinkedList<Tuple>();
			char[][] stateBoard = s.getBoard();

			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					char curPiece = stateBoard[x][y];
					if (curPiece == 'K') {
						kingTeam.addFirst(new Tuple(x, y));
					} else if (curPiece == 'G') {
						kingTeam.addLast(new Tuple(x, y));
					} else if (curPiece == 'D') {
						dragonTeam.add(new Tuple(x, y));
					}
				}
			}
			int returnValue = 0;

			// calculates the value of the dragons
			if (dragonTeam.size() < 4) {
				returnValue += -50;
			} else if (dragonTeam.size() == 4) {
				returnValue += -25;
			} else {
				returnValue += (10 * (dragonTeam.size()));
			}

			// calculates the value based on kings position relative to his goal
			Tuple kingPosition = kingTeam.getFirst();
			returnValue += Math.negateExact((int) (Math.pow((kingPosition.getX() + 1), 4)));

			// calculates the value based on number of guards
			switch (kingTeam.size() - 1) {
			case (0):
				returnValue += 100;
				break;
			case (1):
				returnValue += 40;
				break;
			case (2):
				returnValue += 15;
				break;
			default:
				break;
			}

			// calculates the value based on dragons position relative to kings
			int numberOfSurroundingDragons = getNumSurroundingDragons(s, kingPosition.getX(), kingPosition.getY());

			switch (numberOfSurroundingDragons) {
			case (2):
				returnValue += 300;
				break;
			case (1):
				returnValue += 50;
				break;
			default:
				break;
			}

			// calculating negative points based on how far away the dragons are
			// from the king
			for (Tuple dragon : dragonTeam) {
				int distance = Math.abs(dragon.getX() - kingPosition.getX())
						+ Math.abs(dragon.getY() - kingPosition.getY());
				returnValue -= (distance * 2);
			}
			return returnValue;
		}
	}

	/**
	 * Get the number of dragons surrounding a piece at piecePosition. Per the
	 * game rules, a piece can only be "surrounded" horizontally or vertically
	 * NOT diagonally.
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            The coordinates of the piece to check around
	 * @param y
	 *            The coordinates of the piece to check around
	 * 
	 * @return integer between 0 and 4 indicating number of dragons surrounding
	 *         the piece at piecePosition
	 */
	private int getNumSurroundingDragons(State s, int x, int y) {
		int numToReturn = 0;

		if (isDragon(s, x + 1, y)) {
			numToReturn++;
		}

		if (isDragon(s, x - 1, y)) {
			numToReturn++;
		}

		if (isDragon(s, x, y + 1)) {
			numToReturn++;
		}

		if (isDragon(s, x, y - 1)) {
			numToReturn++;
		}

		return numToReturn;
	}

	/**
	 * Get the number of guards (including king) surrounding a piece at position
	 * x,y. Per the game rules, a piece can only be "surrounded" horizontally or
	 * vertically NOT diagonally.
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            The coordinates of the piece to check around
	 * @param y
	 *            The coordinates of the piece to check around
	 * 
	 * @return integer between 0 and 4 indicating number of guards (including
	 *         king) surrounding piece at x,y
	 */
	private int getNumSurroundingGuards(State s, int x, int y) {
		int numGuards = 0;

		if (isKingOrGuard(s, x - 1, y)) {
			numGuards++;
		}

		if (isKingOrGuard(s, x, y - 1)) {
			numGuards++;
		}

		if (isKingOrGuard(s, x + 1, y)) {
			numGuards++;
		}

		if (isKingOrGuard(s, x, y + 1)) {
			numGuards++;
		}

		return numGuards;
	}

	/**
	 * checks a given tile to see if it is a king or guard
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'K' or 'G', false otherwise.
	 */
	private boolean isKingOrGuard(State s, int x, int y) {
		return checkPiece(s, x, y, 'K') || checkPiece(s, x, y, 'G');
	}

	/**
	 * checks a given tile to see if it is a dragon
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'D', false otherwise.
	 */
	private boolean isDragon(State s, int x, int y) {
		return checkPiece(s, x, y, 'D');
	}

	/**
	 * checks a given tile to see if it is a guard
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a 'G', false otherwise.
	 */
	private boolean isGuard(State s, int x, int y) {
		return checkPiece(s, x, y, 'G');
	}

	/**
	 * check a given tile to see if it is empty
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @return true if the tile contains a '_', false otherwise
	 */
	private boolean isLegalMove(State s, int x, int y) {
		return checkPiece(s, x, y, '_');
	}

	/**
	 * check a given tile to see if it has the required character on it
	 * 
	 * @param s
	 *            the state containing the game board to check
	 * @param x
	 *            the x coordinate of the tile to check
	 * @param y
	 *            the y coordinate of the tile to check
	 * @param letter
	 *            the letter to check for
	 * @return true if the tile contains "letter", false otherwise
	 */
	private boolean checkPiece(State s, int x, int y, char letter) {
		if ((x < 0) || (x > 4) || (y < 0) || (y > 4)) {
			return false;
		}

		if (s.getChar(x, y) == letter) {
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
	protected boolean dragonsWin(State s, Tuple kingPosition) {
		return getNumSurroundingDragons(s, kingPosition.getX(), kingPosition.getY()) >= 3;
	}

	/**
	 * check the king's win condition
	 * 
	 * @return true if the king is at the dragon's "home row", false otherwise.
	 */
	protected boolean kingWins(Tuple kingPosition) {
		if (kingPosition.getX() == 4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if any guards have been captured
	 */
	protected void checkGuardCapture() {
		LinkedList<gamePiece> killList = new LinkedList<gamePiece>();

		for (gamePiece currentPiece : teamOne) {
			// Only consider alive guards
			if ((currentPiece.getMyLetter() != 'G') || (currentPiece.isAlive() == false)) {
				continue;
			}

			int x = currentPiece.getPosition().getX();
			int y = currentPiece.getPosition().getY();

			// If guard is surrounded by at least 3 dragons, the guard dies and
			// is replaced with a dragon
			if (getNumSurroundingDragons(Board.actualGameState, currentPiece.getPosition().getX(),
					currentPiece.getPosition().getY()) >= 3) {
				Dragon newDragon = new Dragon(x, y);
				teamTwo.add(newDragon);
				currentPiece.kill();
				killList.add(currentPiece);

				// The dragon must be replacing a guard, otherwise there is a
				// serious problem
				if (actualGameState.getChar(x, y) != 'G') {
					throw new IllegalStateException(
							"Dragons tried to 'capture' a non-guard in checkGuard, which is not allowed! ");
				}

				actualGameState.setChar(x, y, 'D');
			}
		}

		while (!killList.isEmpty()) {
			teamOne.remove(killList.removeFirst());
		}

	}

	/**
	 * Kills the dragon at a given x and y position provided a dragon exists at
	 * that x,y
	 * 
	 * Returns true on success, false otherwise
	 */
	protected boolean killDragon(int x, int y) {
		// Note, we might have to make this better. Finds the object which
		// represents the piece we are killing
		for (gamePiece pieceToCheck : teamTwo) {
			if (pieceToCheck.checkPosition(new Tuple(x, y))) {
				pieceToCheck.kill();
				teamTwo.remove(pieceToCheck); // Note: this is only safe because
												// we are immediately returning.
				return true;
			}
		}
		return false;
	}

	protected void applyState(State s) {

		if (s.getOldPosition() == null || s.getNewPosition() == null) {
			throw new IllegalStateException(
					"The AI search did not select a move to make in applyState! Check that a valid depth limit was selected.");
		}

		actualGameState = s.clone();
		LinkedList<Tuple> t1 = new LinkedList<Tuple>();
		LinkedList<Tuple> t2 = new LinkedList<Tuple>();
		char[][] stateBoard = s.getBoard();

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				char curPiece = stateBoard[x][y];
				if (curPiece == 'K') {
					t1.addFirst(new Tuple(x, y));
				} else if (curPiece == 'G') {
					t1.addLast(new Tuple(x, y));
				} else if (curPiece == 'D') {
					t2.add(new Tuple(x, y));
				}
			}
		}

		ArrayList<gamePiece> team;
		if (s.dragonsJustMoved()) {
			team = teamTwo;

		} else {
			team = teamOne;
		}

		for (gamePiece curPiece : team) {
			if (curPiece.checkPosition(s.getOldPosition())) {
				curPiece.changePosition(s.getNewPosition());
			}
		}

	}

	/**
	 * method used for testing purposes hopefully.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		Board testBoard = new Board();

		char[][] arr = { { '_', '_', 'D', 'K', 'D' }, { 'D', 'D', 'G', 'G', '_' }, { '_', 'D', '_', 'D', '_' },
				{ '_', '_', '_', '_', '_' }, { '_', '_', '_', '_', '_' } };
		State testState = new State(arr);

		testState.nextTurn();
		System.out.println(testState);

		System.out.println(testBoard.successors(testState));

	}
}