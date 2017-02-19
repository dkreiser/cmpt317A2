package cmpt317A2;

public class King extends gamePiece {

	/**
	 * a simple class that makes use of the gamePiece abstract class. Note: the
	 * values of x and y are hard coded for now, as the king ALWAYS starts at
	 * the same position change this to take in values if this ever changes.
	 */
	public King() {
		myLetter = 'K';
		xPosition = 0;
		yPosition = 2;
	}
}
