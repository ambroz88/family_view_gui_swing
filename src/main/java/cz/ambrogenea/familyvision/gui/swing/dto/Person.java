package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.Gender;

public record Person(
        Long id,
        String gedcomId,
        String firstName,
        String surname,
        Gender gender,
        DatePlaceSimple birthDatePlace,
        DatePlaceSimple deathDatePlace,
        String occupation
) {
}