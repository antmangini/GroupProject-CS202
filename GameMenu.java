import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameMenu {
    private JFrame frame;

    public GameMenu(Point location) {
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(location);

        JPanel menuPanel = new JPanel(new GridLayout(1, 2));
        
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton connectFourButton = new JButton("Connect Four");

        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point location = frame.getLocation(); // Get the location of the current frame
                frame.dispose(); // Close the menu window
                new TicTacToeGame(location); // Launch Tic Tac Toe game with the same location
            }
        });

        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point location = frame.getLocation(); // Get the location of the current frame
                frame.dispose(); // Close the menu window
                new ConnectFourGame(location); // Launch Connect Four game with the same location
            }
        });

        menuPanel.add(ticTacToeButton);
        menuPanel.add(connectFourButton);

        // Add the menu panel to the frame
        frame.add(menuPanel, BorderLayout.CENTER);

        frame.setSize(400, 200); // Set size for the menu window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameMenu(new Point(100, 100));
    }
}
