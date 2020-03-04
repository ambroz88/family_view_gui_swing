package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import org.ambrogenea.familyview.gui.swing.tools.PageSetup;
import org.ambrogenea.familyview.gui.swing.treepanels.AllParentsPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.CloseFamilyPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.FatherLineagePanel;
import org.ambrogenea.familyview.gui.swing.treepanels.LineagePanel;
import org.ambrogenea.familyview.gui.swing.treepanels.MotherLineagePanel;
import org.ambrogenea.familyview.gui.swing.treepanels.ParentLineagePanel;
import org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;

/**
 *
 * @author Jiri Ambroz
 */
public class DrawingFrame extends JPanel {

    private final JFileChooser saverFC;
    private final JButton saveButton;
    private final ScrollPane scrollAncestorPane;

    public DrawingFrame() {
        initComponent();

        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        saverFC.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);

        scrollAncestorPane = new ScrollPane();
        scrollAncestorPane.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saveButton = new JButton("Save tree");
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollAncestorPane, BorderLayout.CENTER);
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));
    }

    public JPanel generateAllAncestors(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new PageSetup(config);
        setup.calculateAllAncestors(personWithAncestors);
        int pictureWidth = setup.getWidth();
        int pictureHeight = setup.getHeight();

        final AllParentsPanel ancestorPanel = new AllParentsPanel(personWithAncestors, config);
        ancestorPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(ancestorPanel);
        scrollAncestorPane.setScrollPosition(setup.getX(), pictureHeight);
        ancestorPanel.drawAncestorPanel(setup);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(ancestorPanel);
            }
        });
        return ancestorPanel;
    }

    public JPanel generateFatherLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new PageSetup(config);
        setup.calculateFatherLineage(personWithAncestors);
        final LineagePanel fathersFamilyPanel = new FatherLineagePanel(personWithAncestors, config);
        generateLineage(setup, fathersFamilyPanel);
        return fathersFamilyPanel;
    }

    public JPanel generateMotherLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new PageSetup(config);
        setup.calculateMotherLineage(personWithAncestors);
        final LineagePanel fathersFamilyPanel = new MotherLineagePanel(personWithAncestors, config);
        generateLineage(setup, fathersFamilyPanel);
        return fathersFamilyPanel;
    }

    public JPanel generateParentsLineage(AncestorPerson personWithAncestors, Configuration config) {
        PageSetup setup = new PageSetup(config);
        setup.calculateParentLineage(personWithAncestors);
        final LineagePanel fathersFamilyPanel = new ParentLineagePanel(personWithAncestors, config);
        generateLineage(setup, fathersFamilyPanel);
        return fathersFamilyPanel;
    }

    private void generateLineage(PageSetup setup, final LineagePanel lineagePanel) {
        int pictureWidth = setup.getWidth();
        int pictureHeight = setup.getHeight();

        lineagePanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(lineagePanel);
        scrollAncestorPane.setScrollPosition(setup.getX(), pictureHeight);
        lineagePanel.drawAncestorPanel(setup);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(lineagePanel);
            }
        });
    }

    public JPanel generateCloseFamily(AncestorPerson personWithAncestors, Configuration config) {
        final CloseFamilyPanel fathersFamilyPanel = new CloseFamilyPanel(personWithAncestors, config);

        scrollAncestorPane.add(fathersFamilyPanel);
        scrollAncestorPane.setScrollPosition((personWithAncestors.getOlderSiblings().size() + 1) * config.getAdultImageWidth() + config.getAdultImageWidth() / 2 - this.getWidth() / 2, fathersFamilyPanel.getHeight());
        fathersFamilyPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(fathersFamilyPanel);
            }
        });
        return fathersFamilyPanel;
    }

    private void saveButtonActionPerformed(RootFamilyPanel ancestorPanel) {
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
