package Final_Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private JTable playerTable;

    //quit function
    private JButton finishButton;
    private JLabel playerTurnLabel;
    private JComboBox<String> challengeComboBox;

    private WordController wordController;
    public int turnCounter =0;
    //TODO work in getRootPane.setDefaultButton() to have enter work for field(s)

    //Global items that need to work for multiple methods
    //map for adding intital data to the jtable model
    Map<String, String> playerData = new HashMap<>();
    //create table model
    DefaultTableModel tableModel = new DefaultTableModel();
    //holds player names to display in jlabel and for retreiving data
    List<String> playerTurnList = new ArrayList();
    //holds player id; doubles as positional data for jtable
    Map<String, Integer> idNamePairMap = new HashMap<>();
    //global date item global to avoid duplication
    Date date = new Date();


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
        setVisible(false);
        setTitle("Scrabble Tracking Application");

        //call listeners
        buttonListeners();

        //call to get number of players
        startMenuGUI startmenuGUI = new startMenuGUI(GamePlayGUI.this);


    }

        public int addNumPlayers() {
            // ask for number of turns
            String playerNum = showInputDialog("Enter number of players");
            //pass string to method for numeric validation
            boolean validation = numCheck(playerNum);
            //while validation is false...
            while (!validation){
                JOptionPane.showMessageDialog(null, "Please enter only numbers");
                playerNum = showInputDialog("Enter number of players");
                validation = numCheck(playerNum);
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
            String scoreString = enterWordTextBox.getText();
            boolean validation = numCheck(scoreString);
            if (!validation){
                JOptionPane.showMessageDialog(null, "Enter only numbers in score field");
            }else {
                int scoreInt = Integer.parseInt(enterWordTextBox.getText());
                //create new object for new row in DB
                scoreObject newScore = new scoreObject(playerTurnList.get(turnCounter), scoreInt, turnCounter, date);

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
            }
            enterWordTextBox.setText("");
        }

        public void dictionaryCall() {
        //get text from box and change case
        String wordToCheck = firstLetterUpper(dictionaryTextBox.getText());
        //pass text to controller for use with database
        WordObject wordSearch = wordController.searchForWord(wordToCheck);
        //object returned; DB query is set to only return one value so this is overkill but that may change
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

        public void challengeCall() {

            //TODO getting nullpointerexception error with the jcombobox
            //get challenged players name
            String challengedPlayer = (String) challengeComboBox.getSelectedItem();

            //get player id from hashmap using playername as the key
            int id = idNamePairMap.get(challengedPlayer);

            //get last score from DB
            LastScoreObject lastPlay = wordController.retrieveScore(id);
            //convert to int

            //Store to int
            int penalty = lastPlay.getLastPlay();
            //set punishment
            int scoreHammer = -(penalty);

            //get word to check from the play, convert to correct case format
            String challengedWord = JOptionPane.showInputDialog("Enter word in question");
            challengedWord = firstLetterUpper(challengedWord);

            //Call database to search for word
            WordObject wordSearch = wordController.searchForWord(challengedWord);
            punishmentPhase(penalty, scoreHammer, wordSearch, challengedPlayer);


            }

        public void punishmentPhase(int penalty, int scoreHammer, WordObject wordSearch, String challengedPlayer){
            //if this breaks, return block to just under the method call in the challengeCall method

            //if exists, get items from object
            if (wordSearch != null) {
                String word = wordSearch.getWord();
                String wordtype = wordSearch.getWordType();
                String def = wordSearch.getDefinition();
                showMessageDialog(String.format(
                        "Challenge failed! Match found.\nWord: %s\nWord Type: %s\nDefinition: %s\n\n" +
                                "%d points will be deducted from %s's score",word, wordtype, def, penalty, playerTurnList.get(turnCounter)));

                //create new row with negative values and add to database
                scoreObject negativeRow = new scoreObject(playerTurnList.get(turnCounter), scoreHammer, idNamePairMap.get(playerTurnList.get(turnCounter)),date);
                wordController.addNewScore(negativeRow);

                //get new updated score and set into table model
                scoreCounting scoreSearch = wordController.searchScore(turnCounter);
                tableModel.setValueAt(scoreSearch.getPlayerScore(), turnCounter, 1);

            } else {
                //get challenged players positional data and last score; add new row with negative score value
                scoreObject negativeRow = new scoreObject(challengedPlayer, scoreHammer, idNamePairMap.get(challengedPlayer),date);
                wordController.addNewScore(negativeRow);
                //search for current score after row added and set to model
                scoreCounting scoreSearch = wordController.searchScore(idNamePairMap.get(challengedPlayer));
                tableModel.setValueAt(scoreSearch.getPlayerScore(), idNamePairMap.get(challengedPlayer), 1);
                //get last entered score
                showMessageDialog(String.format(
                        "Challenge has succeeded! No matching words were found\n\n%d points will be deducted from %s's score.", penalty, challengedPlayer));
            }
        }

        public void finishCall(){
        //call to launch new GUI. Set visible to false
        finishGameGUI finishGame = new finishGameGUI(GamePlayGUI.this);
        setVisible(false);
        }

        public Vector getFinaldata(){
        //call controller for final player stats; return data to finishgamegui
            Vector<Vector> finalData= wordController.retrieveFinal();
            return finalData;
        }

        public MVPObject MVP(){
        //call controller for highest play score; return to finishGUI
            MVPObject playScores =  wordController.retrieveMVP();
            return playScores;
        }

        public winnerObject winner(){
        //call controller for winner score; return
            winnerObject winScore =  wordController.retrieveWinner();
            return winScore;
        }

        public void populateLeaderBoard(){
        //TODO create new table for leaderboard and method to access it
        }

        public String firstLetterUpper(String word){
        //convert word to lower followed by making the first letter uppercase
            String fixedWord = word.toLowerCase().substring(0,1).toUpperCase()+ word.substring(1);
            return fixedWord;
        }

        public boolean numCheck(String check){
        //create pattern to check and match item
            Pattern p = Pattern.compile("[0-9]");
            Matcher m = p.matcher(check);
        if(!m.find()){
            return false;
        }else{
            return true;
        }
        }

        protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }

        protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
