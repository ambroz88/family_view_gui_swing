package org.ambrogenea.familyview.gui.swing.tools;

import static org.ambrogenea.familyview.gui.swing.constant.Spaces.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.gui.swing.constant.Spaces.RESIDENCE_SIZE;
import static org.ambrogenea.familyview.gui.swing.constant.Spaces.SIBLINGS_GAP;
import static org.ambrogenea.familyview.gui.swing.constant.Spaces.VERTICAL_GAP;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.enums.Diagrams;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetupVertical {

    private final Configuration config;
    private int pictureWidth;
    private int pictureHeight;
    private int x;
    private int y;

    public PageSetupVertical(Configuration configuration) {
        config = configuration;
        pictureWidth = 0;
        pictureHeight = 0;
        x = 0;
        y = 0;
    }

    public void calculateParentLineage(AncestorPerson person) {
        calculateParentLineageHorizontal(person);
        calculateLineageVertical(person);
    }

    public void calculateFatherLineage(AncestorPerson person) {
        calculateFatherLineageHorizontal(person);
        calculateLineageVertical(person);
    }

    public void calculateMotherLineage(AncestorPerson person) {
        calculateMotherLineageHorizontal(person);
        calculateLineageVertical(person);
    }

    public void calculateFamily(Configuration config, AncestorPerson person) {
        calculateFamilyWidth(person);
        calculateFamilyX(person);

        calculateFamilyVertical(person);
    }

    public void calculateAllAncestors(AncestorPerson person) {
//        pictureHeight = (config.getAdultImageHeight() + VERTICAL_GAP) * (Math.min(config.getGenerationCount(), person.getAncestorGenerations()) + 1);
        pictureWidth = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getLastParentsCount()));
        x = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getFather().getLastParentsCount() - person.getFather().getInnerParentsCount() + (person.getFather().getInnerParentsCount() + person.getMother().getInnerParentsCount()) / 2));
        calculateLineageVertical(person);
    }

    private void calculateParentLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension();
        pictureWidth = pictureWidth + config.getAdultImageWidth() + config.getCoupleWidthVertical() + HORIZONTAL_GAP;

        if (config.isShowSiblings()) {
            addFathersSiblingDimension(person);

            if (person.getMother().getMaxOlderSiblings() > 0) {
                //mother parent lineage width
                pictureWidth = pictureWidth + config.getParentImageSpace() * (Math.min(person.getMother().getAncestorGenerations(), config.getGenerationCount()) - 1) + config.getCoupleWidth() - config.getParentImageSpace() + SIBLINGS_GAP;
            }
            addMotherSiblingsWidth(person.getMother());
        }

        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + RESIDENCE_SIZE;
        }
    }

    private void calculateFatherLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension();

        if (config.isShowSiblings()) {
            addFathersSiblingDimension(person);
        }

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            int childrenWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getChildrenCount(0);
            if (x < childrenWidth / 2) {
                x = childrenWidth / 2;
            }
            if (childrenWidth > pictureWidth) {
                pictureWidth = childrenWidth;
            }
        }

        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + RESIDENCE_SIZE;
        }
    }

    private void calculateMotherLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension();
