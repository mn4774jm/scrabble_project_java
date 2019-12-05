package Final_Project;

public class WordObject {
    private String word;
    private String wordType;
    private String definition;


    WordObject(String w, String wt, String d) {
        word = w;
        wordType = wt;
        definition = d;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
