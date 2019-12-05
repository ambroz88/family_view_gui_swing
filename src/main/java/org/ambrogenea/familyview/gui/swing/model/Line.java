package org.ambrogenea.familyview.gui.swing.model;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Line {

    public static final int LINEAGE = 2;
    public static final int SIBLINGS = 1;

    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private int type;

    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.type = 2;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
