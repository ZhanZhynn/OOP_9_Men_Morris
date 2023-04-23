package game;


import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Position, Integer> boardPositions;
    private Map<Position, Token> occupiedPosition;

    private int totalPlayer1Pieces;
    private int totalPlayer2Pieces;


    public Board() {
        boardPositions = new Position().getAllPositions();
        occupiedPosition = new HashMap<>();
    }

    public boolean validateTokenPlacement(Position currentPosition, Position newPosition) {
        Integer p1 = boardPositions.get(currentPosition);
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

    public void placeToken(Position position, Token token) {
        if (!occupiedPosition.containsKey(position)) {
            occupiedPosition.put(position, token);
        } else{
            System.out.printf("POSITION ALREADY HAVE TOKEN");
        }
    }

    public void movedToken(Position oldPosition, Position newPosition, Token token){
        occupiedPosition.remove(oldPosition);
        placeToken(newPosition,token);
    }
}
