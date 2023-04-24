package game;

import game.Player.HumanPlayer;
import game.Utils.Colour;
import game.Utils.GamePhase;

import java.awt.*;

public class GameManager {

    private final int MAXTOKEN = 18;
    private GamePhase gamePhase;

    private HumanPlayer player1;
    private HumanPlayer player2;

    private Board board;

    private int totalTokenPlaced;

    public GameManager() {
        board = new Board();

        startGame();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public HumanPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(HumanPlayer player1) {
        this.player1 = player1;
    }

    public HumanPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(HumanPlayer player2) {
        this.player2 = player2;
    }

    public void startGame() {
        player1 = new HumanPlayer("Player 1", Colour.BLACK);
        player2 = new HumanPlayer("Player 2", Colour.WHITE);

        gamePhase = GamePhase.PLACEMENT;

    }


    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public int getTotalTokenPlaced() {
        return totalTokenPlaced;
    }

    public void setTotalTokenPlaced(int totalTokenPlaced) {
        this.totalTokenPlaced = totalTokenPlaced;
    }

    public void changePlayerTurn() {
        if (player1.isTurn()) {
            player1.deactivateTurn();
            player2.activateTurn();
        } else {
            player2.deactivateTurn();
            player1.activateTurn();
        }
    }
    public Colour colorOnTurn (){
        return player1.isTurn() ? player1.getColour() : player2.getColour();
    }

    public void placeToken(Position position) {
        Colour colour = colorOnTurn();
        board.placeNewToken(position, colour);
        totalTokenPlaced++;

        if(totalTokenPlaced == MAXTOKEN){
            gamePhase = GamePhase.MOVEMENT;
        }
    }

    public void moveToken(Position position){
        board.moveToken(position);
    }

    public void setSelectedTokenPosition(Position oldPosition) {
        board.setOldPosition(oldPosition);
    }

    public boolean validateTokenPlacement(Position newPosition) {
        if (gamePhase == GamePhase.MOVEMENT) {
            return board.validateTokenPlacement(newPosition);
        }
        return true;
    }
}
