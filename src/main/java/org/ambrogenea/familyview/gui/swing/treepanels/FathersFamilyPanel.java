package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersFamilyPanel extends RootFamilyPanel {

    public FathersFamilyPanel(AncestorPerson model) {
        super(model);
    }

    public void drawAncestorPanel() {
        int x = getWidth() / 2;
        int y = getHeight() - VERTICAL_GAP;
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
            drawSiblings(fatherXPosition, y, person.getFather());

            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

    private void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson father) {

        Person sibling;
        int olderSiblingCount = father.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = father.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (IMAGE_WIDTH + HORIZONTAL_GAP) - 2 * HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int youngerSiblingCount = father.getYoungerSiblings().size();
        for (int i = 0; i < youngerSiblingCount; i++) {
            sibling = father.getYoungerSiblings().get(i);

            int startX = rootSiblingX + 2 * IMAGE_WIDTH + MARRIAGE_LABEL_WIDTH + 2 * HORIZONTAL_GAP + i * (IMAGE_WIDTH + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

}
