import java.util.ArrayList;

public class ConnectFourBoard extends Board {

    // 2D array to represent the Connect Four board (6 rows x 7 columns)
    private String[][] board = {
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "}
    };

    // Default constructor
    public ConnectFourBoard() {}

    // Private constructor used to create a deep copy of the board (cloning it)
    private ConnectFourBoard(String[][] board) {
        String[][] filledBoard = new String[6][7];
        // Copy each element of the original board to the new board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                filledBoard[i][j] = board[i][j];
            }
        }
        this.board = filledBoard; // Assign the copied board to the current board
    }

    // Method to return the current layout of the board (2D array)
    public String[][] getBoardLayout() {
        return board;
    }

    // Method to create and return a clone (deep copy) of the current board
    public Board cloneBoard() {
        return new ConnectFourBoard(board);
    }

    // Helper method to find the available row for a given column.
    // The row is where the next piece can be placed in the column.
    private int getAvailableRow(int col) {
        // Start checking from the bottom-most row (row 5)
        for (int row = 5; row >= 0; row--) {
            if (board[row][col].equals(" ")) { // If the space is empty
                return row; // Return the row index where the piece can be placed
            }
        }
        return -1; // If no space is available in the column, return -1
    }

    // Method to get a list of all available moves (columns with open spots)
    public ArrayList<Integer> availableMoves() {
        ArrayList<Integer> movesList = new ArrayList<>();
        // Check each column from 0 to 6
        for (int col = 0; col < 7; col++) {
            // If there is an available row in the column, add the column index to the list
            if (getAvailableRow(col) != -1) {
                movesList.add(col);
            }
        }
        return movesList;
    }

    // Method to convert a linear space index (0-41) into row and column coordinates
    public int[] getSpotOnBoard(int space) {
        int row = space / 7; // Row is determined by integer division of space by 7
        int col = space % 7; // Column is the remainder of space divided by 7
        return new int[] { row, col }; // Return the row and column as an array
    }

    // Method to place a player's piece (either "X" or "O") in a specified column
    public boolean setSpace(int col, String player) {
        int row = getAvailableRow(col); // Get the available row in the column
        if (row == -1) return false; // If no space is available, return false
        board[row][col] = player; // Place the player's piece in the board at the available row and column
        return true; // Return true indicating the move was successful
    }

    // Method to check if a player has won by looking for four consecutive pieces
    public boolean hasWon(String player) {
        // Check horizontal wins (four in a row in any row)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                // If four consecutive pieces in a row are the same player, return true
                if (board[row][col].equals(player) && board[row][col + 1].equals(player)
                        && board[row][col + 2].equals(player) && board[row][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        // Check vertical wins (four in a column)
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                // If four consecutive pieces in a column are the same player, return true
                if (board[row][col].equals(player) && board[row + 1][col].equals(player)
                        && board[row + 2][col].equals(player) && board[row + 3][col].equals(player)) {
                    return true;
                }
            }
        }

        // Check diagonal wins (top-left to bottom-right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                // If four consecutive pieces diagonally from top-left to bottom-right are the same player, return true
                if (board[row][col].equals(player) && board[row + 1][col + 1].equals(player)
                        && board[row + 2][col + 2].equals(player) && board[row + 3][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        // Check diagonal wins (bottom-left to top-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                // If four consecutive pieces diagonally from bottom-left to top-right are the same player, return true
                if (board[row][col].equals(player) && board[row - 1][col + 1].equals(player)
                        && board[row - 2][col + 2].equals(player) && board[row - 3][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        return false; // Return false if no win condition is met
    }

    // Method to check if the game is over (either by a win or a draw)
    public boolean gameIsOver() {
        return availableMoves().size() == 0 || hasWon("X") || hasWon("O"); // Game is over if no available moves or if either player has won
    }

    // Method to evaluate the board and return a score
    // +1 for "X" win, -1 for "O" win, and 0 for a draw or undecided game
    public int evaluateBoard() {
        if (hasWon("X")) return 1; // If "X" wins, return 1
        else if (hasWon("O")) return -1; // If "O" wins, return -1
        else return 0; // Return 0 if the game is undecided or a draw
    }
}
