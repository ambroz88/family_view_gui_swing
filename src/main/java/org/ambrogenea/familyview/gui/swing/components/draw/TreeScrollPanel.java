package org.ambrogenea.familyview.gui.swing.components.draw;

import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;

/**
 *
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
        PageSetup setup = treeModel.getPageSetup();
        treePanel.setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));

        treePanel.addNotify();
        treePanel.validate();

        this.setViewportView(treePanel);
//        scrollAncestorPane.setScrollPosition(setup.getRootPosition().getX(), setup.getHeight());
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
