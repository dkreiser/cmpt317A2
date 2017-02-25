package search;

import java.util.Iterator;
import java.util.LinkedList;

import board.Board;
import board.State;
import cmpt317A2.GameNode;

public class AlphaBeta {

	final int depthLimit = 4;

	private Board gameBoard;

	public AlphaBeta(Board b) {
		this.gameBoard = b;
	}

	public GameNode reformedAlphaBeta(GameNode currentNode, double alpha, double beta, boolean startMax){
		GameNode bestNode = currentNode.clone();
		if (currentNode.getDepth() == depthLimit || gameBoard.terminalState(currentNode.getState())){
			bestNode.setValue(gameBoard.utility(currentNode.getState()));
		} else if (startMax){
			bestNode.setValue(alpha);
			
			LinkedList<State> successors = gameBoard.successors(currentNode.getState());
			
			for (State currentState : successors){
				GameNode currNode = new GameNode(currentState, 0, currentNode.getDepth() + 1);
				GameNode nextNode = reformedAlphaBeta(currNode, bestNode.getValue(), beta, false);
				
				if(nextNode.getValue() >= bestNode.getValue()){
					bestNode = nextNode.clone();
					break;
				}
			}
		} else {
			bestNode.setValue(beta);
			
			LinkedList<State> successors = gameBoard.successors(currentNode.getState());
			
			for (State currentState : successors){
				GameNode currNode = new GameNode(currentState, 0, currentNode.getDepth() + 1);
				GameNode nextNode = reformedAlphaBeta(currNode, alpha, bestNode.getValue(), true);
				
				if(nextNode.getValue() >= bestNode.getValue()){
					bestNode = nextNode.clone();
					break;
				}
			}
		}	
		return bestNode;
	}
}
