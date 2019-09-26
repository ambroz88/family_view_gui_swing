package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AncestorPanel extends JPanel {

    private static final int BORDER_WIDTH = 50;
    private static final int CIRCLE_SIZE = 7;

    private final Person model;
    private int verticalShift;
    private Graphics2D g2;

    public AncestorPanel(Person model) {
        this.model = model;

    }

    @Override
    protected void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
        verticalShift = getHeight() / (model.getAncestorGenerations() + 2);

        drawPerson(getWidth() / 2, getHeight() - verticalShift, model);

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

            drawPerson(x, y, person);

            drawGeneration(generationIndex + 1, person.getFather());
            drawGeneration(generationIndex + 1, person.getMother());
        }
    }

    private void drawPerson(int x, int y, Person person) {
        g2.fillOval(x + BORDER_WIDTH, y, CIRCLE_SIZE, CIRCLE_SIZE);
        int fontSize = 14;
        int fontWidth = 6;
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

        if (person.getBirthDate().isEmpty()) {
            g2.drawString(person.getName(), x + BORDER_WIDTH - (person.getName().length() * fontWidth) / 2, y - 7);
        } else {
            g2.drawString(person.getName(), x + BORDER_WIDTH - (person.getName().length() * fontWidth) / 2, y - (fontSize + fontSize / 2));
            fontSize = fontSize - 2;
            g2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, fontSize));
            g2.drawString("* " + person.getBirthDate(), x + BORDER_WIDTH - ((person.getBirthDate().length() + 2) * fontWidth) / 2, y - 7);
        }
    }

}
