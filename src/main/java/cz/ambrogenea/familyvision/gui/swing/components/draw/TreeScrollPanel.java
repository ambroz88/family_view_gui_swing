package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.dto.tree.PageSetup;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.service.ConfigurationService;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeScrollPanel extends JScrollPane {

    private final cz.ambrogenea.familyvision.gui.swing.Window window;
    private TreePanel treePanel;

    public TreeScrollPanel(Window window) {
        this.window = window;
        initComponents();
    }

    private void initComponents() {
        this.getViewport().setBackground(Colors.COMPONENT_BACKGROUND);
        this.setOpaque(false);
    }

    public void generateTreePanel(TreeModel treeModel) {
        treePanel = new TreePanel(treeModel, getConfiguration());
        PageSetup setup = treeModel.getPageSetup(getConfiguration());

        treePanel.addNotify();
        treePanel.validate();

        this.setViewportView(treePanel);
        int viewY;
        if (window.isInDescendentMode()) {
            viewY = 0;
        } else {
            viewY = setup.pictureHeight();
        }
        this.getViewport().setViewPosition(new Point(
                -treeModel.pageMaxCoordinates().getMinX() - this.getWidth() / 2,
                viewY
        ));
    }

    public void setTreePanel(TreePanel treePanel) {
        this.treePanel = treePanel;
        this.treePanel.validate();
        this.setViewportView(this.treePanel);
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }

    private ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }
}
