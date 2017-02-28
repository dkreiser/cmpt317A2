package search;

import java.util.Iterator;
import java.util.LinkedList;

import board.Board;
import board.State;
import cmpt317A2.GameNode;

public class AlphaBeta extends Search {

	public AlphaBeta(Board b) {
		this.gameBoard = b;
		this.depthLimit = 6;
	}

	public GameNode alphaBeta(GameNode currentNode, double alpha, double beta, boolean startMax, boolean AIisDragon) {
		// Step one, get a copy of the current node.
		GameNode bestNode = currentNode.clone();

		// Step two, if we are at a terminal state or at our depth limit, we
		// have to evaluate the state and
		// set the states value appropriately.
		if ((gameBoard.terminalState(currentNode.getState())) || (currentNode.getDepth() == depthLimit)) {
			if (AIisDragon) {
				bestNode.setValue(gameBoard.utility(currentNode.getState()));
			} else {
				bestNode.setValue(-1 * gameBoard.utility(currentNode.getState()));
			}
		} else if (startMax) { // Step three, when max is starting we set our
								// best nodes value to negative infinity
			bestNode.setValue(alpha);

			// Step four, we get a list of successors that returns all of the
			// successors based on the best node.
			LinkedList<State> successors = gameBoard.successors(currentNode.getState());

			// Step five, for each possible state we have to process it.
			Iterator<State> stateIterator = successors.iterator();
			while (stateIterator.hasNext()) {
				State currentState = stateIterator.next().clone();
				// step 6, we have to create a new node with the given state,
				// having a value of 0 and a depth of +1
				GameNode currNode = new GameNode(currentState, 0, currentNode.getDepth() + 1);

				// Step 7, we get the maximum value, which is the max between
				// the bestNodes value and the next nodes(s) value.
				Double maximum = Math.max(bestNode.getValue(),
						alphaBeta(currNode, bestNode.getValue(), beta, false, AIisDragon).getValue());

				// Step 8, we assign alpha to be the largest value between
				// itself and the max value
				alpha = Math.max(alpha, maximum);

				// Step 9, if beta is less than or equal to alpha, we found a
				// node better than our current best so we break and return it.
				if (beta <= alpha) {
					bestNode = currNode.clone();
					bestNode.setValue(maximum);
					break; // this is called beta cutoff
				}
			}
		} else { // Step three, when min is starting we set our best nodes value
					// to positive infinity
			bestNode.setValue(beta);

			// Step four, we get a list of successors that returns all of the
			// successors based on the best node.
			LinkedList<State> successors = gameBoard.successors(currentNode.getState());

			// Step five, for each possible state we have to process it.
			Iterator<State> stateIterator = successors.iterator();
			while (stateIterator.hasNext()) {
				State currentState = stateIterator.next().clone();

				// step 6, we have to create a new node with the given state,
				// having a value of 0 and a depth of +1
				GameNode currNode = new GameNode(currentState, 0, currentNode.getDepth() + 1);

				// Step 7, we get the maximum value, which is the max between
				// the bestNodes value and the next nodes(s) value.
				Double minimum = Math.min(bestNode.getValue(),
						alphaBeta(currNode, alpha, bestNode.getValue(), true, AIisDragon).getValue());

				// Step 8, we assign alpha to be the largest value between
				// itself and the max value
				beta = Math.min(beta, minimum);

				// Step 9, if beta is less than or equal to alpha, we found a
				// node better than our current best so we break and return it.
				if (beta <= alpha) {
					bestNode = currNode.clone();
					bestNode.setValue(minimum);
					break; // this is called alpha cutoff
				}
			}
		}

		// Step 10, we return the bestNode we found.
		return bestNode;
	}
}