package Elements;

import Board.RectangularBoard;
import GUI.LevelPanel;
import Utility.BoardDirection;
import Utility.MoveLogic;
import Utility.Vector2d;

public class Pawn extends AbstractPiece {

    public Pawn(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        super(board, initialPosition, isWhite);
        if(isWhite)
            this.image = ElementImage.WHITEPAWN;
        else
            this.image = ElementImage.BLACKPAWN;
        this.reach = 1;
    }

    public void markTilesYouCanMoveTo() {
        int forwardReach = reach;
        if(!hasMoved) {
            forwardReach = Math.max(2, reach);
        }
        if(isWhite) {
            moveLogic.moveInDirection(position, BoardDirection.NORTH, forwardReach);
            moveLogic.beatInDirection(position, BoardDirection.NORTH_EAST, reach, isWhite);
            moveLogic.beatInDirection(position, BoardDirection.NORTH_WEST, reach, isWhite);
        } else {
            moveLogic.moveInDirection(position, BoardDirection.SOUTH, forwardReach);
            moveLogic.beatInDirection(position, BoardDirection.SOUTH_EAST, reach, isWhite);
            moveLogic.beatInDirection(position, BoardDirection.SOUTH_WEST, reach, isWhite);
        }
        super.markTilesYouCanMoveTo();
    }

    @Override
    public String toShortString() {
        return "P";
    }
}
