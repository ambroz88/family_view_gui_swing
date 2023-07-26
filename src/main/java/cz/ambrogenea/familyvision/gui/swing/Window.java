package cz.ambrogenea.familyvision.gui.swing;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.controller.PersonController;
import cz.ambrogenea.familyvision.controller.TreeGeneratorController;
import cz.ambrogenea.familyvision.controller.TreeShapeConfigurationController;
import cz.ambrogenea.familyvision.controller.VisualConfigurationController;
import cz.ambrogenea.familyvision.dto.AncestorPerson;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreePanel;
import cz.ambrogenea.familyvision.gui.swing.components.draw.TreeScrollPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.DataTablePanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.MenuPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.PersonSetupPanel;
import cz.ambrogenea.familyvision.gui.swing.components.setup.TreeSetupPanel;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.dto.*;
import cz.ambrogenea.familyvision.gui.swing.model.Table;
import cz.ambrogenea.familyvision.gui.swing.service.Config;
import cz.ambrogenea.familyvision.gui.swing.service.JsonParser;
import cz.ambrogenea.familyvision.service.SelectionService;
import cz.ambrogenea.familyvision.service.impl.selection.LineageSelectionService;
import cz.ambrogenea.familyvision.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    private final TreeGeneratorController generatorController;
    private final TreeShapeConfigurationController treeShapeConfigController;
    private final VisualConfigurationController visualConfigController;

    public Window() {
        generatorController = new TreeGeneratorController();
        treeShapeConfigController = new TreeShapeConfigurationController();
        visualConfigController = new VisualConfigurationController();
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> new Window().setVisible(true));
    }

    public void loadTable(FamilyTree familyTree) {
        List<Person> persons = new PersonController().getAll(getTreeId()).stream()
                .map(s -> {
                            try {
                                return JsonParser.get().readValue(s, Person.class);
                            } catch (JsonProcessingException e) {
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        dataTablePanel.setModel(new Table(persons));
        this.setTitle(TITLE + " - " + familyTree.treeName());
    }

    public void updateConfiguration(VisualConfiguration configuration) {
        try {
            visualConfigController.update(JsonParser.get().writeValueAsString(configuration));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void updateConfiguration(TreeShapeConfiguration configuration) {
        try {
            treeShapeConfigController.update(JsonParser.get().writeValueAsString(configuration));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void generateTree() {
        if (dataTablePanel.getSelectedPersonId() != null) {
            Long personId = dataTablePanel.getSelectedPersonId();
            try {
                TreeModel treeModel = JsonParser.get().readValue(generatorController.generateTree(personId), TreeModel.class);
                treeScrollPane.generateTreePanel(treeModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTree() {
        if (dataTablePanel.getSelectedPersonId() != null) {
            try {
                TreeModel treeModel = JsonParser.get().readValue(generatorController.updateTree(), TreeModel.class);
                treeScrollPane.generateTreePanel(treeModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public TreePanel getTreePanel() {
        return treeScrollPane.getTreePanel();
    }

    public void generateDocument() {
        if (dataTablePanel.getSelectedPersonId() != null) {
            XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);

            Long personId = dataTablePanel.getSelectedPersonId();
            SelectionService selectionService = new LineageSelectionService();
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
//        TreeModel treeModel = new FatherLineageTreeService().generateTreeModel(personWithAncestors);
//        return generateTreePanel(treeModel);
        return null;
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
        if (Config.treeShape().getDescendentGenerations() > 0 && actualPerson.getSpouseCouple().getChildren().size() > 0) {
            generations++;
//        } else if (getConfiguration().getGenerationCount() > 1 && !actualPerson.hasNoParents()) {
//            generations++;
        }
        return generations;
    }

    private Long getTreeId() {
        return loadingDataPanel.getSelectedTree().id();
    }

    private TreePanel generateTreePanel(TreeModel treeModel) {
        TreePanel treePanel = new TreePanel(treeModel);
        treePanel.addNotify();
        treePanel.validate();
        return treePanel;
    }
}
