package game;

import java.util.List;

import game.Board;
import game.Player.HumanPlayer;
import game.Position;
//import game.Player;
import game.Utils.Colour;
import game.Utils.GamePhase;

public class GameManager {
    private GamePhase gamePhase;

    private HumanPlayer player;
    private HumanPlayer otherPlayer;
    private HumanPlayer player1;
    private HumanPlayer player2;

    private Board board;

    public GameManager() {
        board = Board.getInstance();

        startGame();
    }

    public void startGame() {
        player1 = new HumanPlayer("Player 1", Colour.BLACK);
        player2 = new HumanPlayer("Player 2", Colour.WHITE);
        player = player1;
        otherPlayer = player2;

        gamePhase = GamePhase.PLACEMENT;

    }


    public GamePhase getGamePhase() {
        return gamePhase;
    }

}
