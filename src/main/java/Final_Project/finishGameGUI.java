package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class finishGameGUI extends JFrame{
    //required to reference GamePlayGUI
    private GamePlayGUI parentComponent;
    private JPanel finishMainPanel;
    private JTable scoreTable;
    private JLabel winnerLabel;
    private JLabel mvpLabel;
    private JButton leaderButton;
    private JButton playagainButton;
    private JButton quitButton;


    finishGameGUI(GamePlayGUI parentComponent){


                //create reference to use methods in GamePlayGUI
        this.parentComponent = parentComponent;

        setContentPane(finishMainPanel);
        pack();
        setVisible(true);
        parentComponent.setEnabled(false);
        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(500,300));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game Results");
        getFinal();
        mvpDisplay();
        winnerDisplay();
        buttonListeners();

    }

    public void getFinal(){

        Vector<Vector> finalVector = parentComponent.getFinaldata();
        Vector colNames = getColumnNames();
        DefaultTableModel tableModel = new DefaultTableModel(finalVector,colNames);
        scoreTable.setModel(tableModel);
    }

    public Vector getColumnNames(){
        Vector<String> colNames = new Vector<>();
        colNames.add("Player Name");
        colNames.add("High Score");
        colNames.add("Total");
        return colNames;
    }

    public void mvpDisplay(){
        MVPObject score = parentComponent.MVP();
        mvpLabel.setText(String.format("MVP: %s with a high score of %d",score.getName(), score.getPlayMax()));
    }

    public void winnerDisplay(){
        winnerObject score = parentComponent.winner();
        winnerLabel.setText(String.format("The winner is %s with a total score of %d", score.getName(), score.getPlaySum()));
    }

    public void buttonListeners(){
        leaderButton.addActionListener(e -> clickLeaderboard());
        playagainButton.addActionListener(e -> clickPlayAgain());
        quitButton.addActionListener(e -> clickQuit());
    }

    public void clickLeaderboard(){
        parentComponent.getLeaderBoard();
    }

    public void clickPlayAgain(){

        //TODO dispose and call program to start from the GamePlayGUI
    }

    public void clickQuit(){
        System.exit(0);
    }
}
