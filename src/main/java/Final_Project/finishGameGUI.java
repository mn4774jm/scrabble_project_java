package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class finishGameGUI extends JFrame{
    //required to reference GamePlayGUI
    private GamePlayGUI gamePlayGUI;
    //declare components
    private JPanel finishMainPanel;
    private JTable scoreTable;
    private JLabel winnerLabel;
    private JLabel mvpLabel;
    private JButton leaderButton;
    private JButton playagainButton;
    private JButton quitButton;


    finishGameGUI(GamePlayGUI gamePlayGUI){
        
        //create reference to use methods in GamePlayGUI
        this.gamePlayGUI = gamePlayGUI;

        //set up basic elements
        setContentPane(finishMainPanel);
        pack();
        setVisible(true);
        gamePlayGUI.setEnabled(false);
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
        //call to get final score data from the gameplay gui
        Vector<Vector> finalVector = gamePlayGUI.getFinaldata();
        //get column names for the final 
        Vector colNames = getColumnNames();
        //create model for use with jtable
        DefaultTableModel tableModel = new DefaultTableModel(finalVector,colNames);
        //set data source
        scoreTable.setModel(tableModel);
    }

    public Vector getColumnNames(){
        //create and set columns for table
        Vector<String> colNames = new Vector<>();
        colNames.add("Player Name");
        colNames.add("High Score");
        colNames.add("Total");
        return colNames;
    }

    public void mvpDisplay(){
        //get info for player with the highest individual score 
        MVPObject score = gamePlayGUI.MVP();
        //info set to label
        mvpLabel.setText(String.format("MVP: %s with a high score of %d",score.getName(), score.getPlayMax()));
    }

    public void winnerDisplay(){
        //get winner data
        winnerObject score = gamePlayGUI.winner();
        //print to label
        winnerLabel.setText(String.format("The winner is %s with a total score of %d", score.getName(), score.getPlaySum()));
    }

    public void buttonListeners(){
        //button calls
        leaderButton.addActionListener(e -> clickLeaderboard());
        //exception handling for restart
        playagainButton.addActionListener(e -> {
            try {
                clickPlayAgain();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        quitButton.addActionListener(e -> clickQuit());
    }

    public void clickLeaderboard(){
        //call to launch leaderBoard
        gamePlayGUI.getLeaderBoard();
    }

    public void clickPlayAgain() throws SQLException {
        //calls restart in main function and disposes of the current window
        main.restart();
        dispose();
    }

    public void clickQuit(){
        //closes program with normal shutdown status
        System.exit(0);
    }
}
