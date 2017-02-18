package cmpt317A2;

public class Tuple {
	private int x;
	private int y;
	
	public Tuple(int xPos, int yPos){
		x = xPos;
		y = yPos;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
}
