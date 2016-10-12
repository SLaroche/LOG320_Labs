package puzzle_lab2.model;

import java.util.HashMap;

// Référence wikipedia Memento_pattern
// private HashMap<BoardState.Memento, String> pointlessBoard = new HashMap<BoardState.Memento, String>();

public class BoardState {
	
    private int[][] state;
    // The class could also contain additional data that is not part of the
    // state saved in the memento..
 
    public void set(int[][] state) {
        this.state = state;
    }
 
    public Memento saveToMemento() {
        return new Memento(this.state);
    }
 
    public void restoreFromMemento(Memento memento) {
        this.state = memento.getSavedState();
    }
    
    public String getKey () {
    	String key = "";
    	for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				key += "" + state[i][j];
			}
		}
    	return key;
    }
    
    public boolean isEqualCheck (int[][] board) {
    	for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (state[i][j] != board[i][j]) {
					return false;
				}
			}
		}
    	return true;
    }
    
    public boolean isEqual (int[][] board) {
    	boolean isEqual = false;
    	if (symmetrical()) {
    		for (int i = 0; i < 4; i++) {
				isEqual = isEqual(board);
				if (isEqual) {
					i = 4;
				}
				board = rotate(board);
			}
    	} else {
    		isEqual = isEqualCheck(board);
    	}
    	return isEqual;
    }
    
    public boolean symmetrical () {
    	for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < 3; j++) {
				if (state[i][j] != 0 && state[i][7 - j] == 0) {
					return false;
				} else if (state[i][j] == 0 && state[i][7 - j] != 0) {
					return false;
				}
			}
		}
    	int [][] tmp = rotate(state);
    	for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < 3; j++) {
				if (tmp[i][j] != 0 && tmp[i][7 - j] == 0) {
					return false;
				} else if (tmp[i][j] == 0 && tmp[i][7 - j] != 0) {
					return false;
				}
			}
		}
    	return true;
    }
    
    public int[][] rotate(int[][] board) {
    	int[][] tmp = new int[7][7];
    	for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				tmp[7 - j][i] = board[i][j];
			}
		}
    	return tmp;
    }
 
    public static class Memento {
        private final int[][] state;

        public Memento(int[][] stateToSave) {
            state = stateToSave;
        }
 
        private int[][] getSavedState() {
            return state;
        }
    }

}
