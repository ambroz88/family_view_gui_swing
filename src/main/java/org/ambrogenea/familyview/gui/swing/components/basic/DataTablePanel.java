package org.ambrogenea.familyview.gui.swing.components.basic;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataTablePanel extends JScrollPane {

    private final JTable recordsTable;
    private final Window window;

    public DataTablePanel(Window window) {
        this.window = window;
        this.setPreferredSize(Dimensions.TABLE_SCROLL_DIMENSION);
        recordsTable = new JTable();
        recordsTable.setPreferredScrollableViewportSize(Dimensions.TABLE_DIMENSION);
        recordsTable.setModel(new DefaultTableModel(
                new Object[][]{{}},
                new String[]{})
        );
        this.setViewportView(recordsTable);
    }

    public void setModel(DefaultTableModel model) {
        this.recordsTable.setModel(model);
        this.recordsTable.getSelectionModel().addListSelectionListener(this::tableSelectionChangeAction);
    }

    public int getSelectedRow() {
        return this.recordsTable.getSelectedRow();
    }

    private void tableSelectionChangeAction(ListSelectionEvent e) {
        window.generateTree();
    }
}
