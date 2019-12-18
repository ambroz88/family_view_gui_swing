package org.ambrogenea.familyview.gui.swing.treepanels;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.MARRIAGE_LABEL_WIDTH;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.VERTICAL_GAP;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class OneFamilyPanel extends RootFamilyPanel {

    public OneFamilyPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel() {
        int x = (personModel.getOlderSiblings().size() + 1) * getConfiguration().getAdultImageWidth() + getConfiguration().getAdultImageWidth() / 2;
        int y = 2 * VERTICAL_GAP + getConfiguration().getAdultImageHeight();
        drawPerson(x, y, personModel);

        drawSpouse(x, y, personModel);

        drawSiblings(x, y, personModel);
        drawParents(x, y, personModel);
    }

    private void drawParents(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            if (person.getFather() != null) {

                addLineToParents(childXPosition, childYPosition);
                drawLabel(childXPosition, y, person.getParents().getMarriageDate());

                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());

                int motherXPosition = childXPosition + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(motherXPosition, y, person.getMother());
            }
        }
    }

}
