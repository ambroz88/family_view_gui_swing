package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersParentsPanel extends RootFamilyPanel {

    public FathersParentsPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel() {
        int x = getConfiguration().getAdultImageWidth() * (personModel.getAncestorGenerations() + 1);
        int y = getHeight() - getConfiguration().getAdultImageHeight();
        drawPerson(x, y, personModel);

        drawFathersFamily(x, y, personModel);
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getFather() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            int fatherXPosition = childXPosition - (getConfiguration().getAdultImageHeight() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            drawPerson(fatherXPosition, y, person.getFather());

            int motherXPosition = childXPosition + (getConfiguration().getAdultImageHeight() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            if (person.getMother() != null) {
                drawPerson(motherXPosition, y, person.getMother());
            }

            drawLabel(childXPosition, y, person.getParents().getMarriageDate());
            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

}
