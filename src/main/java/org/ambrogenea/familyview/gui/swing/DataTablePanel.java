package org.ambrogenea.familyview.gui.swing;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataTablePanel extends JScrollPane {

    private final JTable recordsTable;

    public DataTablePanel() {
        recordsTable = new JTable();
        recordsTable.setModel(new DefaultTableModel(
                new Object[][]{{}},
                new String[]{})
        );
        this.setViewportView(recordsTable);
    }

    public void setModel(DefaultTableModel model) {
        this.recordsTable.setModel(model);
    }

    public int getSelectedRow() {
        return this.recordsTable.getSelectedRow();
    }
}
