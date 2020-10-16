package org.ambrogenea.familyview.gui.swing;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.gui.swing.components.AdultPanel;
import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.components.SiblingPanel;
import org.ambrogenea.familyview.gui.swing.components.TreePanel;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.model.AncestorModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.DataModel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.DefaultConfigurationService;
import org.ambrogenea.familyview.service.impl.paging.AllAncestorPageSetup;
import org.ambrogenea.familyview.service.impl.paging.CloseFamilyPageSetup;
import org.ambrogenea.familyview.service.impl.paging.FatherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.MotherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.ParentLineagePageSetup;
import org.ambrogenea.familyview.service.impl.tree.AllAncestorTreeService;
import org.ambrogenea.familyview.service.impl.tree.CloseFamilyTreeService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.MotherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.ParentLineageTreeService;
import org.ambrogenea.familyview.utils.FileIO;
import org.ambrogenea.familyview.utils.Tools;
import org.ambrogenea.familyview.word.WordGenerator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ApplicationWindow extends JFrame implements PropertyChangeListener {

    private static final int BORDER_SIZE = 70;

    private DataModel dataModel;
    private final ConfigurationService configuration;
    private final JFileChooser openFC;
    private final PersonPanel personImage;
    private final PersonPanel siblingImage;
    private TreeService treeService;

    /**
     * Creates new form ApplicationWindow
     */
    public ApplicationWindow() {
        configuration = new DefaultConfigurationService();
        configuration.addPropertyChangeListener(this);

        initComponents();
        initLogo();

        personImage = new AdultPanel(Tools.generateSamplePerson(), configuration);
        personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
        personImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        personImagePanel.add(personImage);

        siblingImage = new SiblingPanel(Tools.generateSampleChild(), configuration);
        siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
        siblingsImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        siblingsImagePanel.add(siblingImage);

        openFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

        setWindowSize();
    }

    private void setWindowSize() throws HeadlessException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
        this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
    }

    private void initLogo() {
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("SW Icon.png"));
        setIconImage(img.getImage());
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("Logo 120x65.png"));
        logoLabel.setIcon(logo);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        treeTypeGroup = new javax.swing.ButtonGroup();
        settingsTab = new javax.swing.JTabbedPane();
        settingsRootPanel = new javax.swing.JPanel();
        loadInputButton = new javax.swing.JButton();
        tableScroll = new javax.swing.JScrollPane();
        recordsTable = new javax.swing.JTable();
        fileNameLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();
        generateTreeButton = new javax.swing.JButton();
        personBoxPanel = new javax.swing.JPanel();
        ageCheckBox = new javax.swing.JCheckBox();
        templeCheckBox = new javax.swing.JCheckBox();
        placesCheckBox = new javax.swing.JCheckBox();
        shortenPlacesCheckBox = new javax.swing.JCheckBox();
        occupationCheckBox = new javax.swing.JCheckBox();
        parentPanel = new javax.swing.JPanel();
        personImagePanel = new javax.swing.JPanel();
        TopOffsetLabel = new javax.swing.JLabel();
        bottomOffsetLabel = new javax.swing.JLabel();
        fontSizeLabel = new javax.swing.JLabel();
        diagramComboBox = new javax.swing.JComboBox<>();
        diagramLabel = new javax.swing.JLabel();
        imageWidthSlider = new javax.swing.JSlider();
        imageHeightSlider = new javax.swing.JSlider();
        fontSizeSpinner = new javax.swing.JSpinner();
        bottomOffsetSpinner = new javax.swing.JSpinner();
        topOffsetSpinner = new javax.swing.JSpinner();
        adultSizeLabel = new javax.swing.JLabel();
        adultSize = new javax.swing.JLabel();
        filenameLabel = new javax.swing.JLabel();
        siblingPanel = new javax.swing.JPanel();
        siblingsImagePanel = new javax.swing.JPanel();
        siblingsTopOffsetLabel = new javax.swing.JLabel();
        siblingsBottomOffsetLabel = new javax.swing.JLabel();
        siblingsFontSizeLabel = new javax.swing.JLabel();
        siblingsDiagramComboBox = new javax.swing.JComboBox<>();
        siblingsDiagramLabel = new javax.swing.JLabel();
        siblingsWidthSlider = new javax.swing.JSlider();
        siblingsHeightSlider = new javax.swing.JSlider();
        siblingFontSizeSpinner = new javax.swing.JSpinner();
        siblingsBottomOffsetSpinner = new javax.swing.JSpinner();
        siblingsTopOffsetSpinner = new javax.swing.JSpinner();
        siblingsSizeLabel = new javax.swing.JLabel();
        siblingsSize = new javax.swing.JLabel();
        resetModeCheckBox = new javax.swing.JCheckBox();
        treeTypePanel = new javax.swing.JPanel();
        fatherLineageType = new javax.swing.JRadioButton();
        motherLineageType = new javax.swing.JRadioButton();
        parentLineageType = new javax.swing.JRadioButton();
        allAncestorType = new javax.swing.JRadioButton();
        closeFamilyType = new javax.swing.JRadioButton();
        treeSetupPanel = new javax.swing.JPanel();
        spousesCheckbox = new javax.swing.JCheckBox();
        showSiblingsCheckbox = new javax.swing.JCheckBox();
        childrenCheckbox = new javax.swing.JCheckBox();
        showParentsCheckbox = new javax.swing.JCheckBox();
        showSiblingSpouse = new javax.swing.JCheckBox();
        generationsLabel = new javax.swing.JLabel();
        generationSpinner = new javax.swing.JSpinner();
        verticalViewCheckBox = new javax.swing.JCheckBox();
        heraldryCheckBox = new javax.swing.JCheckBox();
        marriageCheckBox = new javax.swing.JCheckBox();
        residenceCheckBox = new javax.swing.JCheckBox();
        shapeLabelBox = new javax.swing.JComboBox<>();
        generateWord = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Family Viewer");
        setName("rootFrame"); // NOI18N

        settingsTab.setName(""); // NOI18N

        loadInputButton.setText("Load GEDCOM file");
        loadInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadInputButtonActionPerformed(evt);
            }
        });

        recordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tableScroll.setViewportView(recordsTable);

        fileNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fileNameLabel.setText("no file selected");
        fileNameLabel.setMaximumSize(new java.awt.Dimension(148, 16));
        fileNameLabel.setPreferredSize(new java.awt.Dimension(148, 25));

        generateTreeButton.setText("Generate Tree");
        generateTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateTreeButtonActionPerformed(evt);
            }
        });

        personBoxPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Person box setup"));
        personBoxPanel.setPreferredSize(new java.awt.Dimension(165, 304));

        ageCheckBox.setSelected(configuration.isShowAge());
        ageCheckBox.setText("Show age");
        ageCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageCheckBoxActionPerformed(evt);
            }
        });

        templeCheckBox.setSelected(configuration.isShowTemple());
        templeCheckBox.setText("Show temple box");
        templeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templeCheckBoxActionPerformed(evt);
            }
        });

        placesCheckBox.setSelected(configuration.isShowPlaces());
        placesCheckBox.setText("Show places");
        placesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placesCheckBoxActionPerformed(evt);
            }
        });

        shortenPlacesCheckBox.setSelected(configuration.isShortenPlaces());
        shortenPlacesCheckBox.setText("Shorten places");
        shortenPlacesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortenPlacesCheckBoxActionPerformed(evt);
            }
        });

        occupationCheckBox.setSelected(configuration.isShowOccupation());
        occupationCheckBox.setText("Show occupation");
        occupationCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                occupationCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personBoxPanelLayout = new javax.swing.GroupLayout(personBoxPanel);
        personBoxPanel.setLayout(personBoxPanelLayout);
        personBoxPanelLayout.setHorizontalGroup(
            personBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(templeCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(placesCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ageCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(occupationCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shortenPlacesCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        personBoxPanelLayout.setVerticalGroup(
            personBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personBoxPanelLayout.createSequentialGroup()
                .addComponent(ageCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(occupationCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shortenPlacesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(templeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Person in direct lineage"));
        parentPanel.setPreferredSize(new java.awt.Dimension(469, 304));

        personImagePanel.setBackground(new java.awt.Color(255, 255, 204));
        personImagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        personImagePanel.setMaximumSize(new java.awt.Dimension(250, 215));
        personImagePanel.setPreferredSize(new java.awt.Dimension(250, 215));

        javax.swing.GroupLayout personImagePanelLayout = new javax.swing.GroupLayout(personImagePanel);
        personImagePanel.setLayout(personImagePanelLayout);
        personImagePanelLayout.setHorizontalGroup(
            personImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        personImagePanelLayout.setVerticalGroup(
            personImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        TopOffsetLabel.setText("Top offset");

        bottomOffsetLabel.setText("Bottom offset");

        fontSizeLabel.setText("Font size");

        diagramComboBox.setModel(new DefaultComboBoxModel<>(Diagrams.values()));
        diagramComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diagramComboBoxActionPerformed(evt);
            }
        });

        diagramLabel.setText("Diagram");

        imageWidthSlider.setMajorTickSpacing(10);
        imageWidthSlider.setMaximum(250);
        imageWidthSlider.setMinimum(100);
        imageWidthSlider.setMinorTickSpacing(5);
        imageWidthSlider.setPaintTicks(true);
        imageWidthSlider.setSnapToTicks(true);
        imageWidthSlider.setValue(configuration.getAdultImageWidth());
        imageWidthSlider.setMaximumSize(new java.awt.Dimension(250, 27));
        imageWidthSlider.setPreferredSize(new java.awt.Dimension(250, 27));
        imageWidthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                imageWidthSliderStateChanged(evt);
            }
        });

        imageHeightSlider.setMajorTickSpacing(10);
        imageHeightSlider.setMaximum(250);
        imageHeightSlider.setMinimum(120);
        imageHeightSlider.setMinorTickSpacing(5);
        imageHeightSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        imageHeightSlider.setPaintTicks(true);
        imageHeightSlider.setSnapToTicks(true);
        imageHeightSlider.setValue(configuration.getAdultImageHeight());
        imageHeightSlider.setMaximumSize(new java.awt.Dimension(29, 215));
        imageHeightSlider.setPreferredSize(new java.awt.Dimension(29, 215));
        imageHeightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                imageHeightSliderStateChanged(evt);
            }
        });

        fontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getAdultFontSize(), 10, 25, 1));
        fontSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fontSizeSpinnerStateChanged(evt);
            }
        });

        bottomOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getAdultBottomOffset(), 0, 40, 1));
        bottomOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bottomOffsetSpinnerStateChanged(evt);
            }
        });

        topOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getAdultTopOffset(), 0, 40, 1));
        topOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                topOffsetSpinnerStateChanged(evt);
            }
        });

        adultSizeLabel.setText("width x height:");

        adultSize.setText(configuration.getAdultImageWidth() + "x" + configuration.getAdultImageHeight());

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageHeightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageWidthSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(personImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diagramLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diagramComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(parentPanelLayout.createSequentialGroup()
                        .addComponent(bottomOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bottomOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parentPanelLayout.createSequentialGroup()
                            .addComponent(fontSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fontSizeSpinner))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parentPanelLayout.createSequentialGroup()
                            .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(adultSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TopOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(topOffsetSpinner)
                                .addComponent(adultSize, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(imageHeightSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addComponent(personImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                    .addGroup(parentPanelLayout.createSequentialGroup()
                        .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(adultSizeLabel)
                            .addComponent(adultSize))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(topOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TopOffsetLabel))
                        .addGap(18, 18, 18)
                        .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fontSizeLabel)
                            .addComponent(fontSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(diagramLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diagramComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bottomOffsetLabel)
                            .addComponent(bottomOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        diagramComboBox.setSelectedItem(configuration.getAdultDiagram());

        filenameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        filenameLabel.setText("Name of selected file:");

        siblingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Children and siblings"));
        siblingPanel.setPreferredSize(new java.awt.Dimension(469, 304));

        siblingsImagePanel.setBackground(new java.awt.Color(255, 255, 204));
        siblingsImagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        siblingsImagePanel.setMaximumSize(new java.awt.Dimension(250, 215));
        siblingsImagePanel.setPreferredSize(new java.awt.Dimension(250, 215));

        javax.swing.GroupLayout siblingsImagePanelLayout = new javax.swing.GroupLayout(siblingsImagePanel);
        siblingsImagePanel.setLayout(siblingsImagePanelLayout);
        siblingsImagePanelLayout.setHorizontalGroup(
            siblingsImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        siblingsImagePanelLayout.setVerticalGroup(
            siblingsImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        siblingsTopOffsetLabel.setText("Top offset");

        siblingsBottomOffsetLabel.setText("Bottom offset");

        siblingsFontSizeLabel.setText("Font size");

        siblingsDiagramComboBox.setModel(new DefaultComboBoxModel<>(Diagrams.values()));
        siblingsDiagramComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siblingsDiagramComboBoxActionPerformed(evt);
            }
        });

        siblingsDiagramLabel.setText("Diagram");

        siblingsWidthSlider.setMajorTickSpacing(10);
        siblingsWidthSlider.setMaximum(250);
        siblingsWidthSlider.setMinimum(100);
        siblingsWidthSlider.setMinorTickSpacing(5);
        siblingsWidthSlider.setPaintTicks(true);
        siblingsWidthSlider.setSnapToTicks(true);
        siblingsWidthSlider.setValue(configuration.getSiblingImageWidth());
        siblingsWidthSlider.setMaximumSize(new java.awt.Dimension(250, 27));
        siblingsWidthSlider.setPreferredSize(new java.awt.Dimension(250, 27));
        siblingsWidthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                siblingsWidthSliderStateChanged(evt);
            }
        });

        siblingsHeightSlider.setMajorTickSpacing(10);
        siblingsHeightSlider.setMaximum(250);
        siblingsHeightSlider.setMinimum(120);
        siblingsHeightSlider.setMinorTickSpacing(5);
        siblingsHeightSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        siblingsHeightSlider.setPaintTicks(true);
        siblingsHeightSlider.setSnapToTicks(true);
        siblingsHeightSlider.setValue(configuration.getSiblingImageHeight());
        siblingsHeightSlider.setMaximumSize(new java.awt.Dimension(29, 215));
        siblingsHeightSlider.setPreferredSize(new java.awt.Dimension(29, 215));
        siblingsHeightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                siblingsHeightSliderStateChanged(evt);
            }
        });

        siblingFontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getSiblingFontSize(), 10, 25, 1));
        siblingFontSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                siblingFontSizeSpinnerStateChanged(evt);
            }
        });

        siblingsBottomOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getSiblingBottomOffset(), 0, 40, 1));
        siblingsBottomOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                siblingsBottomOffsetSpinnerStateChanged(evt);
            }
        });

        siblingsTopOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getSiblingTopOffset(), 0, 40, 1));
        siblingsTopOffsetSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                siblingsTopOffsetSpinnerStateChanged(evt);
            }
        });

        siblingsSizeLabel.setText("width x height:");

        siblingsSize.setText(configuration.getSiblingImageWidth() + "x" + configuration.getSiblingImageHeight());

        javax.swing.GroupLayout siblingPanelLayout = new javax.swing.GroupLayout(siblingPanel);
        siblingPanel.setLayout(siblingPanelLayout);
        siblingPanelLayout.setHorizontalGroup(
            siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(siblingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(siblingsHeightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(siblingsWidthSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(siblingsImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(siblingsDiagramLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(siblingPanelLayout.createSequentialGroup()
                            .addComponent(siblingsFontSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(siblingFontSizeSpinner))
                        .addGroup(siblingPanelLayout.createSequentialGroup()
                            .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(siblingsSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(siblingsTopOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(siblingsTopOffsetSpinner)
                                .addComponent(siblingsSize, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(siblingPanelLayout.createSequentialGroup()
                        .addComponent(siblingsBottomOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(siblingsBottomOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(siblingsDiagramComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        siblingPanelLayout.setVerticalGroup(
            siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, siblingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(siblingsWidthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(siblingPanelLayout.createSequentialGroup()
                        .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(siblingsSizeLabel)
                            .addComponent(siblingsSize))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(siblingsTopOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(siblingsTopOffsetLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(siblingsFontSizeLabel)
                            .addComponent(siblingFontSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(siblingsDiagramLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siblingsDiagramComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(siblingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(siblingsBottomOffsetLabel)
                            .addComponent(siblingsBottomOffsetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(siblingsImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(siblingsHeightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        siblingsDiagramComboBox.setSelectedItem(configuration.getSiblingDiagram());

        resetModeCheckBox.setText("Reset mode");
        resetModeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetModeCheckBoxActionPerformed(evt);
            }
        });

        treeTypePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tree Type"));

        treeTypeGroup.add(fatherLineageType);
        fatherLineageType.setSelected(true);
        fatherLineageType.setText("Father's lineage");
        fatherLineageType.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                fatherLineageTypeAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        fatherLineageType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fatherLineageTypeActionPerformed(evt);
            }
        });

        treeTypeGroup.add(motherLineageType);
        motherLineageType.setText("Mother's lineage");
        motherLineageType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motherLineageTypeActionPerformed(evt);
            }
        });

        treeTypeGroup.add(parentLineageType);
        parentLineageType.setText("Parent's lineage");
        parentLineageType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentLineageTypeActionPerformed(evt);
            }
        });

        treeTypeGroup.add(allAncestorType);
        allAncestorType.setText("All ancestor couple");
        allAncestorType.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                allAncestorTypeStateChanged(evt);
            }
        });

        treeTypeGroup.add(closeFamilyType);
        closeFamilyType.setText("Close family");
        closeFamilyType.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                closeFamilyTypeStateChanged(evt);
            }
        });

        javax.swing.GroupLayout treeTypePanelLayout = new javax.swing.GroupLayout(treeTypePanel);
        treeTypePanel.setLayout(treeTypePanelLayout);
        treeTypePanelLayout.setHorizontalGroup(
            treeTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treeTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treeTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(allAncestorType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parentLineageType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(motherLineageType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fatherLineageType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeFamilyType, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        treeTypePanelLayout.setVerticalGroup(
            treeTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, treeTypePanelLayout.createSequentialGroup()
                .addComponent(fatherLineageType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(motherLineageType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parentLineageType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allAncestorType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeFamilyType)
                .addGap(26, 26, 26))
        );

        treeSetupPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tree setup"));

        spousesCheckbox.setSelected(configuration.isShowSpouses());
        spousesCheckbox.setText("Spouses for root");
        spousesCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spousesCheckboxActionPerformed(evt);
            }
        });

        showSiblingsCheckbox.setSelected(configuration.isShowSiblings());
        showSiblingsCheckbox.setText("Show siblings");
        showSiblingsCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSiblingsCheckboxActionPerformed(evt);
            }
        });

        childrenCheckbox.setSelected(configuration.isShowChildren());
        childrenCheckbox.setText("Show children");
        childrenCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                childrenCheckboxActionPerformed(evt);
            }
        });

        showParentsCheckbox.setSelected(configuration.isShowParents());
        showParentsCheckbox.setText("Show parents");
        showParentsCheckbox.setEnabled(false);
        showParentsCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showParentsCheckboxActionPerformed(evt);
            }
        });

        showSiblingSpouse.setSelected(configuration.isShowSiblingSpouses());
        showSiblingSpouse.setText("Sibling spouse");
        showSiblingSpouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSiblingSpouseActionPerformed(evt);
            }
        });

        generationsLabel.setText("Generations:");

        generationSpinner.setModel(new javax.swing.SpinnerNumberModel(configuration.getGenerationCount(), 1, 20, 1));
        generationSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                generationSpinnerStateChanged(evt);
            }
        });

        verticalViewCheckBox.setSelected(configuration.isShowCouplesVertical());
        verticalViewCheckBox.setText("Verical view");
        verticalViewCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalViewCheckBoxActionPerformed(evt);
            }
        });

        heraldryCheckBox.setSelected(configuration.isShowHeraldry());
        heraldryCheckBox.setText("Show heraldry");
        heraldryCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heraldryCheckBoxActionPerformed(evt);
            }
        });

        marriageCheckBox.setSelected(configuration.isShowMarriage());
        marriageCheckBox.setText("Show marriage");
        marriageCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marriageCheckBoxActionPerformed(evt);
            }
        });

        residenceCheckBox.setSelected(configuration.isShowResidence());
        residenceCheckBox.setText("Show residence");
        residenceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                residenceCheckBoxActionPerformed(evt);
            }
        });

        shapeLabelBox.setModel(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        shapeLabelBox.setSelectedItem(configuration.getLabelShape());
        shapeLabelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeLabelBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout treeSetupPanelLayout = new javax.swing.GroupLayout(treeSetupPanel);
        treeSetupPanel.setLayout(treeSetupPanelLayout);
        treeSetupPanelLayout.setHorizontalGroup(
            treeSetupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(treeSetupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(treeSetupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(marriageCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(residenceCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(childrenCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showParentsCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showSiblingsCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showSiblingSpouse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, treeSetupPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(generationsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(generationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(heraldryCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spousesCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verticalViewCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shapeLabelBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        treeSetupPanelLayout.setVerticalGroup(
            treeSetupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, treeSetupPanelLayout.createSequentialGroup()
                .addComponent(verticalViewCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(heraldryCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marriageCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(residenceCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spousesCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(childrenCheckbox)
                .addGap(5, 5, 5)
                .addComponent(showParentsCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showSiblingsCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showSiblingSpouse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(treeSetupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generationsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shapeLabelBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        generateWord.setText("Generate Word");
        generateWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateWordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsRootPanelLayout = new javax.swing.GroupLayout(settingsRootPanel);
        settingsRootPanel.setLayout(settingsRootPanelLayout);
        settingsRootPanelLayout.setHorizontalGroup(
            settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(filenameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fileNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(generateWord, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(loadInputButton))))
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(generateTreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsRootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(treeTypePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(treeSetupPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(personBoxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(resetModeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(siblingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(tableScroll))
                .addContainerGap())
        );
        settingsRootPanelLayout.setVerticalGroup(
            settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(personBoxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetModeCheckBox))
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(loadInputButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filenameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateTreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(treeTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(siblingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addComponent(treeSetupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(generateWord)
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addComponent(tableScroll))
                .addContainerGap())
        );

        settingsTab.addTab("Settings", settingsRootPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsTab))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsTab)
                .addContainerGap())
        );

        settingsTab.getAccessibleContext().setAccessibleName("Settings");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void childrenCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_childrenCheckboxActionPerformed
        configuration.setShowChildren(childrenCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            spousesCheckbox.setSelected(true);
            configuration.setShowSpouses(true);
        }
    }//GEN-LAST:event_childrenCheckboxActionPerformed

    private void generationSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_generationSpinnerStateChanged
        configuration.setGenerationCount((int) generationSpinner.getValue());
    }//GEN-LAST:event_generationSpinnerStateChanged

    private void spousesCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spousesCheckboxActionPerformed
        configuration.setShowSpouses(spousesCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            childrenCheckbox.setSelected(false);
            configuration.setShowChildren(false);
        }
    }//GEN-LAST:event_spousesCheckboxActionPerformed

    private void showSiblingsCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSiblingsCheckboxActionPerformed
        configuration.setShowSiblings(showSiblingsCheckbox.isSelected());
    }//GEN-LAST:event_showSiblingsCheckboxActionPerformed

    private void generateTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateTreeButtonActionPerformed
        if (recordsTable.getSelectedRow() != -1) {

            AncestorModel ancestors = new AncestorModel(dataModel, configuration.getGenerationCount());
            configuration.setAncestorModel(ancestors);
            AncestorPerson rootPerson;

            DrawingFrame drawing = new DrawingFrame(treeService);
            PageSetup setup;
            if (fatherLineageType.isSelected()) {
                rootPerson = ancestors.generateFatherLineage(recordsTable.getSelectedRow());
                setup = new FatherLineagePageSetup(configuration, rootPerson);
            } else if (motherLineageType.isSelected()) {
                rootPerson = ancestors.generateMotherLineage(recordsTable.getSelectedRow());
                setup = new MotherLineagePageSetup(configuration, rootPerson);
            } else if (parentLineageType.isSelected()) {
                rootPerson = ancestors.generateParentsLineage(recordsTable.getSelectedRow());
                setup = new ParentLineagePageSetup(configuration, rootPerson);
            } else if (allAncestorType.isSelected()) {
                rootPerson = ancestors.generateAncestors(recordsTable.getSelectedRow());
                setup = new AllAncestorPageSetup(configuration, rootPerson);
            } else if (closeFamilyType.isSelected()) {
                rootPerson = ancestors.generateCloseFamily(recordsTable.getSelectedRow());
                setup = new CloseFamilyPageSetup(configuration, rootPerson);
            } else {
                rootPerson = ancestors.generateFatherLineage(recordsTable.getSelectedRow());
                setup = new FatherLineagePageSetup(configuration, rootPerson);
            }

            drawing.generateTreePanel(rootPerson, setup, configuration);

            settingsTab.addTab(rootPerson.getName(), drawing);
            settingsTab.setSelectedIndex(settingsTab.getTabCount() - 1);
        }
    }//GEN-LAST:event_generateTreeButtonActionPerformed

    private void loadInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadInputButtonActionPerformed
        int returnVal = openFC.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFC.getSelectedFile();
            loadTable(file.getAbsolutePath());
            fileNameLabel.setText(file.getName());
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_loadInputButtonActionPerformed

    private void showParentsCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showParentsCheckboxActionPerformed
        configuration.setShowParents(showParentsCheckbox.isSelected());
        if (!showParentsCheckbox.isSelected() && showSiblingsCheckbox.isSelected()) {
            configuration.setShowSiblings(false);
            showSiblingsCheckbox.setSelected(false);
        }
    }//GEN-LAST:event_showParentsCheckboxActionPerformed

    private void heraldryCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heraldryCheckBoxActionPerformed
        configuration.setShowHeraldry(heraldryCheckBox.isSelected());
    }//GEN-LAST:event_heraldryCheckBoxActionPerformed

    private void templeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_templeCheckBoxActionPerformed
        configuration.setShowTemple(templeCheckBox.isSelected());
    }//GEN-LAST:event_templeCheckBoxActionPerformed

    private void generateWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateWordActionPerformed
        if (recordsTable.getSelectedRow() != -1) {
            XWPFDocument doc = WordGenerator.createWordDocument(WordGenerator.FORMAT_A4);

            AncestorModel ancestors = new AncestorModel(dataModel, configuration.getGenerationCount());
            AncestorPerson rootPerson = ancestors.generateCloseFamily(recordsTable.getSelectedRow());
            addFamilyToDoc(rootPerson, doc);
            createFamilyDocument(rootPerson.getFather(), doc);

            saveFamilyDocument(rootPerson, doc);
        }
    }//GEN-LAST:event_generateWordActionPerformed

    private void residenceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_residenceCheckBoxActionPerformed
        configuration.setShowResidence(residenceCheckBox.isSelected());
    }//GEN-LAST:event_residenceCheckBoxActionPerformed

    private void ageCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageCheckBoxActionPerformed
        configuration.setShowAge(ageCheckBox.isSelected());
    }//GEN-LAST:event_ageCheckBoxActionPerformed

    private void placesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placesCheckBoxActionPerformed
        configuration.setShowPlaces(placesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            shortenPlacesCheckBox.setSelected(false);
            configuration.setShortenPlaces(false);
        }
    }//GEN-LAST:event_placesCheckBoxActionPerformed

    private void shortenPlacesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortenPlacesCheckBoxActionPerformed
        configuration.setShortenPlaces(shortenPlacesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            placesCheckBox.setSelected(true);
            configuration.setShowPlaces(true);
        }
    }//GEN-LAST:event_shortenPlacesCheckBoxActionPerformed

    private void occupationCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_occupationCheckBoxActionPerformed
        configuration.setShowOccupation(occupationCheckBox.isSelected());
    }//GEN-LAST:event_occupationCheckBoxActionPerformed

    private void diagramComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diagramComboBoxActionPerformed
        configuration.setAdultDiagram(Diagrams.fromString(diagramComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_diagramComboBoxActionPerformed

    private void imageWidthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_imageWidthSliderStateChanged
        int imageWidth = imageWidthSlider.getValue();
        configuration.setAdultImageWidth(imageWidth);
    }//GEN-LAST:event_imageWidthSliderStateChanged

    private void imageHeightSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_imageHeightSliderStateChanged
        int imageHeight = imageHeightSlider.getValue();
        configuration.setAdultImageHeight(imageHeight);
    }//GEN-LAST:event_imageHeightSliderStateChanged

    private void fontSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fontSizeSpinnerStateChanged
        int fontSize = Integer.valueOf(fontSizeSpinner.getValue().toString());
        configuration.setAdultFontSize(fontSize);
    }//GEN-LAST:event_fontSizeSpinnerStateChanged

    private void topOffsetSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_topOffsetSpinnerStateChanged
        int topOffset = Integer.valueOf(topOffsetSpinner.getValue().toString());
        configuration.setAdultTopOffset(topOffset);
    }//GEN-LAST:event_topOffsetSpinnerStateChanged

    private void bottomOffsetSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bottomOffsetSpinnerStateChanged
        int bottomOffset = Integer.valueOf(bottomOffsetSpinner.getValue().toString());
        configuration.setAdultBottomOffset(bottomOffset);
    }//GEN-LAST:event_bottomOffsetSpinnerStateChanged

    private void marriageCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marriageCheckBoxActionPerformed
        configuration.setShowMarriage(marriageCheckBox.isSelected());
    }//GEN-LAST:event_marriageCheckBoxActionPerformed

    private void resetModeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetModeCheckBoxActionPerformed
        configuration.setResetMode(resetModeCheckBox.isSelected());
    }//GEN-LAST:event_resetModeCheckBoxActionPerformed

    private void siblingsDiagramComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siblingsDiagramComboBoxActionPerformed
        configuration.setSiblingDiagram(Diagrams.fromString(siblingsDiagramComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_siblingsDiagramComboBoxActionPerformed

    private void siblingsWidthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_siblingsWidthSliderStateChanged
        int imageWidth = siblingsWidthSlider.getValue();
        configuration.setSiblingImageWidth(imageWidth);
    }//GEN-LAST:event_siblingsWidthSliderStateChanged

    private void siblingsHeightSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_siblingsHeightSliderStateChanged
        int imageHeight = siblingsHeightSlider.getValue();
        configuration.setSiblingImageHeight(imageHeight);
    }//GEN-LAST:event_siblingsHeightSliderStateChanged

    private void siblingFontSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_siblingFontSizeSpinnerStateChanged
        int fontSize = Integer.valueOf(siblingFontSizeSpinner.getValue().toString());
        configuration.setSiblingFontSize(fontSize);
    }//GEN-LAST:event_siblingFontSizeSpinnerStateChanged

    private void siblingsBottomOffsetSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_siblingsBottomOffsetSpinnerStateChanged
        int bottomOffset = Integer.valueOf(siblingsBottomOffsetSpinner.getValue().toString());
        configuration.setSiblingBottomOffset(bottomOffset);
    }//GEN-LAST:event_siblingsBottomOffsetSpinnerStateChanged

    private void siblingsTopOffsetSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_siblingsTopOffsetSpinnerStateChanged
        int topOffset = Integer.valueOf(siblingsTopOffsetSpinner.getValue().toString());
        configuration.setSiblingTopOffset(topOffset);
    }//GEN-LAST:event_siblingsTopOffsetSpinnerStateChanged

    private void shapeLabelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeLabelBoxActionPerformed
        configuration.setLabelShape(LabelShape.valueOf(shapeLabelBox.getSelectedItem().toString()));
    }//GEN-LAST:event_shapeLabelBoxActionPerformed

    private void showSiblingSpouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSiblingSpouseActionPerformed
        configuration.setShowSiblingSpouses(showSiblingSpouse.isSelected());
    }//GEN-LAST:event_showSiblingSpouseActionPerformed

    private void verticalViewCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalViewCheckBoxActionPerformed
        configuration.setShowCouplesVertical(verticalViewCheckBox.isSelected());
    }//GEN-LAST:event_verticalViewCheckBoxActionPerformed

    private void fatherLineageTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fatherLineageTypeActionPerformed
        if (fatherLineageType.isSelected()) {
            treeService = new FatherLineageTreeService();
        }
    }//GEN-LAST:event_fatherLineageTypeActionPerformed

    private void motherLineageTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motherLineageTypeActionPerformed
        if (motherLineageType.isSelected()) {
            treeService = new MotherLineageTreeService();
        }
    }//GEN-LAST:event_motherLineageTypeActionPerformed

    private void parentLineageTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentLineageTypeActionPerformed
        if (parentLineageType.isSelected()) {
            treeService = new ParentLineageTreeService();
        }

    }//GEN-LAST:event_parentLineageTypeActionPerformed

    private void allAncestorTypeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_allAncestorTypeStateChanged
        if (allAncestorType.isSelected()) {
            treeService = new AllAncestorTreeService();
            showSiblingsCheckbox.setEnabled(false);
            showSiblingSpouse.setEnabled(false);
        } else {
            showSiblingsCheckbox.setEnabled(true);
            showSiblingSpouse.setEnabled(true);
        }
    }//GEN-LAST:event_allAncestorTypeStateChanged

    private void closeFamilyTypeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_closeFamilyTypeStateChanged
        if (closeFamilyType.isSelected()) {
            treeService = new CloseFamilyTreeService();
            showParentsCheckbox.setEnabled(true);
            verticalViewCheckBox.setEnabled(false);
            generationSpinner.setEnabled(false);
        } else {
            showParentsCheckbox.setEnabled(false);
            verticalViewCheckBox.setEnabled(true);
            generationSpinner.setEnabled(true);
        }
    }//GEN-LAST:event_closeFamilyTypeStateChanged

    private void fatherLineageTypeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_fatherLineageTypeAncestorAdded
        treeService = new FatherLineageTreeService();
    }//GEN-LAST:event_fatherLineageTypeAncestorAdded

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
        CloseFamilyPageSetup setup = new CloseFamilyPageSetup(configuration, actualPerson);

        TreePanel familyPanel = createOneFamily(actualPerson, setup);
        int generations = setup.calculateGenerations(actualPerson);

        WordGenerator.setMaxHeight(generations);
        WordGenerator.createFamilyPage(doc, "Rodina " + actualPerson.getName());
        WordGenerator.addImageToPage(doc, familyPanel.getStream(), familyPanel.getWidth(), familyPanel.getHeight());
    }

    private TreePanel createOneFamily(AncestorPerson personWithAncestors, PageSetup setup) {
        TreeService closeFamilyTreeService = new CloseFamilyTreeService();
        TreeModel treeModel = closeFamilyTreeService.generateTreeModel(personWithAncestors, setup.getRootPosition(), configuration);
        TreePanel familyPanel = new TreePanel(treeModel, configuration);
        familyPanel.setPreferredSize(new Dimension(setup.getWidth(), setup.getHeight()));

        familyPanel.addNotify();
        familyPanel.validate();
        return familyPanel;
    }

    private void saveFamilyDocument(AncestorPerson personWithAncestors, XWPFDocument doc) {
        try {
            WordGenerator.writeDocument(System.getProperty("user.home") + "/Documents/Genealogie/" + personWithAncestors.getName() + ".docx", doc);
        } catch (IOException ex) {
            System.out.println("It is not possible to save document: " + ex.getMessage());
        }
    }

    private void loadTable(String absolutePath) {
        try {
            ArrayList<String> lines = FileIO.FileToLines(absolutePath);
            dataModel = new DataModel();
            dataModel.loadGEDCOMLines(lines);
            recordsTable.setModel(new Table(dataModel));
            recordsTable.setAutoCreateRowSorter(true);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TopOffsetLabel;
    private javax.swing.JLabel adultSize;
    private javax.swing.JLabel adultSizeLabel;
    private javax.swing.JCheckBox ageCheckBox;
    private javax.swing.JRadioButton allAncestorType;
    private javax.swing.JLabel bottomOffsetLabel;
    private javax.swing.JSpinner bottomOffsetSpinner;
    private javax.swing.JCheckBox childrenCheckbox;
    private javax.swing.JRadioButton closeFamilyType;
    private javax.swing.JComboBox<Diagrams> diagramComboBox;
    private javax.swing.JLabel diagramLabel;
    private javax.swing.JRadioButton fatherLineageType;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JLabel filenameLabel;
    private javax.swing.JLabel fontSizeLabel;
    private javax.swing.JSpinner fontSizeSpinner;
    private javax.swing.JButton generateTreeButton;
    private javax.swing.JButton generateWord;
    private javax.swing.JSpinner generationSpinner;
    private javax.swing.JLabel generationsLabel;
    private javax.swing.JCheckBox heraldryCheckBox;
    private javax.swing.JSlider imageHeightSlider;
    private javax.swing.JSlider imageWidthSlider;
    private javax.swing.JButton loadInputButton;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JCheckBox marriageCheckBox;
    private javax.swing.JRadioButton motherLineageType;
    private javax.swing.JCheckBox occupationCheckBox;
    private javax.swing.JRadioButton parentLineageType;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JPanel personBoxPanel;
    private javax.swing.JPanel personImagePanel;
    private javax.swing.JCheckBox placesCheckBox;
    private javax.swing.JTable recordsTable;
    private javax.swing.JCheckBox resetModeCheckBox;
    private javax.swing.JCheckBox residenceCheckBox;
    private javax.swing.JPanel settingsRootPanel;
    private javax.swing.JTabbedPane settingsTab;
    private javax.swing.JComboBox<String> shapeLabelBox;
    private javax.swing.JCheckBox shortenPlacesCheckBox;
    private javax.swing.JCheckBox showParentsCheckbox;
    private javax.swing.JCheckBox showSiblingSpouse;
    private javax.swing.JCheckBox showSiblingsCheckbox;
    private javax.swing.JSpinner siblingFontSizeSpinner;
    private javax.swing.JPanel siblingPanel;
    private javax.swing.JLabel siblingsBottomOffsetLabel;
    private javax.swing.JSpinner siblingsBottomOffsetSpinner;
    private javax.swing.JComboBox<Diagrams> siblingsDiagramComboBox;
    private javax.swing.JLabel siblingsDiagramLabel;
    private javax.swing.JLabel siblingsFontSizeLabel;
    private javax.swing.JSlider siblingsHeightSlider;
    private javax.swing.JPanel siblingsImagePanel;
    private javax.swing.JLabel siblingsSize;
    private javax.swing.JLabel siblingsSizeLabel;
    private javax.swing.JLabel siblingsTopOffsetLabel;
    private javax.swing.JSpinner siblingsTopOffsetSpinner;
    private javax.swing.JSlider siblingsWidthSlider;
    private javax.swing.JCheckBox spousesCheckbox;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JCheckBox templeCheckBox;
    private javax.swing.JSpinner topOffsetSpinner;
    private javax.swing.JPanel treeSetupPanel;
    private javax.swing.ButtonGroup treeTypeGroup;
    private javax.swing.JPanel treeTypePanel;
    private javax.swing.JCheckBox verticalViewCheckBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyName.NEW_TREE.toString())) {
            JComponent panel = (JComponent) evt.getNewValue();
            settingsTab.addTab(evt.getOldValue().toString(), panel);
            settingsTab.setSelectedIndex(settingsTab.getTabCount() - 1);
        } else if (evt.getPropertyName().equals(PropertyName.LINEAGE_CONFIG_CHANGE.toString())) {
            personImage.update();
            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
            personImagePanel.repaint();
        } else if (evt.getPropertyName().equals(PropertyName.LINEAGE_SIZE_CHANGE.toString())) {
            personImage.update();
            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
            adultSize.setText(configuration.getAdultImageWidth() + "x" + configuration.getAdultImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_SIZE_CHANGE.toString())) {
            siblingImage.update();
            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
            siblingsSize.setText(configuration.getSiblingImageWidth() + "x" + configuration.getSiblingImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_CONFIG_CHANGE.toString())) {
            siblingImage.update();
            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
            siblingsImagePanel.repaint();
        }
    }

}