//        addMotherLineageDimension();

        if (config.isShowSiblings()) {
            addMotherSiblingsWidth(person.getMother());

            if (person.getMother().getMaxOlderSiblings() > 0) {
                x = x + SIBLINGS_GAP
                        + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * Math.max(person.getMother().getMaxOlderSiblings(), person.getMaxOlderSiblings());
                if (config.isShowSiblingSpouses()) {
                    x = x + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                }
            }
        }

        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + RESIDENCE_SIZE;
        }
    }

    private void addFatherLineageDimension() {
        x = x + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        pictureWidth = pictureWidth + config.getCoupleWidthVertical() + 2 * SIBLINGS_GAP;
    }

    private void addFathersSiblingDimension(AncestorPerson person) {
        int siblingWidth = 0;
        if (person.getMaxOlderSiblings() > 0) {
            x = x + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() + HORIZONTAL_GAP;
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() + SIBLINGS_GAP;

            if (config.isShowSiblingSpouses()) {
                x = x + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
                siblingWidth = siblingWidth + person.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        if (person.getMaxYoungerSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxYoungerSiblings() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + person.getMaxYoungerSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }
        pictureWidth = pictureWidth + siblingWidth;
    }

    private void addMotherLineageDimension() {
        x = x - config.getAdultImageWidth() - config.getMarriageLabelWidth();
        pictureWidth = pictureWidth - config.getAdultImageWidth() - config.getMarriageLabelWidth() + config.getParentImageSpace();
    }

    private void addMotherSiblingsWidth(AncestorPerson mother) {
        int siblingWidth = 0;
        if (mother.getMaxOlderSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxOlderSiblings() + SIBLINGS_GAP;

            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + mother.getMaxOlderSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }

        if (mother.getMaxYoungerSiblings() > 0) {
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxYoungerSiblings() + SIBLINGS_GAP;
            if (config.isShowSiblingSpouses()) {
                siblingWidth = siblingWidth + mother.getMaxYoungerSiblingsSpouse() * (config.getMarriageLabelWidth() + config.getSiblingImageWidth());
            }
        }
        pictureWidth = pictureWidth + siblingWidth;
    }

    private void calculateLineageVertical(AncestorPerson person) {
        if (config.getAdultDiagram().equals(Diagrams.PERGAMEN)) {
            pictureHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() - (int) (config.getAdultImageHeight() * 0.2) + config.getMarriageLabelHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount());
        } else {
            pictureHeight = config.getAdultImageHeight() + 2 * SIBLINGS_GAP
                    + (2 * config.getAdultImageHeight() + config.getMarriageLabelHeight() + VERTICAL_GAP)
                    * Math.min(person.getAncestorGenerations(), config.getGenerationCount());
        }
        y = pictureHeight - VERTICAL_GAP / 2 - config.getAdultImageHeightAlternative() / 2;

        if (config.isShowSpouses() && person.getSpouse() != null) {
            pictureHeight = pictureHeight + config.getAdultImageHeightAlternative() + config.getMarriageLabelHeight();
            if (config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
                pictureHeight = pictureHeight + config.getSiblingImageHeight() + VERTICAL_GAP + SIBLINGS_GAP;
            }
        }
    }

    private void calculateFamilyWidth(AncestorPerson personModel) {
        int childrenLeftWidth = 0;
        int childrenRightWidth = 0;
        int childrenSpouseWidth = 0;
        int parentsHalfWidth = 0;

        int siblingsLeftWidth = config.getSiblingImageWidth() / 2 + HORIZONTAL_GAP;
        int siblingsRightWidth = config.getSiblingImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            parentsHalfWidth = config.getMarriageLabelWidth() / 2 + config.getAdultImageWidth() + HORIZONTAL_GAP;
        }

        if (config.isShowSiblingsFamily()) {
            siblingsLeftWidth = siblingsLeftWidth + personModel.getOlderSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
            siblingsRightWidth = siblingsRightWidth + personModel.getYoungerSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + HORIZONTAL_GAP;
        }
        if (config.isShowSpousesFamily()) {
            int spouseRightIncrease;
            for (int i = 0; i < personModel.getSpouseCouples().size(); i++) {
                spouseRightIncrease = config.getSpouseLabelSpace();

                if (config.isShowChildren()) {
                    if (i == 0) {
                        childrenLeftWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP)) - config.getHalfSpouseLabelSpace();
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP)) + config.getHalfSpouseLabelSpace();
                    } else if (i == personModel.getSpouseCouples().size() - 1) {
                        childrenSpouseWidth = childrenSpouseWidth + (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP));
                        childrenRightWidth = (int) (personModel.getChildrenCount(i) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP));
                    } else {
                        childrenRightWidth = personModel.getChildrenCount(i) * (config.getSiblingImageWidth() + HORIZONTAL_GAP);
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
            pictureWidth = pictureWidth + 2 * RESIDENCE_SIZE;
        }
    }

    private void calculateFamilyX(AncestorPerson personModel) {
        int siblingsLeftSpace = 0;
        int childrenLeftSpace = 0;
        int minimalLeftSpace = config.getAdultImageWidth() / 2 + HORIZONTAL_GAP;

        if (config.isShowSiblingsFamily()) {
            siblingsLeftSpace = personModel.getOlderSiblings().size() * (config.getSiblingImageWidth() + HORIZONTAL_GAP) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        }
        if (config.isShowChildren()) {
            childrenLeftSpace = (int) (personModel.getChildrenCount(0) / 2.0 * (config.getSiblingImageWidth() + HORIZONTAL_GAP) - config.getHalfSpouseLabelSpace());
        }
        if (config.isShowParents() && !personModel.getParents().isEmpty()) {
            minimalLeftSpace = config.getAdultImageWidth() + HORIZONTAL_GAP + config.getMarriageLabelWidth() / 2;
        }

        x = Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
        if (config.isShowResidence()) {
            x = x + RESIDENCE_SIZE;
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
