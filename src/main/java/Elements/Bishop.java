package Elements;

import Board.RectangularBoard;
import Utility.BoardDirection;
import Utility.MoveLogic;
import Utility.Vector2d;

public class Bishop extends AbstractPiece {

    public Bishop(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        super(board, initialPosition, isWhite);
        if(isWhite)
            this.image = ElementImage.WHITEBISHOP;
        else
            this.image = ElementImage.BLACKBISHOP;
    }

    @Override
    public void markTilesYouCanMoveTo() {
        BoardDirection dir = BoardDirection.NORTH_EAST;
        for(int i = 0; i < 4; i++) {
            moveLogic.moveAndBeatInDirection(position, dir, reach, isWhite);
            dir = dir.next().next();
        }
        super.markTilesYouCanMoveTo();
    }

    @Override
    public String toShortString() {
        return "G";
    }
}
