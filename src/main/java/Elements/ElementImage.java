package Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public enum ElementImage {
    WHITEPAWN("whitePawn.png"),
    WHITETOWER("whiteTower.png"),
    WHITEKNIGHT("whiteKnight.png"),
    WHITEBISHOP("whiteBishop.png"),
    WHITEQUEEN("whiteQueen.png"),
    WHITEKING("whiteKing.png"),
    BLACKPAWN("blackPawn.png"),
    BLACKTOWER("blackTower.png"),
    BLACKKNIGHT("blackKnight.png"),
    BLACKBISHOP("blackBishop.png"),
    BLACKQUEEN("blackQueen.png"),
    BLACKKING("blackKing.png");

    public Image image;

    ElementImage(String path) {
        try {
            this.image = ImageIO.read(getClass().getResource("/images/" + path));
        } catch(IOException ex) {
            System.out.print(ex + " exception during element image initialization");
        }
    }
}
