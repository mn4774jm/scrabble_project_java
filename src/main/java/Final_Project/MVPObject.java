package Final_Project;

public class MVPObject {
    private String name;
    private int playMax;

    MVPObject(String n, int p){
        name = n;
        playMax = p;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPlayMax() { return playMax; }

    public void setPlayMax(int playMax) { this.playMax = playMax; }

}
