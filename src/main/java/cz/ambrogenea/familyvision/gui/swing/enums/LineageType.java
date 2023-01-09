package cz.ambrogenea.familyvision.gui.swing.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum LineageType {
    FATHER, MOTHER, PARENTS, ALL;

    public static String[] getStrings() {
        return new String[]{FATHER.name(), MOTHER.name(), PARENTS.name(), ALL.name()};
    }

}
