package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.gui.swing.components.ResidencePanel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class LineagePanel extends RootFamilyPanel {

    public LineagePanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel(PageSetup setup) {
        int x = setup.getX();
        int y = setup.getY();

        if (getConfiguration().isShowFathersLineage() && getConfiguration().isShowMothersLineage() && personModel.getFather() != null) {
            drawParentsLineage(x, y);
        } else {
            drawPerson(x, y, personModel);
            drawSpouseAndSiblings(x, y);
            if (getConfiguration().isShowFathersLineage()) {
                drawFathersFamily(x, y, personModel);
            } else {
                drawMotherFamily(x, y, personModel);
            }
        }

        if (getConfiguration().isShowResidence()) {
            drawResidenceLegend();
        }
    }

    private void drawParentsLineage(int x, int y) {
        int parentsY = y - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;
        int fatherX = x - (getConfiguration().getAdultImageWidth() / 2 + getConfiguration().getWideMarriageLabel() / 2);
        drawPerson(fatherX, parentsY, personModel.getFather());
        drawFathersFamily(fatherX, parentsY, personModel.getFather());

        int motherX;
        if (getConfiguration().isShowSiblings()) {
            int fathersSiblings;
            int mothersSiblings;
            if (personModel.getFather() != null) {
                fathersSiblings = personModel.getFather().getMaxYoungerSiblings();
            } else {
                fathersSiblings = 0;
            }
            if (personModel.getMother() != null) {
                mothersSiblings = personModel.getMother().getMaxOlderSiblings();
            } else {
                mothersSiblings = 0;
            }

            int siblingsAmount = fathersSiblings + mothersSiblings;
            motherX = fatherX + getConfiguration().getAdultImageWidth() + (siblingsAmount + 2) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
        } else {
            motherX = x + (getConfiguration().getAdultImageWidth() / 2 + getConfiguration().getWideMarriageLabel() / 2);
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

    private void drawSpouseAndSiblings(int x, int y) {
        if (getConfiguration().isShowSpouses()) {
            int spousesShift = drawAllSpouses(x, y, personModel);
            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundWifes(x, y, personModel, spousesShift);
            }
        } else if (getConfiguration().isShowSiblings()) {
            drawSiblings(x, y, personModel);
        }
    }

    private void drawFathersFamily(int childXPosition, int childYPosition, AncestorPerson person) {
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

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            drawMother(childXPosition, y, person);

            int motherXPosition;
            if (person.getFather() != null) {
                int fatherXPosition = childXPosition - (getConfiguration().getHalfSpouseLabelSpace());
                drawPerson(fatherXPosition, y, person.getFather());
                motherXPosition = childXPosition + (getConfiguration().getHalfSpouseLabelSpace());
            } else {
                motherXPosition = childXPosition;
            }

            if (getConfiguration().isShowSiblings()) {
                drawSiblingsAroundFather(motherXPosition, y, person.getMother());
            }
            drawFathersFamily(motherXPosition, y, person.getMother());
        }
    }

    private void drawResidenceLegend() {
        ResidencePanel residencePanel = new ResidencePanel(getCityRegister());
        this.add(residencePanel);
        residencePanel.setBounds(HORIZONTAL_GAP, this.getHeight() - residencePanel.getHeight() - HORIZONTAL_GAP, residencePanel.getWidth(), residencePanel.getHeight());
    }

}
