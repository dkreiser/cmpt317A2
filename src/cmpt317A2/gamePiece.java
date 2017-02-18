package cmpt317A2;

public abstract class gamePiece {
	//Variables
	char myLetter;
	int xPosition;
	int yPosition;
	
	boolean alive = true;
	
	void updateCurrentPosition(Tuple newPosition, char piece){
		Board.Gameboard[xPosition][yPosition] = '_';	
		Board.Gameboard[newPosition.getX()][newPosition.getY()] = piece;
		xPosition = newPosition.getX();
		yPosition = newPosition.getY();
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public Tuple getPosition(){
		return new Tuple(xPosition,yPosition);
	}
	
	public boolean checkPosition(Tuple positionToCheck){
		if(xPosition == positionToCheck.getX() && yPosition == positionToCheck.getY()){
			return true;
		}
		else
		{
			return false;
		}
	}

}
