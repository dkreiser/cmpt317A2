package search;

import java.util.Iterator;
import java.util.LinkedList;

import board.Board;
import board.State;
import cmpt317A2.GameNode;

public class Minimax extends Search {

	public Minimax(Board b) {
		this.gameBoard = b;
		// takes about a minute at 6
		// mathematically 8 should take around 5 hours.
		this.depthLimit = 4;
	}

	public GameNode MinimaxValue(GameNode s, boolean MaxStarts, boolean AIisDragon) {

		if (MaxStarts) {
			return MaxValue(s, AIisDragon);
		} else {
			return MinValue(s, AIisDragon);
		}
	}

	// the two mutually recursive functions
	// only difference is the way the "best" successor
	// is determined
	private GameNode MaxValue(GameNode s, boolean AIisDragon) {
		if ((gameBoard.terminalState(s.getState()) ) || (s.getDepth() == depthLimit)) {
			if (AIisDragon) {
				s.setValue(gameBoard.utility(s.getState()));
			} else {
				// Might need to do something more appropriate here if AI is playing as king
				s.setValue(-1 * gameBoard.utility(s.getState()));
			}
			
			return s;
		}

		LinkedList<State> successors = gameBoard.successors(s.getState());
		GameNode best = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		Iterator<State> it = successors.iterator();

		while (it.hasNext()) {
			State current = it.next();
			GameNode curr = new GameNode(current, 0, s.getDepth() + 1);
			GameNode n = MinValue(curr, AIisDragon);
			if (n.getValue() > bestValue) {
				bestValue = n.getValue();
				best = curr.clone();
				best.setValue(bestValue);
			}
		}
		return best;
	}

	private GameNode MinValue(GameNode s, boolean AIisDragon) {
		if ((gameBoard.terminalState(s.getState()) ) || (s.getDepth() == depthLimit)) {
			if (AIisDragon) {
				s.setValue(gameBoard.utility(s.getState()));
			} else {
				// Might need to do something more appropriate here if AI is playing as king
				s.setValue(-1 * gameBoard.utility(s.getState()));
			}
			
			return s;
		}
		
		LinkedList<State> successors = gameBoard.successors(s.getState());
		GameNode best = null;
		double bestValue = Double.POSITIVE_INFINITY;
		Iterator<State> it = successors.iterator();

		while (it.hasNext()) {
			State current = it.next();
			GameNode curr = new GameNode(current, 0, s.getDepth() + 1);
			GameNode n = MaxValue(curr, AIisDragon);
			if (n.getValue() < bestValue) {
				bestValue = n.getValue();
				best = curr.clone();
				best.setValue(bestValue);
			}
		}
		return best;
	}

}
