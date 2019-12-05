package Final_Project;

public class scoreObject {

    private String playerName;
    private int playerScore;

    public scoreObject(String player, int score){
        this.playerName = player;
        this.playerScore = score;
    }


    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getPlayerScore() { return playerScore; }

    public void setPlayerScore(int playerScore) { this.playerScore = playerScore; }

    @Override
    public String toString() {
        return "scoreObject{" +
                "playerName='" + playerName + '\'' +
                ", playerScore=" + playerScore +
                '}';
    }
}
