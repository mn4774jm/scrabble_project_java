package Final_Project;

import java.sql.SQLException;
import java.util.List;

public class WordController {

    WordStore wordStore;

    WordController(WordStore store) { wordStore = store; }

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
}
