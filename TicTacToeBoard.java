import java.util.ArrayList;

public class TicTacToeBoard extends Board {
    // A 2D array representing the Tic-Tac-Toe board, initially empty
    private String[][] board = {{" ", " ", " "},  // Row 1
                                 {" ", " ", " "},  // Row 2
                                 {" ", " ", " "}};  // Row 3
    
    // Default constructor, initializes an empty board
    public TicTacToeBoard() {}
    
    // Private constructor to create a new TicTacToeBoard from an existing board layout
    private TicTacToeBoard(String[][] board) {
        // Create a fresh empty board to start with
        String[][] filledBoard = {{" ", " ", " "},
                                  {" ", " ", " "},
                                  {" ", " ", " "}};
        
        // Copy the state of the provided board to the new filledBoard
        for (int row = 0; row < filledBoard.length; row++) {
            for (int col = 0; col < filledBoard[row].length; col++)
                filledBoard[row][col] = board[row][col];
        }
        this.board = filledBoard;  // Assign the copied board to the class variable
    }

    // Getter method to return the current board layout (2D array of Strings)
    public String[][] getBoardLayout() {
        return board;
    }

    // Create and return a new TicTacToeBoard object that is a clone of the current board
    public Board cloneBoard() {
        return new TicTacToeBoard(board);  // Clone the board by passing the current state to the constructor
    }

    // Returns a list of available moves on the board
    // An available move is any empty space (' ') on the board
    public ArrayList<Integer> availableMoves() {
        ArrayList<Integer> movesList = new ArrayList<>();  // List to hold available move positions

        // Traverse the board to find empty spaces and add them to the moves list
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].equals(" ")) {
                    // Store the move as a 1-indexed position (1-9) instead of 0-indexed
                    movesList.add(row * 3 + col + 1);
                }
            }
        }

        return movesList;  // Return the list of available moves
    }

    // Converts a 1-indexed move (1-9) to a row and column in the 2D board array
    public int[] getSpotOnBoard(int space) {
        int row = (space - 1) / 3;  // Row is determined by integer division of the move index by 3
        int col = (space - 1) % 3;  // Column is the remainder when the move index is divided by 3
        return new int[] { row, col };  // Return the row and column as an array
    }

    // Attempts to place the player's symbol ('X' or 'O') on the board at the given move position
    public boolean setSpace(int space, String player) {
        int[] rowAndCol = getSpotOnBoard(space);  // Get the row and column for the move
        int row = rowAndCol[0];
        int col = rowAndCol[1];

        // Check if the space is already occupied; if so, return false (invalid move)
        if (!board[row][col].equals(" ")) return false;
        else board[row][col] = player;  // Set the space to the player's symbol

        return true;  // Successfully set the space, return true
    }

    // Checks if the given player has won the game
    public boolean hasWon(String player) {
        // Check all rows for a win (three same symbols in a row)
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;  // Found a winning row
            }
        }

        // Check all columns for a win (three same symbols in a column)
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;  // Found a winning column
            }
        }

        // Check both diagonals for a win (three same symbols in a diagonal)
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;  // Found a winning diagonal (top-left to bottom-right)
        }
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;  // Found a winning diagonal (top-right to bottom-left)
        }

        return false;  // No win found for the player
    }

    // Checks if the game is over
    // The game ends if there are no available moves or if a player has won
    public boolean gameIsOver() {
        // Game is over if there are no available moves or either player has won
        return availableMoves().size() == 0 || hasWon("X") || hasWon("O");
    }

    // Evaluates the board's state for use in algorithms (such as minimax)
    // Returns 1 if player "X" has won, -1 if player "O" has won, and 0 for a draw or ongoing game
    public int evaluateBoard() {
        if (hasWon("X")) return 1;  // X has won
        else if (hasWon("O")) return -1;  // O has won
        else return 0;  // No winner, game is ongoing or a draw
    }
}
