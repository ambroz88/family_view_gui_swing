package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorPanel extends JPanel {

    private static final int FONT_WIDTH = 5;
    private static final int BORDER_WIDTH = 50;

    private final Person model;
    private int horizontalShift;
    private int verticalShift;
    private Graphics2D g2;

    public AncestorPanel(Person model) {
        this.model = model;

    }

    @Override
    protected void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
        verticalShift = getHeight() / (model.getAncestorGenerations() + 2);
        g2.fillOval(getWidth() / 2, getHeight() - verticalShift, 5, 5);
        g2.drawString(model.getName(), getWidth() / 2 - (model.getName().length() * FONT_WIDTH) / 2, getHeight() - verticalShift - 2 * FONT_WIDTH);

        drawGeneration(1, model.getFather());
        drawGeneration(1, model.getMother());
    }

    private void drawGeneration(int generationIndex, Person person) {
        if (person != null && generationIndex <= model.getAncestorGenerations()) {
            int y = getHeight() - verticalShift * (generationIndex + 1);
            int x = (getWidth() - BORDER_WIDTH) / 2;
            int shiftWidth = x;
            int shift;

            for (int i = 1; i < person.getAncestorLine().size(); i++) {
                shift = person.getAncestorLine().get(i);
                shiftWidth = shiftWidth / 2;
                x = x + shift * (shiftWidth);
            }

            g2.fillOval(x + BORDER_WIDTH, y, 5, 5);
            g2.drawString(person.getName(), x + BORDER_WIDTH - (person.getName().length() * FONT_WIDTH) / 2, y - 2 * FONT_WIDTH);
            drawGeneration(generationIndex + 1, person.getFather());
            drawGeneration(generationIndex + 1, person.getMother());
        } else {
            System.out.println("complete");
        }
    }

}
