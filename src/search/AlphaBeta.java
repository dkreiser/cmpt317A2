package search;

import java.util.LinkedList;

import board.Board;
import board.State;
import cmpt317A2.GameNode;

public class AlphaBeta extends Search {

	public AlphaBeta(Board b) {
		this.gameBoard = b;
		this.depthLimit = 4;
	}

	public GameNode reformedAlphaBeta(GameNode currentNode, double alpha, double beta, boolean startMax,
			boolean AIisDragon) {
		GameNode bestNode = currentNode.clone();
		if ((gameBoard.terminalState(currentNode.getState()) ) || (currentNode.getDepth() == depthLimit)) {
			if (AIisDragon) {
				bestNode.setValue(gameBoard.utility(currentNode.getState()));
			} else {
				// Might need to do something more appropriate here if AI is playing as king
				bestNode.setValue(-1 * gameBoard.utility(currentNode.getState()));
			}
		} else if (startMax) {
			bestNode.setValue(Double.NEGATIVE_INFINITY);

			LinkedList<State> successors = gameBoard.successors(currentNode.getState());

			for (State currentState : successors) {
				GameNode currNode = new GameNode(currentState.clone(), 0, currentNode.getDepth() + 1);
				GameNode nextNode = reformedAlphaBeta(currNode, alpha, beta, false, AIisDragon);

				Double maximum = Math.max(bestNode.getValue(), nextNode.getValue());
				alpha = Math.max(alpha, maximum);

				if (beta <= alpha) {
					bestNode = nextNode.clone();
					break;
				}
			}
		} else {
			bestNode.setValue(Double.POSITIVE_INFINITY);

			LinkedList<State> successors = gameBoard.successors(currentNode.getState());

			for (State currentState : successors) {
				GameNode currNode = new GameNode(currentState, 0, currentNode.getDepth() + 1);
				GameNode nextNode = reformedAlphaBeta(currNode, alpha, beta, true, AIisDragon);

				Double minimum = Math.min(bestNode.getValue(), nextNode.getValue());
				beta = Math.min(beta, minimum);

				if (beta <= alpha) {
					bestNode = nextNode.clone();
					break;
				}
			}
		}
		return bestNode;
	}
}
