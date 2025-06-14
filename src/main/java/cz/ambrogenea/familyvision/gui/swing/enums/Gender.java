package cz.ambrogenea.familyvision.gui.swing.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Gender {
    MALE("M"), FEMALE("F"), UNKNOWN("U");

    private final String type;

    Gender(String gender) {
        type = gender;
    }

    @Override
    public String toString() {
        return type;
    }
}
