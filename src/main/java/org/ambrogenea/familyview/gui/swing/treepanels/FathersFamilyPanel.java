package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersFamilyPanel extends RootFamilyPanel {

    public FathersFamilyPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel() {
        int x = getWidth() / 2;
        int y = getHeight() - VERTICAL_GAP;
        drawPerson(x, y, personModel);
        drawSpouse(x, y, personModel);
        drawSiblings(x, y, personModel);

        drawFathersFamily(x, y, personModel);
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getFather() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);

            int fatherXPosition = childXPosition - (getConfiguration().getAdultImageHeight() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            drawPerson(fatherXPosition, y, person.getFather());

            drawMother(childXPosition, y, person);
            drawSiblings(fatherXPosition, y, person.getFather());

            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

}
