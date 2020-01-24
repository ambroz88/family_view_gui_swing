package org.ambrogenea.familyview.gui.swing.treepanels;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.MARRIAGE_LABEL_WIDTH;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.RESIDENCE_SIZE;

import org.ambrogenea.familyview.gui.swing.components.ResidencePanel;
import org.ambrogenea.familyview.gui.swing.model.Line;
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

    public void drawAncestorPanel() {
        int x;
        if (getConfiguration().isShowSiblings()) {
            x = getWidth() / 2;
        } else {
            x = getConfiguration().getAdultImageWidth() * Math.min(personModel.getAncestorGenerations(), getConfiguration().getGenerationCount()) + MARRIAGE_LABEL_WIDTH / 2;
            if (getConfiguration().isShowResidence()) {
                x = x + RESIDENCE_SIZE;
            }
        }
        int y = getHeight() - VERTICAL_GAP;

        if (getConfiguration().isShowFathersLineage() && getConfiguration().isShowMothersLineage()) {
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
        int fatherX = x - getConfiguration().getAdultImageWidth() / 2 - MARRIAGE_LABEL_WIDTH / 2;
        drawPerson(fatherX, parentsY, personModel.getFather());
        drawFathersFamily(fatherX, parentsY, personModel.getFather());

        int motherX = fatherX + getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH_LARGER;
        drawPerson(motherX, parentsY, personModel.getMother());
        drawFathersFamily(motherX, parentsY, personModel.getMother());

        int centerXPosition = (fatherX + motherX) / 2;
        drawPerson(centerXPosition, y, personModel);

        drawLongerLabel(centerXPosition, parentsY, personModel.getParents().getMarriageDate());
        lines.add(new Line(fatherX, parentsY, motherX, parentsY));
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

                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());

                if (person.getAncestorLine().size() < getConfiguration().getGenerationCount()) {
                    if (getConfiguration().isShowSiblings()) {
                        drawSiblingsAroundMother(fatherXPosition, y, person.getFather());
                    }

                    drawFathersFamily(fatherXPosition, y, person.getFather());
                }
            }
        }
    }

    private void drawMotherFamily(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            addLineToParents(childXPosition, childYPosition);
            drawMother(childXPosition, y, person);

            if (person.getFather() != null) {
                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());
            }
            int motherXPosition = childXPosition + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
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
