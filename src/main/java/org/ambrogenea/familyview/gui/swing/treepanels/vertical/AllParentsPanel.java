package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.Position;
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
        Position child = new Position(x, y);
        drawFirstParents(child);
//        drawFirstParentsVertical(child);
    }

    private void drawFirstParentsVertical(Position child) {
        if (personModel.getMother() != null) {

            addLineToParentsVertical(child);
            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, personModel.getSimpleBirthPlace());
            }

            if (personModel.getFather() != null) {
                Position fatherPosition = drawFather(child, personModel.getFather());
                drawParents(personModel.getFather(), fatherPosition);
            }

            Position motherPosition = drawMother(child, personModel.getMother(), personModel.getParents().getMarriageDate());
            drawParents(personModel.getMother(), motherPosition);
        }

        drawPerson(child, personModel);

        if (getConfiguration().isShowSpouses()) {
            drawSpouse(child, personModel);
            if (getConfiguration().isShowChildren()) {
                drawChildren(child, personModel.getSpouseCouple());
            }
        }

    }

    private void drawFirstParents(Position child) {
        if (personModel.getMother() != null) {
            int parentsY = child.getY() - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;

            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(child, personModel.getSimpleBirthPlace());
            }

            int shiftX = getConfiguration().getAdultImageWidth() / 4;
            double motherParentsCount = Math.min(personModel.getFather().getInnerParentsCount(), personModel.getFather().getLastParentsCount());
            double fatherParentsCount = Math.min(personModel.getMother().getInnerParentsCount(), personModel.getMother().getLastParentsCount());
            int fatherX = child.getX() - getConfiguration().getAdultImageWidth() + shiftX
                    - (int) ((getConfiguration().getCoupleWidthVertical() + shiftX) * fatherParentsCount);
            int motherX = child.getX() + getConfiguration().getAdultImageWidth() - shiftX
                    + (int) ((getConfiguration().getCoupleWidthVertical() + shiftX) * motherParentsCount);

            if (personModel.getFather() != null) {
                Position fatherPosition = new Position(fatherX, parentsY);
                drawPerson(fatherPosition, personModel.getFather());
                drawGrandParent(personModel.getFather(), fatherPosition);
            }

            Position motherPosition = new Position(motherX, parentsY);
            drawPerson(motherPosition, personModel.getMother());
            drawGrandParent(personModel.getMother(), motherPosition);

            int halfAdult = getConfiguration().getAdultImageWidth() / 2;
            int labelWidth = motherX - fatherX - getConfiguration().getAdultImageWidth();
            drawLabel(new Position(fatherX + halfAdult, parentsY - getConfiguration().getMarriageLabelHeight() / 2), labelWidth, personModel.getParents().getMarriageDate());

            int newChildX = (fatherX + motherX) / 2;
            Position newChild = new Position(newChildX, child.getY());
            drawPerson(newChild, personModel);
            drawLine(newChild, new Position(newChildX, parentsY), Line.LINEAGE);

            if (getConfiguration().isShowSpouses()) {
                drawSpouse(newChild, personModel);

                if (getConfiguration().isShowChildren()) {
                    drawChildren(newChild, personModel.getSpouseCouple());
                }
            }
        }

    }

    private void drawGrandParent(AncestorPerson child, Position childPosition) {
        if (child.getMother() != null) {
            if (child.getFather() == null) {
                Position motherPosition = new Position(childPosition);
                motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);
                drawPerson(motherPosition, child.getMother());
                drawParents(child.getMother(), motherPosition);
                drawLine(childPosition, motherPosition, Line.LINEAGE);
            } else {
                Position fatherPosition = drawFather(childPosition, child.getFather());
                drawParents(child.getFather(), fatherPosition);

                Position motherPosition = drawMother(childPosition, child.getMother(), child.getParents().getMarriageDate());
                drawParents(child.getMother(), motherPosition);

                fatherPosition.addY(getConfiguration().getAdultImageHeightAlternative());
                drawLine(childPosition, fatherPosition, Line.LINEAGE);

                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(childPosition, child.getSimpleBirthPlace());
                }
            }
        }
    }

    private void drawParents(AncestorPerson child, Position childPosition) {
        if (child.getMother() != null) {
            int fatherY;
            int motherY;
            int fatherX;
            int motherX;
            int childX = childPosition.getX();
            int childY = childPosition.getY();

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

                Position fatherPosition = new Position(fatherX, fatherY);
                drawPerson(fatherPosition, child.getFather());
                drawParents(child.getFather(), fatherPosition);

                Position motherPosition = new Position(motherX, motherY);
                drawPerson(motherPosition, child.getMother());
                drawParents(child.getMother(), motherPosition);

                drawLabel(new Position(fatherX, labelY + getConfiguration().getMarriageLabelHeight() / 2),
                        getConfiguration().getMarriageLabelWidth(), child.getParents().getMarriageDate());
                drawLine(childPosition, new Position(fatherX, labelY), Line.LINEAGE);

                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(new Position(fatherX, childY), child.getSimpleBirthPlace());
                }
            }

        }
    }

}
