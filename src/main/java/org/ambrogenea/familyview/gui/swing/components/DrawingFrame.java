package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.paging.AllAncestorPageSetup;
import org.ambrogenea.familyview.service.impl.paging.CloseFamilyPageSetup;
import org.ambrogenea.familyview.service.impl.paging.FatherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.MotherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.ParentLineagePageSetup;
import org.ambrogenea.familyview.service.impl.tree.AllAncestorTreeService;
import org.ambrogenea.familyview.service.impl.tree.CloseFamilyTreeService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.MotherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.ParentLineageTreeService;

/**
 *
 * @author Jiri Ambroz
 */
public class DrawingFrame extends JPanel {

    private JFileChooser saverFC;
    private JButton saveButton;
    private ScrollPane scrollAncestorPane;

    public DrawingFrame() {
        initComponent();
        initFileChooser();

        initSaveButton();
        initScrollPane();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));
    }

    private void initFileChooser() {
        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        saverFC.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    private void initSaveButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saveButton = new JButton("Save tree");
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void initScrollPane() throws HeadlessException {
        scrollAncestorPane = new ScrollPane();
        scrollAncestorPane.setBackground(Color.WHITE);
        add(scrollAncestorPane, BorderLayout.CENTER);
    }

    public JPanel generateAllAncestors(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new AllAncestorPageSetup(config, personWithAncestors);

        TreeService treeService = new AllAncestorTreeService(config, personWithAncestors);
        return generateTreePanel(treeService, setup, config);
    }

    public JPanel generateFatherLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new FatherLineagePageSetup(config, personWithAncestors);

        TreeService treeService = new FatherLineageTreeService(config, personWithAncestors);
        return generateTreePanel(treeService, setup, config);
    }

    public JPanel generateMotherLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new MotherLineagePageSetup(config, personWithAncestors);

        TreeService treeService = new MotherLineageTreeService(config, personWithAncestors);
        return generateTreePanel(treeService, setup, config);
    }

    public JPanel generateParentsLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new ParentLineagePageSetup(config, personWithAncestors);

        TreeService treeService = new ParentLineageTreeService(config, personWithAncestors);
        return generateTreePanel(treeService, setup, config);
    }

    public JPanel generateCloseFamily(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new CloseFamilyPageSetup(config, personWithAncestors);

        TreeService treeService = new CloseFamilyTreeService(config, personWithAncestors);
        return generateTreePanel(treeService, setup, config);
    }

    private TreePanel generateTreePanel(TreeService treeService, PageSetup setup, Configuration config) {
        TreeModel treeModel = treeService.generateTreeModel(setup.getRootPosition());
        final TreePanel treePanel = new TreePanel(treeModel, config);
        treePanel.setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));

        treePanel.addNotify();
        treePanel.validate();

        scrollAncestorPane.add(treePanel);
        scrollAncestorPane.setScrollPosition(setup.getRootPosition().getX(), setup.getHeight());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(treePanel);
            }
        });
        return treePanel;
    }

    private void saveButtonActionPerformed(TreePanel ancestorPanel) {
        int returnVal = saverFC.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = saverFC.getSelectedFile();
            if (!file.getAbsolutePath().contains(".")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(ancestorPanel.getPicture(), "PNG", file);
                System.out.println("Picture was saved to " + file.getName() + ".");
            } catch (IOException ex) {
                System.out.println("Saving was failed due to: " + ex.getLocalizedMessage() + ".");
            }
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

}
