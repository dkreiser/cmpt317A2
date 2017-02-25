package search;

import java.util.Iterator;
import java.util.LinkedList;

import board.Board;
import board.State;
import gameTree.GameNode;

public class AlphaBeta {

	final int depthLimit = 3;

	private Board gameBoard;

	public AlphaBeta(Board b) {
		this.gameBoard = b;
	}

	public GameNode AlphaBetaValue(GameNode s, boolean MaxStarts) {

		if (MaxStarts) {
			return MaxValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		} else {
			return MinValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		}
	}

	// the two mutually recursive functions
	// only difference is the way the "best" successor
	// is determined
	private GameNode MaxValue(GameNode s, double alpha, double beta) {
		if (gameBoard.terminalState(s.getState()) || (s.getDepth() == depthLimit)) {
			s.setValue(gameBoard.utility(s.getState()));
			return s;
		}
		
		LinkedList<State> successors = gameBoard.successors(s.getState());
		Iterator<State> it = successors.iterator();
		
		GameNode v = s.clone();

		while (it.hasNext()) {
			State current = it.next();
			GameNode curr = new GameNode(current, 0, s.getDepth() + 1);
			
			v.setValue(Double.max(v.getValue(), MinValue(curr, alpha, beta).getValue()));
			alpha = Double.max(alpha, v.getValue());

			if (beta <= alpha) {
				Double bestValue = v.getValue();
				v = curr.clone();
				v.setValue(bestValue);
				break;
			}

			
		}

		return v;
	}

	private GameNode MinValue(GameNode s, double alpha, double beta) {
		if (gameBoard.terminalState(s.getState()) || (s.getDepth() == depthLimit)) {
			s.setValue(gameBoard.utility(s.getState()));
			return s;
		}
		
		LinkedList<State> successors = gameBoard.successors(s.getState());
		Iterator<State> it = successors.iterator();

		GameNode v = s.clone();

		while (it.hasNext()) {
			State current = it.next();
			GameNode curr = new GameNode(current, 0, s.getDepth() + 1);
			
			v.setValue(Double.min(v.getValue(), MaxValue(curr, alpha, beta).getValue()));
			beta = Double.min(beta, v.getValue());
			
			if (beta <= alpha) {
				Double bestValue = v.getValue();
				v = curr.clone();
				v.setValue(bestValue);
				break;
			}

			
		}

		return v;
	}

}
