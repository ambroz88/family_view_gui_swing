package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Dimension;

import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CloseFamilyPanel extends RootFamilyPanel {

    private PageSetup setup;

    public CloseFamilyPanel(AncestorPerson model, Configuration config) {
        super(model, config);
        calculateSize();
    }

    private void calculateSize() {
        setup = new PageSetup(configuration);
        setup.calculateFamily(configuration, personModel);
        setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));
        setSize(new Dimension(setup.getWidth(), setup.getHeight()));
    }

    public void drawAncestorPanel() {
        int x = setup.getX();
        int y = setup.getY();

        drawPerson(x, y, personModel);

        if (getConfiguration().isShowParents()) {
            drawParents(x, y, personModel);
            if (getConfiguration().isShowHeraldry() && !personModel.getParents().isEmpty()) {
                addHeraldry(x, y, personModel.getSimpleBirthPlace());
            }
        }

        if (getConfiguration().isShowSpousesFamily()) {
            if (getConfiguration().isShowSiblingsFamily()) {
                if (getConfiguration().isShowChildren()) {
                    int lastSpousePosition = drawAllSpousesWithKids(x, y, personModel);
                    drawSiblingsAroundWifes(x, y, personModel, lastSpousePosition);
                } else {
                    drawAllSpouses(x, y, personModel);
                    drawSiblingsAroundWifes(x, y, personModel, 0);
                }
            } else if (getConfiguration().isShowChildren()) {
                drawAllSpousesWithKids(x, y, personModel);
            } else {
                drawAllSpouses(x, y, personModel);
            }
        } else if (getConfiguration().isShowSiblingsFamily()) {
            drawSiblings(x, y, personModel);
        }
    }

    private void drawParents(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int halfLabelWidth = getConfiguration().getMarriageLabelWidth() / 2;
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            int motherXPosition;

            if (person.getFather() != null) {
                int fatherXPosition = childXPosition - getConfiguration().getHalfSpouseLabelSpace();
                drawPerson(fatherXPosition, y, person.getFather());

                motherXPosition = childXPosition + getConfiguration().getHalfSpouseLabelSpace();
                drawLabel(childXPosition - halfLabelWidth, childXPosition + halfLabelWidth, y, person.getParents().getMarriageDate());
            } else {
                motherXPosition = childXPosition;
            }

            addLineToParents(childXPosition, childYPosition);
            drawPerson(motherXPosition, y, person.getMother());
        }
    }

    public int calculateGenerations() {
        int generationCount = 1;

        if (getConfiguration().isShowParents() && !personModel.getParents().isEmpty()) {
            generationCount++;
        }
        if (getConfiguration().isShowChildren() && personModel.getAllChildrenCount() > 0) {
            generationCount++;
        }
        return generationCount;
    }

}
