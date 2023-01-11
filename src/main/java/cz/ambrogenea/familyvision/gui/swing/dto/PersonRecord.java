package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.Sex;

import java.util.concurrent.TimeUnit;

public record PersonRecord(
        Position position,
        String id,
        String firstName,
        String surname,
        Sex sex,
        DatePlace birthDatePlace,
        DatePlace deathDatePlace,
        String occupation,
        boolean living,
        boolean directLineage
) {

    public boolean isChild() {
        int deathAge = getAge();
        return deathAge < 8 && deathAge != -1;
    }

    public int getAge() {
        int ageInYears = -1;
        if (birthDatePlace().date() != null && deathDatePlace().date() != null) {
            ageInYears = (int) (TimeUnit.DAYS.convert(
                    deathDatePlace().date().getTime() - birthDatePlace().date().getTime(), TimeUnit.MILLISECONDS
            ) / 365);
        }
        return ageInYears;
    }

}
