package Final_Project;

import javax.swing.*;

public class startMenuGUI extends JFrame{
    private JPanel mainPanel;
    private JButton StartButton;
    private JButton rulesButton;
    private JButton leaderboardButton;
    private JButton quitButton;


    public startMenuGUI(GamePlayGUI gamePlayGUI) {
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }
}
