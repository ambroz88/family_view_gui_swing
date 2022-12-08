package org.ambrogenea.familyview.gui.swing.components.draw;

import org.ambrogenea.familyview.dto.tree.PageSetup;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.service.ConfigurationService;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeScrollPanel extends JScrollPane {

    private final Window window;
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
        this.getViewport().setViewPosition(new Point(setup.getWidth() / 2, setup.getHeight()));
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
