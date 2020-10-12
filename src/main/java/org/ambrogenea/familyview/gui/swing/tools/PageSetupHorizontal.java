package org.ambrogenea.familyview.gui.swing.tools;

import static org.ambrogenea.familyview.constant.Spaces.*;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetupHorizontal implements Paging {

    public final Configuration config;
    public int pictureWidth;
    public int pictureHeight;
    public int x;
    public int y;

    public PageSetupHorizontal(Configuration configuration) {
        config = configuration;
        pictureWidth = 0;
        pictureHeight = 0;
        x = 0;
        y = 0;
    }

    public void calculateAllAncestors(AncestorPerson person) {
        pictureHeight = (config.getAdultImageHeight() + VERTICAL_GAP) * (Math.min(config.getGenerationCount(), person.getAncestorGenerations()) + 1);
        pictureWidth = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getLastParentsCount()));
        x = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (person.getFather().getLastParentsCount() - person.getFather().getInnerParentsCount() + (person.getFather().getInnerParentsCount() + person.getMother().getInnerParentsCount()) / 2));
        y = getHeight() - config.getAdultImageHeight();
    }

    public void calculateParentLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension(person);
        pictureWidth = pictureWidth + config.getWideMarriageLabel() + config.getAdultImageWidth();

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

        if (config.isShowSpouses()) {
            pictureWidth = pictureWidth + config.getParentImageSpace();
        }

        if (config.isShowSiblings()) {
            addFathersSiblingDimension(person);
        }

        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + RESIDENCE_SIZE;
        }
    }

    public void calculateMotherLineageHorizontal(AncestorPerson person) {
        addFatherLineageDimension(person);
        addMotherLineageDimension(person);

        if (config.isShowSiblings()) {
            addMotherSiblingsWidth(person.getMother());

            if (person.getMother().getOlderSiblings().size() > 0) {
                x = x + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * Math.max(person.getMother().getMaxOlderSiblings(), person.getMaxOlderSiblings()) - config.getParentImageSpace();
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
        int ancestorGeneration;
        if (person.getFather().getAncestorGenerations() > 0) {
            ancestorGeneration = person.getFather().getAncestorGenerations() + 1;
        } else {
            ancestorGeneration = person.getMother().getAncestorGenerations() + 1;
        }
        x = x + config.getParentImageSpace() * Math.min(ancestorGeneration, config.getGenerationCount()) + config.getAdultImageWidth() / 2 + SIBLINGS_GAP;
        pictureWidth = pictureWidth + config.getParentImageSpace() * Math.min(ancestorGeneration, config.getGenerationCount()) + config.getCoupleWidth() - config.getParentImageSpace() + 2 * SIBLINGS_GAP;
    }

    public void addFathersSiblingDimension(AncestorPerson person) {
        int siblingWidth = 0;
        if (person.getMaxOlderSiblings() > 0) {
            x = x + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() - config.getParentImageSpace() + HORIZONTAL_GAP;
            siblingWidth = (config.getSiblingImageWidth() + HORIZONTAL_GAP) * person.getMaxOlderSiblings() - config.getParentImageSpace() + SIBLINGS_GAP;

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
            siblingWidth = siblingWidth + (config.getSiblingImageWidth() + HORIZONTAL_GAP) * mother.getMaxOlderSiblings() - config.getParentImageSpace() + SIBLINGS_GAP;

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
        pictureHeight = (config.getAdultImageHeight() + VERTICAL_GAP) * (Math.min(person.getAncestorGenerations(), config.getGenerationCount()) + 1);
        y = pictureHeight - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;

        if (config.isShowSpouses() && config.isShowChildren() && person.getSpouseCouple() != null && !person.getSpouseCouple().getChildren().isEmpty()) {
            pictureHeight = pictureHeight + config.getAdultImageHeight() + VERTICAL_GAP;
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
