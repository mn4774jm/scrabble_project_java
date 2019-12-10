package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.Console;
import java.lang.reflect.Field;
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
    //holds player names to display in jlabel and for retreiving data
    List<String> playerTurnList = new ArrayList();
    //holds player id; doubles as positional data for jtable
    Map<String, Integer> idNamePairMap = new HashMap<>();

    public int turnCounter =0;
    private Object sItem;

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
        int numPlayers = addNumPlayers();
        //call to get player names passing number of players as the argument
        playerNames(numPlayers);
        playerTurnLabel.setText(playerTurnList.get(0)+"'s Turn");

    }

        public int addNumPlayers() {
            // ask for number of turns
            String playerNum = showInputDialog("Enter number of players");
            //regular expression to validate for only digits
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

            //create primer for new player score
            int scorePrimer = 0;
            //create a player id number
            int id = x-1;
            idNamePairMap.put(playerName, id);
            //create new ticket object for each player
            Date date = new Date();
            scoreObject newObject = new scoreObject(playerName,scorePrimer,id, date);
            //pass to controller
            wordController.addNewScore(newObject);

            //add cell directly to jtable with zero as the default for row 0
            String[] newTableRow = {playerName, "0"};
            tableModel.addRow(newTableRow);
            playerTurnList.add(playerName);
            //add player names to challenge jcombo box
            challengeComboBox.addItem(playerName);
            //add player to hashmap for jtable positioning in challenge mode
        }

        }

        public void buttonListeners(){
            //method call for user score entry
            enterWordButton.addActionListener(e -> {
                try {
                    enterScores();
                } catch (NoSuchFieldException ex) {
                    ex.printStackTrace();
                }
            });
            //method to consult dictionary
            dictionaryButton.addActionListener(e -> dictionaryCall());
            //method to challenge
            challengeButton.addActionListener(e -> challengeCall());
            //finish game
            finishButton.addActionListener(e -> finishCall());
        }

        public void enterScores() throws NoSuchFieldException {

            //get value from enterWord text box
            int scoreString = Integer.parseInt(enterWordTextBox.getText());

            Date date = new Date();
            //create new object for new row in DB
            scoreObject newScore = new scoreObject(playerTurnList.get(turnCounter),scoreString,turnCounter,date);
            //call method to create new row
            wordController.addNewScore(newScore);
            //get sum value from database
            scoreCounting scoreSearch = wordController.searchScore(turnCounter);
            //set set score to appropriate cell
            tableModel.setValueAt(scoreSearch.getPlayerScore(), turnCounter, 1);
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
        //get text from box and change case
        String wordToCheck = firstLetterUpper(dictionaryTextBox.getText());
        //pass text to controller for use with database
        WordObject wordSearch = wordController.searchForWord(wordToCheck);
        //object returned
        if (wordSearch != null) {
            String word = wordSearch.getWord();
            String wordtype = wordSearch.getWordType();
            String def = wordSearch.getDefinition();
            showMessageDialog(String.format("Congratulations! Match found.\nWord: %s\nWord Type: %s\nDefinition: %s",word, wordtype, def));
        }else{
            showMessageDialog("No matches found");
        }
        //clear text from box
        dictionaryTextBox.setText("");
        }

        public void challengeCall(){
        //TODO set up challenge method
            //get challenged players name
            String challengedPlayer = (String) challengeComboBox.getSelectedItem();
            //get player id from hashmap using playername as the key
            int id = idNamePairMap.get(challengedPlayer);
            //get last score from DB
            LastScoreObject lastPlay = wordController.retrieveScore(id);
            //Store to int
            int penalty = lastPlay.getLastPlay();
            int scoreHammer = penalty - (penalty*2);
            Date date = new Date();
            //get word to check from the play, convert to correct case format
            String challengedWord = JOptionPane.showInputDialog("Enter word in question");
            challengedWord = firstLetterUpper(challengedWord);

                WordObject wordSearch = wordController.searchForWord(challengedWord);
                if (wordSearch != null) {
                    String word = wordSearch.getWord();
                    String wordtype = wordSearch.getWordType();
                    String def = wordSearch.getDefinition();
                    showMessageDialog(String.format(
                            "Challenge failed! Match found.\nWord: %s\nWord Type: %s\nDefinition: %s\n\n" +
                                    "%d points will be deducted from %s's score",word, wordtype, def, penalty, playerTurnList.get(turnCounter)));

                    scoreObject negativeRow = new scoreObject(playerTurnList.get(turnCounter), scoreHammer, idNamePairMap.get(playerTurnList.get(turnCounter)),date);
                    wordController.addNewScore(negativeRow);
                    scoreCounting scoreSearch = wordController.searchScore(turnCounter);
                    tableModel.setValueAt(scoreSearch.getPlayerScore(), turnCounter, 1);

                } else {
                    //get challenged players positional data and last score
                    scoreObject negativeRow = new scoreObject(challengedPlayer, scoreHammer, idNamePairMap.get(challengedPlayer),date);
                    wordController.addNewScore(negativeRow);
                    scoreCounting scoreSearch = wordController.searchScore(idNamePairMap.get(challengedPlayer));
                    tableModel.setValueAt(scoreSearch.getPlayerScore(), idNamePairMap.get(challengedPlayer), 1);
                    //get last entered score
                    showMessageDialog(String.format(
                            "Challenge has succeeded! No matching words were found\n\n%d points will be deducted from %s's score.", penalty, challengedPlayer));
                }
            }


        public void finishCall(){
        //TODO set up finish call; decide between JOption and new window with other options
        }
        //used to convert first letter of a word to upeercase and the body to lower
        public String firstLetterUpper(String word){
            String fixedWord = word.toLowerCase().substring(0,1).toUpperCase()+ word.substring(1);
            return fixedWord;
        }



    protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
