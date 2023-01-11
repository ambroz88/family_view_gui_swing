package cz.ambrogenea.familyvision.gui.swing.dto;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record ResidenceDto(
        Position position,
        String city,
        String date,
        int number
) {
}
