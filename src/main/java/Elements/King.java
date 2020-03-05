package Elements;

import Board.RectangularBoard;
import Utility.BoardDirection;
import Utility.MoveLogic;
import Utility.Vector2d;


public class King extends AbstractPiece {

    public King(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        super(board, initialPosition, isWhite);
        if(isWhite)
            this.image = ElementImage.WHITEKING;
        else
            this.image = ElementImage.BLACKKING;
        this.reach = 1;
    }


    @Override
    public void markTilesYouCanMoveTo() {
        BoardDirection dir = BoardDirection.NORTH;
        for(int i = 0; i < 8; i++) {
            moveLogic.moveAndBeatInDirection(position, dir, reach, isWhite);
            dir = dir.next();
        }
        moveLogic.castleMoves(isWhite);
        super.markTilesYouCanMoveTo();
    }

    @Override
    public String toShortString() {
        return "K";
    }
}
