import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConnectFourGame {
    // Declare class-level variables
    private JFrame frame;                   // The main window for the game
    private JPanel modePanel;               // Panel for selecting game mode (Player vs Player or Player vs AI)
    private JButton playerButton;           // Button for starting Player vs Player game
    private JButton aiButton;               // Button for starting Player vs AI game
    private ConnectFourBoard board;         // Object that manages the game board logic
    private JButton[][] buttons;            // 2D array to represent the buttons for the board grid
    private String currentPlayer;           // The current player ("X" or "O")
    private int xWins;                      // Counter for Player X wins
    private int oWins;                      // Counter for Player O wins
    private int draws;                      // Counter for number of draws
    private JLabel scoreLabel;              // Label to display the score
    private String gameMode;                // The selected game mode ("Player" or "AI")
    private int aiDifficulty;               // Difficulty level for AI

    // Constructor to initialize the game window
    public ConnectFourGame(Point location) {
        frame = new JFrame("Connect Four");            // Create the main game frame with title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the game when the window is closed
        frame.setLayout(new BorderLayout());           // Use BorderLayout for layout management
        frame.setLocation(location);                   // Set the location of the window (from passed Point)

        // Panel to select the game mode
        modePanel = new JPanel(new GridLayout(1, 2));
        playerButton = new JButton("Player vs Player");    // Button to start Player vs Player game
        aiButton = new JButton("Player vs AI");            // Button to start Player vs AI game

        // Add ActionListener for Player vs Player button
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";                     // Set game mode to "Player"
                initializeGame();                        // Start the game
            }
        });

        // Add ActionListener for Player vs AI button
        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";                        // Set game mode to "AI"
                promptAIDifficulty();                    // Ask the user to choose AI difficulty
            }
        });

        // Add buttons to the mode selection panel
        modePanel.add(playerButton);
        modePanel.add(aiButton);

        // Add the mode selection panel to the frame and display the window
        frame.add(modePanel, BorderLayout.CENTER);
        frame.setSize(400, 200);                           // Set the initial size of the frame
        frame.setVisible(true);
    }

    // Prompt the user to choose AI difficulty (between 1 and 10)
    private void promptAIDifficulty() {
        String input = JOptionPane.showInputDialog(frame, "Enter AI difficulty (1-10):");
        if (input == null) {
            // User pressed cancel, return to the main menu
            resetToMenu();
            return;
        }
        try {
            aiDifficulty = Integer.parseInt(input);         // Convert input to integer
            if (aiDifficulty < 1 || aiDifficulty > 10) {    // Validate the difficulty
                JOptionPane.showMessageDialog(frame, "Please enter a number between 1 and 10.");
                promptAIDifficulty();                      // Prompt again if invalid
            } else {
                initializeGame();                          // Start the game with the selected difficulty
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number between 1 and 10.");
            promptAIDifficulty();                          // Prompt again if the input is not a valid number
        }
    }

    // Initialize the game after the mode has been selected
    private void initializeGame() {
        board = new ConnectFourBoard();                  // Initialize the game board
        buttons = new JButton[6][7];                     // 6 rows, 7 columns
        currentPlayer = "X";                             // Player X starts the game

        // Create the main game window by clearing previous components
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Create a label to display the current score (wins and draws)
        scoreLabel = new JLabel("X Wins: " + xWins + "   O Wins: " + oWins + "   Draws: " + draws, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add score label to the top of the frame
        frame.add(scoreLabel, BorderLayout.NORTH);

        // Create a new panel for the Connect Four grid
        JPanel gamePanel = new JPanel(new GridLayout(6, 7));  // 6 rows, 7 columns

        // Initialize the buttons for the grid
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));  // Large font for button text
                buttons[row][col].setFocusPainted(false);                      // Remove focus border
                // Add action listener to handle button clicks (user's moves)
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton buttonClicked = (JButton) e.getSource();
                        int col = getButtonColumn(buttonClicked);    // Get the column index of the clicked button
                        if (gameMode.equals("Player")) {
                            handlePlayerMove(col);                    // Handle player move in PvP mode
                        } else if (gameMode.equals("AI")) {
                            handlePlayerMove(col);                    // Handle player move in PvAI mode
                            handleAIMove();                            // AI makes its move after player's
                        }
                    }
                });
                gamePanel.add(buttons[row][col]);  // Add each button to the game panel
            }
        }

        // Add the game grid panel to the frame
        frame.add(gamePanel, BorderLayout.CENTER);

        // Resize the frame to make it suitable for the game grid
        frame.setSize(700, 600);  // Increase window size to accommodate the grid
        frame.setVisible(true);

        // Add a button to return to the main menu
        JButton backToMenuButton = new JButton("Back to Main Menu");
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetToMenu();  // Go back to the main menu when clicked
            }
        });
        frame.add(backToMenuButton, BorderLayout.SOUTH);
    }

    // Reset the game and show the main menu again
    private void resetToMenu() {
        // Clear game components and show the mode selection panel again
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Reset the scores and game state
        xWins = 0;
        oWins = 0;
        draws = 0;

        // Create the mode selection panel again
        JPanel modePanel = new JPanel(new GridLayout(1, 2));
        JButton playerButton = new JButton("Player vs Player");
        JButton aiButton = new JButton("Player vs AI");

        // Action listeners for the buttons
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "Player";          // Set mode to Player vs Player
                initializeGame();             // Start the game
            }
        });

        aiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = "AI";             // Set mode to Player vs AI
                promptAIDifficulty();         // Ask for AI difficulty level
            }
        });

        modePanel.add(playerButton);
        modePanel.add(aiButton);

        frame.add(modePanel, BorderLayout.CENTER);
        frame.setSize(400, 200);  // Set size for the main menu window
        frame.setVisible(true);

        // Dispose of the current window and show the GameMenu screen again
        frame.dispose();
        new GameMenu(frame.getLocation());
    }

    // Helper method to get the column index of the button clicked
    private int getButtonColumn(JButton button) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (buttons[row][col] == button) {
                    return col;  // Return the column index of the clicked button
                }
            }
        }
        return -1;  // Return -1 if no match is found
    }

    // Handle player's move in both modes
    private void handlePlayerMove(int col) {
        if (board.setSpace(col, currentPlayer)) {    // Try to place the player's move
            updateBoardUI();                         // Update the UI with the new board state
            if (board.hasWon(currentPlayer)) {       // Check if the player has won
                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                updateScore(currentPlayer);         // Update the score for the winner
                resetGame();                        // Reset the game
            } else if (board.gameIsOver()) {         // Check if the game is a draw
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                draws++;                             // Increment draw counter
                updateScoreDisplay();               // Update the score display
                resetGame();                        // Reset the game
            } else {
                // Switch to the other player
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            }
        }
    }

    // Handle AI's move in Player vs AI mode
    private void handleAIMove() {
        // Use the minimax algorithm to calculate the best move for the AI
        int[] bestMove = board.minimax(false, aiDifficulty, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int bestMovePosition = bestMove[1];
        if (bestMovePosition != -1) {
            board.setSpace(bestMovePosition, "O"); // Make the AI move
            updateBoardUI();                       // Update the UI with the new board state
            if (board.hasWon("O")) {               // Check if the AI has won
                JOptionPane.showMessageDialog(frame, "AI wins!");
                updateScore("O");                  // Update the score for AI
                resetGame();                       // Reset the game
            } else if (board.gameIsOver()) {       // Check if the game is a draw
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                draws++;                            // Increment draw counter
                updateScoreDisplay();              // Update the score display
                resetGame();                       // Reset the game
            } else {
                currentPlayer = "X";  // Switch back to player X
            }
        }
    }

    // Update the UI to reflect the current state of the game board
    private void updateBoardUI() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setText(board.getBoardLayout()[row][col]);  // Set button text to board's value
            }
        }
    }

    // Reset the game board and start over
    private void resetGame() {
        board = new ConnectFourBoard();  // Create a new board
        currentPlayer = "X";             // Reset to player X

        // Clear the board UI
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setText(" ");  // Reset all button texts to empty
            }
        }
    }

    // Update the score based on the winner
    private void updateScore(String winner) {
        if (winner.equals("X")) {
            xWins++;  // Increment X wins if X wins
        } else if (winner.equals("O")) {
            oWins++;  // Increment O wins if O wins
        }
        updateScoreDisplay();  // Update the displayed score
    }

    // Update the displayed score
    private void updateScoreDisplay() {
        scoreLabel.setText("X Wins: " + xWins + "   O Wins: " + oWins + "   Draws: " + draws);
    }
}
