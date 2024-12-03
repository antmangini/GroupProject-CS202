import java.util.ArrayList;

public class TicTacToeBoard extends Board {

    private String[][] board = {{" ", " ", " "},
                                 {" ", " ", " "},
                                 {" ", " ", " "}};
    
    public TicTacToeBoard() {}
    
    private TicTacToeBoard(String[][] board) {
        String[][] filledBoard = {{" ", " ", " "},
                                  {" ", " ", " "},
                                  {" ", " ", " "}};
        
        for (int row = 0; row < filledBoard.length; row++) {
            for (int col = 0; col < filledBoard[row].length; col++)
                filledBoard[row][col] = board[row][col];
        }
        this.board = filledBoard;
    }

    public String[][] getBoardLayout() {
        return board;
    }

    public Board cloneBoard() {
        return new TicTacToeBoard(board);
    }

    public ArrayList<Integer> availableMoves() {
        ArrayList<Integer> movesList = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].equals(" ")) {
                    movesList.add(row * 3 + col + 1);  // Store move in 1-indexed format
                }
            }
        }

        return movesList;
    }

    public int[] getSpotOnBoard(int space) {
        int row = (space - 1) / 3;
        int col = (space - 1) % 3;
        return new int[] { row, col };
    }

    public boolean setSpace(int space, String player) {
        int[] rowAndCol = getSpotOnBoard(space);
        int row = rowAndCol[0];
        int col = rowAndCol[1];

        if (!board[row][col].equals(" ")) return false;
        else board[row][col] = player;

        return true;
    }

    public boolean hasWon(String player) {
        // Check rows, columns, and diagonals for a win
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) return true;
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) return true;
        }

        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) return true;
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) return true;

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
