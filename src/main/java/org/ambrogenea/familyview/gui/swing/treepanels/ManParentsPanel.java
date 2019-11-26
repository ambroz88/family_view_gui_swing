package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Color;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ManParentsPanel extends RootFamilyPanel {

    private static final int BORDER_WIDTH = 50;

    private final AncestorPerson model;
    private int verticalShift;

    public ManParentsPanel(AncestorPerson model) {
        setBackground(Color.WHITE);
        this.setLayout(null);
        this.model = model;
    }

    public void drawAncestorPanel() {
        verticalShift = getHeight() / (model.getAncestorGenerations() + 2);
        drawPerson((MINIMAL_WIDTH - BORDER_WIDTH) * model.getAncestorGenerations() + 2 * BORDER_WIDTH, getHeight() - verticalShift, model);

        drawFathersParents(model);
    }

    private void drawFathersParents(AncestorPerson person) {
        if (person.getFather() != null) {
            int y = getHeight() - verticalShift * (person.getFather().getAncestorLine().size());
            int x = (MINIMAL_WIDTH - BORDER_WIDTH) * person.getFather().getAncestorGenerations() + 2 * BORDER_WIDTH;

            if (person.getMother() != null) {
                drawPerson(x + 2 * (MINIMAL_WIDTH - BORDER_WIDTH), y, person.getMother());
            }

            drawPerson(x, y, person.getFather());
            drawFathersParents(person.getFather());
        }
    }

}
