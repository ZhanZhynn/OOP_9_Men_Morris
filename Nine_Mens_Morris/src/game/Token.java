package game;

import game.Utils.Colour;

/**
 * @author Priyesh
 * <p>
 * This class is used to represent the token.
 */

public class Token {

    private Colour colour;
    private Position position;

    private int isPartOfMillCount;

    private int[] millId;

    public Token(Colour colour, Position position) {
        this.colour = colour;
        this.position = position;
        isPartOfMillCount = 0;
        millId = new int[]{0, 0};
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getIsPartOfMillCount() {
        return isPartOfMillCount;
    }

    public void setIsPartOfMillCount(int isPartOfMillCount) {
        this.isPartOfMillCount = isPartOfMillCount;
    }

    public void increaseIsPartOfMillCount() {
        this.isPartOfMillCount = this.isPartOfMillCount + 1;
    }

    public void decreaseIsPartOfMillCount() {
        this.isPartOfMillCount = this.isPartOfMillCount - 1;
    }

    public int[] getMillId() {
        return millId;
    }

    public void setMillId(int[] millId) {
        this.millId = millId;
    }

    public void updateMillId(int id) {
        if (millId[0] != 0) {
            millId[1] = id;
        } else {
            millId[0] = id;
        }
    }

    public void resetMillId(int id){
        if (millId[0]==id){
            millId[0] =0;
        }else if (millId[1]==id){
            millId[1] = 0;
        }
    }

    public void selfMillIdReset(){
        this.millId = new int[]{0,0};
    }

}
