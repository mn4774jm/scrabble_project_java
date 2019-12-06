package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



//import static input.InputUtils.*;

public class GamePlayGUI extends JFrame {

    private JPanel mainPanel;
    //word entry components
    private JButton enterWordButton;
    private JTextField enterWordTextBox;

    //components that use dictionary API
    private JButton dictionaryButton;
    private JTextField dictionaryTextBox;
    private JButton challengeButton;

    //components for displaying data
    //private JList<scoreObject> playerNameList;
    //private JList PlayerScoreList;
    private JTable playerTable;

    //quit function
    private JButton finishButton;
    private JLabel playerTurnLabel;

    private WordController wordController;

    //private DefaultListModel<scoreObject> PlayerScoreModel;
    Map<String, String> playerData = new HashMap<>();
    DefaultTableModel tableModel = new DefaultTableModel();
    List<String> playerTurnList = new ArrayList();

    public int turnCounter =0;



    GamePlayGUI(WordController wordController) {

        this.wordController = wordController;
        //set-up columns
        tableModel.addColumn("Player Name");
        tableModel.addColumn("Current Score");

        //add the initial data
        for (String data : playerData.keySet()) {
            tableModel.addRow(new String[]{data, playerData.get(data)});
        }
        //set jtables data source to the model
        playerTable.setModel(tableModel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setTitle("Scrabble Tracking Application");

        buttonListeners();
        addPlayers();
        playerTurnLabel.setText(playerTurnList.get(0)+"'s Turn");

    }

        public void addPlayers(){
        // ask for number of turns
        //TODO validation will not allow the user to exit directly from the window. have to close the application from the task bar
        String playerNum = showInputDialog("Enter number of players");
            Pattern p = Pattern.compile("[A-Z,a-z,&%$#@!()*^]");
            Matcher m = p.matcher(playerNum);
            while (m.find()) {
                JOptionPane.showMessageDialog(null, "Please enter only numbers");
                playerNum = showInputDialog("Enter number of players");
            }//TODO make a seperate method for player names after player numbers
        // convert to integer
        int convertedNum = Integer.parseInt(playerNum);

        // loop through number of players, ask player names, add to Jtablemodel and tracking list
        for (int x = 1; x < convertedNum+1; x++){
            String playerName = showInputDialog("Enter player #"+x+"'s name");
            //add cell directly to jtable with zero as the default for row 0
            String[] newTableRow = {playerName, "0"};
            tableModel.addRow(newTableRow);
            playerTurnList.add(playerName);
        }
        }
        //TODO get value from jtablemodel for current player's score and add new score from the textbox
        //TODO add validation for score input

        public void buttonListeners(){
            //method call for user score entry
            enterWordButton.addActionListener(e -> enterScores());
            //method to consult dictionary
            dictionaryButton.addActionListener(e -> dictionaryCall());
            //method to challenge
            challengeButton.addActionListener(e -> challengeCall());
            //finish game
            finishButton.addActionListener(e -> finishCall());
        }

        public void enterScores() {
            //get value from enterWord text box
            int scoreString = Integer.parseInt(enterWordTextBox.getText());
            //pull current player score from the table model

            int pulledValue = Integer.parseInt(tableModel.getValueAt(turnCounter, 1).toString());

            //add text box score to jtable score
            int finalInt = pulledValue + scoreString;
            //set set score to appropriate cell
            tableModel.setValueAt(finalInt, turnCounter, 1);
            //if not the last player in list, add 1 to the turn counter to advance the game
            if (turnCounter < playerTurnList.size() - 1) {
                turnCounter += 1;
                playerTurnLabel.setText(playerTurnList.get(turnCounter) + "'s turn");
            } else {
                //if last player in list reset counter to 0
                turnCounter = 0;
                playerTurnLabel.setText(playerTurnList.get(turnCounter) + "'s turn");
            }
            //clear text box after turn
            enterWordTextBox.setText("");
        }

        public void dictionaryCall(){
        //TODO build dictionary API call
        String wordToCheck = dictionaryTextBox.getText();
        WordObject wordSearch = wordController.searchForWord(wordToCheck);
        if (wordSearch != null) {
            String word = wordSearch.getWord();
            String def = wordSearch.getDefinition();
            showMessageDialog("Congratulations! Match found.\nWord: "+word+"\nDefinition: "+def);
        }else{
            showMessageDialog("No matches found");
        }
        }
        public void challengeCall(){
        //TODO set up challenge method
        }
        public void finishCall(){
        //TODO set up finish call; decide between JOption and new window with other options
        }

    protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
