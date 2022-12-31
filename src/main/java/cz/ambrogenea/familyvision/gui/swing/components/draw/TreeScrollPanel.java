package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.service.util.Config;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeScrollPanel extends JScrollPane {

    private TreePanel treePanel;

    public TreeScrollPanel() {
        this.getViewport().setBackground(Colors.COMPONENT_BACKGROUND);
        this.setOpaque(false);
    }

    public void generateTreePanel(TreeModel treeModel) {
        treePanel = new TreePanel(treeModel);

        treePanel.addNotify();
        treePanel.validate();

        this.setViewportView(treePanel);
        int viewY;
        if (Config.treeShape().getAncestorGenerations() < 1) {
            viewY = 0;
        } else {
            viewY = treeModel.getPageSetup().pictureHeight();
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

}
