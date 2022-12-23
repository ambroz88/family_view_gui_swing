package org.ambrogenea.familyview.gui.swing;

import org.ambrogenea.familyview.configuration.Configuration;
import org.ambrogenea.familyview.domain.FamilyData;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.gui.swing.components.draw.TreePanel;
import org.ambrogenea.familyview.gui.swing.components.draw.TreeScrollPanel;
import org.ambrogenea.familyview.gui.swing.components.setup.*;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.ParsingService;
import org.ambrogenea.familyview.service.SelectionService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.DefaultConfigurationService;
import org.ambrogenea.familyview.service.impl.parsing.GedcomParsingService;
import org.ambrogenea.familyview.service.impl.selection.DescendentSelectionService;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

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

    private final ConfigurationService configuration;

    private MenuPanel loadingDataPanel;
    private TreeTypePanel treeTypePanel;
    private PersonSetupPanel personSetupPanel;
    private PersonBoxSetupPanel personBoxSetupPanel;
    private DataTablePanel dataTablePanel;

    private TreeSetupPanel treeSetupPanel;
    private TreeScrollPanel treeScrollPane;

    private SelectionService selectionService;
    private TreeService treeService;
    private FamilyData familyData;

    public Window() {
        configuration = new DefaultConfigurationService(new Configuration());
        configuration.addPropertyChangeListener(this);

        setWindowSize();
        initComponents();
        addComponents();
        initLogo();

        selectionService = new FathersSelectionService(configuration);
        treeService = new FatherLineageTreeService();
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
        treeTypePanel = new TreeTypePanel(this);
        personSetupPanel = new PersonSetupPanel(this);
        personBoxSetupPanel = new PersonBoxSetupPanel(this);
        dataTablePanel = new DataTablePanel(this);

        treeSetupPanel = new TreeSetupPanel(this);
        treeScrollPane = new TreeScrollPanel(this);
    }

    private void addComponents() {
        JPanel leftPanel = new JPanel(new BorderLayout(0, 5));
        leftPanel.setPreferredSize(Dimensions.LEFT_PANEL_DIMENSION);

        JPanel setupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        setupPanel.setPreferredSize(Dimensions.SETUP_PANEL_DIMENSION);
        setupPanel.add(loadingDataPanel);
        setupPanel.add(treeTypePanel);
        setupPanel.add(personSetupPanel);
        setupPanel.add(personBoxSetupPanel);
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
                familyData = parsingService.parse(inputStream);
                dataTablePanel.setModel(new Table(familyData, getConfiguration()));
                selectionService.setFamilyData(familyData);
                getConfiguration().setFamilyData(familyData);
                this.setTitle(TITLE + " - " + gedcomFile.getName());
//            recordsTable.setAutoCreateRowSorter(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateTree() {
        if (dataTablePanel.getSelectedRow() != -1) {
            String personId = familyData.getPersonByPosition(dataTablePanel.getSelectedRow()).getId();
            AncestorPerson rootPerson = selectionService.select(personId, getConfiguration().getGenerationCount());

            TreeModel treeModel = treeService.generateTreeModel(rootPerson, getConfiguration());
            treeScrollPane.generateTreePanel(treeModel);
        }
    }

    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }

    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
        this.selectionService.setFamilyData(familyData);
    }

    public TreePanel getTreePanel() {
        return treeScrollPane.getTreePanel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyName.NEW_TREE.toString())) {
            TreePanel panel = (TreePanel) evt.getNewValue();
            treeScrollPane.setTreePanel(panel);
        } else  if (evt.getPropertyName().equals(PropertyName.LINEAGE_CONFIG_CHANGE.toString())) {
//            personImage.update();
//            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
//            personImagePanel.repaint();
        } else if (evt.getPropertyName().equals(PropertyName.LINEAGE_SIZE_CHANGE.toString())) {
//            personImage.update();
//            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
//            adultSize.setText(configuration.getAdultImageWidth() + "x" + configuration.getAdultImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_SIZE_CHANGE.toString())) {
//            siblingImage.update();
//            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
//            siblingsSize.setText(configuration.getSiblingImageWidth() + "x" + configuration.getSiblingImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_CONFIG_CHANGE.toString())) {
//            siblingImage.update();
//            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
//            siblingsImagePanel.repaint();
        }
    }

    public ConfigurationService getConfiguration() {
        return configuration;
    }

    public boolean isInDescendentMode(){
        return selectionService.getClass().equals(DescendentSelectionService.class);
    }

    public void generateDocument() {
        if (dataTablePanel.getSelectedRow() != -1) {
            XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);

            String personId = familyData.getPersonByPosition(dataTablePanel.getSelectedRow()).getId();
            AncestorPerson rootPerson = selectionService.select(personId, 10);
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
                if (generations < configuration.getGenerationCount()) {
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
        DefaultConfigurationService config = new DefaultConfigurationService(new Configuration());
        config.setGenerationCount(10);
        TreeModel treeModel = treeService.generateTreeModel(personWithAncestors, config);
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
        if (getConfiguration().isShowChildren() && actualPerson.getChildrenCount(0) > 0) {
            generations++;
//        } else if (getConfiguration().getGenerationCount() > 1 && !actualPerson.hasNoParents()) {
//            generations++;
        }
        return generations;
    }

    private TreePanel generateTreePanel(TreeModel treeModel) {
        TreePanel treePanel = new TreePanel(treeModel, getConfiguration());
        treePanel.addNotify();
        treePanel.validate();
        return treePanel;
    }
}
