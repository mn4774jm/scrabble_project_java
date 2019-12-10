package Final_Project;

import java.sql.SQLException;
import java.util.List;

public class WordController {

    WordStore wordStore;

    WordController(WordStore store) { wordStore = store; }

    protected WordObject searchForWord(String wordToCheck) {
        return wordStore.checkWord(wordToCheck);
    }
    protected boolean addNames(scoreObject nameToAdd) {
       try{
           wordStore.addScore(nameToAdd);
           return true;
       } catch (SQLException e) {
           return false;
       }
    }
    protected List<scoreObject> searchScore(int turnCounter) {
        List<scoreObject> score = wordStore.getCurrentScore(turnCounter);
        return score;
    }
}
