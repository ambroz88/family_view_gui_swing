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

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.VERTICAL_GAP;

import org.ambrogenea.familyview.gui.swing.treepanels.AllParentsPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.CloseFamilyPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.LineagePanel;
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

    public void generateAllAncestors(AncestorPerson personWithAncestors, Configuration config) {
        int pictureHeight = (config.getAdultImageHeight() + RootFamilyPanel.VERTICAL_GAP) * (personWithAncestors.getAncestorGenerations() + 1);
        int pictureWidth = (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * ((int) Math.pow(2, personWithAncestors.getAncestorGenerations()) + 2);

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
    }

    public JPanel generateLineage(AncestorPerson personWithAncestors, Configuration config) {
        int pictureWidth;
        if (config.isShowSiblings()) {
            pictureWidth = (config.getAdultImageWidth() + RootFamilyPanel.HORIZONTAL_GAP) * (personWithAncestors.getAncestorGenerations() + 24);
        } else {
            pictureWidth = config.getAdultImageWidth() * (Math.min(personWithAncestors.getAncestorGenerations(), config.getGenerationCount()) + 2) + config.getAdultImageWidth();
            if (config.isShowFathersLineage() && config.isShowMothersLineage()) {
                pictureWidth = pictureWidth + (config.getWideMarriageLabel() - RootFamilyPanel.MARRIAGE_LABEL_WIDTH);
            }
            if (config.isShowResidence()) {
                pictureWidth = pictureWidth + RootFamilyPanel.RESIDENCE_SIZE;
            }
        }
        int pictureHeight = (config.getAdultImageHeight() + RootFamilyPanel.VERTICAL_GAP) * (Math.min(personWithAncestors.getAncestorGenerations(), config.getGenerationCount()) + 1);
        if (config.isShowSpouses() && config.isShowChildren()) {
            pictureHeight = pictureHeight + config.getAdultImageHeight() + VERTICAL_GAP;
        }

        final LineagePanel fathersFamilyPanel = new LineagePanel(personWithAncestors, config);
        fathersFamilyPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(fathersFamilyPanel);
        scrollAncestorPane.setScrollPosition(pictureWidth / 2 - this.getWidth() / 2, pictureHeight);
        fathersFamilyPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(fathersFamilyPanel);
            }
        });
        return fathersFamilyPanel;
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
