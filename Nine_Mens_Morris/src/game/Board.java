package game;


import game.Utils.Colour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Position, Integer> boardPositions;
    private Map<Position, Token> occupiedPosition;

    private ObjectProperty<Position> tokenPlacedPosition;

    private Position oldPosition;
    private Position newPosition;


    public Board() {
        boardPositions = new Position().getAllPositions();
        occupiedPosition = new HashMap<>();
        tokenPlacedPosition = new SimpleObjectProperty<>();
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    public Map<Position, Integer> getBoardPositions() {
        return boardPositions;
    }

    public void setBoardPositions(Map<Position, Integer> boardPositions) {
        this.boardPositions = boardPositions;
    }

    public Map<Position, Token> getOccupiedPosition() {
        return occupiedPosition;
    }

    public void setOccupiedPosition(Map<Position, Token> occupiedPosition) {
        this.occupiedPosition = occupiedPosition;
    }

    public Position getTokenPlacedPosition() {
        return tokenPlacedPosition.get();
    }

    public ObjectProperty<Position> tokenPlacedPositionProperty() {
        return tokenPlacedPosition;
    }

    public void setTokenPlacedPosition(Position tokenPlacedPosition) {
        this.tokenPlacedPosition.set(tokenPlacedPosition);
    }

    public boolean validateTokenPlacement( Position newPosition) {
        Integer p1 = boardPositions.get(oldPosition);
        Integer p2 = boardPositions.get(newPosition);

        if (p1 % 8 == 0) {
            // for case when 8 try move to 9
            if (p1 - p2 == -1) {
                return false;
            }
            //for case when 8 move to 1
            if (p1 - p2 == 7) {
                return true;
            }
        }
        //1,9,17 special edge cases when they move to last position in the square
        if (p1 == 1 || p1 == 9 || p1 == 17) {
            if (p1 - p2 == -7) {
                return true;
            }
            if (p1 - p2 == 1) {
                return false;
            }
        }
        //move to neighbour
        if (Math.abs(p1 - p2) == 1) {
            return true;
        }
        //middle linking square movement
        if (p1 % 2 == 0) {
            return Math.abs(p1 - p2) == 8;
        }
        return false;
    }

    //In PLACEMENT phase, new tokens are created
    public void placeNewToken(Position position, Colour colour) {
        if (!occupiedPosition.containsKey(position)) {
            occupiedPosition.put(position, new Token(colour, position));
            System.out.print(occupiedPosition.toString() + "\n");
        } else{
            System.out.print("POSITION ALREADY HAVE TOKEN");
        }
    }

    public void moveToken(Position newPosition){
        //get the token
        Token token = occupiedPosition.get(oldPosition);
        //remove old position from list
        occupiedPosition.remove(oldPosition);

        //update token position and place back into list
        token.setPosition(newPosition);
        occupiedPosition.put(newPosition,token);
    }
}
