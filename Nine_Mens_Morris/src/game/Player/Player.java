package Player;

import Action.Action;

abstract class Player {

    private boolean isTurn;
    private Action action;
    private int totalPiecesOnBoard;
    private int totalPiecesToPlace;
    private String tokenColour;


    public Player() {
    }

    public Player(String tokenColour) {
        this.tokenColour = tokenColour;
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

    public String getTokenColour() {
        return tokenColour;
    }

    public void setTokenColour(String tokenColour) {
        this.tokenColour = tokenColour;
    }

    private void playerInit(){
        totalPiecesOnBoard = 0;
        totalPiecesToPlace = 9;
    }
}
