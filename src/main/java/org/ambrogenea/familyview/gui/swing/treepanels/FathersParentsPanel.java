package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Color;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.MINIMAL_HEIGHT;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.MINIMAL_WIDTH;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersParentsPanel extends RootFamilyPanel {

    private final AncestorPerson model;

    public FathersParentsPanel(AncestorPerson model) {
        setBackground(Color.WHITE);
        this.setLayout(null);
        this.model = model;
    }

    public void drawAncestorPanel() {
        int x = (MINIMAL_WIDTH - BORDER_WIDTH) * model.getAncestorGenerations() + 2 * BORDER_WIDTH;
        int y = getHeight() - MINIMAL_HEIGHT;
        drawPerson(x, y, model);

        drawFathersFamily(x, y, model);
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getFather() != null) {
            int y = childYPosition - MINIMAL_HEIGHT;

            addLineToParents(childXPosition, childYPosition);
            int fatherXPosition = childXPosition - MINIMAL_WIDTH + BORDER_WIDTH;
            drawPerson(fatherXPosition, y, person.getFather());

            if (person.getMother() != null) {
                drawPerson(childXPosition + MINIMAL_WIDTH - BORDER_WIDTH, y, person.getMother());
            }

            drawLabel(childXPosition, y, person.getParents().getMarriageDate());
            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

}
