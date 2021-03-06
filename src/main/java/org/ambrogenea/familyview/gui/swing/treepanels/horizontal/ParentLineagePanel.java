package org.ambrogenea.familyview.gui.swing.treepanels.horizontal;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ParentLineagePanel extends LineagePanel {

    public ParentLineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    @Override
    public void drawAncestorPanel(PageSetup setup) {
        int x = setup.getX();
        int y = setup.getY();

        if (personModel.getFather() != null) {
            drawParentsLineage(x, y);
        } else if (personModel.getMother() != null) {
            drawPerson(x, y, personModel);
            drawSpouseAndSiblings(x, y);
            drawMotherFamily(x, y, personModel);
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

    private void drawParentsLineage(int x, int y) {
        int parentsY = y - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
        int fatherX = x - getConfiguration().getHalfSpouseLabelSpace();

        drawPerson(fatherX, parentsY, personModel.getFather());
        drawFathersFamily(fatherX, parentsY, personModel.getFather());

        int motherX;
        if (getConfiguration().isShowSiblings()) {
            int fathersSiblings = personModel.getFather().getMaxYoungerSiblings();
            drawSiblings(fatherX, parentsY, personModel.getFather());

            int mothersSiblings = personModel.getMother().getMaxOlderSiblings();
            int siblingsAmount = fathersSiblings + mothersSiblings;
            motherX = x + getConfiguration().getAdultImageWidth() + Math.max((siblingsAmount + 2) * (getConfiguration().getAdultImageWidth() + Spaces.HORIZONTAL_GAP), getConfiguration().getWideMarriageLabel());
            drawSiblings(motherX, parentsY, personModel.getMother());
        } else {
            motherX = x + (getConfiguration().getAdultImageWidth() + getConfiguration().getWideMarriageLabel());
        }

        drawPerson(motherX, parentsY, personModel.getMother());
        drawFathersFamily(motherX, parentsY, personModel.getMother());

        int centerXPosition = (fatherX + motherX) / 2;
        drawPerson(centerXPosition, y, personModel);
        drawSpouseAndSiblings(centerXPosition, y);

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(centerXPosition + getConfiguration().getHalfSpouseLabelSpace(), y, personModel.getSpouseCouple());
        }

        drawLabel(fatherX + getConfiguration().getAdultImageWidth() / 2, motherX - getConfiguration().getAdultImageWidth() / 2, parentsY, personModel.getParents().getMarriageDate());
        lines.add(new Line(centerXPosition, y, centerXPosition, parentsY));
    }

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        int y = childYPosition - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;

        addLineToParents(childXPosition, childYPosition);
        drawMother(childXPosition, y, person);

        if (getConfiguration().isShowSiblings()) {
            drawSiblingsAroundFather(childXPosition, y, person.getMother());
        }
        drawFathersFamily(childXPosition, y, person.getMother());
    }

}
