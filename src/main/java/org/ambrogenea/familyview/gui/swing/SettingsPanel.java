package org.ambrogenea.familyview.gui.swing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.service.*;
import org.ambrogenea.familyview.service.impl.parsing.GedcomParsingService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class SettingsPanel extends JPanel {

    private final Window window;

    private TreeTypePanel treeTypePanel;
    private TreeSetupPanel treeSetupPanel;
    private LoadingDataPanel loadingDataPanel;
    private DataTablePanel dataTablePanel;

    private TreeService treeService;
    private SelectionService selectionService;

    private FamilyData familyData;

    public SettingsPanel(Window window) {
        this.window = window;

        treeTypePanel = new TreeTypePanel(this);
        treeSetupPanel = new TreeSetupPanel(window.getConfiguration());
        loadingDataPanel = new LoadingDataPanel(this);
        dataTablePanel = new DataTablePanel();

        addComponents();
    }

    private void addComponents() {

    }

    public void loadTable(String absolutePath) {
        try {
            ParsingService parsingService = new GedcomParsingService();

            InputStream inputStream = new FileInputStream(absolutePath);
            familyData = parsingService.parse(inputStream);

            dataTablePanel.setModel(new Table(familyData));
            selectionService.setFamilyData(familyData);
//            recordsTable.setAutoCreateRowSorter(true);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateTree() {
        if (dataTablePanel.getSelectedRow() != -1) {
            getConfiguration().setFamilyData(familyData);
            String personId = familyData.getPersonByPosition(dataTablePanel.getSelectedRow()).getId();

            AncestorPerson rootPerson = selectionService.select(personId, getConfiguration().getGenerationCount());

            PageSetup setup = treeTypePanel.createPageSetup(rootPerson);

            TreeModel treeModel = treeService.generateTreeModel(rootPerson, setup, getConfiguration());
            DrawingFrame drawing = new DrawingFrame();
            drawing.generateTreePanel(treeModel, getConfiguration());

            window.addTab(treeModel.getTreeName(), drawing);
        }
    }

    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }

    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    public void setSiblingsShow(boolean show) {
        treeSetupPanel.setSiblingsShow(show);
    }

    public ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
