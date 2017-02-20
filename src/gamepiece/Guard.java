package gamepiece;

public class Guard extends gamePiece {

	/**
	 * a simple class that makes use of the gamePiece abstract class.
	 * 
	 * @param x
	 *            the x position of the new guard
	 * @param y
	 *            the y position of the new guard
	 */
	public Guard(int x, int y) {
		myLetter = 'G';
		xPosition = x;
		yPosition = y;
	}
}
