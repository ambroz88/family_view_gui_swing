package cz.ambrogenea.familyvision.gui.swing.dto;

public record UploadImageRequest(
        String familyName,
        int imageWidth,
        int imageHeight
) {
}
