package Final_Project;

public class WordController {

    WordStore wordStore;

    WordController(WordStore store) { wordStore = store; }

    protected WordObject searchForWord(String wordToCheck) {
        return wordStore.checkWord(wordToCheck);
    }
}
