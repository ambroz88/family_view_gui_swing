package org.ambrogenea.familyview.gui.swing.model;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jiri Ambroz
 */
public class ImageModel {

    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ImageModel(BufferedImage image, int x, int y, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.height = height;
        double scale = height / (double) image.getHeight();
        this.width = (int) (image.getWidth() * scale);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
