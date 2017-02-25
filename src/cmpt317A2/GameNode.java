package cmpt317A2;

import board.State;

public class GameNode {

	protected State state;
	protected double value;
	protected int depth;
	
	public GameNode(State state, double value, int depth) {
		this.state = state;
		this.value = value;
		this.depth = depth;
	}

	public State getState() {
		return state;
	}

	public double getValue() {
		return value;
	}

	public int getDepth() {
		return depth;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public GameNode clone() {
		return new GameNode(state.clone(), value, depth);
	}
	
	public String toString(){
		String returnString = "";
		returnString += state.toString();
		returnString += "Value is: " + value;
		returnString += "\nDepth is: " + depth;
		returnString += "\n~~~~~~~~~~\n";
		return returnString;
	}
}
