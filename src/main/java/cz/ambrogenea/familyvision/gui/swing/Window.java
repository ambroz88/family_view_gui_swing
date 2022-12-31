package cz.ambrogenea.familyvision.gui.swing;

import cz.ambrogenea.familyvision.controller.TreeGeneratorController;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.dto.tree.TreeModel;
import cz.ambrogenea.familyvision.enums.PropertyName;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreePanel;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreeScrollPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.*;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.model.Table;
import cz.ambrogenea.familyvision.service.ParsingService;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.impl.parsing.GedcomParsingService;
import cz.ambrogenea.familyvision.service.impl.selection.FathersSelectionService;
import cz.ambrogenea.familyvision.service.impl.tree.FatherLineageTreeService;
import cz.ambrogenea.familyvision.service.util.Config;
import cz.ambrogenea.familyvision.service.util.Services;
import cz.ambrogenea.familyvision.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.xml.sax.SAXParseException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Window extends JFrame implements PropertyChangeListener {

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
        ImageIcon img = new ImageIcon("src/main/resources/SW Icon.png");
        this.setIconImage(img.getImage());
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

        leftPanel.add(setupPanel, BorderLayout.NORTH);
        leftPanel.add(dataTablePanel, BorderLayout.CENTER);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> new Window().setVisible(true));
    }

    public void loadTable(File gedcomFile) {
        String absolutePath = gedcomFile.getAbsolutePath();
        try {
            ParsingService parsingService = new GedcomParsingService();

            try ( InputStream inputStream = new FileInputStream(absolutePath)) {
                parsingService.saveData(inputStream);
                dataTablePanel.setModel(new Table());
                this.setTitle(TITLE + " - " + gedcomFile.getName());
//            recordsTable.setAutoCreateRowSorter(true);
            }
        } catch (IOException | SAXParseException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateTree() {
        if (dataTablePanel.getSelectedRow() != -1) {
            String personId = Services.person().getPeopleInTree().get(dataTablePanel.getSelectedRow()).getGedcomId();
            TreeModel treeModel = TreeGeneratorController.generateTree(personId);
            treeScrollPane.generateTreePanel(treeModel);
        }
    }

    public TreePanel getTreePanel() {
        return treeScrollPane.getTreePanel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyName.NEW_TREE.toString())) {
            TreePanel panel = (TreePanel) evt.getNewValue();
            treeScrollPane.setTreePanel(panel);
        }
    }

    public void generateDocument() {
        if (dataTablePanel.getSelectedRow() != -1) {
            XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);

            String personId = Services.person().getPeopleInTree().get(dataTablePanel.getSelectedRow()).getGedcomId();
            SelectionService selectionService = new FathersSelectionService();
            AncestorPerson rootPerson = selectionService.select(personId);
            addFamilyToDoc(rootPerson, doc);

            if (rootPerson.getFather() != null) {
                createFamilyDocument(rootPerson.getFather(), doc);
            } else {
                createFamilyDocument(rootPerson.getMother(), doc);
            }

            saveFamilyDocument(rootPerson, doc);
        }
    }

    private void createFamilyDocument(AncestorPerson person, XWPFDocument doc) {
        if (person != null) {
            AncestorPerson actualPerson = person;
            int generations = 0;
            while (actualPerson != null) {
                if (generations < Config.treeShape().getAncestorGenerations()) {
                    addFamilyToDoc(actualPerson, doc);
                    actualPerson = actualPerson.getFather();
                    generations++;
                } else {
                    actualPerson = null;
                }
            }
        }
    }

    private void addFamilyToDoc(AncestorPerson actualPerson, XWPFDocument doc) {
        TreePanel familyPanel = createOneFamily(actualPerson);
        int generations = calculateGenerations(actualPerson);
        WordGenerator.setMaxHeight(generations);
        WordGenerator.createFamilyPage(doc, "Rodina " + actualPerson.getName());
        WordGenerator.addImageToPage(doc, familyPanel.getStream(), familyPanel.getPreferredSize().width, familyPanel.getPreferredSize().height);
    }

    private TreePanel createOneFamily(AncestorPerson personWithAncestors) {
        TreeModel treeModel = new FatherLineageTreeService().generateTreeModel(personWithAncestors);
        return generateTreePanel(treeModel);
    }

    private void saveFamilyDocument(AncestorPerson personWithAncestors, XWPFDocument doc) {
        try {
            WordGenerator.writeDocument(System.getProperty("user.home") + "/Documents/Genealogie/" + personWithAncestors.getName() + ".docx", doc);
        } catch (IOException ex) {
            System.out.println("It is not possible to save document: " + ex.getMessage());
        }
    }

    private int calculateGenerations(AncestorPerson actualPerson) {
        int generations = 1;
        if (Config.treeShape().getDescendentGenerations() > 0 && actualPerson.getChildrenCount(0) > 0) {
            generations++;
//        } else if (getConfiguration().getGenerationCount() > 1 && !actualPerson.hasNoParents()) {
//            generations++;
        }
        return generations;
    }

    private TreePanel generateTreePanel(TreeModel treeModel) {
        TreePanel treePanel = new TreePanel(treeModel);
        treePanel.addNotify();
        treePanel.validate();
        return treePanel;
    }
}
