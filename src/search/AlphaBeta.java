package search;

import java.util.Iterator;
import java.util.LinkedList;

import board.Board;
import board.State;
import cmpt317A2.GameNode;

public class AlphaBeta extends Search {

	public AlphaBeta(Board b) {
		this.gameBoard = b;
		this.depthLimit = 5;
	}
	
	public GameNode alphaBeta(GameNode s, boolean MaxStarts, boolean AIisDragon) {

		if (MaxStarts) {
			return alphaBetaMax(s.clone(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, AIisDragon);
		} else {
			return alphaBetaMin(s.clone(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, AIisDragon);
		}
	}

	private GameNode alphaBetaMax(GameNode s, Double alpha, Double beta, boolean AIisDragon) {
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
			GameNode n = alphaBetaMin(curr, alpha, beta, AIisDragon);
			if (n.getValue() > bestValue) {
				bestValue = n.getValue();
				best = curr.clone();
				best.setValue(bestValue);
			}
			
			if (bestValue >= beta){
				break;
			}
			
			alpha = Math.max(alpha, bestValue);
		}
		return best;
	}

	private GameNode alphaBetaMin(GameNode s, Double alpha, Double beta, boolean AIisDragon) {
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
			GameNode n = alphaBetaMax(curr, alpha, beta, AIisDragon);
			if (n.getValue() < bestValue) {
				bestValue = n.getValue();
				best = curr.clone();
				best.setValue(bestValue);
			}
			
			if (bestValue <= alpha){
				break;
			}
			
			beta = Math.min(beta, bestValue);
		}
		return best;
	}
}