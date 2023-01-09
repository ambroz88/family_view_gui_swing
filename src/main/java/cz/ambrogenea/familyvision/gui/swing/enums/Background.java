package cz.ambrogenea.familyvision.gui.swing.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Background {
    TRANSPARENT, WHITE, PAPER;

    public static String[] getStrings() {
        return new String[]{TRANSPARENT.name(), WHITE.name(), PAPER.name()};
    }
}
