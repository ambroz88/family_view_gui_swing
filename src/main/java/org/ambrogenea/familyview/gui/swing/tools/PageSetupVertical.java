package org.ambrogenea.familyview.gui.swing.tools;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetupVertical implements Paging {

    public final Configuration config;
    public int pictureWidth;
    public int pictureHeight;
    public int x;
    public int y;

    public PageSetupVertical(Configuration configuration) {
        config = configuration;
        pictureWidth = 0;
        pictureHeight = 0;
        x = 0;
        y = 0;
    }

    public void calculateAllAncestors(AncestorPerson person) {
//        pictureHeight = (config.getAdultImageHeight() + VERTICAL_GAP) * (Math.min(config.getGenerationCount(), person.getAncestorGenerations()) + 1);
        pictureWidth = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getLastParentsCount()));
        x = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getFather().getLastParentsCount() - person.getFather().getInnerParentsCount() + (person.getFather().getInnerParentsCount() + person.getMother().getInnerParentsCount()) / 2));
        calculateLineageVertical(person);
    }

    public void calculateParentLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension(person);
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

    public void calculateFatherLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension(person);

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

    public void calculateMotherLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension(person);
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

    public void addFatherLineageDimension(AncestorPerson person) {
        x = x + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        pictureWidth = pictureWidth + config.getCoupleWidthVertical() + 2 * SIBLINGS_GAP;
    }

    public void addFathersSiblingDimension(AncestorPerson person) {
        int siblingWidth = 0;
        if (person.getMaxOlderSiblings() > 0) {
            int olderSiblingsCount;
            if (config.isShowFathersLineage() && config.isShowMothersLineage()) {
                olderSiblingsCount = Math.max(person.getMaxOlderSiblings(), person.getFather().getAllSiblingsCount());
            } else {
                olderSiblingsCount = person.getMaxOlderSiblings();
            }

            x = x + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * olderSiblingsCount + HORIZONTAL_GAP;
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * olderSiblingsCount + SIBLINGS_GAP;

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

    public void addMotherLineageDimension(AncestorPerson person) {
        x = x - config.getAdultImageWidth() - config.getMarriageLabelWidth();
        pictureWidth = pictureWidth - config.getAdultImageWidth() - config.getMarriageLabelWidth() + config.getParentImageSpace();
    }

    public void addMotherSiblingsWidth(AncestorPerson mother) {
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

    public void calculateLineageVertical(AncestorPerson person) {
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

    public int getWidth() {
        return pictureWidth;
    }

    public int getHeight() {
        return pictureHeight;
    }

    public Position getRootPosition() {
        return new Position(x, y);
    }

}
