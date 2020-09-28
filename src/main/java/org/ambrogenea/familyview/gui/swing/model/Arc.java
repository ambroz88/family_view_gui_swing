package org.ambrogenea.familyview.gui.swing.model;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Arc {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;
    public static final int ANGLE_SIZE = 90;

    private final Position leftUpperCorner;
    private final int startAngle;
    private int type;

    public Arc(Position leftUpperCorner, int startAngle) {
        this.leftUpperCorner = leftUpperCorner;
        this.startAngle = startAngle;
        this.type = Line.SIBLINGS;
    }

    public Position getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
