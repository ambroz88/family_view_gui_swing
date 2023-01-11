package cz.ambrogenea.familyvision.gui.swing.enums;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public enum Diagram {
    SCROLL, HERALDRY, STICKY_NOTES, WAVE, DOUBLE_WAVE, OVAL;

    private static final String SCROLL_STRING = "scroll";
    private static final String HERALDRY_STRING = "heraldry";
    private static final String STICKY_NOTES_STRING = "stickyNotes";
    private static final String WAVE_STRING = "wave";
    private static final String DOUBLE_WAVE_STRING = "doubleWave";
    private static final String OVAL_STRING = "oval";

    public static Diagram fromString(String name) {
        return switch (name) {
            case HERALDRY_STRING -> HERALDRY;
            case STICKY_NOTES_STRING -> STICKY_NOTES;
            case WAVE_STRING -> WAVE;
            case DOUBLE_WAVE_STRING -> DOUBLE_WAVE;
            case OVAL_STRING -> OVAL;
            default -> SCROLL;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case SCROLL -> SCROLL_STRING;
            case HERALDRY -> HERALDRY_STRING;
            case STICKY_NOTES -> STICKY_NOTES_STRING;
            case WAVE -> WAVE_STRING;
            case DOUBLE_WAVE -> DOUBLE_WAVE_STRING;
            case OVAL -> OVAL_STRING;
        };
    }

    public static String[] getStrings() {
        return new String[]{
                SCROLL.name(),
                HERALDRY.name(),
                STICKY_NOTES.name(),
                WAVE.name(),
                DOUBLE_WAVE.name(),
                OVAL.name(),
        };
    }

}
