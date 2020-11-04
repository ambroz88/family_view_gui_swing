package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.components.basic.DataTablePanel;
import org.ambrogenea.familyview.gui.swing.components.basic.LoadingDataPanel;
import org.ambrogenea.familyview.gui.swing.components.basic.TreeSetupPanel;
import org.ambrogenea.familyview.gui.swing.components.basic.TreeTypePanel;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.service.*;
import org.ambrogenea.familyview.service.impl.parsing.GedcomParsingService;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;

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
        this.setLayout(new BorderLayout());

        treeTypePanel = new TreeTypePanel(this);
        treeSetupPanel = new TreeSetupPanel(window.getConfiguration());
        loadingDataPanel = new LoadingDataPanel(this);
        dataTablePanel = new DataTablePanel();

        selectionService = new FathersSelectionService();
        treeService = new FatherLineageTreeService();
        addComponents();
    }

    private void addComponents() {
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        int height = Dimensions.DATA_LOADING_PANEL_HEIGHT + Dimensions.TREE_SETUP_PANEL_HEIGHT + Dimensions.TREE_TYPE_PANEL_HEIGHT;
        leftPanel.setPreferredSize(new Dimension(Dimensions.DATA_LOADING_PANEL_WIDTH + 10, height + 10));
        leftPanel.add(loadingDataPanel);
        leftPanel.add(treeTypePanel);
        leftPanel.add(treeSetupPanel);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(dataTablePanel, BorderLayout.CENTER);
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
        this.selectionService.setFamilyData(familyData);
    }

    public void setSiblingsShow(boolean show) {
        treeSetupPanel.setSiblingsShow(show);
    }

    public ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
