package game;

import game.Player.HumanPlayer;
import game.Utils.Colour;
import game.Utils.GamePhase;

import java.awt.*;

/**
 * @author Priyesh
 *
 * This class is used to manage the game.
 * It also contains the methods to change the game phase and the player turn.
 *
 */

public class GameManager {

    private final int MAXTOKEN = 18;
    private GamePhase gamePhase;

    private HumanPlayer player1;
    private HumanPlayer player2;

    private Board board;

    private int totalTokenPlaced;

    private boolean isMill;

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


    public boolean isMill() {
        return isMill;
    }

    public void setMill(boolean mill) {
        isMill = mill;
    }

    public void startGame() {
        player1 = new HumanPlayer("Player 1", Colour.BLACK);
        player2 = new HumanPlayer("Player 2", Colour.WHITE);

        gamePhase = GamePhase.PLACEMENT;
        player1.activateTurn();
        player2.deactivateTurn();

    }


    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GamePhase gamephase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Getter to get token numbers of tokens on the board.
     */
    public int getTotalTokenPlaced() {
        return totalTokenPlaced;
    }

    /**
     * Setter to set token numbers of tokens on the board.
     */
    public void setTotalTokenPlaced(int totalTokenPlaced) {
        this.totalTokenPlaced = totalTokenPlaced;
    }


    /**
     * This method is used to change the game phase.
     */
    public void changePlayerTurn() {
        if (player1.isTurn()) {
            player1.deactivateTurn();
            player2.activateTurn();
        } else {
            player2.deactivateTurn();
            player1.activateTurn();
        }
    }

    public void gameOver(){
        gamePhase = GamePhase.GAMEOVER;
        player1.deactivateTurn();
        player2.deactivateTurn();

    }

    /**
     * This method is used to get the colour of the player whose turn it is.
     */
    public Colour colorOnTurn (){
        return player1.isTurn() ? player1.getColour() : player2.getColour();
    }

    /**
     * This method is used to place a token on the board.
     *
     * @param position the position to place the token.
     */
    public void placeToken(Position position) {
        Colour colour = colorOnTurn();
        board.placeNewToken(position, colour);
        totalTokenPlaced++;

        // Update the number of tokens placed by the player
        if (player1.isTurn()){
            player1.tilePlaced();
        } else {
            player2.tilePlaced();
        }

        System.out.println("player 1:" + player1.getTotalPiecesOnBoard() + " player 2:" + player2.getTotalPiecesOnBoard());

        if(totalTokenPlaced == MAXTOKEN){
            gamePhase = GamePhase.MOVEMENT;
        }
    }

    /**
     * This method is used to move a token on the board.
     *
     * @param position the position to move the token to.
     */
    public void moveToken(Position position){
        board.moveToken(position);
    }

    /**
     * This method is used to set the position of the selected token.
     *
     * @param oldPosition the current position of the selected token.
     */
    public void setSelectedTokenPosition(Position oldPosition) {
        board.setOldPosition(oldPosition);
    }

    /**
     * This method is to validate the placement of the token using the logic in {@code Board}
     *
     * @param newPosition the position to be validated
     * @return true if the token can be placed at the position, false otherwise.
     *
     */
    public boolean validateTokenPlacement(Position newPosition) {
        if (gamePhase == GamePhase.MOVEMENT) {
            return board.validateTokenPlacement(newPosition);
        }
        return true;
    }

    /**
     * Check whether the game is won
     *
     * winning condition: opponent only has 2 tokens left
     */
    public int checkWin(){
        if (player1.getTotalPiecesToPlace() == 0 && player1.getTotalPiecesOnBoard() < 3){
            return 2;   // player 2 wins
        } else if (player2.getTotalPiecesToPlace() == 0 && player2.getTotalPiecesOnBoard() < 3){
            return 1;   // player 1 wins
        }
        return 0;
    }



    public void updateMillStatus(Position tokenPosition){
        isMill = board.checkIfMill(tokenPosition);
    }

    public boolean removeToken(Position tokenPosition){
        if (player1.isTurn()){
            player1.removeToken();
        } else {
            player2.removeToken();
        }
        System.out.println("player 1:" + player1.getTotalPiecesOnBoard() + " player 2:" + player2.getTotalPiecesOnBoard());

        return board.removeToken(tokenPosition);
    }

}
