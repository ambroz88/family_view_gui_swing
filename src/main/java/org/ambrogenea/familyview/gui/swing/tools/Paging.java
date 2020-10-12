package org.ambrogenea.familyview.gui.swing.tools;

import org.ambrogenea.familyview.domain.Position;
import org.ambrogenea.familyview.model.AncestorPerson;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public interface Paging {

    void calculateParentLineageHorizontal(AncestorPerson person);

    void calculateFatherLineageHorizontal(AncestorPerson person);

    void calculateMotherLineageHorizontal(AncestorPerson person);

    void addFatherLineageDimension(AncestorPerson person);

    void addFathersSiblingDimension(AncestorPerson person);

    void addMotherLineageDimension(AncestorPerson person);

    void addMotherSiblingsWidth(AncestorPerson mother);

    void calculateLineageVertical(AncestorPerson person);

    void calculateAllAncestors(AncestorPerson person);

    public int getWidth();

    public int getHeight();

    public Position getRootPosition();

}
