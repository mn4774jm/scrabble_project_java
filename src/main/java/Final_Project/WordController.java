package Final_Project;

import java.sql.SQLException;
import java.util.Vector;

public class WordController {
    //Reference
    WordStore wordStore;
    WordController(WordStore store) { wordStore = store; }

    //connections between the gui and the wordstore databases

    protected WordObject searchForWord(String wordToCheck) {

        return wordStore.checkWord(wordToCheck);
    }

    protected boolean addNewScore(scoreObject nameToAdd) {
       try{
           wordStore.addScore(nameToAdd);
           return true;
       } catch (SQLException e) {
           return false;
       }
    }

    protected scoreCounting searchScore(int turnCounter) {
        scoreCounting score = wordStore.getCurrentScore(turnCounter);
        return score;
    }

    protected LastScoreObject retrieveScore(int id){
        LastScoreObject lastScoreObject = wordStore.retreiveLastScore(id);
        return lastScoreObject;
    }

    protected Vector<Vector> retrieveFinal(){
        Vector<Vector> finalScore = wordStore.finalScoreData();
        return finalScore;
    }

    protected MVPObject retrieveMVP(){
        MVPObject stats = wordStore.mvp();
        return stats;
    }

    protected winnerObject retrieveWinner(){
        winnerObject stats = wordStore.winner();
        return stats;
    }

}
