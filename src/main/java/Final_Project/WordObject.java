package Final_Project;

public class WordObject {
    private String word;
    private String wordType;
    private String definition;

    //create constructor
    WordObject(String w, String wt, String d) {
        word = w;
        wordType = wt;
        definition = d;
    }
    //create getters
    public String getWord() {
        return word;
    }

    public String getWordType() {
        return wordType;
    }

    public String getDefinition() {
        return definition;
    }
}
