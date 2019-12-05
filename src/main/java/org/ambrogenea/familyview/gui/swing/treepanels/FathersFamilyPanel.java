package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Color;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FathersFamilyPanel extends RootFamilyPanel {

    private static final int BORDER_WIDTH = 50;
    private static final int SIBLING_SHIFT = 15;

    private final AncestorPerson model;

    public FathersFamilyPanel(AncestorPerson model) {
        setBackground(Color.WHITE);
        this.setLayout(null);
        this.model = model;
    }

    public void drawAncestorPanel() {
        int x = getWidth() / 2 + MINIMAL_WIDTH / 2;
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

            drawSiblings(fatherXPosition, y, person.getFather());

            drawFathersFamily(fatherXPosition, y, person.getFather());
        }
    }

    private void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson father) {

        Person sibling;
        int olderSiblingCount = father.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = father.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * MINIMAL_WIDTH - BORDER_WIDTH;
            drawPerson(startX, rootSiblingY - SIBLING_SHIFT, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int youngerSiblingCount = father.getYoungerSiblings().size();
        for (int i = 0; i < youngerSiblingCount; i++) {
            sibling = father.getYoungerSiblings().get(i);

            int startX = rootSiblingX + 2 * MINIMAL_WIDTH + i * MINIMAL_WIDTH + 2 * BORDER_WIDTH;
            drawPerson(startX, rootSiblingY + SIBLING_SHIFT, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

}
