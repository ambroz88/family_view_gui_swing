package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.constant.Spaces;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record Arc(
        int centerX,
        int centerY,
        int startX,
        int startY,
        int startAngle
) {

    public static final int RADIUS = Spaces.HORIZONTAL_GAP;
    public static final int ANGLE_SIZE = 90;

}
