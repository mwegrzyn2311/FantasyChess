package Elements;

import Board.RectangularBoard;
import Utility.BoardDirection;
import Utility.MoveLogic;
import Utility.Vector2d;

public class Queen extends AbstractPiece {

    public Queen(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        super(board, initialPosition, isWhite);
        if(isWhite)
            this.image = ElementImage.WHITEQUEEN;
        else
            this.image = ElementImage.BLACKQUEEN;
    }

    @Override
    public void markTilesYouCanMoveTo() {
        BoardDirection dir = BoardDirection.NORTH;
        for(int i = 0; i < 8; i++) {
            moveLogic.moveAndBeatInDirection(position, dir, reach, isWhite);
            dir = dir.next();
        }
        super.markTilesYouCanMoveTo();
    }

    @Override
    public String toShortString() {
        return "H";
    }
}
