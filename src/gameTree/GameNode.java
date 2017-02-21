package gameTree;

import board.State;

public class GameNode {
	
	//private ArrayList<GameNode> children;
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
	
	/*public GameNode( Tuple newValue, int currentDepth, State state){
		lastMoveMade = newValue;
		depth = currentDepth;
		myState = state;
	}
	
	//getters and setters
	public State getState(){
		return myState;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public ArrayList<GameNode> getChildren(){
		return this.children;
	}
	
	public Tuple getValue(){
		return lastMoveMade;
	}
	
	//node functionality
	public void addChild (GameNode newChild){
		children.add(newChild);
	}
	
	public void removeChild (GameNode childToRemove){
		if(children.contains(childToRemove)){
			children.remove(childToRemove);
		}
	}*/
}
