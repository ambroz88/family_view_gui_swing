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

import org.ambrogenea.familyview.gui.swing.treepanels.AllParentsPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.FathersFamilyPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.FathersParentsPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel;
import org.ambrogenea.familyview.model.AncestorPerson;

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

    public void generateAllAncestors(AncestorPerson personWithAncestors) {
        int pictureHeight = (RootFamilyPanel.IMAGE_HEIGHT + RootFamilyPanel.VERTICAL_GAP) * (personWithAncestors.getAncestorGenerations() + 1);

        final AllParentsPanel ancestorPanel = new AllParentsPanel(personWithAncestors);
        ancestorPanel.setPreferredSize(new Dimension((RootFamilyPanel.IMAGE_WIDTH + RootFamilyPanel.HORIZONTAL_GAP) * ((int) Math.pow(2, personWithAncestors.getAncestorGenerations()) + 2), pictureHeight));
        scrollAncestorPane.add(ancestorPanel);
        ancestorPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(ancestorPanel);
            }
        });
    }

    public void generateFathersParents(AncestorPerson personWithAncestors) {
        int pictureHeight = (RootFamilyPanel.IMAGE_HEIGHT + RootFamilyPanel.VERTICAL_GAP) * (personWithAncestors.getAncestorGenerations() + 1);
        int pictureWidth = RootFamilyPanel.IMAGE_WIDTH * (personWithAncestors.getAncestorGenerations() + 1);

        final FathersParentsPanel fathersParentsPanel = new FathersParentsPanel(personWithAncestors);
        fathersParentsPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(fathersParentsPanel);
        fathersParentsPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(fathersParentsPanel);
            }
        });
    }

    public void generateFathersParentsWithSiblings(AncestorPerson personWithAncestors) {
        int pictureHeight = (RootFamilyPanel.IMAGE_HEIGHT + RootFamilyPanel.VERTICAL_GAP) * (personWithAncestors.getAncestorGenerations() + 1);
        int pictureWidth = (RootFamilyPanel.IMAGE_WIDTH + RootFamilyPanel.HORIZONTAL_GAP) * (personWithAncestors.getAncestorGenerations() + 24);

        final FathersFamilyPanel fathersFamilyPanel = new FathersFamilyPanel(personWithAncestors);
        fathersFamilyPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
        scrollAncestorPane.add(fathersFamilyPanel);
        fathersFamilyPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(fathersFamilyPanel);
            }
        });
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
