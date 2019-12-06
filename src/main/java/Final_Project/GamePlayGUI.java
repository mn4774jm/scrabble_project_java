package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
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
    private JComboBox<String> challengeComboBox;

    private WordController wordController;

    //map for adding intital data to the jtable model
    Map<String, String> playerData = new HashMap<>();
    //create table model
    DefaultTableModel tableModel = new DefaultTableModel();
    //holds player names to display in jlabel
    List<String> playerTurnList = new ArrayList();
//    //hashmap to hold player names and scores
//    Map<String, List<Integer>> playerNameScore = new HashMap<>();
    Map<Integer, List> playerIDScore = new HashMap<>();
//    //map to hold player name and position in jatable
//    Map<String, Integer> playerTablePosition = new HashMap<>();

    public int turnCounter =0;



    GamePlayGUI(WordController wordController) {

        this.wordController = wordController;
        //set-up columns
        tableModel.addColumn("Player Name");
        tableModel.addColumn("Current Score");

        //add the initial data to jtable
        for (String data : playerData.keySet()) {
            tableModel.addRow(new String[]{data, playerData.get(data)});
        }
        //set jtables data source to the model
        playerTable.setModel(tableModel);
        //centers application window
        setLocationRelativeTo(null);
        //set dimensions
        setPreferredSize(new Dimension(500,300));
        //close application when window is closed
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setTitle("Scrabble Tracking Application");
        //call listeners
        buttonListeners();
        //call to get number of players
        int numPlayers = addPlayers();
        //call to get player names passing number of players as the argument
        playerNames(numPlayers);
        playerTurnLabel.setText(playerTurnList.get(0)+"'s Turn");

    }

        public int addPlayers() {
            // ask for number of turns
            String playerNum = showInputDialog("Enter number of players");
            //regular expression to validate for only digits
//            Pattern p = Pattern.compile("[A-Z,a-z,&%$#@!()*^]");
            Pattern p = Pattern.compile("[0-9]");
            Matcher m = p.matcher(playerNum);
            while (!m.find()) {
                JOptionPane.showMessageDialog(null, "Please enter only numbers");
                playerNum = showInputDialog("Enter number of players");
                m = p.matcher(playerNum);
            }
            // convert to integer and return
            return Integer.parseInt(playerNum);
        }

        public void playerNames(int numPlayers){
        challengeComboBox.addItem("Choose player to challenge");

        // loop through number of players, ask player names, add to Jtablemodel and tracking list
        for (int x = 1; x < numPlayers +1; x++){
            String playerName = showInputDialog("Enter player #"+x+"'s name");
            //add cell directly to jtable with zero as the default for row 0
            String[] newTableRow = {playerName, "0"};
            tableModel.addRow(newTableRow);
            playerTurnList.add(playerName);
            //add player names to challenge jcombo box
            challengeComboBox.addItem(playerName);
            //add player to hashmap for jtable positioning in challenge mode
//            playerTablePosition.put(playerName,x-1);
            //initialize values for playerNameScore map

            List<Integer> primer = new ArrayList();
            primer.add(0);
            playerIDScore.put(x-1,primer);
            System.out.println(playerIDScore); //TODO last thing I did was set up hashmap
        }

        }

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
        List<Integer> tempList = new ArrayList<>();
            //get value from enterWord text box
            int scoreString = Integer.parseInt(enterWordTextBox.getText());

            //getting list from hashmap
            List addScore = playerIDScore.get(turnCounter);

            //converting object from list into int
            for (Object things : addScore){
                int temp = Integer.parseInt(things.toString());
                //add to new list created at beginning of method
                tempList.add(temp);

            }
            //attempting to add a new value to list; this seems to only allow for two values
            //if it helps the hashmap it is coming from was created on line 47 and it was initialized on line 120 -122
            tempList.add(scoreString);
            System.out.println(tempList);

            //TODO figure out how to get my list out to modify/way to add directly to list
//            addScore.add(scoreString);
//            playerIDScore.put(turnCounter, addScore);
//            System.out.println(playerIDScore);


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
    //TODO translate current mySQL database to SQLite for easier use and packaging
        public void dictionaryCall(){
        //get text from box
        String wordToCheck = dictionaryTextBox.getText();
        //pass text to controller for use with database
        WordObject wordSearch = wordController.searchForWord(wordToCheck);
        //object returned
        if (wordSearch != null) {
            String word = wordSearch.getWord();
            String wordtype = wordSearch.getWordType();
            String def = wordSearch.getDefinition();
            showMessageDialog("Congratulations! Match found.\nWord: "+word+"\nWord Type: "+wordtype+ "\nDefinition: "+def);
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
