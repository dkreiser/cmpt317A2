package gamepiece;

public class Dragon extends gamePiece {

	/**
	 * a simple class that makes use of the gamePiece abstract class.
	 * 
	 * @param x
	 *            the x position of the new dragon
	 * @param y
	 *            the y position of the new dragon
	 */
	public Dragon(int x, int y) {
		myLetter = 'D';
		xPosition = x;
		yPosition = y;
	}
}
