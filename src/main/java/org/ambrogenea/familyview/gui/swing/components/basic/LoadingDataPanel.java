package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.gui.swing.components.SettingsPanel;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.Loader;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class LoadingDataPanel extends JPanel {

    private final SettingsPanel window;
    private final JFileChooser openFC;
    private JButton loadInputButton;
    private JLabel fileNameLabel;
    private JLabel fileName;
    private JButton generateTreeButton;

    public LoadingDataPanel(SettingsPanel window) {
        this.window = window;

        openFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

        this.setLayout(new GridLayout(4, 1, 10, 0));
        this.setPreferredSize(new Dimension(Dimensions.DATA_LOADING_PANEL_WIDTH, Dimensions.DATA_LOADING_PANEL_HEIGHT));

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/loader", window.getConfiguration().getLocale());
        loadInputButton = new JButton(description.getString(Loader.LOAD));

        fileNameLabel = new JLabel(description.getString(Loader.NAME));
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fileName = new JLabel(description.getString(Loader.NO_FILE));
        fileName.setHorizontalAlignment(SwingConstants.CENTER);

        generateTreeButton = new JButton(description.getString(Loader.GENERATE));
    }

    private void initActions() {
        loadInputButton.addActionListener(this::loadInputButtonActionPerformed);
        generateTreeButton.addActionListener(this::generateTreeButtonActionPerformed);
    }

    private void addComponents() {
        this.add(loadInputButton);
        this.add(fileNameLabel);
        this.add(fileName);
        this.add(generateTreeButton);
    }

    private void generateTreeButtonActionPerformed(ActionEvent evt) {
        window.generateTree();
    }

    private void loadInputButtonActionPerformed(ActionEvent evt) {
        int returnVal = openFC.showOpenDialog(window);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFC.getSelectedFile();
            window.loadTable(file.getAbsolutePath());
            fileName.setText(file.getName());
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
}
