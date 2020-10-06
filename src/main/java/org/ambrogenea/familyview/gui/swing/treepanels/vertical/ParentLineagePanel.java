package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.Position;
import org.ambrogenea.familyview.gui.swing.tools.PageSetupVertical;
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
    public void drawAncestorPanel(PageSetupVertical setup) {
        int x = setup.getX();
        int y = setup.getY();
        Position child = new Position(x, y);

        if (personModel.getFather() != null) {
            drawParentsLineage(child);
        } else if (personModel.getMother() != null) {
            drawPerson(child, personModel);
            drawSpouseAndSiblings(child);
            drawMotherFamily(child, personModel);
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend(setup.getHeight());
        }
    }

    private void drawParentsLineage(Position child) {
        int parentsY = child.getY() - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(child.getX(), parentsY);

        drawPerson(fatherPosition, personModel.getFather());
        drawFathersFamilyVertical(fatherPosition, personModel.getFather());

        int motherX;
        if (getConfiguration().isShowSiblings()) {
            int fathersSiblings = personModel.getFather().getMaxYoungerSiblings();
            drawSiblings(fatherPosition, personModel.getFather());

            int mothersSiblings = personModel.getMother().getMaxOlderSiblings();
            int siblingsAmount = fathersSiblings + mothersSiblings;
            motherX = child.getX() + getConfiguration().getAdultImageWidth() + Math.max((siblingsAmount + 2) * (getConfiguration().getAdultImageWidth() + Spaces.HORIZONTAL_GAP), getConfiguration().getWideMarriageLabel());
            drawSiblings(new Position(motherX, parentsY), personModel.getMother());
        } else {
            if (personModel.getFather().getFather() == null && personModel.getFather().getMother() == null) {
                motherX = child.getX() + getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth();
            } else {
                motherX = child.getX() + getConfiguration().getAdultImageWidth() + getConfiguration().getCoupleWidthVertical();
            }
        }

        Position motherPosition = new Position(motherX, parentsY);
        drawPerson(motherPosition, personModel.getMother());
        drawFathersFamilyVertical(motherPosition, personModel.getMother());

        int centerXPosition = (fatherPosition.getX() + motherX) / 2;
        Position childPosition = new Position(centerXPosition, child.getY());
        drawPerson(childPosition, personModel);
        drawSpouseAndSiblings(childPosition);

        if (getConfiguration().isShowSpouses() && getConfiguration().isShowChildren()) {
            drawChildren(new Position(centerXPosition, child.getY()), personModel.getSpouseCouple());
        }

        fatherPosition.addX(getConfiguration().getAdultImageWidth() / 2);
        fatherPosition.addY(-getConfiguration().getMarriageLabelHeight() / 2);
        int labelWidth = motherX - fatherPosition.getX() - getConfiguration().getAdultImageWidth() / 2;
        drawLabel(fatherPosition, labelWidth, personModel.getParents().getMarriageDate());
        drawLine(childPosition, new Position(centerXPosition, fatherPosition.getY() + getConfiguration().getMarriageLabelHeight()), Line.LINEAGE);
    }

    private void drawMotherFamily(Position child, AncestorPerson person) {
        int motherY = child.getY() - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
        Position motherPosition = new Position(child.getX(), motherY);

        addLineToParentsVertical(child);
        drawMother(motherPosition, person.getMother(), person.getParents().getMarriageDate());

        if (getConfiguration().isShowSiblings()) {
            drawSiblingsAroundMother(motherPosition, person.getMother());
        }
        drawFathersFamilyVertical(motherPosition, person.getMother());
    }

}
