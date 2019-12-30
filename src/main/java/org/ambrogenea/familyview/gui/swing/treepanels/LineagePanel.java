package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class LineagePanel extends RootFamilyPanel {

    public LineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel() {
        int x;
        if (getConfiguration().isShowSiblings()) {
            x = getWidth() / 2;
        } else {
            x = getConfiguration().getAdultImageWidth() * personModel.getAncestorGenerations() + MARRIAGE_LABEL_WIDTH / 2;
        }
        int y = getHeight() - VERTICAL_GAP;

        drawPerson(x, y, personModel);
        drawSpouseAndSiblings(x, y);

        if (getConfiguration().isShowFathersLineage()) {
            drawFathersFamily(x, y, personModel);
        } else {
            drawMotherFamily(x, y, personModel);
        }
    }

    private void drawSpouseAndSiblings(int x, int y) {
        if (getConfiguration().isShowSpouses()) {
            int spousesShift = drawAllSpouses(x, y, personModel);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundWifes(x, y, personModel, spousesShift);
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(x, y, personModel);
        }
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            addHeraldry(childXPosition, childYPosition, person);
            drawMother(childXPosition, y, person);

            if (person.getFather() != null) {

                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());

                if (person.getAncestorLine().size() < getConfiguration().getGenerationCount()) {
                    if (getConfiguration().isShowSiblings()) {
                        drawSiblingsAroundMother(fatherXPosition, y, person.getFather());
                    }

                    drawFathersFamily(fatherXPosition, y, person.getFather());
                }
            }
        }
    }

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            drawMother(childXPosition, y, person);

            if (person.getFather() != null) {
                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());
            }
            int motherXPosition = childXPosition + (getConfiguration().getAdultImageHeight() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundFather(motherXPosition, y, person.getMother());
            }
            drawFathersFamily(motherXPosition, y, person.getMother());
        }
    }

}
