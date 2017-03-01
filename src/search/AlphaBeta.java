package search;

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
				s.setValue(-1 * gameBoard.utility(s.getState()));
			}
		} else {
			LinkedList<State> successors = gameBoard.successors(s.getState());
			double bestValue = alpha;
	
			for (State currState : successors){
				GameNode curr = new GameNode(currState, 0, s.getDepth() + 1);
				
				GameNode n = alphaBetaMin(curr.clone(), alpha, beta, AIisDragon);
				
				bestValue = Math.max(bestValue, n.getValue());
				
				if (bestValue >= beta) {
					bestValue = n.getValue();
					s = curr.clone();
					s.setValue(bestValue);
					break;
				}
				alpha = Math.max(alpha, bestValue);
			}
		}
		return s;
	}

	private GameNode alphaBetaMin(GameNode s, Double alpha, Double beta, boolean AIisDragon) {
		if ((gameBoard.terminalState(s.getState()) ) || (s.getDepth() == depthLimit)) {
			if (AIisDragon) {
				s.setValue(gameBoard.utility(s.getState()));
			} else {
				s.setValue(-1 * gameBoard.utility(s.getState()));
			}
		} else {
			LinkedList<State> successors = gameBoard.successors(s.getState());
			double bestValue = beta;
	
			for (State currState : successors){
				GameNode curr = new GameNode(currState, 0, s.getDepth() + 1);
				
				GameNode n = alphaBetaMax(curr.clone(), alpha, beta, AIisDragon);
				
				bestValue = Math.min(bestValue, n.getValue());
				
				if (bestValue <= alpha) {
					bestValue = n.getValue();
					s = curr.clone();
					s.setValue(bestValue);
					break;
				}
				beta = Math.min(beta, bestValue);
			}
		}
		return s;
	}
}