import java.util.ArrayList;
import java.util.Collections;

// This is an abstract class that serves as a blueprint for creating different types of game boards (e.g., Tic-Tac-Toe).
public abstract class Board {

    // Abstract method to set a player's mark on a specified space on the board.
    abstract public boolean setSpace(int space, String player);

    // Abstract method to check if a specific player has won the game.
    abstract public boolean hasWon(String player);

    // Abstract method to check if the game is over (either win or draw).
    abstract public boolean gameIsOver();

    // Abstract method to return a list of available moves that the player can make.
    abstract public ArrayList<Integer> availableMoves();

    // Abstract method to convert a space number into a coordinate (row, column) on the board.
    abstract public int[] getSpotOnBoard(int space);

    // Abstract method to create and return a clone (deep copy) of the current board.
    abstract public Board cloneBoard();

    // Abstract method to evaluate the current state of the board and return a numeric score.
    abstract public int evaluateBoard();

    // Abstract method to return the current layout of the board as a 2D string array.
    abstract public String[][] getBoardLayout(); 

    // This method implements the Minimax algorithm with Alpha-Beta pruning for optimizing decision making.
    // It determines the best possible move for the current player (either maximizing or minimizing).
    public int[] minimax(boolean isMaximizing, int depth, int alpha, int beta) {
        // Initialize the best value based on whether the current player is maximizing or minimizing.
        int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestMove = -1; // -1 indicates no valid move initially.

        // Set the symbol ('X' or 'O') based on the player's turn.
        String symbol = isMaximizing ? "X" : "O";
        
        // If the game is over or we've reached the maximum depth, return the evaluated board score and no move.
        if (gameIsOver() || depth == 0) return new int[] { evaluateBoard(), bestMove };
        
        // Get all the available moves that can be made.
        ArrayList<Integer> moves = availableMoves();
        // Shuffle the moves randomly to add unpredictability (optional).
        Collections.shuffle(moves);
        
        // Loop through each possible move.
        for (Integer move : moves) {
            // Clone the current board state to simulate making a move.
            Board newBoard = cloneBoard();
            // Set the symbol ('X' or 'O') on the new board at the current move.
            newBoard.setSpace(move, symbol);
    
            // Recursively call the minimax function to evaluate the board after this hypothetical move.
            int hypotheticalValue = newBoard.minimax(!isMaximizing, depth - 1, alpha, beta)[0];
    
            // Maximizing player's turn: Try to find the maximum score.
            if (isMaximizing) {
                if (hypotheticalValue > bestValue) {
                    bestValue = hypotheticalValue;
                    bestMove = move; // Update the best move if this move has a higher score.
                }

                // Update alpha to be the best score found so far.
                alpha = Math.max(alpha, bestValue);
            } 
            // Minimizing player's turn: Try to find the minimum score.
            else {
                if (hypotheticalValue < bestValue) {
                    bestValue = hypotheticalValue;
                    bestMove = move; // Update the best move if this move has a lower score.
                }

                // Update beta to be the best score found so far.
                beta = Math.min(beta, bestValue);
            }
    
            // Alpha-Beta pruning: If beta is less than or equal to alpha, stop further exploration of this branch.
            if (beta <= alpha) break;
        }
    
        // Return the best score and the corresponding best move.
        return new int[] { bestValue, bestMove };
    }
}
