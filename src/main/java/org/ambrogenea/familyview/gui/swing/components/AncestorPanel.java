package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorPanel extends JPanel {

    public static final int MINIMAL_WIDTH = 140;
    public static final int IMAGE_WIDTH = MINIMAL_WIDTH - 20;
    public static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 0.8);
    private static final int BORDER_WIDTH = 50;
    private static final int CIRCLE_SIZE = 7;
    private static final int SPACE = 7;

    private final AncestorPerson model;
    private int verticalShift;

    public AncestorPanel(AncestorPerson model) {
        setBackground(Color.WHITE);
        this.setLayout(null);
        this.model = model;
    }

    public void drawAncestorPanel() {
        verticalShift = getHeight() / (model.getAncestorGenerations() + 2);
        drawPerson(MINIMAL_WIDTH * (int) Math.pow(2, model.getAncestorGenerations()) / 2, getHeight() - verticalShift, model);

        drawGeneration(model.getFather());
        drawGeneration(model.getMother());
    }

    private void drawGeneration(AncestorPerson person) {
        if (person != null) {
            int y = getHeight() - verticalShift * (person.getAncestorLine().size());
            int x = (MINIMAL_WIDTH * (int) Math.pow(2, model.getAncestorGenerations()) - BORDER_WIDTH) / 2;
            int shiftWidth = x;
            int shift;

            for (int i = 1; i < person.getAncestorLine().size(); i++) {
                shift = person.getAncestorLine().get(i);
                shiftWidth = shiftWidth / 2;
                x = x + shift * (shiftWidth);
            }

            drawPerson(x, y, person);

            drawGeneration(person.getFather());
            drawGeneration(person.getMother());
        }
    }

    private void drawPerson(int x, int y, final AncestorPerson person) {
        JPanel personPanel = new PersonPanel(person);
        this.add(personPanel);
        personPanel.setBounds(x - IMAGE_WIDTH / 2, y - IMAGE_HEIGHT / 2, IMAGE_WIDTH, IMAGE_HEIGHT);
        personPanel.repaint();
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }
}
