package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.LabelType;

public record Marriage(
        Position position,
        String date,
        int boysCount,
        int girlsCount,
        LabelType labelType
) {
}
