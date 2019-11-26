package org.ambrogenea.familyview.gui.swing.components;

import org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel;
import org.ambrogenea.familyview.gui.swing.treepanels.AllParentsPanel;

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

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class DrawingFrame extends JFrame {

    private final JFileChooser saverFC;
    private final JButton saveButton;
    private final ScrollPane scrollAncestorPane;

    public DrawingFrame(Person person) {
        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);

        setTitle("Ancestors of " + person.getName());
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
        ancestorPanel.setPreferredSize(new Dimension(RootFamilyPanel.MINIMAL_WIDTH * ((int) Math.pow(2, personWithAncestors.getAncestorGenerations()) + 2), getHeight()));
        scrollAncestorPane.add(ancestorPanel);
        ancestorPanel.drawAncestorPanel();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(ancestorPanel);
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
