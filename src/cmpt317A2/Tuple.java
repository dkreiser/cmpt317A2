package cmpt317A2;

/**
 * a custom created class that simply holds an x and a y value as a "pair"
 */
public class Tuple {
	
	/** the x value of the tuple */
	private int x;
	
	/** the y value of the tuple */
	private int y;
	
	/** a constructor that takes in two integers and sets creates a new tuple */
	public Tuple(int xPos, int yPos){
		x = xPos;
		y = yPos;
	}
	
	/** returns the x value of the tuple */
	public int getX() {
		return x;
	}

	/** sets the x value of the tuple */
	public void setX(int x) {
		this.x = x;
	}

	/** returns the y value of the tuple */
	public int getY() {
		return y;
	}

	/** sets the y value of the tuple */
	public void setY(int y) {
		this.y = y;
	}

	/** handy toString method to print the tuple in the way we want it */
	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
}
