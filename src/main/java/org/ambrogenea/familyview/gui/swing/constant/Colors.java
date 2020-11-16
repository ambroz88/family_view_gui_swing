package org.ambrogenea.familyview.gui.swing.constant;

import java.awt.Color;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public abstract class Colors {

    public static Color LIGHT_BROWN = new Color(210, 180, 140);
    public static Color DARK_BROWN = new Color(139, 69, 19);
    public static Color LIGHT_RED = new Color(255, 69, 0);
    public static Color DARK_RED = new Color(220, 20, 60);
    public static Color LIGHT_GREEN = new Color(173, 255, 47);
    public static Color OLIVE_GREEN = new Color(128, 128, 0);
    public static Color DARK_GREEN = new Color(0, 128, 0);
    public static Color LIGHT_BLUE = new Color(135, 206, 250);
    public static Color DARK_BLUE = new Color(65, 105, 225);
    public static Color LIGHT_GRAY = new Color(211, 211, 211);
    public static Color DARK_GRAY = new Color(128, 128, 128);
    public static Color PINK = new Color(255, 105, 180);
    public static Color PURPLE = new Color(138, 43, 226);
    public static Color YELLOW = new Color(255, 215, 0);

    public static final Color AMBROGENEA_COLOR = new Color(215, 165, 95);
    public static final Color LINE_COLOR = Color.GRAY;

    public static final Color SW_BACKGROUND = new Color(250, 225, 150);
    public static final Color COMPONENT_BACKGROUND = new Color(240, 240, 240);
    public static final Color TABLE_BACKGROUND = new Color(250, 250, 250);
    public static final Color LABEL_BACKGROUND = new Color(250, 250, 250);

    public static Color[] getColors() {
        return new Color[]{LIGHT_RED, DARK_BLUE, LIGHT_GREEN, YELLOW, DARK_BROWN, LIGHT_GRAY, OLIVE_GREEN, DARK_RED, LIGHT_BLUE, PURPLE, LIGHT_BROWN, DARK_GREEN, PINK, DARK_GRAY};
    }
}
