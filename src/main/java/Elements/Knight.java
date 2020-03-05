package Elements;

import Board.RectangularBoard;
import Utility.MoveLogic;
import Utility.Vector2d;

public class Knight extends AbstractPiece {

    public Knight(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        super(board, initialPosition, isWhite);
        if(isWhite)
            this.image = ElementImage.WHITEKNIGHT;
        else
            this.image = ElementImage.BLACKKNIGHT;
        this.reach = 3;
    }


    @Override
    public void markTilesYouCanMoveTo() {
        moveLogic.knightMoves(position, reach, isWhite);
        super.markTilesYouCanMoveTo();
    }

    @Override
    public String toShortString() {
        return "S";
    }
}
