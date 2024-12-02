import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    private Board board;
    private JFrame frame;
    private JButton[][] buttons;
    private String currentPlayer;
    private boolean isAIPlayer;
    
    public TicTacToeGame() {
        board = new Board();
        buttons = new JButton[3][3];
        currentPlayer = "X";
        
        // Ask for game mode (Player vs Player or Player vs AI)
        isAIPlayer = askForGameMode();
        
        // Create the main frame for the game
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));
        
        // Initialize the buttons for the Tic Tac Toe grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setBackground(Color.WHITE);
                
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onCellClicked(r, c);
                    }
                });
                
                frame.add(buttons[row][col]);
            }
        }
        
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
    
    private boolean askForGameMode() {
        Object[] options = {"Player vs Player", "Player vs AI"};
        int choice = JOptionPane.showOptionDialog(frame, 
                "Choose Game Mode", 
                "Game Mode Selection", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, options, options[0]);
        return choice == 1;  // If the user chooses "Player vs AI", return true
    }

    private void onCellClicked(int row, int col) {
        int space = row * 3 + col + 1;
        
        if (board.setSpace(space, currentPlayer)) {
            buttons[row][col].setText(currentPlayer);
            
            if (board.hasWon(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, currentPlayer + " Wins!");
                resetGame();
            } else if (board.gameIsOver()) {
                JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
                resetGame();
            } else {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X"; // Switch players
                
                // If playing against AI, let AI make a move
                if (isAIPlayer && currentPlayer.equals("O")) {
                    aiMove();
                }
            }
        }
    }

    private void aiMove() {
        int depth = 10; // AI search depth
        int[] result = board.minimax(true, depth);  // AI plays as 'X'
        int bestMove = result[1];
        int[] spot = board.getSpotOnBoard(bestMove);

        // Make AI move
        board.setSpace(bestMove, "O");
        buttons[spot[0]][spot[1]].setText("O");

        // Check after AI move
        if (board.hasWon("O")) {
            JOptionPane.showMessageDialog(frame, "AI Wins!");
            resetGame();
        } else if (board.gameIsOver()) {
            JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
            resetGame();
        } else {
            currentPlayer = "X"; // Switch back to player X
        }
    }

    private void resetGame() {
        board = new Board();
        currentPlayer = "X"; // Reset to player "X"
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }

        // If playing against AI, AI starts first
        if (isAIPlayer) {
            aiMove();
        }
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}
