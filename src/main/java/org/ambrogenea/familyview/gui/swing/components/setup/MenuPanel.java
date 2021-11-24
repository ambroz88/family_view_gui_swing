package org.ambrogenea.familyview.gui.swing.components.setup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.components.draw.TreePanel;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.Menu;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MenuPanel extends JPanel {

    private final Window window;

    private JFileChooser openFC;
    private JFileChooser saverFC;

    private JButton loadInputButton;
    private JButton saveButton;
    private JButton docButton;
    private JLabel logoLabel;

    public MenuPanel(Window window) {
        super(new BorderLayout());
        this.window = window;
        this.setBackground(Colors.SW_BACKGROUND);
        this.setPreferredSize(Dimensions.DATA_PANEL_DIMENSION);

        initComponents();
        initFileChoosers();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/menu", window.getConfiguration().getLocale());
        loadInputButton = new JButton(new ImageIcon("src/main/resources/icons/Import 40x40.jpg"));
        loadInputButton.setToolTipText(description.getString(Menu.LOAD));
        loadInputButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        saveButton = new JButton(new ImageIcon("src/main/resources/icons/Save 40x40.jpg"));
        saveButton.setToolTipText(description.getString(Menu.SAVE));
        saveButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        docButton = new JButton("Doc");
        docButton.setToolTipText(description.getString(Menu.DOCUMENT));
        docButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        logoLabel = new JLabel(new ImageIcon("src/main/resources/Logo 120x65.png"));
    }

    private void initFileChoosers() {
        openFC = new JFileChooser(System.getProperty("user.home") + "/Documents/AmbroGENEA/zákazníci");
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/AmbroGENEA/zákazníci");
        saverFC.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    private void initActions() {
        loadInputButton.addActionListener(this::loadInputButtonActionPerformed);
        saveButton.addActionListener(this::saveButtonActionPerformed);
        docButton.addActionListener(this::docButtonActionPerformed);
    }

    private void addComponents() {
        JPanel leftMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
        leftMenu.add(loadInputButton);
        leftMenu.add(saveButton);
        leftMenu.add(docButton);
        leftMenu.setBackground(Colors.SW_BACKGROUND);

        this.add(leftMenu, BorderLayout.WEST);
        this.add(logoLabel, BorderLayout.EAST);
    }

    private void loadInputButtonActionPerformed(ActionEvent evt) {
        int returnVal = openFC.showOpenDialog(window);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFC.getSelectedFile();
            window.loadTable(file);
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        TreePanel ancestorPanel = window.getTreePanel();
        saverFC.setSelectedFile(new File(ancestorPanel.getTreeName()));
        int returnVal = saverFC.showSaveDialog(window);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = saverFC.getSelectedFile();
            if (!file.getAbsolutePath().endsWith(".png")) {
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

    private void docButtonActionPerformed(ActionEvent evt) {

    }

}
