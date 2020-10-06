package org.ambrogenea.familyview.gui.swing.treepanels;

import org.ambrogenea.familyview.gui.swing.model.Position;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Couple;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface RootFamilyInterface {

    abstract void drawPerson(Position center, AncestorPerson person);

    abstract void drawSiblings(Position rootSiblingPosition, AncestorPerson rootSibling);

    abstract Position drawMother(Position childPosition, AncestorPerson mother, String marriageDate);

    abstract Position drawFather(Position childPosition, AncestorPerson father);

    abstract Position drawSpouse(Position rootPersonPosition, AncestorPerson person);

    abstract Position drawAllSpouses(Position rootPersonPosition, AncestorPerson person);

    abstract int drawChildren(Position fatherPosition, Couple couple);

    abstract void drawLabel(Position husbandPosition, int labelWidth, String text);

}
