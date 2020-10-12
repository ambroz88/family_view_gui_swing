package org.ambrogenea.familyview.gui.swing.tools;

import static org.ambrogenea.familyview.constant.Spaces.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.constant.Spaces.RESIDENCE_SIZE;
import static org.ambrogenea.familyview.constant.Spaces.SIBLINGS_GAP;
import static org.ambrogenea.familyview.constant.Spaces.VERTICAL_GAP;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PageSetup {

    private final Configuration config;
    private final Paging paging;

    private int pictureWidth;
    private int pictureHeight;
    private int x;
    private int y;

    public PageSetup(Configuration config) {
        this.config = config;
        if (config.isShowCouplesVertical()) {
            paging = new PageSetupVertical(config);
        } else {
            paging = new PageSetupHorizontal(config);
        }
    }

    public void calculateParentLineage(AncestorPerson person) {
        paging.calculateParentLineageHorizontal(person);
        paging.calculateLineageVertical(person);
    }

    public void calculateFatherLineage(AncestorPerson person) {
        paging.calculateFatherLineageHorizontal(person);
        paging.calculateLineageVertical(person);
    }

    public void calculateMotherLineage(AncestorPerson person) {
        paging.calculateMotherLineageHorizontal(person);
        paging.calculateLineageVertical(person);
    }

    public void calculateAllAncestors(AncestorPerson person) {
        paging.calculateAllAncestors(person);
    }

    public void calculateFamily(Configuration config, AncestorPerson person) {
        pictureWidth = calculateFamilyWidth(person);
        x = calculateFamilyX(person);

        int generationHeight = config.getAdultImageHeight() + VERTICAL_GAP;
        pictureHeight = generationHeight * calculateGenerations(person);
        y = calculateFamilyY(person);
    }

    private int calculateFamilyX(AncestorPerson personModel) {
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

        int x = Math.max(minimalLeftSpace, Math.max(siblingsLeftSpace, childrenLeftSpace + HORIZONTAL_GAP));
        if (config.isShowResidence()) {
            x = x + RESIDENCE_SIZE;
        }

        return x;
    }

    private int calculateFamilyY(AncestorPerson personModel) {
        int y = getHeight() - VERTICAL_GAP / 2 - config.getAdultImageHeight() / 2;
        if (config.isShowChildren() && personModel.getSpouseCouple() != null && !personModel.getSpouseCouple().getChildren().isEmpty()) {
            y = getHeight() - (int) (1.5 * (VERTICAL_GAP + config.getAdultImageHeight()));
        }

        return y;
    }

    private int calculateFamilyWidth(AncestorPerson personModel) {
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
        int pictureWidth = leftWidth + rightWidth;
        if (config.isShowResidence()) {
            pictureWidth = pictureWidth + 2 * RESIDENCE_SIZE;
        }

        return pictureWidth;
    }

    public int calculateGenerations(AncestorPerson personModel) {
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
        return paging.getWidth();
    }

    public int getHeight() {
        return paging.getHeight();
    }

    public Position getRootPosition() {
        return paging.getRootPosition();
    }

    public Configuration getConfig() {
        return config;
    }

}
