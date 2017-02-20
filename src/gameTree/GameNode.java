package gameTree;

import java.util.ArrayList;

import cmpt317A2.Tuple;
import board.State;

public class GameNode {
	
	private ArrayList<GameNode> children;
	private Tuple lastMoveMade;
	private State myState;
	private int depth;
	
	public GameNode( Tuple newValue, int currentDepth, State state){
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
	}
}
