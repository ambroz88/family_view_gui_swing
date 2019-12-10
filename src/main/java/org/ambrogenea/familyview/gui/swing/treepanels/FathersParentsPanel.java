package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersParentsPanel extends RootFamilyPanel {

    public FathersParentsPanel(AncestorPerson model) {
        super(model);
    }

    public void drawAncestorPanel() {
        int x = IMAGE_WIDTH * (model.getAncestorGenerations() + 1);
        int y = getHeight() - IMAGE_HEIGHT;
        drawPerson(x, y, model);

        drawFathersFamily(x, y, model);
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getFather() != null) {
            int y = childYPosition - IMAGE_HEIGHT - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            int fatherXPosition = childXPosition - (IMAGE_HEIGHT / 2 + MARRIAGE_LABEL_WIDTH / 2);
            drawPerson(fatherXPosition, y, person.getFather());

            int motherXPosition = childXPosition + (IMAGE_HEIGHT / 2 + MARRIAGE_LABEL_WIDTH / 2);
            if (person.getMother() != null) {
                drawPerson(motherXPosition, y, person.getMother());
            }

            drawLabel(childXPosition, y, person.getParents().getMarriageDate());
            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

}
