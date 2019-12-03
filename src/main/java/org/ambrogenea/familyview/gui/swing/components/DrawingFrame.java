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
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);

        setTitle(title);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));

        scrollAncestorPane = new ScrollPane();
        scrollAncestorPane.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save tree");
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollAncestorPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void generateAllAncestors(AncestorPerson personWithAncestors) {
        final AllParentsPanel ancestorPanel = new AllParentsPanel(personWithAncestors);
        int pictureHeight = RootFamilyPanel.MINIMAL_HEIGHT * (personWithAncestors.getAncestorGenerations() + 2);
        ancestorPanel.setPreferredSize(new Dimension(RootFamilyPanel.MINIMAL_WIDTH * ((int) Math.pow(2, personWithAncestors.getAncestorGenerations()) + 2), pictureHeight));
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
        final FathersParentsPanel fathersParentsPanel = new FathersParentsPanel(personWithAncestors);
        int pictureHeight = RootFamilyPanel.MINIMAL_HEIGHT * (personWithAncestors.getAncestorGenerations() + 2);
        fathersParentsPanel.setPreferredSize(new Dimension(RootFamilyPanel.MINIMAL_WIDTH * (personWithAncestors.getAncestorGenerations() + 2), pictureHeight));
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
        final FathersFamilyPanel fathersFamilyPanel = new FathersFamilyPanel(personWithAncestors);
        int pictureHeight = RootFamilyPanel.MINIMAL_HEIGHT * (personWithAncestors.getAncestorGenerations() + 2);
        fathersFamilyPanel.setPreferredSize(new Dimension(RootFamilyPanel.MINIMAL_WIDTH * (personWithAncestors.getAncestorGenerations() + 20), pictureHeight));
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
        int returnVal = saverFC.showOpenDialog(ancestorPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = saverFC.getSelectedFile();
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
