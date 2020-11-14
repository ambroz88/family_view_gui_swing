package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RightPanel extends JPanel {

    private final Window window;

    private JScrollPane scrollAncestorPane;
    private TreeSetupPanel treeSetupPanel;
    private TreePanel treePanel;

    public RightPanel(Window window) {
        this.window = window;
        this.setLayout(new BorderLayout());

        initComponents();
        addComponents();
    }

    private void initComponents() {
        treeSetupPanel = new TreeSetupPanel(getConfiguration());

        scrollAncestorPane = new JScrollPane();
        scrollAncestorPane.setBackground(Color.WHITE);
        scrollAncestorPane.setOpaque(true);
    }

    private void addComponents() {
        this.add(treeSetupPanel, BorderLayout.NORTH);
        this.add(scrollAncestorPane, BorderLayout.CENTER);
    }

    public void setSiblingsShow(boolean show) {
        treeSetupPanel.setSiblingsShow(show);
    }

    public void generateTreePanel(TreeModel treeModel, ConfigurationService config) {
        treePanel = new TreePanel(treeModel, config);
        PageSetup setup = treeModel.getPageSetup();
        treePanel.setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));

        treePanel.addNotify();
        treePanel.validate();

        scrollAncestorPane.setViewportView(treePanel);
//        scrollAncestorPane.setScrollPosition(setup.getRootPosition().getX(), setup.getHeight());
    }

    public void setTreePanel(TreePanel treePanel) {
        this.treePanel = treePanel;
        this.treePanel.validate();
        scrollAncestorPane.setViewportView(this.treePanel);
    }

    public ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }

}
