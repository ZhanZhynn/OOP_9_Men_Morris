package game;

import java.util.*;

/**
 * @author Priyesh
 *
 * This class is used to represent the position of the board.
 *
 * It contains the x and y coordinates of the position.
 *
 * It contains methods related to the position of the board.
 *
 */

public class Position {

    private int x;
    private int y;

    /**
     * Constructor for a position object which sets the position in the form (x, y)
     *
     * @param x
     *            position in the x-direction(horizontal)
     * @param y
     *            position in the y-direction (vertical)
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * This method is used to get all the valid positions of the board according to the column and row of grid pane.
     *
     * @return allPositions
     */
    public Map<Position, Integer> getAllPositions(){
        Map<Position, Integer> allPositions = new HashMap<>();

        Position p22 = new Position(2,2);
        Position p23 = new Position(2,3);
        Position p24 = new Position(2,4);
        Position p34 = new Position(3,4);
        Position p44 = new Position(4,4);
        Position p43 = new Position(4,3);
        Position p42 = new Position(4,2);
        Position p32 = new Position(3,2);

        Position p11 = new Position(1,1);
        Position p13 = new Position(1,3);
        Position p15 = new Position(1,5);
        Position p35 = new Position(3,5);
        Position p55 = new Position(5,5);
        Position p53 = new Position(5,3);
        Position p51 = new Position(5,1);
        Position p31 = new Position(3,1);

        Position p00 = new Position(0,0);
        Position p03 = new Position(0,3);
        Position p06 = new Position(0,6);
        Position p36 = new Position(3,6);
        Position p66 = new Position(6,6);
        Position p63 = new Position(6,3);
        Position p60 = new Position(6,0);
        Position p30 = new Position(3,0);


        allPositions.put(p22, 1);
        allPositions.put(p23, 2);
        allPositions.put(p24, 3);
        allPositions.put(p34, 4);
        allPositions.put(p44, 5);
        allPositions.put(p43, 6);
        allPositions.put(p42, 7);
        allPositions.put(p32, 8);
        allPositions.put(p11, 9);
        allPositions.put(p13, 10);
        allPositions.put(p15, 11);
        allPositions.put(p35, 12);
        allPositions.put(p55, 13);
        allPositions.put(p53, 14);
        allPositions.put(p51, 15);
        allPositions.put(p31, 16);
        allPositions.put(p00, 17);
        allPositions.put(p03, 18);
        allPositions.put(p06, 19);
        allPositions.put(p36, 20);
        allPositions.put(p66, 21);
        allPositions.put(p63, 22);
        allPositions.put(p60, 23);
        allPositions.put(p30, 24);


        return allPositions;
    }

}
