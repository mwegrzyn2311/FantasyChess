package Elements;

import Board.RectangularBoard;
import Effects.IEffects;
import Utility.Move;
import Utility.MoveLogic;
import Utility.Vector2d;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPiece implements IPiece {
    protected Vector2d position;
    protected int reach = -1;
    protected boolean invincible = false;
    protected boolean isWhite;
    protected List<IEffects> effects = new LinkedList<IEffects>();
    protected RectangularBoard board;
    protected ElementImage image;
    protected boolean hasMoved = false;
    protected MoveLogic moveLogic;

    public AbstractPiece(RectangularBoard board, Vector2d initialPosition, boolean isWhite) {
        this.position = initialPosition;
        this.isWhite = isWhite;
        this.board = board;
        this.moveLogic = new MoveLogic(board);
    }

    public void kill() {
        System.out.println(this+" at "+position+" gets killed");
        if(isWhite) {
            board.whitePieces.remove(this);
        } else {
            board.blackPieces.remove(this);
        }
    }

    public Image getImage() {
        return this.image.image;
    }

    @Override
    public boolean isInvincible() {
        return this.invincible;
    }

    @Override
    public void markTilesYouCanMoveTo() {
        moveLogic.demarkMovesPuttingYouUnderCheck(this);
    }

    @Override
    public void moveTo(Vector2d newPosition) {
        IPiece destinationPiece = board.getPiece(newPosition);
        Move move = new Move(this, destinationPiece, !this.hasMoved);
        if(!(destinationPiece instanceof Empty)) {
            if(isWhite) {
                destinationPiece.kill();
            } else {
                destinationPiece.kill();
            }
        }
        // Check if move is castle
        if(this instanceof King && Math.abs(newPosition.subtract(position).x) == 2) {
            move.castle = true;
            // We either move king east...
            if(newPosition.subtract(position).x == 2) {
                board.getPiece(new Vector2d(board.width-1, position.y)).moveTo(position.add(new Vector2d(1, 0)));
            }
            // ...or west
            else {
                board.getPiece(new Vector2d(0, position.y)).moveTo(position.add(new Vector2d(-1, 0)));
            }
        }

        board.setPiece(this.position, new Empty(board, this.position));
        board.levelPanel.updateTile(this.position);
        board.setPiece(newPosition, this);
        board.levelPanel.updateTile(newPosition);

        hasMoved = true;
        this.board.moves.addLast(move);
        // We finish by checking if we have checked opponent
        if(moveLogic.check(isWhite)) {
            board.levelPanel.undo();
        }
        if(moveLogic.check(!isWhite)){
            if(isWhite) {
                System.out.println("White checks black");
            } else {
                System.out.println("Black checks white");
            }
            if(!moveLogic.opponentCanMove(!isWhite)) {
                if(isWhite) {
                    System.out.println("White mates black");
                } else {
                    System.out.println("Black mates white");
                }
            }
        } else if(!moveLogic.opponentCanMove(!isWhite)) {
            System.out.println("Draw");
        }
    }

    @Override
    public boolean isWhite() {
        return this.isWhite;
    }

    @Override
    public boolean hasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setFirstMove(boolean firstMove) {
        this.hasMoved = !firstMove; }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2d newPosition) {this.position = newPosition;}

    @Override
    public int getReach() {
        return this.reach;
    }

    @Override
    public String toString() {
        return toShortString();
    }
}
