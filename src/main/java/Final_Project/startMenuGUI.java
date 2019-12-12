package Final_Project;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class startMenuGUI extends JFrame{
    private GamePlayGUI gamePlayGUI;
    private JPanel mainPanel;
    private JButton StartButton;
    private JButton rulesButton;
    private JButton leaderboardButton;
    private JButton quitButton;


    public startMenuGUI(GamePlayGUI gamePlayGUI) {

        this.gamePlayGUI = gamePlayGUI;
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        buttonListeners();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void buttonListeners(){
        StartButton.addActionListener(e -> startGame());
        rulesButton.addActionListener(e -> callRules());
        leaderboardButton.addActionListener(e -> gamePlayGUI.getLeaderBoard());
        quitButton.addActionListener(e -> quit());
    }
    public void startGame(){
        setVisible(false);
        gamePlayGUI.setVisible(true);
        int numPlayers = gamePlayGUI.addNumPlayers();
        gamePlayGUI.playerNames(numPlayers);
    }

    public void callRules(){
        try{
            Desktop.getDesktop().browse(new URL("https://scrabble.hasbro.com/en-us/rules").toURI());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void quit(){
        System.exit(0);
    }
}
