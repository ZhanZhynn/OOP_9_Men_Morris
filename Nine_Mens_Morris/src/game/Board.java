package game;


import game.Utils.Colour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Priyesh
 *
 * This class is used to represent the board in the game.
 * It contains the positions of the board and the tokens placed on the board.
 * It also contains the logic to validate the placement of the tokens.
 *
 */

public class Board {

    private Map<Position, Integer> boardPositions;
    private Map<Position, Token> occupiedPosition;

    private ObjectProperty<Position> tokenPlacedPosition;

    private Position oldPosition;


    /**
     * Constructor for the board class which initializes the board positions and the occupied positions.
     *
     */
    public Board() {
        boardPositions = new Position().getAllPositions();
        occupiedPosition = new HashMap<>();
        tokenPlacedPosition = new SimpleObjectProperty<>();
    }

    /**
     * Getter to get current position of the token
     */
    public Position getOldPosition() {
        return oldPosition;
    }

    /**
     * Setter to set current position of the token
     */
    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    /**
     * A Map of all the positions on the board and their corresponding position index
     */
    public Map<Position, Integer> getBoardPositions() {
        return boardPositions;
    }

    /**
     * Setter to set the board positions
     */
    public void setBoardPositions(Map<Position, Integer> boardPositions) {
        this.boardPositions = boardPositions;
    }

    /**
     * A Map of all the positions on the board which are occupied by the tokens
     */
    public Map<Position, Token> getOccupiedPosition() {
        return occupiedPosition;
    }

    /**
     * Setter to set the occupied positions
     */
    public void setOccupiedPosition(Map<Position, Token> occupiedPosition) {
        this.occupiedPosition = occupiedPosition;
    }

    /**
     * This method is used to place a token on the board
     */
    public Position getTokenPlacedPosition() {
        return tokenPlacedPosition.get();
    }

    public ObjectProperty<Position> tokenPlacedPositionProperty() {
        return tokenPlacedPosition;
    }

    public void setTokenPlacedPosition(Position tokenPlacedPosition) {
        this.tokenPlacedPosition.set(tokenPlacedPosition);
    }

    /**
     * This method is used to validate whether a token can be placed at the new position from the current position
     *
     * @param newPosition
     *            the position on the board where the token is to be placed
     */
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

    /**
     * This method is used to place a new token on the board
     * In PLACEMENT phase, new tokens are created and placed on the board
     *
     * @param position
     *           the position on the board where the token is to be placed
     *
     * @param colour
     *      the colour of the token to be placed
     */
    //
    public void placeNewToken(Position position, Colour colour) {
        if (!occupiedPosition.containsKey(position)) {
            occupiedPosition.put(position, new Token(colour, position));
            System.out.print(occupiedPosition.toString() + "\n");
        } else{
            System.out.print("POSITION ALREADY HAVE TOKEN");
        }
    }

    /**
     * This method is used to move a token from one position to another
     * In MOVEMENT phase, tokens are moved from one position to another
     *
     * @param newPosition
     *           the position on the board where the token is to be placed
     */
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
