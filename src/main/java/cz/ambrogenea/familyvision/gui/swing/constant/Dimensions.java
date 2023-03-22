package cz.ambrogenea.familyvision.gui.swing.constant;

import java.awt.Dimension;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Dimensions {

    public static int SCROLL_IMAGE_WIDTH = 190;
    public static int SCROLL_IMAGE_HEIGHT = 130;
    public static int DOUBLE_WAVE_IMAGE_HEIGHT = 110;
    public static int DOUBLE_WAVE_IMAGE_WIDTH = 160;
    public static int PORTRAIT_IMAGE_WIDTH = 130;
    public static int PORTRAIT_IMAGE_HEIGHT = 130;
    public static int DEFAULT_IMAGE_HEIGHT = 120;
    public static int DEFAULT_IMAGE_WIDTH = 160;

    public final static int SCROLL_SHIFT = 25;
    public final static int LEFT_PANEL_WIDTH = 320;
    public final static int BUTTON_MENU_PANEL_HEIGHT = 90;
    public final static int BUTTON_MENU_HEIGHT = 60;
    private final static int PERSON_SETUP_PANEL_HEIGHT = 250;

    public static Dimension LEFT_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 10, 800);
    public static Dimension SETUP_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, 400);
    public static Dimension DATA_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_HEIGHT);
    public static Dimension PERSON_SETUP_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, PERSON_SETUP_PANEL_HEIGHT + BUTTON_MENU_PANEL_HEIGHT);
    public static Dimension TABLE_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 100, 480);
    public static Dimension TREE_SETUP_DIMENSION = new Dimension(500, 80);
    public static Dimension TREE_BUTTON_DIMENSION = new Dimension(54, 48);
}
