package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.tools.PageSetupVertical;
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
    public void drawAncestorPanel(PageSetupVertical setup) {
        int x = setup.getX();
        int y = setup.getY();

        drawPerson(x, y, personModel);
        drawSpouseAndSiblings(x, y);

        if (personModel.getMother() != null) {
            drawMotherFamily(x, y, personModel);
        }

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(x, y, personModel.getSpouseCouple());
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        int motherY = childYPosition - getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;

        addLineToParentsVertical(childXPosition, childYPosition);
        drawPerson(childXPosition, motherY, person.getMother());

        if (person.getFather() != null) {
            drawFatherVertical(childXPosition, motherY, person);
        }

        if (getConfiguration().isShowSiblings()) {
            drawSiblingsAroundFather(childXPosition, motherY, person.getMother());
        }

        if (getConfiguration().isShowHeraldry()) {
            addHeraldry(childXPosition, childYPosition, person.getSimpleBirthPlace());
        }
        drawFathersFamilyVertical(childXPosition, motherY, person.getMother());
    }

}
