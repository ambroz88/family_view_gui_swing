package org.ambrogenea.familyview.gui.swing.constant;

import java.awt.Dimension;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Dimensions {

    public final static int SCROLL_SHIFT = 10;
    public final static int LEFT_PANEL_WIDTH = 320;
    public final static int BUTTON_MENU_PANEL_HEIGHT = 90;
    public final static int BUTTON_MENU_HEIGHT = 60;
    private final static int PERSON_SETUP_PANEL_HEIGHT = 200;

    public static Dimension LEFT_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 10, 800);
    public static Dimension SETUP_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, 2 * BUTTON_MENU_PANEL_HEIGHT + BUTTON_MENU_HEIGHT + 220);
    public static Dimension DATA_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_HEIGHT);
    public static Dimension TREE_TYPE_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_PANEL_HEIGHT);
    public static Dimension PERSON_SETUP_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, PERSON_SETUP_PANEL_HEIGHT + BUTTON_MENU_PANEL_HEIGHT);
//    public static Dimension PERSON_BOX_SETUP_DIMENSION = new Dimension(LEFT_PANEL_WIDTH - 10, PERSON_SETUP_PANEL_HEIGHT);
    public static Dimension TABLE_SCROLL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, 500);
    public static Dimension TABLE_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 100, 490);

    public static Dimension TREE_SETUP_DIMENSION = new Dimension(500, 80);
    public static Dimension TREE_BUTTON_DIMENSION = new Dimension(54, 48);
    public static Dimension BUTTON_DIMENSION = new Dimension(44, 44);

}
