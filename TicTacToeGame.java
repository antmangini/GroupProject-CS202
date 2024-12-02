import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    private TicTacToeBoard board;
    private JFrame frame;
    private JButton[][] buttons;
    private String currentPlayer;
    private int xWins, oWins, draws;
    private JLabel scoreLabel;
    private String gameMode;  // "Player vs Player" or "Player vs AI"
    private int aiDifficulty; // AI difficulty level (1 to 10)

    public TicTacToeGame() {
        // Initial setup of the frame and components
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Use BorderLayout to arrange components

        // Create a panel for the game mode selection buttons
        JPanel modePanel = new JPanel(new GridLayout(1, 2));
        
        JButton playerButton = new JButton("Player vs Player");
        JButton aiButton = new JButton("Player vs AI");

        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";
                initializeGame(); // Start the game
            }
        });

        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";
                promptAIDifficulty(); // Ask for AI difficulty
            }
        });

        modePanel.add(playerButton);
        modePanel.add(aiButton);

        // Add the mode selection panel to the frame
        frame.add(modePanel, BorderLayout.CENTER);

        frame.setSize(400, 200); // Set size for mode selection window
        frame.setVisible(true);
    }

    private void promptAIDifficulty() {
        // Prompt the user for AI difficulty (1 to 10)
        String input = JOptionPane.showInputDialog(frame, 
                "Select AI difficulty (1 - Easy, 10 - Hard):", 
                "AI Difficulty", 
                JOptionPane.QUESTION_MESSAGE);
    
        // Check if the user cancelled or closed the dialog (input is null)
        if (input == null) {
            resetToMenu(); // Return to main menu if the user cancels
            return;
        }
    
        try {
            aiDifficulty = 11 - Integer.parseInt(input);
            if (aiDifficulty < 1 || aiDifficulty > 10) {
                JOptionPane.showMessageDialog(frame, "Please enter a number between 1 and 10.");
                promptAIDifficulty(); // Prompt again if the value is invalid
            } else {
                initializeGame(); // Start the game with the selected difficulty
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number between 1 and 10.");
            promptAIDifficulty(); // Prompt again in case of invalid input
        }
    }    

    private void initializeGame() {
        // Initialize game components after the mode has been selected
        board = new TicTacToeBoard();
        buttons = new JButton[3][3];
        currentPlayer = "X"; // Player X starts
        xWins = 0;
        oWins = 0;
        draws = 0;

        // Create the main game frame for Tic Tac Toe
        frame.getContentPane().removeAll(); // Clear previous components
        frame.setLayout(new BorderLayout());

        // Create the score label
        scoreLabel = new JLabel("X Wins: 0   O Wins: 0   Draws: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add the score label to the frame
        frame.add(scoreLabel, BorderLayout.NORTH);

        // Create the panel to hold the Tic Tac Toe buttons
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));

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
                
                gamePanel.add(buttons[row][col]);
            }
        }

        // Add the game panel to the frame
        frame.add(gamePanel, BorderLayout.CENTER);

        // Resize window for the game
        frame.setSize(500, 500); // Increase window size
        frame.setVisible(true);

        // Add "Back to Main Menu" button
        JButton backToMenuButton = new JButton("Back to Main Menu");
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetToMenu();
            }
        });

        frame.add(backToMenuButton, BorderLayout.SOUTH);

        // Start the game loop if AI is selected and it's AI's turn
        if (gameMode.equalsIgnoreCase("AI") && currentPlayer.equals("O")) {
            aiMove(); // AI's move
        }
    }

    private void resetToMenu() {
        // Clear the game components and show the initial mode selection screen
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel modePanel = new JPanel(new GridLayout(1, 2));
        
        JButton playerButton = new JButton("Player vs Player");
        JButton aiButton = new JButton("Player vs AI");

        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";
                initializeGame(); // Start the game
            }
        });

        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";
                promptAIDifficulty(); // Ask for AI difficulty
            }
        });

        modePanel.add(playerButton);
        modePanel.add(aiButton);

        frame.add(modePanel, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    private void onCellClicked(int row, int col) {
        int space = row * 3 + col + 1;

        if (board.setSpace(space, currentPlayer)) {
            buttons[row][col].setText(currentPlayer);

            if (board.hasWon(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, currentPlayer + " Wins!");
                updateScore(currentPlayer);
                resetGame();
            } else if (board.gameIsOver()) {
                JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
                draws++;
                updateScoreDisplay();
                resetGame();
            } else {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X"; // Switch players
                if (gameMode.equalsIgnoreCase("AI") && currentPlayer.equals("O")) {
                    aiMove(); // AI makes its move if it's "O"'s turn
                }
            }
        }
    }

    private void aiMove() {
        // Make AI move (Minimax algorithm)
        int[] bestMove = board.minimax(true, aiDifficulty); // AI plays as "O", use the difficulty level for depth
        int bestMovePosition = bestMove[1];
        int[] rowAndCol = board.getSpotOnBoard(bestMovePosition);
        int row = rowAndCol[0];
        int col = rowAndCol[1];
    
        // Perform the AI move
        board.setSpace(bestMovePosition, "O");
        buttons[row][col].setText("O");
    
        // Check if the AI won or if the game is over
        if (board.hasWon("O")) {
            JOptionPane.showMessageDialog(frame, "O Wins!");
            updateScore("O");
            resetGame();
        } else if (board.gameIsOver()) {
            JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
            draws++;
            updateScoreDisplay();
            resetGame();
        } else {
            currentPlayer = "X"; // Switch back to player X
        }
    }    

    private void resetGame() {
        board = new TicTacToeBoard();
        currentPlayer = "X"; // Reset to player "X"

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }
    }

    private void updateScore(String winner) {
        if (winner.equals("X")) {
            xWins++;
        } else if (winner.equals("O")) {
            oWins++;
        }
        updateScoreDisplay();
    }

    private void updateScoreDisplay() {
        scoreLabel.setText("X Wins: " + xWins + "   O Wins: " + oWins + "   Draws: " + draws);
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}
