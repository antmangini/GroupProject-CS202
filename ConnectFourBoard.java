import java.util.ArrayList;
import java.util.Collections;

public class ConnectFourBoard extends Board {

    private String[][] board = {
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " "}
    };

    // Constructor
    public ConnectFourBoard() {}

    // Private constructor for cloning the board
    private ConnectFourBoard(String[][] board) {
        String[][] filledBoard = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                filledBoard[i][j] = board[i][j];
            }
        }
        this.board = filledBoard;
    }

    public String[][] getBoardLayout() {
        return board;
    }

    public Board cloneBoard() {
        return new ConnectFourBoard(board);
    }

    // Helper method to get the available row for a given column
    private int getAvailableRow(int col) {
        for (int row = 5; row >= 0; row--) {
            if (board[row][col].equals(" ")) {
                return row;
            }
        }
        return -1; // If no space is available in the column
    }

    public ArrayList<Integer> availableMoves() {
        ArrayList<Integer> movesList = new ArrayList<>();
        for (int col = 0; col < 7; col++) {
            if (getAvailableRow(col) != -1) {
                movesList.add(col);
            }
        }
        return movesList;
    }

    
    public int[] getSpotOnBoard(int space) {
        int row = space / 7;
        int col = space % 7;
        return new int[] { row, col };
    }

    
    public boolean setSpace(int col, String player) {
        int row = getAvailableRow(col);
        if (row == -1) return false; // No space available in the column
        board[row][col] = player;
        return true;
    }

    
    public boolean hasWon(String player) {
        // Check horizontal, vertical, and diagonal for a win
        // Check horizontal
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col].equals(player) && board[row][col + 1].equals(player)
                        && board[row][col + 2].equals(player) && board[row][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        // Check vertical
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (board[row][col].equals(player) && board[row + 1][col].equals(player)
                        && board[row + 2][col].equals(player) && board[row + 3][col].equals(player)) {
                    return true;
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col].equals(player) && board[row + 1][col + 1].equals(player)
                        && board[row + 2][col + 2].equals(player) && board[row + 3][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col].equals(player) && board[row - 1][col + 1].equals(player)
                        && board[row - 2][col + 2].equals(player) && board[row - 3][col + 3].equals(player)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean gameIsOver() {
        return availableMoves().size() == 0 || hasWon("X") || hasWon("O");
    }

    public int evaluateBoard() {
        if (hasWon("X")) return 1;
        else if (hasWon("O")) return -1;
        else return 0;
    }
}
