package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.model.Table;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DataTablePanel extends JScrollPane {

    private final JTable recordsTable;
    private final Window window;

    public DataTablePanel(Window window) {
        this.window = window;
        this.getViewport().setBackground(Colors.COMPONENT_BACKGROUND);

        recordsTable = new JTable();
        recordsTable.setPreferredScrollableViewportSize(Dimensions.TABLE_DIMENSION);
        recordsTable.setBackground(Colors.TABLE_BACKGROUND);
        recordsTable.setModel(new DefaultTableModel(
                new Object[][]{{}},
                new String[]{})
        );
        recordsTable.setModel(new Table());
        recordsTable.setAutoCreateRowSorter(true);
        this.setViewportView(recordsTable);
    }

    public void setModel(DefaultTableModel model) {
        this.recordsTable.setModel(model);
        this.recordsTable.getSelectionModel().addListSelectionListener(this::tableSelectionChangeAction);
    }

    public String getSelectedPersonId() {
        int rowIndex = this.recordsTable.getSelectedRow();
        if (rowIndex >= 0) {
            return recordsTable.getValueAt(rowIndex, 0).toString();
        } else {
            return null;
        }
    }

    private void tableSelectionChangeAction(ListSelectionEvent e) {
        window.generateTree();
    }
}
