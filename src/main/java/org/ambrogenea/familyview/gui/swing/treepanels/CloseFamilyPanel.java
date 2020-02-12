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
        int pictureHeight = calculateHeight();
        int pictureWidth = calculateWidth();
        if (configuration.isShowResidence()) {
            pictureWidth = pictureWidth + 2 * RootFamilyPanel.RESIDENCE_SIZE;
        }

        setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        setSize(new Dimension(pictureWidth, pictureHeight));
    }

    private int calculateHeight() {
        int generationHeight = getConfiguration().getAdultImageHeight() + VERTICAL_GAP;
        return generationHeight * calculateGenerations();
    }

    private int calculateWidth() {
        int childrenLeftWidth = 0;
        int childrenRightWidth = 0;
        int childrenSpouseWidth = 0;
        int parentsHalfWidth = 0;

        int siblingsLeftWidth = getConfiguration().getAdultImageWidth() / 2 + HORIZONTAL_GAP;
        int siblingsRightWidth = getConfiguration().getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (getConfiguration().isShowParents() && !personModel.getParents().isEmpty()) {
            parentsHalfWidth = getConfiguration().getMarriageLabelWidth() / 2 + getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP;
        }

        if (getConfiguration().isShowSiblingsFamily()) {
            siblingsLeftWidth = siblingsLeftWidth + personModel.getOlderSiblings().size() * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
            siblingsRightWidth = siblingsRightWidth + personModel.getYoungerSiblings().size() * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
        }
        if (getConfiguration().isShowSpousesFamily()) {
            int spouseRightIncrease;
            for (int i = 0; i < personModel.getSpouseCouples().size(); i++) {
                spouseRightIncrease = getConfiguration().getSpouseLabelSpace();

                if (getConfiguration().isShowChildren()) {
                    if (i == 0) {
                        childrenLeftWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP)) - getConfiguration().getHalfSpouseLabelSpace();
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP)) + getConfiguration().getHalfSpouseLabelSpace();
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
        if (getConfiguration().isShowChildren() && personModel.getSpouseCouple() != null && !personModel.getSpouseCouple().getChildren().isEmpty()) {
            y = getHeight() - (int) (1.5 * (VERTICAL_GAP + getConfiguration().getAdultImageHeight()));
        }

        int x = calculateXPosition();

        drawPerson(x, y, personModel);

        if (getConfiguration().isShowParents()) {
            drawParents(x, y, personModel);
            if (getConfiguration().isShowHeraldry() && !personModel.getParents().isEmpty()) {
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
            childrenLeftSpace = (int) (personModel.getChildrenCount(0) / 2.0 * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - getConfiguration().getHalfSpouseLabelSpace());
        }
        if (getConfiguration().isShowParents() && !personModel.getParents().isEmpty()) {
            minimalLeftSpace = getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP + getConfiguration().getMarriageLabelWidth() / 2;
        }

        int resultPosition = Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
        if (configuration.isShowResidence()) {
            resultPosition = resultPosition + RootFamilyPanel.RESIDENCE_SIZE;
        }
        return resultPosition;
    }

    private void drawParents(int childXPosition, int childYPosition, AncestorPerson person) {
        if (person.getMother() != null) {
            int halfLabelWidth = getConfiguration().getMarriageLabelWidth() / 2;
            int y = childYPosition - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;

            int motherXPosition;

            if (person.getFather() != null) {
                int fatherXPosition = childXPosition - getConfiguration().getHalfSpouseLabelSpace();
                drawPerson(fatherXPosition, y, person.getFather());

                motherXPosition = childXPosition + getConfiguration().getHalfSpouseLabelSpace();
                drawLabel(childXPosition - halfLabelWidth, childXPosition + halfLabelWidth, y, person.getParents().getMarriageDate());
            } else {
                motherXPosition = childXPosition;
            }

            addLineToParents(childXPosition, childYPosition);
            drawPerson(motherXPosition, y, person.getMother());
        }
    }

    public int calculateGenerations() {
        int generationCount = 1;

        if (getConfiguration().isShowParents() && !personModel.getParents().isEmpty()) {
            generationCount++;
        }
        if (getConfiguration().isShowChildren() && personModel.getAllChildrenCount() > 0) {
            generationCount++;
        }
        return generationCount;
    }

}
