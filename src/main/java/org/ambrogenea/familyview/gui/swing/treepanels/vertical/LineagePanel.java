package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.components.ResidencePanel;
import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Position;
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

    protected void drawSpouseAndSiblings(Position rootPerson) {
        if (getConfiguration().isShowSpouses()) {
            drawSpouse(rootPerson, personModel);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundMother(rootPerson, personModel);
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(rootPerson, personModel);
        }
    }

    protected void drawFathersFamilyVertical(Position child, AncestorPerson person) {
        if (person.getMother() != null) {

            addLineToParentsVertical(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, person.getSimpleBirthPlace());
            }

            if (person.getFather() != null) {
                drawMother(child, person.getMother(), person.getParents().getMarriageDate());

                Position fatherPosition = drawFather(child, person.getFather());

                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(fatherPosition, person.getFather());
                }

                drawFathersFamilyVertical(fatherPosition, person.getFather());
            } else {
                Position motherPosition = drawFather(child, person.getMother());
                drawFathersFamilyVertical(motherPosition, person.getMother());
                if (getConfiguration().isShowSiblings()) {
                    drawSiblingsAroundMother(motherPosition, person.getMother());
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
