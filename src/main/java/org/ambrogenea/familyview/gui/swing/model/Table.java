package org.ambrogenea.familyview.gui.swing.model;

import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.domain.Person;
import org.ambrogenea.familyview.gui.swing.description.TableHeader;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.impl.DefaultConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Table extends DefaultTableModel {

    public static final int TABLE_COLUMN = 4;

    private final FamilyData familyData;
    private final ConfigurationService configuration;

    public Table(FamilyData tree, ConfigurationService configuration) {
        super();
        this.familyData = tree;
        this.configuration = configuration;
        setColumnIdentifiers(getHeaderNames());
    }

    public Table(FamilyData tree) {
        super();
        this.familyData = tree;
        this.configuration = new DefaultConfigurationService();
        setColumnIdentifiers(getHeaderNames());
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
        return TABLE_COLUMN;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person chosen = familyData.getPersonByPosition(rowIndex);
        String result = "";
        if (columnIndex == 0) {
            result = chosen.getFirstName();
        } else if (columnIndex == 1) {
            result = chosen.getSurname();
        } else if (columnIndex == 2) {
            result = chosen.getBirthDatePlace().getLocalizedDate(configuration.getLocale());
        } else if (columnIndex == 3) {
            result = chosen.getBirthDatePlace().getSimplePlace();
        }

        return result;
    }

    private Object[] getHeaderNames() {
        ResourceBundle description = ResourceBundle.getBundle("language/tableHeader", this.configuration.getLocale());
        return new Object[]{
            description.getString(TableHeader.FIRST_NAME),
            description.getString(TableHeader.SURNAME),
            description.getString(TableHeader.BIRTH_DATE),
            description.getString(TableHeader.BIRTH_PLACE)
        };
    }
}
