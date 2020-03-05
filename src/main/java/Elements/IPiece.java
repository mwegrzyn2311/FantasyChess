package Elements;

import Utility.Vector2d;

import java.awt.*;

public interface IPiece {
    /**
     *
     */
    public void moveTo(Vector2d destination);
    /**
     *
     */
    public Image getImage();
    /**
     *
     */
    public boolean isInvincible();
    /**
     *
     */
    public void markTilesYouCanMoveTo();
    /**
     *
     */
    public String toShortString();
    /**
     *
     */
    public boolean isWhite();
    /**
     *
     */
    public boolean hasMoved();
    /**
     *
     */
    public void setFirstMove(boolean firstMove);
    /**
     *
     * @return
     */
    public Vector2d getPosition();
    /**
     *
     */
    public void setPosition(Vector2d newPosition);
    /**
     *
     */
    public int getReach();
}
