import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConnectFourGame {
    private JFrame frame;
    private JPanel modePanel;
    private JButton playerButton;
    private JButton aiButton;
    private ConnectFourBoard board;
    private JButton[][] buttons;
    private String currentPlayer;
    private int xWins;
    private int oWins;
    private int draws;
    private JLabel scoreLabel;
    private String gameMode;
    private int aiDifficulty;

    public ConnectFourGame(Point location) {
        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(location); // Set the location of the new frame

        modePanel = new JPanel(new GridLayout(1, 2));
        playerButton = new JButton("Player vs Player");
        aiButton = new JButton("Player vs AI");

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
        String input = JOptionPane.showInputDialog(frame, "Enter AI difficulty (1-10):");
        if (input == null) {
            // User pressed cancel, return to the main menu
            resetToMenu();
            return;
        }
        try {
            aiDifficulty = Integer.parseInt(input);
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
        board = new ConnectFourBoard();
        buttons = new JButton[6][7];
        currentPlayer = "X"; // Player X starts

        // Create the main game frame for Connect Four
        frame.getContentPane().removeAll(); // Clear previous components
        frame.setLayout(new BorderLayout());

        // Create the score label
        scoreLabel = new JLabel("X Wins: " + xWins + "   O Wins: " + oWins + "   Draws: " + draws, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add the score label to the frame
        frame.add(scoreLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(6, 7));

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton buttonClicked = (JButton) e.getSource();
                        int col = getButtonColumn(buttonClicked);
                        if (gameMode.equals("Player")) {
                            handlePlayerMove(col);
                        } else if (gameMode.equals("AI")) {
                            handlePlayerMove(col);
                            handleAIMove();
                        }
                    }
                });
                gamePanel.add(buttons[row][col]);
            }
        }

        frame.add(gamePanel, BorderLayout.CENTER);

        // Resize window for the game
        frame.setSize(700, 600); // Increase window size
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
    }

    private void resetToMenu() {
        // Clear the game components and show the initial mode selection screen
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Reset scores and game state
        xWins = 0;
        oWins = 0;
        draws = 0;

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
        frame.setSize(400, 200); // Set size for mode selection window
        frame.setVisible(true);

        // Return to main menu
        frame.dispose();
        new GameMenu(frame.getLocation());
    }

    private int getButtonColumn(JButton button) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (buttons[row][col] == button) {
                    return col;
                }
            }
        }
        return -1;
    }

    private void handlePlayerMove(int col) {
        if (board.setSpace(col, currentPlayer)) {
            updateBoardUI();
            if (board.hasWon(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                updateScore(currentPlayer);
                resetGame(); // Reset the game
            } else if (board.gameIsOver()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                draws++;
                updateScoreDisplay();
                resetGame(); // Reset the game
            } else {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            }
        }
    }

    private void handleAIMove() {
        // Use minimax to determine the best move for the AI
        int[] bestMove = board.minimax(false, aiDifficulty, Integer.MIN_VALUE, Integer.MAX_VALUE); // AI plays as "O", use the difficulty level for depth
        int bestMovePosition = bestMove[1];
        if (bestMovePosition != -1) {
            board.setSpace(bestMovePosition, "O");
            updateBoardUI();
            if (board.hasWon("O")) {
                JOptionPane.showMessageDialog(frame, "AI wins!");
                updateScore("O");
                resetGame(); // Reset the game
            } else if (board.gameIsOver()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                draws++;
                updateScoreDisplay();
                resetGame(); // Reset the game
            } else {
                currentPlayer = "X"; // Switch back to player X
            }
        }
    }

    private void updateBoardUI() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col].setText(board.getBoardLayout()[row][col]);
            }
        }
    }

    private void resetGame() {
        board = new ConnectFourBoard();
        currentPlayer = "X"; // Reset to player "X"

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
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
}
