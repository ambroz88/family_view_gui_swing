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
        int x = getConfiguration().getAdultImageWidth() * personModel.getAncestorGenerations() + MARRIAGE_LABEL_WIDTH / 2;
        int y = getHeight() - getConfiguration().getAdultImageHeight();
        drawPerson(x, y, personModel);

        drawFathersFamily(x, y, personModel);
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            drawMother(childXPosition, y, person);

            if (person.getFather() != null) {

                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());

                drawFathersFamily(fatherXPosition, y, person.getFather());
            }
        }
    }

}
