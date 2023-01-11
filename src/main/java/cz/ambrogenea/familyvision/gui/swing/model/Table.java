package cz.ambrogenea.familyvision.gui.swing.model;

import cz.ambrogenea.familyvision.gui.swing.description.TableHeader;
import cz.ambrogenea.familyvision.gui.swing.dto.Person;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Table extends DefaultTableModel {

    public static final int TABLE_COLUMN = 5;

    private final Locale locale;
    private final List<Person> persons;

    public Table(List<Person> persons) {
        this.persons = persons;
        this.locale = Config.visual().getLocale();
        setColumnIdentifiers(getHeaderNames());
    }

    @Override
    public int getRowCount() {
        if (persons == null) {
            return 0;
        }
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        return TABLE_COLUMN;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String result = "";
        if (rowIndex < getRowCount()) {
            Person chosen = persons.get(rowIndex);
            if (columnIndex == 0) {
                result = chosen.gedcomId();
            } else if (columnIndex == 1) {
                result = chosen.firstName();
            } else if (columnIndex == 2) {
                result = chosen.surname();
            } else if (columnIndex == 3) {
                result = chosen.birthDatePlace().year();
            } else if (columnIndex == 4) {
                result = chosen.birthDatePlace().city();
            }
        }
        return result;
    }

    private Object[] getHeaderNames() {
        ResourceBundle description = ResourceBundle.getBundle("language/tableHeader", locale);
        return new Object[]{
                description.getString(TableHeader.ID),
                description.getString(TableHeader.FIRST_NAME),
                description.getString(TableHeader.SURNAME),
                description.getString(TableHeader.BIRTH_DATE),
                description.getString(TableHeader.BIRTH_PLACE)
        };
    }
}
