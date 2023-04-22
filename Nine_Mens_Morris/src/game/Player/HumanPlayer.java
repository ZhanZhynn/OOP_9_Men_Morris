package Player;

public class HumanPlayer extends Player{

    private String playerName;

    public HumanPlayer(String playerName, String tokenColour) {
        super(tokenColour);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
