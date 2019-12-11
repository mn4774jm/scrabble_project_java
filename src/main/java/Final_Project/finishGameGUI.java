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




    //TODO call getFinalData

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
        getFinal();
        setTitle("Game Results");

    }
    public void getFinal(){
        //TODO currently only returns a single row; is a sql issue
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
    public void buttonListeners(){


    }
}
