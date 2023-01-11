package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.Sex;

public record Person(
        String gedcomId,
        String firstName,
        String surname,
        Sex sex,
        DatePlaceSimple birthDatePlace,
        DatePlaceSimple deathDatePlace,
        String occupation
) {
}