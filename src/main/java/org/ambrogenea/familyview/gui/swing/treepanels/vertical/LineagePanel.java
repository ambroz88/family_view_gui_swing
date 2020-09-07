package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.components.ResidencePanel;
import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.tools.PageSetupVertical;
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

    public abstract void drawAncestorPanel(PageSetupVertical setup);

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

    protected void drawFathersFamilyVertical(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int motherY = childYPosition - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;

            addLineToParentsVertical(childXPosition, childYPosition);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childXPosition, childYPosition, person.getSimpleBirthPlace());
            }

            int fatherYPosition = motherY - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();

            if (person.getFather() != null) {
                drawMotherVertical(childXPosition, motherY, person);

                drawPerson(childXPosition, fatherYPosition, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(childXPosition, childYPosition, person.getFather());
                }

                drawFathersFamilyVertical(childXPosition, fatherYPosition, person.getFather());
            } else {
                drawPerson(childXPosition, fatherYPosition, person);
                drawFathersFamilyVertical(childXPosition, fatherYPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(childXPosition, childYPosition, person.getMother());
                }
            }

        }
    }

    protected void drawResidenceLegend(int parentPanelHeight) {
        ResidencePanel residencePanel = new ResidencePanel(getCityRegister());
        this.add(residencePanel);
        residencePanel.setBounds(Spaces.HORIZONTAL_GAP, parentPanelHeight - residencePanel.getHeight() - Spaces.HORIZONTAL_GAP, residencePanel.getWidth(), residencePanel.getHeight());
    }

}
