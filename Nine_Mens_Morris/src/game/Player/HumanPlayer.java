package game.Player;

import game.Colour;

public class HumanPlayer extends Player{

    private String playerName;

    public HumanPlayer(String playerName, Colour colour) {
        super(colour);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
