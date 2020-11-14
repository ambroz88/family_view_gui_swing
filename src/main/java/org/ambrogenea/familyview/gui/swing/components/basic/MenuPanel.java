package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.gui.swing.Window;
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
    private JButton generateTreeButton;

    public MenuPanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT, 5, 1));
        this.window = window;
        this.setPreferredSize(Dimensions.DATA_PANEL_DIMENSION);

        initComponents();
        initFileChoosers();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/menu", window.getConfiguration().getLocale());
        loadInputButton = new JButton("L");
        loadInputButton.setToolTipText(description.getString(Menu.LOAD));
        loadInputButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        saveButton = new JButton("S");
        saveButton.setToolTipText(description.getString(Menu.SAVE));
        saveButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        generateTreeButton = new JButton("G");
        generateTreeButton.setToolTipText(description.getString(Menu.GENERATE));
        generateTreeButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);
    }

    private void initFileChoosers() {
        openFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

        saverFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        saverFC.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    private void initActions() {
        loadInputButton.addActionListener(this::loadInputButtonActionPerformed);
        generateTreeButton.addActionListener(this::generateTreeButtonActionPerformed);
        saveButton.addActionListener(this::saveButtonActionPerformed);
    }

    private void addComponents() {
        this.add(loadInputButton);
        this.add(saveButton);
        this.add(generateTreeButton);
    }

    private void generateTreeButtonActionPerformed(ActionEvent evt) {
        window.generateTree();
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

}
