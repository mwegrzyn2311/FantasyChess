package GUI;

import Utility.Vector2d;

import javax.swing.*;
import java.awt.*;

public class ElementTile extends JComponent {
    private boolean isWhiteTile;
    private Color tileDefaultColor;
    private Color tileMarkColor = new Color(47, 105, 255);
    private Color tileChosenColor = new Color(28, 49, 255);
    private Color currentColor;
    private Color borderColor = new Color(37, 37, 37);
    private Image image;
    private int tileSize;
    public Vector2d position;

    public ElementTile(Image image, boolean isWhiteTile, int tileSize, Vector2d position) {
        this.isWhiteTile = isWhiteTile;
        this.image = image;
        this.tileSize = tileSize;
        this.position = position;
        this.setPreferredSize(new Dimension(tileSize, tileSize));
        this.setBorder(BorderFactory.createLineBorder(borderColor));
        if(isWhiteTile) {
            this.tileDefaultColor = new Color(255, 255, 255);
        } else{
            this.tileDefaultColor = new Color(28, 127, 11);
        }
        this.currentColor = this.tileDefaultColor;
    }
    public void changeImage(Image newImage) {
        this.image = newImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(currentColor);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.drawImage(this.image, 0,0, null);
    }

    public void mark() {
        this.currentColor = tileMarkColor;
    }

    public void markChosen() {this.currentColor = tileChosenColor;}

    public void demark() {
        this.currentColor = tileDefaultColor;
    }

}
