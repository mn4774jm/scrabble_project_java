package Final_Project;

import java.util.Date;

public class scoreObject {

    private String playerName;
    private int playerScore;
    private int playerID;
    private Date datePlayed;



    public scoreObject(String player, int score, int id, Date date){
        this.playerName = player;
        this.playerScore = score;
        this.playerID = id;
        this.datePlayed = date;

    }

    public Date getDatePlayed() { return datePlayed; }

    public void setDatePlayed(Date datePlayed) { this.datePlayed = datePlayed; }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getPlayerScore() { return playerScore; }

    public void setPlayerScore(int playerScore) { this.playerScore = playerScore; }

    public int getPlayerID() { return playerID; }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public String toString() {
        return "scoreObject{" +
                "playerName='" + playerName + '\'' +
                ", playerScore=" + playerScore +
                '}';
    }
}
