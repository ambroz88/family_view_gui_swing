package cz.ambrogenea.familyvision.gui.swing.enums;

import java.util.Arrays;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum LineageType {
    ONE, TWO, FOUR, EIGHT, ALL;

    public static String[] getStrings() {

        return Arrays.stream(LineageType.values())
                .map(Enum::name)
                .toArray(String[]::new)
        ;
    }

}
