package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeModel;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

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
            viewY = treeModel.pageSetup().pictureHeight();
        }
        this.getViewport().setViewPosition(new Point(
                -treeModel.pageSetup().startPosition().x() - this.getWidth() / 2,
                viewY
        ));
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }

}
