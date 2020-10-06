package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.model.Position;
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
        Position child = new Position(x, y);

        drawPerson(child, personModel);
        drawSpouseAndSiblings(child);

        if (personModel.getMother() != null) {
            drawMotherFamily(child, personModel);
        }

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(child, personModel.getSpouseCouple());
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

    private void drawMotherFamily(Position childPosition, AncestorPerson person) {
        addLineToParentsVertical(childPosition);
        Position motherPosition = drawFather(childPosition, person.getMother());

        if (person.getFather() != null) {
            drawMother(childPosition, person.getFather(), person.getParents().getMarriageDate());
        }

        if (getConfiguration().isShowSiblings()) {
            drawSiblingsAroundMother(motherPosition, person.getMother());
        }

        if (getConfiguration().isShowHeraldry()) {
            addHeraldry(childPosition, person.getSimpleBirthPlace());
        }
        drawFathersFamilyVertical(motherPosition, person.getMother());
    }

}
