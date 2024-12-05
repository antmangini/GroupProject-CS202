import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    private TicTacToeBoard board;  // Game board (model) for TicTacToe
    private JFrame frame;          // Main window frame for the game
    private JButton[][] buttons;   // Buttons representing the TicTacToe grid
    private String currentPlayer;  // Holds the current player ("X" or "O")
    private int xWins, oWins, draws; // Tracking wins and draws for both players
    private JLabel scoreLabel;     // Label to display the current score
    private String gameMode;      // Game mode: "Player vs Player" or "Player vs AI"
    private int aiDifficulty;     // AI difficulty level (1 to 10)

    public TicTacToeGame(Point location) {
        // Initial setup for the game frame and components
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Use BorderLayout to arrange components
        frame.setLocation(location); // Set the window location on screen

        // Panel for game mode selection
        JPanel modePanel = new JPanel(new GridLayout(1, 2));
        
        JButton playerButton = new JButton("Player vs Player");
        JButton aiButton = new JButton("Player vs AI");

        // Action listeners for buttons to select game mode
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";  // Set game mode to Player vs Player
                initializeGame();     // Initialize the game
            }
        });

        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";  // Set game mode to Player vs AI
                promptAIDifficulty(); // Ask the user to choose AI difficulty
            }
        });

        // Add the mode selection buttons to the panel
        modePanel.add(playerButton);
        modePanel.add(aiButton);

        // Add the panel to the frame
        frame.add(modePanel, BorderLayout.CENTER);

        frame.setSize(400, 200);  // Set the size of the initial window
        frame.setVisible(true);    // Make the frame visible
    }

    private void promptAIDifficulty() {
        // Prompt the user for the difficulty level for the AI (1 - 10)
        String input = JOptionPane.showInputDialog(frame, 
                "Select AI difficulty (1 - Easy, 10 - Hard):", 
                "AI Difficulty", 
                JOptionPane.QUESTION_MESSAGE);
    
        // If user cancels or closes the input dialog, return to main menu
        if (input == null) {
            resetToMenu();
            return;
        }
    
        try {
            aiDifficulty = Integer.parseInt(input);
            // Ensure the input is within valid range (1-10)
            if (aiDifficulty < 1 || aiDifficulty > 10) {
                JOptionPane.showMessageDialog(frame, "Please enter a number between 1 and 10.");
                promptAIDifficulty(); // Prompt again if invalid input
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
        buttons = new JButton[3][3];  // Initialize the 3x3 grid of buttons
        currentPlayer = "X";  // Player X starts the game
        xWins = 0;
        oWins = 0;
        draws = 0;

        // Clear previous game components and set up the main game frame
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Create the score label and set its font
        scoreLabel = new JLabel("X Wins: 0   O Wins: 0   Draws: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add the score label to the top of the frame
        frame.add(scoreLabel, BorderLayout.NORTH);

        // Panel for the TicTacToe grid
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));

        // Initialize the buttons for the TicTacToe grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");  // Empty cell initially
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));  // Large font
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setBackground(Color.WHITE);

                // Add ActionListener for each button to handle click events
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onCellClicked(r, c);
                    }
                });

                // Add the button to the game panel
                gamePanel.add(buttons[row][col]);
            }
        }

        // Add the game panel to the frame
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setSize(500, 500);  // Increase the window size for the game
        frame.setVisible(true);    // Make the game window visible

        // Add "Back to Main Menu" button to allow returning to the mode selection screen
        JButton backToMenuButton = new JButton("Back to Main Menu");
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetToMenu();
            }
        });

        frame.add(backToMenuButton, BorderLayout.SOUTH);

        // Start the game loop if AI mode is selected and it's AI's turn
        if (gameMode.equalsIgnoreCase("AI") && currentPlayer.equals("O")) {
            aiMove(); // Let the AI make its move
        }
    }

    private void resetToMenu() {
        // Reset the game to the main menu
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Create the mode selection panel again
        JPanel modePanel = new JPanel(new GridLayout(1, 2));
        
        JButton playerButton = new JButton("Player vs Player");
        JButton aiButton = new JButton("Player vs AI");

        // Re-add listeners to restart the game in the selected mode
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";
                initializeGame();  // Start the game
            }
        });

        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";
                promptAIDifficulty();  // Ask for AI difficulty
            }
        });

        modePanel.add(playerButton);
        modePanel.add(aiButton);

        frame.add(modePanel, BorderLayout.CENTER);
        frame.setSize(400, 200);
        frame.setVisible(true);

        // Dispose of the current frame and create a new GameMenu instance
        frame.dispose();
        new GameMenu(frame.getLocation());
    }

    private void onCellClicked(int row, int col) {
        int space = row * 3 + col + 1;

        // Set the space on the board for the current player
        if (board.setSpace(space, currentPlayer)) {
            buttons[row][col].setText(currentPlayer);

            // Check if the current player has won
            if (board.hasWon(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, currentPlayer + " Wins!");
                updateScore(currentPlayer);  // Update the score based on the winner
                resetGame();  // Reset the game for a new round
            } else if (board.gameIsOver()) {
                // If no winner and no moves left, it's a draw
                JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
                draws++;
                updateScoreDisplay();
                resetGame();  // Reset the game
            } else {
                // Switch to the other player
                currentPlayer = currentPlayer.equals("X") ? "O" : "X"; 
                if (gameMode.equalsIgnoreCase("AI") && currentPlayer.equals("O")) {
                    aiMove(); // Let AI make its move if it's AI's turn
                }
            }
        }
    }

    private void aiMove() {
        // Perform AI's move using the Minimax algorithm
        int[] bestMove = board.minimax(false, aiDifficulty, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int bestMovePosition = bestMove[1];
        int[] rowAndCol = board.getSpotOnBoard(bestMovePosition);
        int row = rowAndCol[0];
        int col = rowAndCol[1];
    
        // Perform the move and update the button text
        board.setSpace(bestMovePosition, "O");
        buttons[row][col].setText("O");
    
        // Check if the AI won or if it's a draw
        if (board.hasWon("O")) {
            JOptionPane.showMessageDialog(frame, "O Wins!");
            updateScore("O");
            resetGame();  // Reset the game
        } else if (board.gameIsOver()) {
            JOptionPane.showMessageDialog(frame, "Game Over! It's a draw.");
            draws++;
            updateScoreDisplay();
            resetGame();  // Reset the game
        } else {
            // Switch back to player X's turn
            currentPlayer = "X"; 
        }
    }

    private void resetGame() {
        // Reset the board and buttons for a new round
        board = new TicTacToeBoard();
        currentPlayer = "X";  // Reset to player "X"

        // Clear the button texts
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }
    }

    private void updateScore(String winner) {
        // Update the score based on the winner
        if (winner.equals("X")) {
            xWins++;
        } else if (winner.equals("O")) {
            oWins++;
        }
        updateScoreDisplay();  // Update the displayed score
    }

    private void updateScoreDisplay() {
        // Update the score label to show current scores
        scoreLabel.setText("X Wins: " + xWins + "   O Wins: " + oWins + "   Draws: " + draws);
    }
}
