import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameMenu {
    private JFrame frame;

    // Constructor to initialize the game menu window and set its position.
    public GameMenu(Point location) {
        // Create a new JFrame for the game menu window and set its title.
        frame = new JFrame("Game Menu");
        
        // Ensure the program exits when the menu window is closed.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the layout of the frame to BorderLayout for positioning components.
        frame.setLayout(new BorderLayout());
        
        // Set the location of the window based on the passed Point object.
        frame.setLocation(location);

        // Create a JPanel to hold the buttons for the two games with a GridLayout of 1 row and 2 columns.
        JPanel menuPanel = new JPanel(new GridLayout(1, 2));
        
        // Create buttons for Tic Tac Toe and Connect Four games.
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton connectFourButton = new JButton("Connect Four");

        // Add an action listener to the Tic Tac Toe button. When clicked, it launches the Tic Tac Toe game.
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current location of the menu window.
                Point location = frame.getLocation();
                
                // Close the game menu window.
                frame.dispose();
                
                // Launch the Tic Tac Toe game, passing the same location as the menu window.
                new TicTacToeGame(location);
            }
        });

        // Add an action listener to the Connect Four button. When clicked, it launches the Connect Four game.
        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current location of the menu window.
                Point location = frame.getLocation();
                
                // Close the game menu window.
                frame.dispose();
                
                // Launch the Connect Four game, passing the same location as the menu window.
                new ConnectFourGame(location);
            }
        });

        // Add the Tic Tac Toe and Connect Four buttons to the panel.
        menuPanel.add(ticTacToeButton);
        menuPanel.add(connectFourButton);

        // Add the menu panel to the center of the frame.
        frame.add(menuPanel, BorderLayout.CENTER);

        // Set the size of the menu window to 400x200 pixels.
        frame.setSize(400, 200);
        
        // Make the frame visible so the user can interact with it.
        frame.setVisible(true);
    }

    // The main method that runs the application.
    public static void main(String[] args) {
        // Create a new GameMenu object, positioning it at (100, 100) on the screen.
        new GameMenu(new Point(100, 100));
    }
}
