package org.ambrogenea.familyview.gui.swing.constant;

import java.awt.Dimension;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Dimensions {

    private final static int LEFT_PANEL_WIDTH = 290;
    private final static int BUTTON_MENU_HEIGHT = 60;
    private final static int BUTTON_MENU_PANEL_HEIGHT = 100;

    public static Dimension LEFT_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 10, 800);
    public static Dimension SETUP_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, 2 * BUTTON_MENU_PANEL_HEIGHT + BUTTON_MENU_HEIGHT + 10);
    public static Dimension DATA_PANEL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_HEIGHT);
    public static Dimension TREE_TYPE_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_PANEL_HEIGHT);
    public static Dimension PERSON_SETUP_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, BUTTON_MENU_PANEL_HEIGHT);
    public static Dimension TABLE_SCROLL_DIMENSION = new Dimension(LEFT_PANEL_WIDTH, 500);
    public static Dimension TABLE_DIMENSION = new Dimension(LEFT_PANEL_WIDTH + 100, 490);

    public static Dimension TREE_SETUP_DIMENSION = new Dimension(500, BUTTON_MENU_PANEL_HEIGHT);
    public static Dimension TREE_BUTTON_DIMENSION = new Dimension(54, 48);
    public static Dimension BUTTON_DIMENSION = new Dimension(44, 44);

}
