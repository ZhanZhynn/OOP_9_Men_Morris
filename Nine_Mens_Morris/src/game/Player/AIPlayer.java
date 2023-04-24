package game.Player;

import game.Utils.Colour;

public class AIPlayer extends Player{

    private String playerName;

    public AIPlayer(String playerName, Colour colour) {
        super(colour);
        this.playerName = playerName;
    }


}
