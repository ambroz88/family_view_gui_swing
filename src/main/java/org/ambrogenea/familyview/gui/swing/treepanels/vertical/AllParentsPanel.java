package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import java.awt.Point;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.tools.PageSetupVertical;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.enums.Sex;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AllParentsPanel extends RootFamilyPanel {

    public AllParentsPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel(PageSetupVertical setup) {
        int x = setup.getX();
        int y = setup.getY();
        drawFirstParents(x, y);
//        drawFirstParentsVertical(x, y);
    }

    private void drawFirstParentsVertical(int childX, int childY) {
        if (personModel.getMother() != null) {

            addLineToParentsVertical(childX, childY);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childX, childY, personModel.getSimpleBirthPlace());
            }

            int motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
            int fatherY = motherY - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();

            if (personModel.getFather() != null) {
                drawPerson(childX, fatherY, personModel.getFather());
                drawParents(personModel.getFather(), childX, fatherY);
            }

            int motherX = childX + getConfiguration().getMarriageLabelWidth();
            drawPerson(motherX, motherY, personModel.getMother());
            drawParents(personModel.getMother(), motherX, motherY);

            drawLabel(childX, motherX, motherY - getConfiguration().getAdultImageHeightAlternative() / 2, personModel.getParents().getMarriageDate());
        }

        drawPerson(childX, childY, personModel);

        if (getConfiguration().isShowSpouses()) {
            drawSpouse(childX, childY, personModel);
            if (getConfiguration().isShowChildren()) {
                drawChildren(childX, childY, personModel.getSpouseCouple());
            }
        }

    }

    private void drawFirstParents(int childX, int childY) {
        if (personModel.getMother() != null) {
            int parentsY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;

            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(childX, childY, personModel.getSimpleBirthPlace());
            }

            int shiftX = getConfiguration().getAdultImageWidth() / 4;
            double motherParentsCount = Math.min(personModel.getFather().getInnerParentsCount(), personModel.getFather().getLastParentsCount());
            double fatherParentsCount = Math.min(personModel.getMother().getInnerParentsCount(), personModel.getMother().getLastParentsCount());
            int fatherX = childX - getConfiguration().getAdultImageWidth() + shiftX
                    - (int) ((getConfiguration().getCoupleWidthVertical() + shiftX) * fatherParentsCount);
            int motherX = childX + getConfiguration().getAdultImageWidth() - shiftX
                    + (int) ((getConfiguration().getCoupleWidthVertical() + shiftX) * motherParentsCount);

            if (personModel.getFather() != null) {
                drawPerson(fatherX, parentsY, personModel.getFather());
                drawGrandParent(personModel.getFather(), fatherX, parentsY);
            }

            drawPerson(motherX, parentsY, personModel.getMother());
            drawGrandParent(personModel.getMother(), motherX, parentsY);

            int halfAdult = getConfiguration().getAdultImageWidth() / 2;
            drawLabel(fatherX + halfAdult, motherX - halfAdult, parentsY + getConfiguration().getMarriageLabelHeight() / 2, personModel.getParents().getMarriageDate());

            int newChildX = (fatherX + motherX) / 2;
            drawPerson(newChildX, childY, personModel);
            drawLine(new Point(newChildX, childY), new Point(newChildX, parentsY), Line.LINEAGE);

            if (getConfiguration().isShowSpouses()) {
                drawSpouse(newChildX, childY, personModel);

                if (getConfiguration().isShowChildren()) {
                    drawChildren(newChildX, childY, personModel.getSpouseCouple());
                }
            }
        }

    }

    private void drawGrandParent(AncestorPerson child, int childX, int childY) {
        if (child.getMother() != null) {
            int motherX;

            int fatherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                    - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();
            int motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
            int labelY = (motherY + fatherY) / 2;

            if (child.getFather() == null) {
                motherX = childX;
                drawPerson(motherX, motherY, child.getMother());
                drawParents(child.getMother(), motherX, motherY);
            } else {
                motherX = childX + getConfiguration().getMarriageLabelWidth();
                drawPerson(childX, fatherY, child.getFather());
                drawParents(child.getFather(), childX, fatherY);

                drawPerson(motherX, motherY, child.getMother());
                drawParents(child.getMother(), motherX, motherY);

                drawLabel(childX, motherX, labelY + getConfiguration().getMarriageLabelHeight() / 2, child.getParents().getMarriageDate());
                drawLine(new Point(childX, childY), new Point(childX, labelY), Line.LINEAGE);

                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(childX, childY, child.getSimpleBirthPlace());
                }
            }
        }
    }

    private void drawParents(AncestorPerson child, int childX, int childY) {
        if (child.getMother() != null) {
            int fatherY;
            int motherY;
            int fatherX;
            int motherX;

            if (child.getFather() == null) {
                motherX = childX;
            } else {
                int shiftX = getConfiguration().getAdultImageWidth() / 4;

                if (child.getSex().equals(Sex.FEMALE)) {
                    fatherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - 2 * getConfiguration().getAdultImageHeightAlternative() - 2 * getConfiguration().getMarriageLabelHeight();
                    motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();

                    double fatherParentsCount;
                    if (child.getFather().getAncestorGenerations() > 1) {
                        fatherParentsCount = child.getFather().getLastParentsCount();
                        fatherX = childX + getConfiguration().getAdultImageWidth() - shiftX
                                + (int) ((getConfiguration().getCoupleWidthVertical() - shiftX) * fatherParentsCount);
                    } else {
                        fatherParentsCount = (child.getFather().getAncestorGenerations());
                        fatherX = childX + getConfiguration().getAdultImageWidth() - shiftX
                                + (int) ((getConfiguration().getAdultImageWidth() * 0.75) * fatherParentsCount);
                    }
                    motherX = fatherX + getConfiguration().getMarriageLabelWidth();
                } else {
                    fatherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();
                    motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;

                    double motherParentsCount;
                    if (child.getMother().getAncestorGenerations() > 1) {
                        motherParentsCount = child.getMother().getLastParentsCount();
                        motherX = childX - getConfiguration().getAdultImageWidth() + shiftX
                                - (int) ((getConfiguration().getCoupleWidthVertical() - shiftX) * motherParentsCount);
                    } else {
                        motherParentsCount = (child.getMother().getAncestorGenerations());
                        motherX = childX - (int) ((getConfiguration().getCoupleWidthVertical() - shiftX) * motherParentsCount);
                    }
                    fatherX = motherX - getConfiguration().getMarriageLabelWidth();
                }

                int labelY = (motherY + fatherY) / 2;

                drawPerson(fatherX, fatherY, child.getFather());
                drawParents(child.getFather(), fatherX, fatherY);

                drawPerson(motherX, motherY, child.getMother());
                drawParents(child.getMother(), motherX, motherY);

                drawLabel(fatherX, motherX, labelY + getConfiguration().getMarriageLabelHeight() / 2, child.getParents().getMarriageDate());
                drawLine(new Point(childX, childY), new Point(fatherX, labelY), Line.LINEAGE);

                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(fatherX, childY, child.getSimpleBirthPlace());
                }
            }

        }
    }

}
