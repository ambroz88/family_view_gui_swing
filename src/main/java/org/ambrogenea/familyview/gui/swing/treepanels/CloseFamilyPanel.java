package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Dimension;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CloseFamilyPanel extends RootFamilyPanel {

    public CloseFamilyPanel(AncestorPerson model, Configuration config) {
        super(model, config);
        calculateSize();
    }

    private void calculateSize() {
        int pictureHeight;
        if (getConfiguration().isShowParents() && getConfiguration().isShowChildren()) {
            pictureHeight = (3 * getConfiguration().getAdultImageHeight() + 3 * VERTICAL_GAP);
        } else if (getConfiguration().isShowParents() || getConfiguration().isShowChildren()) {
            pictureHeight = (2 * getConfiguration().getAdultImageHeight() + 2 * VERTICAL_GAP);
        } else {
            pictureHeight = getConfiguration().getAdultImageHeight() + VERTICAL_GAP;
        }

        int pictureWidth = calculateWidth();

        setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        setSize(new Dimension(pictureWidth, pictureHeight));
    }

    private int calculateWidth() {
        int childrenLeftWidth = 0;
        int childrenRightWidth = 0;
        int childrenSpouseWidth = 0;
        int parentsHalfWidth = 0;

        int siblingsLeftWidth = getConfiguration().getAdultImageWidth() / 2 + HORIZONTAL_GAP;
        int siblingsRightWidth = getConfiguration().getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (getConfiguration().isShowParents()) {
            parentsHalfWidth = MARRIAGE_LABEL_WIDTH / 2 + getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP;
        }

        if (getConfiguration().isShowSiblingsFamily()) {
            siblingsLeftWidth = siblingsLeftWidth + personModel.getOlderSiblings().size() * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
            siblingsRightWidth = siblingsRightWidth + personModel.getYoungerSiblings().size() * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
        }
        if (getConfiguration().isShowSpousesFamily()) {
            int spouseRightIncrease;
            for (int i = 0; i < personModel.getSpouseCouples().size(); i++) {
                spouseRightIncrease = MARRIAGE_LABEL_WIDTH + getConfiguration().getAdultImageWidth();

                if (getConfiguration().isShowChildren()) {
                    if (i == 0) {
                        childrenLeftWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP));
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP));
                    } else if (i == personModel.getSpouseCouples().size() - 1) {
                        childrenSpouseWidth = childrenSpouseWidth + (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP));
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP));
                    } else {
                        childrenRightWidth = personModel.getChildrenCount(i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
                    }
                    childrenRightWidth = childrenRightWidth + SIBLINGS_GAP;
                }
                if (i == personModel.getSpouseCouples().size() - 1) {
                    siblingsRightWidth = childrenSpouseWidth + Math.max(siblingsRightWidth + spouseRightIncrease, childrenRightWidth);
                } else {
                    childrenSpouseWidth = childrenSpouseWidth + Math.max(spouseRightIncrease, childrenRightWidth + HORIZONTAL_GAP);
                }
            }
        }

        int leftWidth = Math.max(parentsHalfWidth, Math.max(siblingsLeftWidth, childrenLeftWidth));
        int rightWidth = Math.max(parentsHalfWidth, siblingsRightWidth);
        return leftWidth + rightWidth;
    }

    public void drawAncestorPanel() {
        int y = getHeight() - VERTICAL_GAP / 2 - getConfiguration().getAdultImageHeight() / 2;
        if (getConfiguration().isShowChildren()) {
            y = getHeight() - 2 * VERTICAL_GAP - getConfiguration().getAdultImageHeight();
        }

        int x = calculateXPosition();

        drawPerson(x, y, personModel);

        if (getConfiguration().isShowParents()) {
            drawParents(x, y, personModel);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(x, y, personModel);
            }
        }

        if (getConfiguration().isShowSpousesFamily()) {
            if (getConfiguration().isShowSiblingsFamily()) {
                if (getConfiguration().isShowChildren()) {
                    int lastSpousePosition = drawAllSpousesWithKids(x, y, personModel);
                    drawSiblingsAroundWifes(x, y, personModel, lastSpousePosition);
                } else {
                    drawAllSpouses(x, y, personModel);
                    drawSiblingsAroundWifes(x, y, personModel, 0);
                }
            } else if (getConfiguration().isShowChildren()) {
                drawAllSpousesWithKids(x, y, personModel);
            } else {
                drawAllSpouses(x, y, personModel);
            }
        } else if (getConfiguration().isShowSiblingsFamily()) {
            drawSiblings(x, y, personModel);
        }
    }

    private int calculateXPosition() {
        int siblingsLeftSpace = 0;
        int childrenLeftSpace = 0;
        int minimalLeftSpace = getConfiguration().getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (getConfiguration().isShowSiblingsFamily()) {
            siblingsLeftSpace = personModel.getOlderSiblings().size() * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) + getConfiguration().getAdultImageWidth() / 2 + SIBLINGS_GAP;
        }
        if (getConfiguration().isShowChildren()) {
            childrenLeftSpace = (int) (personModel.getChildrenCount(0) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) / 2);
        }
        if (getConfiguration().isShowParents()) {
            minimalLeftSpace = getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP + MARRIAGE_LABEL_WIDTH / 2;
        }

        return Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
    }

    private void drawParents(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            int motherXPosition = childXPosition + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            addLineToParents(childXPosition, childYPosition);
            drawPerson(motherXPosition, y, person.getMother());

            if (person.getFather() != null) {
                drawLabel(childXPosition, y, person.getParents().getMarriageDate());

                int fatherXPosition = childXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawPerson(fatherXPosition, y, person.getFather());
            }
        }
    }

}
