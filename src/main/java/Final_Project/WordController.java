package Final_Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class WordController {
    //Reference
    WordStore wordStore;
    WordController(WordStore store) { wordStore = store; }

    //connections between the gui and the wordstore databases

    //for use with dictionary database word searhes
    protected WordObject searchForWord(String wordToCheck) {

        return wordStore.checkWord(wordToCheck);
    }

    //used to add new rows to dictionary
    protected boolean addNewScore(scoreObject nameToAdd) {
       try{
           wordStore.addScore(nameToAdd);
           return true;
       } catch (SQLException e) {
           return false;
       }
    }

    //gets current score for player. Data used to populate jtable
    protected int searchScore(int turnCounter) {
        int score = wordStore.getCurrentScore(turnCounter);
        return score;
    }

    //gets the last score played for specific player
    protected LastScoreObject retrieveScore(int id){
        LastScoreObject lastScoreObject = wordStore.retreiveLastScore(id);
        return lastScoreObject;
    }

    //used to get the final sum scores for all players
    protected Vector<Vector> retrieveFinal(){
        Vector<Vector> finalScore = wordStore.finalScoreData();
        return finalScore;
    }

    //returns highest individual score player row
    protected MVPObject retrieveMVP(){
        MVPObject stats = wordStore.mvp();
        return stats;
    }

    //returns highest sum score player
    protected winnerObject retrieveWinner(){
        winnerObject stats = wordStore.winner();
        return stats;
    }

    //adds the winning score to the leaderboard table
    protected boolean addWinScore(winnerObject nameToAdd) {
        try{
            wordStore.addWinningScore(nameToAdd);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //returns the leaderboard top 10 scores
    protected Vector<Vector> getLeadersList() {
        Vector<Vector> leaders = wordStore.highScores();
        return leaders;
    }

}
