package org.ambrogenea.familyview.gui.swing.model;

import javax.swing.table.DefaultTableModel;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Table extends DefaultTableModel {

    private final FamilyData familyData;

    public Table(FamilyData tree) {
        super();
        setColumnIdentifiers(getHeaderNames());
        this.familyData = tree;
    }

    @Override
    public int getRowCount() {
        if (familyData == null) {
            return 1;
        }
        return familyData.getIndividualMap().size();
    }

    @Override
    public int getColumnCount() {
        return getHeaderNames().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person chosen = familyData.getPersonByPosition(rowIndex);
        String result = "";
        if (columnIndex == 0) {
            result = chosen.getFirstName();
        }
        if (columnIndex == 1) {
            result = chosen.getSurname();
        }
        if (columnIndex == 2) {
            result = chosen.getBirthDate();
        }
        if (columnIndex == 3) {
            result = chosen.getBirthPlace();
        }
        if (columnIndex == 4) {
            result = chosen.getDeathDate();
        }
        if (columnIndex == 5) {
            result = chosen.getDeathPlace();
        }

        return result;
    }

    private static Object[] getHeaderNames() {
        return new Object[]{"First name", "Surname", "Date of birth", "Place of birth", "Date of death", "Place of death"};
    }
}
