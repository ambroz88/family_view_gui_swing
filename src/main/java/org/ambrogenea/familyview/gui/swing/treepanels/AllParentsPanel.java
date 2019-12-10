package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AllParentsPanel extends RootFamilyPanel {

    public AllParentsPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel() {
        drawPerson(getWidth() / 2, getHeight() - getConfiguration().getAdultImageHeight(), personModel);

        drawParentsParent(personModel.getFather());
        drawParentsParent(personModel.getMother());
    }

    private void drawParentsParent(AncestorPerson person) {
        if (person != null) {
            int x = getWidth() / 2;
            int y = getHeight() - getConfiguration().getAdultImageHeight() - (getConfiguration().getAdultImageHeight() + VERTICAL_GAP) * (person.getAncestorLine().size() - 1);
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
