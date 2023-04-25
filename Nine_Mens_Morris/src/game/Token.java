package game;

import game.Utils.Colour;

/**
 * @author Priyesh
 *
 * This class is used to represent the token.
 *
 */

public class Token {

    private Colour colour;
    private Position position;

    public Token(Colour colour) {
        this.colour = colour;
    }

    public Token(Colour colour, Position position) {
        this.colour = colour;
        this.position = position;
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
}
