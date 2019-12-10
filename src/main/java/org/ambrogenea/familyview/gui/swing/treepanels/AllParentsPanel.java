package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AllParentsPanel extends RootFamilyPanel {

    public AllParentsPanel(AncestorPerson model) {
        super(model);
    }

    public void drawAncestorPanel() {
        drawPerson(getWidth() / 2, getHeight() - IMAGE_HEIGHT, model);

        drawParentsParent(model.getFather());
        drawParentsParent(model.getMother());
    }

    private void drawParentsParent(AncestorPerson person) {
        if (person != null) {
            int x = getWidth() / 2;
            int y = getHeight() - IMAGE_HEIGHT - (IMAGE_HEIGHT + VERTICAL_GAP) * (person.getAncestorLine().size() - 1);
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
