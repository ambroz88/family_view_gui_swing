package cz.ambrogenea.familyvision.gui.swing.components.setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.controller.DataController;
import cz.ambrogenea.familyvision.controller.FamilyTreeController;
import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreePanel;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.Menu;
import cz.ambrogenea.familyvision.gui.swing.dto.FamilyTree;
import cz.ambrogenea.familyvision.gui.swing.dto.FamilyTreeRequest;
import cz.ambrogenea.familyvision.gui.swing.service.Config;
import cz.ambrogenea.familyvision.gui.swing.service.JsonParser;
import org.xml.sax.SAXParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class MenuPanel extends JPanel {

    //    public static final String FILE_CHOOSER_PATH = "/Documents/AmbroGENEA/zákazníci";
    public static final String FILE_CHOOSER_PATH = "/Documents/Genealogie/Data-vstupy";
    private final Window window;

    private JFileChooser openFC;
    private JFileChooser saverFC;

    private JButton loadInputButton;
    private JButton saveButton;
    private JButton docButton;
    private JLabel logoLabel;
    private JComboBox<FamilyTree> treeSelection;
    private DefaultComboBoxModel<FamilyTree> treeSelectionModel;

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
        ResourceBundle description = ResourceBundle.getBundle("language/menu", Config.visual().getLocale());
        BufferedImage loadInputImage;
        BufferedImage saveButtonImage;
        BufferedImage logoImage;
        try {
            loadInputImage = ImageIO.read(getClass().getResourceAsStream("/icons/Import 40x40.jpg"));
            saveButtonImage = ImageIO.read(getClass().getResourceAsStream("/icons/Save 40x40.jpg"));
            logoImage = ImageIO.read(getClass().getResourceAsStream("/Logo 120x65.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadInputButton = new JButton(new ImageIcon(loadInputImage));
        loadInputButton.setToolTipText(description.getString(Menu.LOAD));
        loadInputButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        saveButton = new JButton(new ImageIcon(saveButtonImage));
        saveButton.setToolTipText(description.getString(Menu.SAVE));
        saveButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        docButton = new JButton("Doc");
        docButton.setToolTipText(description.getString(Menu.DOCUMENT));
        docButton.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        logoLabel = new JLabel(new ImageIcon(logoImage));
        treeSelectionModel = new DefaultComboBoxModel<>();
        treeSelection = new JComboBox<>(treeSelectionModel);
        treeSelection.setPreferredSize(new Dimension(3 * Dimensions.TREE_BUTTON_DIMENSION.width, 20));
    }

    private void initFileChoosers() {
        openFC = new JFileChooser(System.getProperty("user.home") + FILE_CHOOSER_PATH);
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

        saverFC = new JFileChooser(System.getProperty("user.home") + FILE_CHOOSER_PATH);
        saverFC.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
        saverFC.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    private void initActions() {
        loadInputButton.addActionListener(this::loadInputButtonActionPerformed);
        saveButton.addActionListener(this::saveButtonActionPerformed);
        docButton.addActionListener(this::docButtonActionPerformed);
        treeSelection.addItemListener(this::treeSelectionPropertyChanged);
    }

    private void addComponents() {
        JPanel leftMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
        leftMenu.add(loadInputButton);
        leftMenu.add(saveButton);
        leftMenu.add(docButton);
        leftMenu.setBackground(Colors.SW_BACKGROUND);

        this.add(leftMenu, BorderLayout.WEST);
        this.add(logoLabel, BorderLayout.EAST);
        this.add(treeSelection, BorderLayout.SOUTH);
    }

    private void loadInputButtonActionPerformed(ActionEvent evt) {
        int returnVal = openFC.showOpenDialog(window);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFC.getSelectedFile();
            FamilyTreeController familyTreeController = new FamilyTreeController();
            try {
                String request = JsonParser.get().writeValueAsString(new FamilyTreeRequest(file.getName()));
                String response = familyTreeController.create(request);
                FamilyTree tree = JsonParser.get().readValue(response, FamilyTree.class);

                DataController dataController = new DataController();
                dataController.parseData(file, tree.id());

                treeSelectionModel.addElement(tree);
                treeSelectionModel.setSelectedItem(tree);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException | SAXParseException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Opening file: " + file.getName() + ".");
        } else {
            System.out.println("Opening file request cancelled by user.");
        }
    }

    private void treeSelectionPropertyChanged(ItemEvent propertyChangeEvent) {
        FamilyTree tree = (FamilyTree) propertyChangeEvent.getItem();
        if (Objects.equals(tree.treeName(), getSelectedTree().treeName())) {
            window.loadTable(tree);
        }
    }

    public FamilyTree getSelectedTree() {
        return ((FamilyTree) treeSelectionModel.getSelectedItem());
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
        window.generateDocument();
    }

}
