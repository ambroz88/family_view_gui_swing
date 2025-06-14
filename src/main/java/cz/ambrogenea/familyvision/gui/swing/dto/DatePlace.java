package cz.ambrogenea.familyvision.gui.swing.dto;

import java.util.Date;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record DatePlace(
        Date date,
        String dateText,
        String place,
        String originalPlace,
        String shortName,
        String area,
        String districtAbbrev
) {
}
