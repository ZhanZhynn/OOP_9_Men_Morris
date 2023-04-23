package game;

import game.Player.HumanPlayer;

public class Game {

    private HumanPlayer player1;
    private HumanPlayer player2;
    private Board board;

    private GamePhase gamePhase;
    public Game() {

        board = new Board();
        player1 = new HumanPlayer("Player 1", Colour.BLACK);
        player2 = new HumanPlayer("Player 2", Colour.WHITE);
        player1.activateTurn();
        gamePhase = GamePhase.PLACEMENT;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
