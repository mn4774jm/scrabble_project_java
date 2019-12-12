package Final_Project;

public class MVPObject {
    private String name;
    private int playMax;

    MVPObject(String n, int p){
        //constructor
        this.name = n;
        this.playMax = p;
    }
    //getters for object
    public String getName() { return name; }
    public int getPlayMax() { return playMax; }

}
