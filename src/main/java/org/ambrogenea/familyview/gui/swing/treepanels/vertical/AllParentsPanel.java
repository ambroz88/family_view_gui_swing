package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

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
        drawPerson(x, y, personModel);

        if (getConfiguration().isShowSpouses()) {
            drawSpouse(x, y, personModel);
            if (getConfiguration().isShowChildren()) {
                drawChildren(x, y, personModel.getSpouseCouple());
            }
        }

        drawFirstParents(x, y);
    }

    private void drawFirstParents(int childX, int childY) {
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
                double fatherParentsCount = Math.min(child.getFather().getInnerParentsCount(), child.getFather().getLastParentsCount());
                double motherParentsCount = Math.min(child.getMother().getInnerParentsCount(), child.getMother().getLastParentsCount());

                if (child.getSex().equals(Sex.FEMALE)) {
                    fatherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - 2 * getConfiguration().getAdultImageHeightAlternative() - 2 * getConfiguration().getMarriageLabelHeight();
                    motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();
                    fatherX = childX + getConfiguration().getAdultImageWidth() / 2 + (int) (getConfiguration().getCoupleWidthVertical() * (fatherParentsCount));
                    motherX = fatherX + getConfiguration().getMarriageLabelWidth();
                } else {
                    fatherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP
                            - getConfiguration().getAdultImageHeightAlternative() - getConfiguration().getMarriageLabelHeight();
                    motherY = childY - getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
                    motherX = childX - (int) ((2 * getConfiguration().getMarriageLabelWidth() + getConfiguration().getAdultImageWidth())
                            * (motherParentsCount));
                    fatherX = motherX - getConfiguration().getMarriageLabelWidth();
                }

                int labelY = (motherY + fatherY) / 2;

                drawPerson(fatherX, fatherY, child.getFather());
                drawParents(child.getFather(), fatherX, fatherY);

                drawPerson(motherX, motherY, child.getMother());
                drawParents(child.getMother(), motherX, motherY);

                drawLabel(fatherX, motherX, labelY + getConfiguration().getMarriageLabelHeight() / 2, child.getParents().getMarriageDate());
                lines.add(new Line(childX, childY, fatherX, childY));
                lines.add(new Line(fatherX, childY, fatherX, labelY));

                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(fatherX, childY, child.getSimpleBirthPlace());
                }
            }

        }
    }

}
