package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class LeaderBoardGUI extends JFrame{
    private GamePlayGUI gamePlayGUI;

    private JTable leaderTable;
    private JPanel mainPanel;
    private JButton closeButton;
    DefaultTableModel tableModel = new DefaultTableModel();

    public LeaderBoardGUI(GamePlayGUI gamePlayGUI) {

        this.gamePlayGUI = gamePlayGUI;
        leaderTable.setModel(tableModel);
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
        Vector<Vector> winVector = gamePlayGUI.winnerData();
      Vector colNames = getColumns();
        DefaultTableModel winModel = new DefaultTableModel(winVector,colNames);
        leaderTable.setModel(winModel);
        }

    public Vector getColumns(){
        Vector<String> colNames = new Vector<>();
        colNames.add("Name");
        colNames.add("Score");

        return colNames;
    }

    }

