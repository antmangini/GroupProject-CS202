import java.util.ArrayList;
import java.util.Collections;
public abstract class Board {

    abstract public boolean setSpace(int space, String player);
    abstract public boolean hasWon(String player);
    abstract public boolean gameIsOver();
    abstract public ArrayList<Integer> availableMoves();
    abstract public int[] getSpotOnBoard(int space);
    abstract public Board cloneBoard();
    abstract public int evaluateBoard();

    public int[] minimax(boolean isMaximizing, int depth, int alpha, int beta) {
        int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestMove = -1; // Use -1 to indicate no valid move initially
        String symbol = isMaximizing ? "X" : "O";
        
        if (gameIsOver() || depth == 0) {
            return new int[] { evaluateBoard(), bestMove };
        }
    
        ArrayList<Integer> moves = availableMoves();
        Collections.shuffle(moves); // Optional: Shuffle moves to prevent AI from choosing predictable patterns
    
        for (Integer move : moves) {
            Board newBoard = cloneBoard();
            newBoard.setSpace(move, symbol);
    
            // Recurse with minimized/maximized value
            int hypotheticalValue = newBoard.minimax(!isMaximizing, depth - 1, alpha, beta)[0];
    
            // Maximizing player's turn
            if (isMaximizing) {
                if (hypotheticalValue > bestValue) {
                    bestValue = hypotheticalValue;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestValue);
            } 
            // Minimizing player's turn
            else {
                if (hypotheticalValue < bestValue) {
                    bestValue = hypotheticalValue;
                    bestMove = move;
                }
                beta = Math.min(beta, bestValue);
            }
    
            // Alpha-Beta pruning: stop exploring this branch if it won't be better than the current one
            if (beta <= alpha) {
                break;
            }
        }
    
        return new int[] { bestValue, bestMove };
    }
    
}
