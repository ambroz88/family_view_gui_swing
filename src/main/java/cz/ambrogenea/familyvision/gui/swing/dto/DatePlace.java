package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.DateSpecification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record DatePlace(
    Date date,
    DateSpecification dateSpecification,
    String place,
    String datePattern
) {

    public String getSimplePlace() {
        return place.split(",")[0];
    }

    public String getLocalizedDate(Locale locale) {
        if (datePattern == null) {
            return "?";
        }
        if (date != null) {
            SimpleDateFormat dtf = new SimpleDateFormat(datePattern, locale);
            if (dateSpecification != null) {
                return dateSpecification.getString(locale) + " " + dtf.format(date);
            }
            return dtf.format(date);
        } else {
            return "";
        }
    }
}
