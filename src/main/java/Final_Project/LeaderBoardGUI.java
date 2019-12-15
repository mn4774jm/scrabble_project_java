package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class LeaderBoardGUI extends JFrame{
    //declare components
    private GamePlayGUI gamePlayGUI;
    private JTable leaderTable;
    private JPanel mainPanel;
    private JButton closeButton;

    public LeaderBoardGUI(GamePlayGUI gamePlayGUI) {
        //reference for use of gamePlayGUI
        this.gamePlayGUI = gamePlayGUI;
        //basic set up
        setPreferredSize(new Dimension(500, 300));
        setTitle("Leader Board");
        populateTable();
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        buttonListeners();
        setLocationRelativeTo(null);

    }

    public void buttonListeners(){
        closeButton.addActionListener(e -> dispose());
    }

    public void populateTable(){
        //get winner object as vector
        Vector<Vector> winVector = gamePlayGUI.winnerData();
        //get column names
        Vector colNames = getColumns();
        //create table model and set datasource
        DefaultTableModel winModel = new DefaultTableModel(winVector,colNames);
        leaderTable.setModel(winModel);
        }

    public Vector getColumns(){
        //create and set column names
        Vector<String> colNames = new Vector<>();
        colNames.add("Name");
        colNames.add("Score");

        return colNames;
    }

    }

