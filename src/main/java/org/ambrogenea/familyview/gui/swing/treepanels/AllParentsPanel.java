package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Color;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AllParentsPanel extends RootFamilyPanel {

    private static final int BORDER_WIDTH = 50;

    private final AncestorPerson model;
    private int verticalShift;

    public AllParentsPanel(AncestorPerson model) {
        setBackground(Color.WHITE);
        this.setLayout(null);
        this.model = model;
    }

    public void drawAncestorPanel() {
        verticalShift = getHeight() / (model.getAncestorGenerations() + 2);
        drawPerson(MINIMAL_WIDTH * (int) Math.pow(2, model.getAncestorGenerations()) / 2, getHeight() - verticalShift, model);

        drawParentsParent(model.getFather());
        drawParentsParent(model.getMother());
    }

    private void drawParentsParent(AncestorPerson person) {
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

            drawParentsParent(person.getFather());
            drawParentsParent(person.getMother());
        }
    }

}
