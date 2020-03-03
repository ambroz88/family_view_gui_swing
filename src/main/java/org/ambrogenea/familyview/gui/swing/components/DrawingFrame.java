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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.SIBLINGS_GAP;

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
public class DrawingFrame extends JFrame {

    private final JFileChooser saverFC;
    private final JButton saveButton;
    private final ScrollPane scrollAncestorPane;

    public DrawingFrame(String title) {
        initComponent(title);

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

    private void initComponent(String title) {
        setTitle(title);
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("SW Icon.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
    }

    public JPanel generateAllAncestors(AncestorPerson personWithAncestors, Configuration config) {
        int pictureHeight = (config.getAdultImageHeight() + RootFamilyPanel.VERTICAL_GAP) * (Math.min(config.getGenerationCount(), personWithAncestors.getAncestorGenerations()) + 1);
        int pictureWidth = (int) ((config.getCoupleWidth() + SIBLINGS_GAP) * (personWithAncestors.getLastParentsCount() + 1));

        final AllParentsPanel ancestorPanel = new AllParentsPanel(personWithAncestors, config);
        ancestorPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(ancestorPanel);
        scrollAncestorPane.setScrollPosition(pictureWidth / 2 - this.getWidth() / 2, pictureHeight);
        ancestorPanel.drawAncestorPanel();

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

    private void generateLineage(PageSetup setup, final LineagePanel fathersFamilyPanel) {
        int pictureWidth = setup.getWidth();
        int pictureHeight = setup.getHeight();

        fathersFamilyPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(fathersFamilyPanel);
        scrollAncestorPane.setScrollPosition(pictureWidth / 2 - this.getWidth() / 2, pictureHeight);
        fathersFamilyPanel.drawAncestorPanel(setup);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(fathersFamilyPanel);
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
