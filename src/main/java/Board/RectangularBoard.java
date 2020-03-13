package Board;

import Elements.IPiece;
import Elements.*;
import GUI.LevelPanel;
import Utility.Move;
import Utility.Vector2d;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;


public class RectangularBoard {
    public int width;
    public int height;
    public IPiece[][] pieces;
    public LevelPanel levelPanel;
    public Vector2d lowerLeft;
    public Vector2d upperRight;
    public King whiteKing;
    public King blackKing;
    public LinkedList<Move> moves = new LinkedList<>();
    public int knightMaxReach = 3;
    public int pawnMaxReach = 2;
    public List<IPiece> whitePieces = new LinkedList<>();
    public List<IPiece> blackPieces = new LinkedList<>();

    public RectangularBoard(JSONArray levelArray, LevelPanel levelPanel) {
        this.levelPanel = levelPanel;
        this.height = levelArray.length();
        this.width = levelArray.getJSONArray(0).length();
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width-1, height-1);
        this.pieces = new IPiece[this.width][this.height];
        for(int j = 0; j < this.height; j++) {
            JSONArray row = levelArray.getJSONArray(j);
            for(int i = 0; i < this.width; i++){
                String ele = row.getString(i);
                if(ele.equals("")) {
                    this.pieces[i][j] = new Empty(this, new Vector2d(i,j));
                    continue;
                }
                boolean isWhite = ele.charAt(0) == 'B';
                switch (ele.charAt(1)) {
                    case('P'):
                        this.pieces[i][j] = new Pawn(this, new Vector2d(i,j), isWhite);
                        break;
                    case('W'):
                        this.pieces[i][j] = new Tower(this, new Vector2d(i,j), isWhite);
                        break;
                    case('G'):
                        this.pieces[i][j] = new Bishop(this, new Vector2d(i,j), isWhite);
                        break;
                    case('S'):
                        this.pieces[i][j] = new Knight(this, new Vector2d(i,j), isWhite);
                        break;
                    case('H'):
                        this.pieces[i][j] = new Queen(this, new Vector2d(i,j), isWhite);
                        break;
                    case('K'):
                        this.pieces[i][j] = new King(this, new Vector2d(i,j), isWhite);
                        if(isWhite)
                            whiteKing = (King)this.pieces[i][j];
                        else
                            blackKing = (King)this.pieces[i][j];
                        break;
                    default:
                        throw new IllegalArgumentException("Character " + ele.charAt(1) + " does not correspond to any existing element");
                }
                if (this.pieces[i][j].isWhite()) {
                    this.whitePieces.add(this.pieces[i][j]);
                } else {
                    this.blackPieces.add(this.pieces[i][j]);
                }

            }
        }

    }

    public boolean isInBoard(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft));
    }

    public boolean isOccupied(Vector2d position) {
        return (!(getPiece(position) instanceof Empty));
    }

    public boolean isOccupiedByOpponent(Vector2d position, boolean isWhite) {
        return (!(getPiece(position) instanceof Empty) && getPiece(position).isWhite() != isWhite);
    }

    public boolean isOccupiedByInvincibleElement(Vector2d position) {
        return (this.pieces[position.x][position.y].isInvincible());
    }

    public IPiece getPiece(Vector2d position) {
        if(!isInBoard(position))
            return null;
        else
            return this.pieces[position.x][position.y];
    }

    public void setPiece(Vector2d position, IPiece newPiece) {
        newPiece.setPosition(position);
        this.pieces[position.x][position.y] = newPiece;
    }

    public void setPiece(IPiece newPiece) {
        setPiece(newPiece.getPosition(), newPiece);
    }

    public void undoMove(){
        if(this.moves.isEmpty())
            return;
        Move lastMove = this.moves.getLast();
        this.moves.removeLast();
        undoSpecificMove(lastMove);
    }

    public void undoSpecificMove(Move move) {
        move.fromPiece.setFirstMove(move.firstMove);
        if(!(move.toPiece instanceof Empty)) {
            if(move.toPiece.isWhite()) {
                whitePieces.add(move.toPiece);
            } else {
                blackPieces.add(move.toPiece);
            }
        }
        if(!move.isSpecialMove()) {
            this.setPiece(move.from, move.fromPiece);
            this.setPiece(move.to, move.toPiece);
        } else if(move.castle) {
            this.setPiece(move.from, move.fromPiece);
            this.setPiece(move.to, move.toPiece);
        } else if(move.enPassant) {

        } else if(move.promotion) {

        }
    }

    public void makeSpecificMove(Move move) {
        if (!move.isSpecialMove()) {
            this.setPiece(move.to, move.fromPiece);
            this.setPiece(move.from, move.toPiece);
        } else if (move.castle) {
            this.setPiece(move.to, move.fromPiece);
            this.setPiece(move.from, move.toPiece);
        } else if (move.enPassant) {

        } else if (move.promotion) {

        }
    }
}
