package cmpt317A2;

public abstract class gamePiece {
	//Variables
	/**
	 * the letter associated to the game piece, for now this will only ever be:
	 * 	K for King
	 * 	G for Guard
	 * 	D for Dragon
	 */
	char myLetter;
	
	/** the x coordinate of the piece*/
	int xPosition;
	
	/** the y coordinate of the piece*/
	int yPosition;
	
	/** a boolean to know if the unit is alive or not */
	boolean alive = true;
	
	/**
	 * change our current positional coordinates to a new set of coordinates
	 * and update the game board appropriately.
	 * Note: we don't have to do error testing here because it will be impossible
	 * for the player(s) to enter a position that is invalid based on the structure
	 * of the rest of our program.
	 * @param newPosition the position we are moving to.
	 */
	void changePosition(Tuple newPosition){
		Board.gameBoard[xPosition][yPosition] = '_';	
		Board.gameBoard[newPosition.getX()][newPosition.getY()] = this.myLetter;
		xPosition = newPosition.getX();
		yPosition = newPosition.getY();
	}
	
	/** a method to check if the unit is still alive*/
	public boolean isAlive() {
		return alive;
	}
	
	/** a method that creates a returns a new Tuple of our current position*/
	public Tuple getPosition(){
		return new Tuple(xPosition,yPosition);
	}
	
	/**
	 * @param positionToCheck the position we are checking against our own.
	 * @return true if that is our position, false otherwise.
	 */
	public boolean checkPosition(Tuple positionToCheck){
		if(xPosition == positionToCheck.getX() && yPosition == positionToCheck.getY()){
			return true;
		} else {
			return false;
		}
	}

	public String toString(){
		switch (myLetter){
		case('K'):
			return "King: " + getPosition();
		case('G'):
			return "Guard: " + getPosition();
		case('D'):
			return "Dragon: " + getPosition();
		default:
			//will never occur.
			return "";
		}
	}
}
