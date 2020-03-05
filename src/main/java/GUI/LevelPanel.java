package GUI;

import Board.RectangularBoard;
import Elements.Empty;
import Elements.IPiece;
import Utility.Vector2d;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.LinkedList;

public class LevelPanel extends JPanel {
    private int defaultTileSize = 100;
    private int width;
    private int height;
    private RectangularBoard board;
    private JSONArray levelArray;
    private ElementTile[][] tiles;
    public List<ElementTile> markedTiles = new LinkedList<>();
    public IPiece chosenPiece = null;
    boolean whiteTurn = true;

    public LevelPanel(JSONArray levelArray) {
        this.levelArray = levelArray;
        this.height = levelArray.length();
        this.width = levelArray.getJSONArray(0).length();
        this.setLayout(new GridLayout(height, width));
        this.setPreferredSize(new Dimension(width * defaultTileSize, height * defaultTileSize));
        this.tiles = new ElementTile[width][height];
        this.board = new RectangularBoard(levelArray, this);

        this.initTiles();
        this.addListeners();
        this.setKeyBindings();
    }

    public void initTiles() {
        ElementTile tile;
        for(int j = 0; j < this.height; j++) {
            for(int i = 0; i < this.width; i++) {
                boolean isWhite = (i%2 != j%2);
                tile =  new ElementTile(this.board.pieces[i][j].getImage(), isWhite, this.defaultTileSize, new Vector2d(i,j));
                this.add(tile);
                this.tiles[i][j] = tile;
            }
        }

    }

    public void updateTile(Vector2d position) {
        getTile(position).changeImage(board.getPiece(position).getImage());
    }

    public void updateTiles() {
        for(int j = 0; j < this.height; j++) {
            for(int i = 0; i < this.width; i++) {
                updateTile(new Vector2d(i,j));
            }
        }
    }

    private void addListeners() {
        LevelPanel levelPanel = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickedPoint = MouseInfo.getPointerInfo().getLocation();
                Point levelCornerPoint = levelPanel.getLocationOnScreen();
                clickedPoint.translate(- levelCornerPoint.x, - levelCornerPoint.y);
                Vector2d location = new Vector2d(clickedPoint.x/defaultTileSize, clickedPoint.y/defaultTileSize);

                ElementTile tile = getTile(location);
                if(markedTiles.contains(tile)) {
                    demarkTiles();
                    chosenPiece.moveTo(location);
                    whiteTurn = !whiteTurn;
                } else {
                    demarkTiles();
                    if(chosenPiece != null && chosenPiece == board.getPiece(location)) {
                        levelPanel.repaint();
                        chosenPiece = null;
                        return;
                    }
                    chosenPiece = board.getPiece(location);
                    if(!(chosenPiece instanceof Empty) && chosenPiece.isWhite() == whiteTurn) {
                        getTile(location).markChosen();
                        chosenPiece.markTilesYouCanMoveTo();
                    }
                }
                levelPanel.repaint();
            }
        });
    }

    private void setKeyBindings() {
        InputMap inputMap = this.getInputMap();
        ActionMap actionMap = this.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("R"), "Reset");
        actionMap.put("Reset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLevel();
            }
        });
        inputMap.put(KeyStroke.getKeyStroke("U"), "Undo");
        actionMap.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
    }

    private void resetLevel() {
        System.out.println("Reseting level");
        this.board = new RectangularBoard(levelArray, this);
        this.updateTiles();
        this.whiteTurn = true;
        this.repaint();
    }

    public void undo() {
        if(this.board.moves.isEmpty())
            return;
        Vector2d from = this.board.moves.getLast().from;
        Vector2d to = this.board.moves.getLast().to;
        this.board.undoMove();
        this.updateTile(from);
        this.updateTile(to);
        whiteTurn = !whiteTurn;
        chosenPiece = null;
        repaint();
    }

    public void demarkTiles() {
        for(ElementTile tile: markedTiles) {
            tile.demark();
        }
        if(this.chosenPiece != null) {
            getTile(this.chosenPiece.getPosition()).demark();
        }
        markedTiles = new LinkedList<>();
    }

    public ElementTile getTile(Vector2d position) {
        return this.tiles[position.x][position.y];
    }


}
