package cz.ambrogenea.familyvision.gui.swing.model;

import cz.ambrogenea.familyvision.domain.Person;
import cz.ambrogenea.familyvision.gui.swing.description.TableHeader;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.service.util.Services;

import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Table extends DefaultTableModel {

    public static final int TABLE_COLUMN = 4;

    private final Locale locale;

    public Table() {
        super();
        this.locale = Config.visual().getLocale();
        setColumnIdentifiers(getHeaderNames());
    }

    @Override
    public int getRowCount() {
        return Services.person().getPeopleInTree().size();
    }

    @Override
    public int getColumnCount() {
        return TABLE_COLUMN;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String result = "";
        if (rowIndex < getRowCount()) {
            Person chosen = Services.person().getPeopleInTree().get(rowIndex);
            if (columnIndex == 0) {
                result = chosen.getFirstName();
            } else if (columnIndex == 1) {
                result = chosen.getSurname();
            } else if (columnIndex == 2) {
                result = chosen.getBirthDatePlace().getLocalizedDate(locale);
            } else if (columnIndex == 3) {
                result = chosen.getBirthDatePlace().getSimplePlace();
            }
        }
        return result;
    }

    private Object[] getHeaderNames() {
        ResourceBundle description = ResourceBundle.getBundle("language/tableHeader", locale);
        return new Object[]{
            description.getString(TableHeader.FIRST_NAME),
            description.getString(TableHeader.SURNAME),
            description.getString(TableHeader.BIRTH_DATE),
            description.getString(TableHeader.BIRTH_PLACE)
        };
    }
}
