package Final_Project;

public class winnerObject {
    private String name;
    private int playSum;

    //set up constructor
    winnerObject(String n, int p){
        name = n;
        playSum = p;
    }
    //create getters
    public String getName() {
        return name;
    }

    public int getPlaySum() {
        return playSum;
    }
}
