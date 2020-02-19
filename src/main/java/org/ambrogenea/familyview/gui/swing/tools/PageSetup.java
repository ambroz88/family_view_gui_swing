package org.ambrogenea.familyview.gui.swing.tools;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.SIBLINGS_GAP;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.VERTICAL_GAP;

import org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetup {

    private final Configuration config;
    private int pictureWidth;
    private int pictureHeight;
    private int x;
    private int y;

    public PageSetup(Configuration configuration) {
        config = configuration;
        pictureWidth = 0;
        pictureHeight = 0;
        x = 0;
        y = 0;
    }

    public void calculateLineage(AncestorPerson person) {
        calculateLineageHorizontal(person);
        calculateLineageVertical(person);
    }

    public void calculateFamily(Configuration config, AncestorPerson person) {
        calculateFamilyWidth(person);
        calculateFamilyX(person);

        calculateFamilyVertical(person);
    }

    private void calculateLineageHorizontal(AncestorPerson person) {
        if (config.isShowSiblings()) {
            if (config.isShowFathersLineage() && config.isShowMothersLineage()) {
                pictureWidth = (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * (person.getFather().getMaxOlderSiblings() + person.getFather().getMaxYoungerSiblings() + Math.min(person.getAncestorGenerations(), config.getGenerationCount()) + person.getMother().getMaxOlderSiblings() + person.getMother().getMaxYoungerSiblings() + Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount()));
                x = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 1) + (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * person.getFather().getMaxOlderSiblings() + config.getAdultImageWidth() + config.getMarriageLabelWidth() / 2 + SIBLINGS_GAP;
            } else if (config.isShowFathersLineage()) {
                pictureWidth = (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * (person.getFather().getMaxOlderSiblings() + person.getFather().getMaxYoungerSiblings() + Math.min(person.getAncestorGenerations(), config.getGenerationCount()) + 2);
                x = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 1) + (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * person.getFather().getMaxOlderSiblings() + config.getAdultImageWidth() + config.getMarriageLabelWidth() / 2 + SIBLINGS_GAP;
            } else {
                pictureWidth = (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * (person.getMother().getMaxOlderSiblings() + person.getMother().getMaxYoungerSiblings() + Math.min(person.getMother().getAncestorGenerations() + 1, config.getGenerationCount()) + 2);
                x = config.getParentImageSpace() * (Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount())) + (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * person.getMother().getMaxOlderSiblings() - config.getMarriageLabelWidth() / 2 + SIBLINGS_GAP;
            }
        } else {
            if (config.isShowFathersLineage() && config.isShowMothersLineage()) {
                pictureWidth = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount())) + config.getWideMarriageLabel() + 2 * (config.getAdultImageWidth() + RootFamilyPanel.SIBLINGS_GAP);
                x = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getAdultImageWidth() + config.getWideMarriageLabel() / 2 + SIBLINGS_GAP;
            } else if (config.isShowFathersLineage()) {
                pictureWidth = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getMarriageLabelWidth() + 2 * (config.getAdultImageWidth() + RootFamilyPanel.SIBLINGS_GAP);
                x = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getAdultImageWidth() + config.getMarriageLabelWidth() / 2 + SIBLINGS_GAP;
            } else {
                pictureWidth = config.getParentImageSpace() * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) - 2) + config.getMarriageLabelWidth() + 2 * (config.getAdultImageWidth() + RootFamilyPanel.SIBLINGS_GAP);
                x = config.getParentImageSpace() * (Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount())) - config.getMarriageLabelWidth() / 2 + SIBLINGS_GAP;
            }
        }
        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + RootFamilyPanel.RESIDENCE_SIZE;
        }
    }

    private void calculateLineageVertical(AncestorPerson person) {
        pictureHeight = (config.getAdultImageHeight() + RootFamilyPanel.VERTICAL_GAP) * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) + 1);
        y = pictureHeight - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            pictureHeight = pictureHeight + config.getAdultImageHeight() + VERTICAL_GAP;
        }
    }

    private void calculateFamilyWidth(AncestorPerson personModel) {
        int childrenLeftWidth = 0;
        int childrenRightWidth = 0;
        int childrenSpouseWidth = 0;
        int parentsHalfWidth = 0;

        int siblingsLeftWidth = config.getAdultImageWidth() / 2 + HORIZONTAL_GAP;
        int siblingsRightWidth = config.getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            parentsHalfWidth = config.getMarriageLabelWidth() / 2 + config.getAdultImageWidth() + HORIZONTAL_GAP;
        }

        if (config.isShowSiblingsFamily()) {
            siblingsLeftWidth = siblingsLeftWidth + personModel.getOlderSiblings().size() * (config.getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
            siblingsRightWidth = siblingsRightWidth + personModel.getYoungerSiblings().size() * (config.getAdultImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
        }
        if (config.isShowSpousesFamily()) {
            int spouseRightIncrease;
            for (int i = 0; i < personModel.getSpouseCouples().size(); i++) {
                spouseRightIncrease = config.getSpouseLabelSpace();

                if (config.isShowChildren()) {
                    if (i == 0) {
                        childrenLeftWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getAdultImageWidth() + HORIZONTAL_GAP)) - config.getHalfSpouseLabelSpace();
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getAdultImageWidth() + HORIZONTAL_GAP)) + config.getHalfSpouseLabelSpace();
                    } else if (i == personModel.getSpouseCouples().size() - 1) {
                        childrenSpouseWidth = childrenSpouseWidth + (int) (personModel.getChildrenCount(i) / 2.0 * (config.getAdultImageWidth() + HORIZONTAL_GAP));
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getAdultImageWidth() + HORIZONTAL_GAP));
                    } else {
                        childrenRightWidth = personModel.getChildrenCount(i) * (config.getAdultImageWidth() + HORIZONTAL_GAP);
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
        pictureWidth = leftWidth + rightWidth;
        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + 2 * RootFamilyPanel.RESIDENCE_SIZE;
        }
    }

    private void calculateFamilyX(AncestorPerson personModel) {
        int siblingsLeftSpace = 0;
        int childrenLeftSpace = 0;
        int minimalLeftSpace = config.getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowSiblingsFamily()) {
            siblingsLeftSpace = personModel.getOlderSiblings().size() * (config.getAdultImageWidth() + HORIZONTAL_GAP) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        }
        if (config.isShowChildren()) {
            childrenLeftSpace = (int) (personModel.getChildrenCount(0) / 2.0 * (config.getAdultImageWidth() + HORIZONTAL_GAP) - config.getHalfSpouseLabelSpace());
        }
        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            minimalLeftSpace = config.getAdultImageWidth() + HORIZONTAL_GAP + config.getMarriageLabelWidth() / 2;
        }

        x = Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
        if (config.isShowResidence()) {
            x = x + RootFamilyPanel.RESIDENCE_SIZE;
        }
    }

    private void calculateFamilyVertical(AncestorPerson personModel) {
        int generationHeight = config.getAdultImageHeight() + VERTICAL_GAP;
        pictureHeight = generationHeight * calculateGenerations(personModel);

        y = getHeight() - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;
        if (config.isShowChildren() && personModel.getSpouseCouple() != null && !personModel.getSpouseCouple().getChildren().isEmpty()) {
            y = getHeight() - (int) (1.5 * (VERTICAL_GAP + config.getAdultImageHeight()));
        }

    }

    private int calculateGenerations(AncestorPerson personModel) {
        int generationCount = 1;

        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            generationCount++;
        }
        if (config.isShowChildren() && personModel.getAllChildrenCount() > 0) {
            generationCount++;
        }
        return generationCount;
    }

    public int getWidth() {
        return pictureWidth;
    }

    public int getHeight() {
        return pictureHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
