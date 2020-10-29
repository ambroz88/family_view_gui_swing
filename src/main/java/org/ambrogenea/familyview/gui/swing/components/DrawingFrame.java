package org.ambrogenea.familyview.gui.swing.components;

import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

    public void generateTreePanel(TreeModel treeModel, ConfigurationService config) {
        final TreePanel treePanel = new TreePanel(treeModel, config);
        PageSetup setup = treeModel.getPageSetup();
        treePanel.setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));

        treePanel.addNotify();
        treePanel.validate();

        scrollAncestorPane.add(treePanel);
        scrollAncestorPane.setScrollPosition(setup.getRootPosition().getX(), setup.getHeight());
        saveButton.addActionListener(evt -> saveButtonActionPerformed(treePanel));
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
