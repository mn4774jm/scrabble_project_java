package Final_Project;

public class winnerObject {
    private String name;
    private int playSum;


    winnerObject(String n, int p){
        name = n;
        playSum = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaySum() {
        return playSum;
    }

    public void setPlaySum(int playSum) {
        this.playSum = playSum;
    }
}
