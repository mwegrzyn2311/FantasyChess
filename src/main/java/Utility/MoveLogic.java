package Utility;

import Board.RectangularBoard;
import Elements.*;
import GUI.ElementTile;
import GUI.LevelPanel;

import java.util.Iterator;


public class MoveLogic {
    private RectangularBoard board;
    private LevelPanel levelPanel;

    public MoveLogic(RectangularBoard board) {
        this.board = board;
        this.levelPanel = board.levelPanel;
    }

    public void moveInDirection(Vector2d position, BoardDirection dir, int reach) {
        Vector2d destination = position;
        Vector2d oneStep = dir.toUnitVector();
        if(reach == -1) {
            while(true) {
                destination = destination.add(oneStep);
                if(!moveStep(destination, board))
                    break;
            }
        } else {
            for(int i = 0; i < reach; i++) {
                destination = destination.add(oneStep);
                if(!moveStep(destination, board))
                    break;
            }
        }
     }
    // Returns false if loop should be stopped
    private boolean moveStep(Vector2d destination, RectangularBoard board) {
        if(board.isInBoard(destination) && !board.isOccupied(destination)) {
            ElementTile tile = levelPanel.getTile(destination);
            tile.mark();
            levelPanel.markedTiles.add(tile);
            return true;
        } else {
            return false;
        }
    }

    public void moveAndBeatInDirection(Vector2d position, BoardDirection dir, int reach, boolean isWhite) {
        Vector2d destination = position;
        Vector2d oneStep = dir.toUnitVector();
        if(reach == -1) {
            while(true) {
                destination = destination.add(oneStep);
                if(!moveAndBeatStep(destination, isWhite))
                    break;
            }
        } else {
            for(int i = 0; i < reach; i++) {
                destination = destination.add(oneStep);
                if(!moveAndBeatStep(destination, isWhite))
                    break;
            }
        }
    }
    // Returns false if loop should be stopped
    private boolean moveAndBeatStep(Vector2d destination, boolean isWhite) {
        if(board.isInBoard(destination) && !board.isOccupied(destination)) {
            ElementTile tile = levelPanel.getTile(destination);
            tile.mark();
            levelPanel.markedTiles.add(tile);
            return true;
        } else if(board.isInBoard(destination) && board.isOccupiedByOpponent(destination, isWhite)){
            ElementTile tile = levelPanel.getTile(destination);
            tile.mark();
            levelPanel.markedTiles.add(tile);
            return false;
        } else {
            return false;
        }
    }

    public void beatInDirection(Vector2d position, BoardDirection dir, int reach, boolean isWhite) {
        Vector2d destination = position;
        Vector2d oneStep = dir.toUnitVector();
        if(reach == -1) {
            while(true){
                destination = destination.add(oneStep);
                if(!beatStep(destination, isWhite))
                    break;
            }
        } else {
            for (int i = 0; i < reach; i++) {
                destination = destination.add(oneStep);
                if (!beatStep(destination, isWhite))
                    break;
            }
        }
    }
    // Returns false if loop should be stopped
    private boolean beatStep(Vector2d destination, boolean isWhite) {
        if(board.isInBoard(destination) && !board.isOccupied(destination)) {
            return true;
        } else if(board.isInBoard(destination) && board.isOccupiedByOpponent(destination, isWhite)){
            ElementTile tile = levelPanel.getTile(destination);
            tile.mark();
            levelPanel.markedTiles.add(tile);
            return false;
        } else {
            return false;
        }
    }

    public void knightMoves(Vector2d position, int reach, boolean isWhite) {
        Vector2d destination;
        for(int i = 1-reach; i <= reach-1; i++) {
            if(i == 0)
                continue;
            int j = reach - Math.abs(i);
            Vector2d oneStep = new Vector2d(i,j);
            destination = position.add(oneStep);
            knightStep(destination, isWhite);

            oneStep = new Vector2d(i,-j);
            destination = position.add(oneStep);
            knightStep(destination, isWhite);
        }
    }
    public void knightStep(Vector2d destination, boolean isWhite) {
        if(board.isInBoard(destination) && (!board.isOccupied(destination) || board.isOccupiedByOpponent(destination, isWhite))) {
            ElementTile tile = levelPanel.getTile(destination);
            tile.mark();
            levelPanel.markedTiles.add(tile);
        }
    }

