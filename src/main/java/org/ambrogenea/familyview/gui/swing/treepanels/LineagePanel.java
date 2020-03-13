package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.gui.swing.components.ResidencePanel;
import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public abstract class LineagePanel extends RootFamilyPanel {

    public LineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public abstract void drawAncestorPanel(PageSetup setup);

    protected void drawSpouseAndSiblings(int x, int y) {
        if (getConfiguration().isShowSpouses()) {
            int spousesShift = drawAllSpouses(x, y, personModel);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundWifes(x, y, personModel, spousesShift);
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(x, y, personModel);
        }
    }

    protected void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childXPosition, childYPosition, person);
            }
            drawMother(childXPosition, y, person);

            if (person.getFather() != null) {

                int fatherXPosition = childXPosition - (getConfiguration().getHalfSpouseLabelSpace());
                drawPerson(fatherXPosition, y, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(fatherXPosition, y, person.getFather());
                }

                drawFathersFamily(fatherXPosition, y, person.getFather());
            } else {
                drawFathersFamily(childXPosition, y, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(childXPosition, y, person.getMother());
                }
            }

        }
    }

    protected void drawResidenceLegend(int parentPanelHeight) {
        ResidencePanel residencePanel = new ResidencePanel(getCityRegister());
        this.add(residencePanel);
        residencePanel.setBounds(HORIZONTAL_GAP, parentPanelHeight - residencePanel.getHeight() - HORIZONTAL_GAP, residencePanel.getWidth(), residencePanel.getHeight());
    }

}
