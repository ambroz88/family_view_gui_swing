package cz.ambrogenea.familyvision.gui.swing.enums;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Sex {
    MALE("M"), FEMALE("F"), UNKNOWN("U");

    private final String type;

    Sex(String sex) {
        type = sex;
    }

    public static Sex getSex(String sexValue) {
        return switch (sexValue) {
            case "F" -> FEMALE;
            case "U" -> UNKNOWN;
            default -> MALE;
        };
    }

    @Override
    public String toString() {
        return type;
    }
}
