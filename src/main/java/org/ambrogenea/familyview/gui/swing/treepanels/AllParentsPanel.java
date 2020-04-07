package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class AllParentsPanel extends RootFamilyPanel {

    public AllParentsPanel(AncestorPerson model, Configuration config) {
        super(model, config);
    }

    public void drawAncestorPanel(PageSetup setup) {
        int x = setup.getX();
        int y = setup.getY();
        drawPerson(x, y, personModel);
        drawParents(personModel, x, y);
    }

    private void drawParents(AncestorPerson child, int childX, int childY) {
        int parentsY = childY - getConfiguration().getAdultImageHeight() - VERTICAL_GAP;
        if (child.getMother() != null) {
            double motherParentsCount = Math.min(child.getMother().getInnerParentsCount(), child.getMother().getLastParentsCount());

            int motherX;
            if (child.getFather() == null) {
                motherX = childX;
            } else {
                double fatherParentsCount = Math.min(child.getFather().getInnerParentsCount(), child.getFather().getLastParentsCount());

                int fatherX;
                if (motherParentsCount == 0 || fatherParentsCount == 0) {
                    fatherX = childX - getConfiguration().getHalfSpouseLabelSpace();
                    motherX = childX + getConfiguration().getHalfSpouseLabelSpace();
                } else {
                    double motherParentWidth = (getConfiguration().getCoupleWidth() + SIBLINGS_GAP) * motherParentsCount;
                    double fatherParentWidth = (getConfiguration().getCoupleWidth() + SIBLINGS_GAP) * fatherParentsCount;
                    int halfParentWidth = (int) (fatherParentWidth + motherParentWidth) / 2;
                    fatherX = childX - halfParentWidth;
                    motherX = childX + halfParentWidth;
                }

                drawPerson(fatherX, parentsY, child.getFather());
                drawLabel(fatherX + getConfiguration().getAdultImageWidth() / 2, motherX - getConfiguration().getAdultImageWidth() / 2, parentsY, child.getParents().getMarriageDate());
                drawParents(child.getFather(), fatherX, parentsY);
                if (getConfiguration().isShowHeraldry()) {
                    addHeraldry(childX, childY, child.getSimpleBirthPlace());
                }
            }

            drawPerson(motherX, parentsY, child.getMother());
            drawParents(child.getMother(), motherX, parentsY);
            lines.add(new Line(childX, childY, childX, parentsY));
        }
    }

}
