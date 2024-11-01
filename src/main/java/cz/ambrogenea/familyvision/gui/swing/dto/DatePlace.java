package cz.ambrogenea.familyvision.gui.swing.dto;

import java.util.Date;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record DatePlace(
        Date date,
        String textDate,
        String place
) {

    public String getSimplePlace() {
        return place.split(",")[0];
    }

}
