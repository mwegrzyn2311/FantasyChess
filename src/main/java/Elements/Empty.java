package Elements;

import Board.RectangularBoard;
import Utility.Vector2d;

import java.awt.*;

public class Empty implements IPiece {
    private RectangularBoard board;
    private Vector2d position;

    public Empty(RectangularBoard board, Vector2d initialPosition) {
        this.board = board;
        this.position = initialPosition;
    }

    @Override
    public void moveTo(Vector2d destination) { }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public void markTilesYouCanMoveTo() {
        board.levelPanel.demarkTiles();
    }

    @Override
    public String toShortString() {
        return " ";
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    @Override
    public boolean hasMoved() {
        return false;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2d newPosition) {this.position = newPosition;}

    @Override
    public void setFirstMove(boolean firstMove) { }

    @Override
    public int getReach() {
        return 0;
    }

    @Override
    public String toString() {
        return "Empty";
    }

    @Override
    public void kill(){}
}
