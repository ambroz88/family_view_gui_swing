package cz.ambrogenea.familyvision.gui.swing.dto;

public record Marriage(
        MarriageRectangle rectangle,
        String date,
        int boysCount,
        int girlsCount
) {
}
