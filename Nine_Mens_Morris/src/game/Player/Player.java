package game.Player;

import game.Action.Action;
import game.Colour;


abstract class Player {

    private boolean isTurn;
    private Action action;
    private int totalPiecesOnBoard;
    private int totalPiecesToPlace;
    private Colour colour;

    public Player(Colour colour) {
        this.colour = colour;
        totalPiecesToPlace = 9;
        totalPiecesOnBoard = 0;
        isTurn = false;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getTotalPiecesOnBoard() {
        return totalPiecesOnBoard;
    }

    public void setTotalPiecesOnBoard(int totalPiecesOnBoard) {
        this.totalPiecesOnBoard = totalPiecesOnBoard;
    }

    public int getTotalPiecesToPlace() {
        return totalPiecesToPlace;
    }

    public void setTotalPiecesToPlace(int totalPiecesToPlace) {
        this.totalPiecesToPlace = totalPiecesToPlace;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void removeToken(){
        totalPiecesOnBoard--;
    }

    public void tilePlaced(){
        totalPiecesToPlace--;
        totalPiecesOnBoard++;
    }

    public void activateTurn(){
        isTurn = true;
    }

    public void deactivateTurn(){
        isTurn = false;
    }
}
