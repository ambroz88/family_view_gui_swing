package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MotherLineagePanel extends LineagePanel {

    public MotherLineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    @Override
    public void drawAncestorPanel(PageSetup setup) {
        int x = setup.getX();
        int y = setup.getY();

        drawPerson(x, y, personModel);
        drawSpouseAndSiblings(x, y);

        if (personModel.getMother() != null) {
            drawMotherFamily(x, y, personModel);
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

        addLineToParents(childXPosition, childYPosition);
        int motherXPosition = drawMother(childXPosition, y, person);

        if (person.getFather() != null) {
            int fatherXPosition = childXPosition - (getConfiguration().getHalfSpouseLabelSpace());
            drawPerson(fatherXPosition, y, person.getFather());
        }

        if (getConfiguration().isShowSiblings()) {
            drawSiblingsAroundFather(motherXPosition, y, person.getMother());
        }

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(childXPosition + getConfiguration().getHalfSpouseLabelSpace(), y, personModel.getSpouseCouple());
        }

        drawFathersFamily(motherXPosition, y, person.getMother());
    }

}
