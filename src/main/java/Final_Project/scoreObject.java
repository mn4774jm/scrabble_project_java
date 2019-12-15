package Final_Project;

import java.util.Date;

public class scoreObject {

    private String playerName;
    private int playerScore;
    private int playerID;
    private Date datePlayed;


    //set up constructors
    public scoreObject(String player, int score, int id, Date date){
        this.playerName = player;
        this.playerScore = score;
        this.playerID = id;
        this.datePlayed = date;

    }
    //create getters and toString
    public Date getDatePlayed() { return datePlayed; }

    public String getPlayerName() { return playerName; }

    public int getPlayerScore() { return playerScore; }

    public int getPlayerID() { return playerID; }

    //create toString method
    @Override
    public String toString() {
        return "scoreObject{" +
                "playerName='" + playerName + '\'' +
                ", playerScore=" + playerScore +
                '}';
    }
}
