package Utility;

import Elements.IPiece;

public class Move {
    public IPiece fromPiece;
    public IPiece toPiece;
    public Vector2d from;
    public Vector2d to;
    public boolean firstMove;
    public boolean castle = false;
    public boolean enPassant = false;
    public boolean promotion = false;

    public Move(IPiece from, IPiece to) {
        this.fromPiece = from;
        this.toPiece = to;
        this.from = fromPiece.getPosition();
        this.to = toPiece.getPosition();
    }

    public boolean isSpecialMove() {
        return (this.castle || this.enPassant || this.promotion);
    }
}