    public void castleMoves(boolean isWhite) {
        if(isWhite) {
            if(board.whiteKing.hasMoved())
                return;
        } else {
            if(board.blackKing.hasMoved())
                return;
        }
        checkCastleLine(isWhite);
    }
    private void checkCastleLine(boolean isWhite) {
        int row;
        if(isWhite) {
            row = board.height-1;
        } else {
            row = 0;
        }
        int lastColumn = board.width-1;
        Vector2d kingPosition;
        if(isWhite) {
            kingPosition = board.whiteKing.getPosition();
        } else {
            kingPosition = board.blackKing.getPosition();
        }
        Vector2d suspectedTowerPosition = new Vector2d(lastColumn, row);
        IPiece suspectedTower = board.getPiece(suspectedTowerPosition);
        if(suspectedTower instanceof Tower && !suspectedTower.hasMoved() && suspectedTower.isWhite() == isWhite) {
            Vector2d rightStep = new Vector2d(1,0);
            Vector2d position = kingPosition.add(rightStep);
            boolean canCastle = true;
            while(!position.equals(suspectedTowerPosition)) {
                if(!(board.getPiece(position) instanceof Empty)) {
                    canCastle = false;
                    break;
                }
                position = position.add(rightStep);
            }
            if(canCastle) {
                ElementTile tile = levelPanel.getTile(kingPosition.add(new Vector2d(2,0)));
                tile.mark();
                levelPanel.markedTiles.add(tile);
            }
        }
        suspectedTowerPosition = new Vector2d(0, row);
        suspectedTower = board.getPiece(suspectedTowerPosition);
        if(suspectedTower instanceof Tower && !suspectedTower.hasMoved() && suspectedTower.isWhite() == isWhite) {
            Vector2d leftStep = new Vector2d(-1,0);
            Vector2d position = kingPosition.add(leftStep);
            boolean canCastle = true;
            while(!position.equals(suspectedTowerPosition)) {
                if(!(board.getPiece(position) instanceof Empty)) {
                    canCastle = false;
                    break;
                }
                position = position.add(leftStep);
            }
            if(canCastle) {
                ElementTile tile = levelPanel.getTile(kingPosition.add(new Vector2d(-2,0)));
                tile.mark();
                levelPanel.markedTiles.add(tile);
            }
        }
    }

    public boolean check(boolean isWhite) {
        King king;
        if(isWhite) {
            king = board.whiteKing;
        } else {
            king = board.blackKing;
        }
        Vector2d kingPosition = king.getPosition();
        return(checkedByKnight(kingPosition, isWhite) || checkedByTower(kingPosition, isWhite) || checkedByBishop(kingPosition, isWhite) || checkedByPawn(kingPosition, isWhite));
    }
    private boolean checkedByKnight(Vector2d kingPosition, boolean isWhite) {
        for(int reach = 1; reach <= board.knightMaxReach; reach++) {
            for(int i = 1-reach; i <= reach-1; i++) {
                if(reach != 0 && i==0)
                    continue;
                int j = reach-Math.abs(i);
                IPiece suspectedKnight = board.getPiece(kingPosition.add(new Vector2d(i,j)));
                if(suspectedKnight instanceof Knight && suspectedKnight.isWhite() != isWhite && suspectedKnight.getReach() == reach)
                    return true;
                j = -j;
                suspectedKnight = board.getPiece(kingPosition.add(new Vector2d(i,j)));
                if(suspectedKnight instanceof Knight && suspectedKnight.isWhite() != isWhite && suspectedKnight.getReach() == reach)
                    return true;
            }
        }
        return false;
    }
    private boolean checkedByTower(Vector2d kingPosition, boolean isWhite) {
        BoardDirection dir = BoardDirection.NORTH;
        for(int i = 0; i < 4; i++) {
            Vector2d position = kingPosition.add(dir.toUnitVector());
            int reach = 1;
            while(board.isInBoard(position)) {
                IPiece piece = board.getPiece(position);
                if((piece instanceof Tower || piece instanceof Queen || piece instanceof King) && piece.isWhite() != isWhite && (piece.getReach() == -1 || piece.getReach() >= reach)) {
                    return true;
                } else if(!(piece instanceof Empty)) {
                    break;
                }
                position = position.add(dir.toUnitVector());
                reach++;
            }
            dir = dir.next().next();
        }
        return false;
    }
    private boolean checkedByBishop(Vector2d kingPosition, boolean isWhite) {
        BoardDirection dir = BoardDirection.NORTH_EAST;
        for(int i = 0; i < 4; i++) {
            Vector2d position = kingPosition.add(dir.toUnitVector());
            int reach = 1;
            while(board.isInBoard(position)) {
                IPiece piece = board.getPiece(position);
                if((piece instanceof Bishop || piece instanceof Queen || piece instanceof King) && piece.isWhite() != isWhite && (piece.getReach() == -1 || piece.getReach() >= reach)) {
                    return true;
                } else if(!(piece instanceof Empty)) {
                    break;
                }
                position = position.add(dir.toUnitVector());
                reach++;
            }
            dir = dir.next().next();
        }
        return false;
    }
    private boolean checkedByPawn(Vector2d kingPosition, boolean isWhite) {
        BoardDirection dir;
        if(isWhite) {
            dir = BoardDirection.NORTH_WEST;
        } else {
            dir = BoardDirection.SOUTH_EAST;
        }
        for(int j = 0; j <= 1; j++) {
            Vector2d position = kingPosition.add(dir.toUnitVector());
            for(int i = 1; i <= board.pawnMaxReach; i++) {
                IPiece suspectedPawn = board.getPiece(position);
                if(suspectedPawn instanceof Pawn && suspectedPawn.isWhite() != isWhite && (suspectedPawn.getReach() == -1 || suspectedPawn.getReach() >= i))
                    return true;
                else if(!(suspectedPawn instanceof Empty) || suspectedPawn == null)
                    break;
                position = position.add(dir.toUnitVector());
            }
            dir = dir.next().next();
        }
        return false;
    }

    public void demarkMovesPuttingYouUnderCheck(IPiece piece) {
        // TODO: Add if statements for castle / enPassant, etc.
        Iterator<ElementTile> iter = levelPanel.markedTiles.iterator();
        while(iter.hasNext()) {
            ElementTile tile = iter.next();
            Move move = new Move(piece, board.getPiece(tile.position));
            board.makeSpecificMove(move);
            if(check(piece.isWhite())) {
                tile.demark();
                iter.remove();
            }
            board.undoSpecificMove(move);
        }
    }
}
