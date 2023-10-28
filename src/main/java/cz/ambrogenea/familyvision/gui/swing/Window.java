package cz.ambrogenea.familyvision.gui.swing;

import cz.ambrogenea.familyvision.gui.swing.components.draw.TreePanel;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreeScrollPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.DataTablePanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.MenuPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.PersonSetupPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.TreeSetupPanel;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.constant.PageFormat;
import cz.ambrogenea.familyvision.gui.swing.dto.*;
import cz.ambrogenea.familyvision.gui.swing.http.Connections;
import cz.ambrogenea.familyvision.gui.swing.model.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Window extends JFrame {

    private static final String TITLE = "Family Viewer";
    private static final int BORDER_SIZE = 70;

    private MenuPanel loadingDataPanel;
    private PersonSetupPanel personSetupPanel;
    private DataTablePanel dataTablePanel;

    private TreeSetupPanel treeSetupPanel;
    private TreeScrollPanel treeScrollPane;

    public Window() {
        setWindowSize();
        initComponents();
        addComponents();
        initLogo();
    }

    private void initLogo() {
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/SW Icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setWindowSize() throws HeadlessException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
        this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setForeground(Colors.COMPONENT_BACKGROUND);

        loadingDataPanel = new MenuPanel(this);
        personSetupPanel = new PersonSetupPanel(this);
        dataTablePanel = new DataTablePanel(this);

        treeSetupPanel = new TreeSetupPanel(this);
        treeScrollPane = new TreeScrollPanel();
    }

    private void addComponents() {
        JPanel leftPanel = new JPanel(new BorderLayout(0, 5));
        leftPanel.setPreferredSize(Dimensions.LEFT_PANEL_DIMENSION);

        JPanel setupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        setupPanel.setPreferredSize(Dimensions.SETUP_PANEL_DIMENSION);
        setupPanel.add(loadingDataPanel);
        setupPanel.add(personSetupPanel);
        setupPanel.setBackground(Colors.SW_BACKGROUND);

        leftPanel.add(setupPanel, BorderLayout.CENTER);
        leftPanel.add(dataTablePanel, BorderLayout.SOUTH);
        leftPanel.setBackground(Colors.SW_BACKGROUND);

        JPanel rightPanel = new JPanel(new BorderLayout(0, 5));
        rightPanel.add(treeSetupPanel, BorderLayout.NORTH);
        rightPanel.add(treeScrollPane, BorderLayout.CENTER);
        rightPanel.setBackground(Colors.SW_BACKGROUND);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
                if ("Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> new Window().setVisible(true));
    }

    public void loadTable(FamilyTree familyTree) {
        try {
            List<Person> persons = Connections.getPersonsInTree(getTreeId());
            dataTablePanel.setModel(new Table(persons));
            this.setTitle(TITLE + " - " + familyTree.treeName());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getTreeId() {
        return loadingDataPanel.getSelectedTree().id();
    }

    public void updateConfiguration(VisualConfiguration configuration) {
        try {
            Connections.updateVisualConfiguration(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateConfiguration(TreeShapeConfiguration configuration) {
        try {
            Connections.updateTreeShapeConfiguration(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateTree() {
        if (dataTablePanel.getSelectedPersonId() != null) {
            Long personId = dataTablePanel.getSelectedPersonId();
            try {
                treeScrollPane.generateTreePanel(Connections.generateTree(personId));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateTree() {
        if (dataTablePanel.getSelectedPersonId() != null) {
            try {
                treeScrollPane.generateTreePanel(Connections.updateTree());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public TreePanel getTreePanel() {
        return treeScrollPane.getTreePanel();
    }

    public void generateDocument() throws URISyntaxException, IOException {
        if (dataTablePanel.getSelectedPersonId() != null) {
            Long personId = dataTablePanel.getSelectedPersonId();

            TreeModel[] models = Connections.generateTreeModels(personId, PageFormat.FORMAT_A4);
            for (TreeModel treeModel : models) {
                TreePanel familyPanel = generateTreePanel(treeModel);
                Connections.uploadImageToDoc(familyPanel.getStream(), treeModel.treeName(), familyPanel.getPreferredSize());
            }

            String personName = dataTablePanel.getSelectedPersonName();
            try {
                Connections.saveDocument(System.getProperty("user.home") + "/Documents/Genealogie/" + personName + ".docx");
            } catch (IOException ex) {
                System.out.println("It is not possible to save document: " + ex.getMessage());
            }
        }
    }

    private TreePanel generateTreePanel(TreeModel treeModel) {
        TreePanel treePanel = new TreePanel(treeModel);
        treePanel.addNotify();
        treePanel.validate();
        return treePanel;
    }

}
