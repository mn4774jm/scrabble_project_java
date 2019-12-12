package Final_Project;

//TODO translate to non-object variable types

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
