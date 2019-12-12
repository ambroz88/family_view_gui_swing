package org.ambrogenea.familyview.gui.swing.treepanels;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.MARRIAGE_LABEL_WIDTH;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.VERTICAL_GAP;

import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Person;

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

        if (personModel.getSpouse() != null) {
            int spouseXPosition = x + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
            drawPerson(spouseXPosition, y, personModel.getSpouse());
            lines.add(new Line(x, y, spouseXPosition, y));
            drawLabel(x + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2), y, personModel.getSpouseCouple().getMarriageDate());
        }

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

    private void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;
        int olderSiblingCount = rootChild.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - 2 * HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int youngerSiblingCount = rootChild.getYoungerSiblings().size();
        int startX;
        for (int i = 0; i < youngerSiblingCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            if (rootChild.getSpouse() != null) {
                startX = rootSiblingX + 2 * getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH + 2 * HORIZONTAL_GAP + i * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            } else {
                startX = rootSiblingX + getConfiguration().getAdultImageWidth() + 3 * HORIZONTAL_GAP + i * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            }
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

}
