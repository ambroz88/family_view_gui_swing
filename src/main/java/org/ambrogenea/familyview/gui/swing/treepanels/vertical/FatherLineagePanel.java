package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.tools.PageSetupVertical;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FatherLineagePanel extends LineagePanel {

    public FatherLineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    @Override
    public void drawAncestorPanel(PageSetupVertical setup) {
        int x = setup.getX();
        int y = setup.getY();

        drawPerson(x, y, personModel);
        drawSpouseAndSiblings(x, y);

        if (personModel.getFather() != null) {
            if (getConfiguration().isShowCouplesVertical()) {
                drawFathersFamilyVertical(x, y, personModel);
            }
        }

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(x + getConfiguration().getHalfSpouseLabelSpace(), y, personModel.getSpouseCouple());
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

}
