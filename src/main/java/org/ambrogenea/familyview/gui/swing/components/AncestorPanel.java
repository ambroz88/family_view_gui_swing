package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorPanel extends JPanel {

    public static final int MINIMAL_WIDTH = 120;
    private static final int BORDER_WIDTH = 50;
    private static final int CIRCLE_SIZE = 7;
    private static final int SPACE = 7;

    private final AncestorPerson model;
    private int verticalShift;
    private Graphics2D g2;

    public AncestorPanel(AncestorPerson model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
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

    private void drawPerson(int x, int y, AncestorPerson person) {
        g2.fillOval(x + BORDER_WIDTH, y, CIRCLE_SIZE, CIRCLE_SIZE);
        int fontSize = 14;
        int fontWidth = 6;
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

        if (person.getBirthDate().isEmpty()) {
            g2.drawString(person.getName(), x + BORDER_WIDTH - (person.getName().length() * fontWidth) / 2, y - SPACE);
        } else {
            g2.drawString(person.getName(), x + BORDER_WIDTH - (person.getName().length() * fontWidth) / 2, y - (fontSize + fontSize / 2));
            fontSize = fontSize - 2;
            g2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, fontSize));
            g2.drawString("* " + person.getBirthDate(), x + BORDER_WIDTH - ((person.getBirthDate().length() + 2) * fontWidth) / 2, y - SPACE);
        }
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }
}
