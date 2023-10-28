package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.PersonBoxSetup;
import cz.ambrogenea.familyvision.gui.swing.description.PersonSetup;
import cz.ambrogenea.familyvision.gui.swing.dto.VisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.enums.Background;
import cz.ambrogenea.familyvision.gui.swing.enums.Diagram;
import cz.ambrogenea.familyvision.gui.swing.enums.LabelShape;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonSetupPanel extends JPanel {

    private final Window window;
    private final VisualConfiguration configuration;

    private JCheckBox ageCheckBox;
    private JCheckBox occupationCheckBox;
    private JCheckBox placesCheckBox;
    private JCheckBox shortenPlacesCheckBox;
    private JCheckBox templeCheckBox;
    private JCheckBox titleCheckBox;
    private JCheckBox childrenCountCheckBox;

    private JLabel diagramLabel;
    private JComboBox<String> diagramComboBox;
    private JLabel backgroundLabel;
    private JComboBox<String> backgroundComboBox;
    private JLabel marriageShapeLabel;
    private JComboBox<String> marriageShapeComboBox;

    private JPanel personBoxSetupPanel;
    private JLabel directLabel;
    private JLabel sideLabel;
    private JSpinner adultWidthSpinner;
    private JLabel widthLabel;
    private JSpinner siblingWidthSpinner;
    private JSpinner adultHeightSpinner;
    private JLabel heightLabel;
    private JSpinner siblingHeightSpinner;
    private JSpinner adultFontSizeSpinner;
    private JLabel fontSizeLabel;
    private JSpinner siblingFontSizeSpinner;
    private JSpinner verticalShiftSpinner;
    private JLabel verticalShiftLabel;


    public PersonSetupPanel(Window window) {
        super(new BorderLayout(0, 5));
        this.window = window;
        this.setPreferredSize(Dimensions.PERSON_SETUP_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);
        configuration = Config.visual();

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        Locale locale = configuration.getLocale();
        ResourceBundle description = ResourceBundle.getBundle("language/personSetup", locale);
        this.setBorder(new TitledBorder(description.getString(PersonSetup.TITLE)));

        ageCheckBox = new JCheckBox(description.getString(PersonSetup.AGE));
        ageCheckBox.setSelected(configuration.isShowAge());
        ageCheckBox.setOpaque(false);
        occupationCheckBox = new JCheckBox(description.getString(PersonSetup.OCCUPATION));
        occupationCheckBox.setSelected(configuration.isShowOccupation());
        occupationCheckBox.setOpaque(false);
        placesCheckBox = new JCheckBox(description.getString(PersonSetup.PLACES));
        placesCheckBox.setSelected(configuration.isShowPlaces());
        placesCheckBox.setOpaque(false);
        shortenPlacesCheckBox = new JCheckBox(description.getString(PersonSetup.SHORT_PLACES));
        shortenPlacesCheckBox.setSelected(configuration.isShortenPlaces());
        shortenPlacesCheckBox.setOpaque(false);
        templeCheckBox = new JCheckBox(description.getString(PersonSetup.TEMPLE));
        templeCheckBox.setSelected(configuration.isShowOrdinances());
        templeCheckBox.setOpaque(false);
        titleCheckBox = new JCheckBox(description.getString(PersonSetup.SHOW_TITLE));
        titleCheckBox.setSelected(configuration.isShowTitle());
        titleCheckBox.setOpaque(false);
        childrenCountCheckBox = new JCheckBox(description.getString(PersonSetup.SHOW_CHILDREN_COUNT));
        childrenCountCheckBox.setSelected(configuration.isShowChildrenCount());
        childrenCountCheckBox.setOpaque(false);

        diagramLabel = new JLabel(description.getString(PersonSetup.DIAGRAM), JLabel.LEFT);
        String[] names = new String[Diagram.values().length];
        for (int i = 0; i < names.length; i++) {
            names[i] = description.getString(Diagram.values()[i].toString());
        }
        diagramComboBox = new JComboBox<>(new DefaultComboBoxModel<>(names));
        diagramComboBox.setSelectedItem(description.getString(configuration.getDiagram().toString()));
        backgroundLabel = new JLabel(description.getString(PersonSetup.BACKGROUND), JLabel.LEFT);
        backgroundComboBox = new JComboBox<>(new DefaultComboBoxModel<>(Background.getStrings()));
        backgroundComboBox.setSelectedItem(configuration.getBackground().toString());
        marriageShapeLabel = new JLabel(description.getString(PersonSetup.MARRIAGE_SHAPE), JLabel.LEFT);
        marriageShapeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        marriageShapeComboBox.setSelectedItem(configuration.getMarriageLabelShape().toString());

        personBoxSetupPanel = new JPanel(new GridLayout(5, 3, 10, 5));
        personBoxSetupPanel.setBackground(Colors.SW_BACKGROUND);
        initPersonBoxComponents();
        initPersonBoxActions();
        addPersonBoxComponents();
    }

    private void initActions() {
        ageCheckBox.addActionListener(this::ageCheckBoxActionPerformed);
        occupationCheckBox.addActionListener(this::occupationCheckBoxActionPerformed);
        placesCheckBox.addActionListener(this::placesCheckBoxActionPerformed);
        shortenPlacesCheckBox.addActionListener(this::shortenPlacesCheckBoxActionPerformed);
        templeCheckBox.addActionListener(this::templeCheckBoxActionPerformed);
        titleCheckBox.addActionListener(this::titleCheckBoxActionPerformed);
        childrenCountCheckBox.addActionListener(this::childrenCountCheckBoxActionPerformed);
        diagramComboBox.addActionListener(this::diagramComboBoxActionPerformed);
        backgroundComboBox.addActionListener(this::backgroundComboBoxActionPerformed);
        marriageShapeComboBox.addActionListener(this::shapeLabelBoxActionPerformed);
    }

    private void addComponents() {
        JPanel checkboxes = new JPanel(new GridLayout(3, 3, 0, 5));
        checkboxes.add(ageCheckBox);
        checkboxes.add(occupationCheckBox);
        checkboxes.add(titleCheckBox);
        checkboxes.add(placesCheckBox);
        checkboxes.add(shortenPlacesCheckBox);
        checkboxes.add(templeCheckBox);
        checkboxes.add(childrenCountCheckBox);
        checkboxes.setBackground(Colors.SW_BACKGROUND);

        JPanel diagramPanel = new JPanel(new BorderLayout());
        diagramPanel.add(diagramLabel, BorderLayout.NORTH);
        diagramPanel.add(diagramComboBox, BorderLayout.SOUTH);
        diagramPanel.setBackground(Colors.SW_BACKGROUND);

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(backgroundLabel, BorderLayout.NORTH);
        backgroundPanel.add(backgroundComboBox, BorderLayout.SOUTH);
        backgroundPanel.setBackground(Colors.SW_BACKGROUND);

        JPanel marriagePanel = new JPanel(new BorderLayout());
        marriagePanel.add(marriageShapeLabel, BorderLayout.NORTH);
        marriagePanel.add(marriageShapeComboBox, BorderLayout.SOUTH);
        marriagePanel.setBackground(Colors.SW_BACKGROUND);

        JPanel comboBoxes = new JPanel(new GridLayout(1, 3, 10, 0));
        comboBoxes.add(diagramPanel);
        comboBoxes.add(backgroundPanel);
        comboBoxes.add(marriagePanel);
        comboBoxes.setBackground(Colors.SW_BACKGROUND);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, 5));

        JPanel top = new JPanel(new BorderLayout(0, 5));
        top.add(comboBoxes, BorderLayout.NORTH);
        top.add(checkboxes, BorderLayout.CENTER);
        top.add(sep, BorderLayout.SOUTH);
        top.setBackground(Colors.SW_BACKGROUND);

        this.add(top, BorderLayout.NORTH);
        this.add(personBoxSetupPanel, BorderLayout.CENTER);
    }

    private void ageCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowAge(ageCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void occupationCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowOccupation(occupationCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void placesCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowPlaces(placesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            shortenPlacesCheckBox.setSelected(false);
            configuration.setShortenPlaces(false);
        }
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void shortenPlacesCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShortenPlaces(shortenPlacesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            placesCheckBox.setSelected(true);
            configuration.setShowPlaces(true);
        }
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void templeCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowOrdinances(templeCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void titleCheckBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setShowTitle(titleCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void childrenCountCheckBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setShowChildrenCount(childrenCountCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void shapeLabelBoxActionPerformed(ActionEvent evt) {
        configuration.setMarriageLabelShape(LabelShape.valueOf(marriageShapeComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void diagramComboBoxActionPerformed(ActionEvent evt) {
        ResourceBundle description = ResourceBundle.getBundle("language/personSetup", configuration.getLocale());
        Iterator<String> it = description.getKeys().asIterator();
        String selectedDiagramName = Objects.requireNonNull(diagramComboBox.getSelectedItem()).toString();
        String diagramType;
        while (it.hasNext()) {
            diagramType = it.next();
            if (description.getString(diagramType).equals(selectedDiagramName)) {
                configuration.setDiagram(Diagram.fromString(diagramType));
                int imageWidth;
                int imageHeight;

                switch (configuration.getDiagram()) {
                    case HERALDRY -> {
                        imageWidth = Dimensions.PORTRAIT_IMAGE_WIDTH;
                        imageHeight = Dimensions.PORTRAIT_IMAGE_HEIGHT;
                    }
                    case SCROLL -> {
                        imageWidth = Dimensions.SCROLL_IMAGE_WIDTH;
                        imageHeight = Dimensions.SCROLL_IMAGE_HEIGHT;
                    }
                    case DOUBLE_WAVE -> {
                        imageWidth = Dimensions.DOUBLE_WAVE_IMAGE_WIDTH;
                        imageHeight = Dimensions.DOUBLE_WAVE_IMAGE_HEIGHT;
                    }
                    default -> {
                        imageWidth = Dimensions.DEFAULT_IMAGE_WIDTH;
                        imageHeight = Dimensions.DEFAULT_IMAGE_HEIGHT;
                    }
                }
                configuration.setAdultImageWidth(imageWidth);
                configuration.setSiblingImageWidth(imageWidth);
                adultWidthSpinner.setValue(imageWidth);
                siblingWidthSpinner.setValue(imageWidth);

                configuration.setAdultImageHeight(imageHeight);
                configuration.setSiblingImageHeight(imageHeight);
                adultHeightSpinner.setValue(imageHeight);
                siblingHeightSpinner.setValue(imageHeight);

                window.updateTree();
                break;
            }
        }
    }

    private void backgroundComboBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setBackground(Background.valueOf(backgroundComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void initPersonBoxComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", configuration.getLocale());
        directLabel = new JLabel(description.getString(PersonBoxSetup.DIRECT), JLabel.CENTER);
        sideLabel = new JLabel(description.getString(PersonBoxSetup.SIDE), JLabel.CENTER);
        heightLabel = new JLabel(description.getString(PersonBoxSetup.HEIGHT), JLabel.CENTER);
        heightLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_HEIGHT));
        widthLabel = new JLabel(description.getString(PersonBoxSetup.WIDTH), JLabel.CENTER);
        widthLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_HEIGHT));
        fontSizeLabel = new JLabel(description.getString(PersonBoxSetup.FONT_SIZE), JLabel.CENTER);
        fontSizeLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_HEIGHT));

        adultWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageWidth(), 100, 300, 10));
        adultHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageHeight(), 100, 300, 10));
        adultFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultFontSize(), 10, 22, 1));
        siblingWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageWidth(), 100, 300, 10));
        siblingHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageHeight(), 100, 300, 10));
        siblingFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingFontSize(), 10, 22, 1));

        verticalShiftSpinner = new JSpinner(new SpinnerNumberModel(configuration.getVerticalShift(), -30, 30, 5));
        verticalShiftLabel = new JLabel(description.getString(PersonBoxSetup.VERTICAL_SHIFT), JLabel.CENTER);
    }

    private void initPersonBoxActions() {
        adultWidthSpinner.addChangeListener(this::adultWidthSpinnerStateChanged);
        adultHeightSpinner.addChangeListener(this::adultHeightSpinnerStateChanged);
        adultFontSizeSpinner.addChangeListener(this::fontSizeSpinnerStateChanged);
        siblingWidthSpinner.addChangeListener(this::siblingsWidthSpinnerStateChanged);
        siblingHeightSpinner.addChangeListener(this::siblingsHeightSpinnerStateChanged);
        siblingFontSizeSpinner.addChangeListener(this::siblingFontSizeSpinnerStateChanged);
        verticalShiftSpinner.addChangeListener(this::adultVerticalShiftSpinnerStateChanged);
    }

    private void addPersonBoxComponents() {
        personBoxSetupPanel.add(directLabel);
        personBoxSetupPanel.add(new JLabel(""));
        personBoxSetupPanel.add(sideLabel);

        personBoxSetupPanel.add(adultWidthSpinner);
        personBoxSetupPanel.add(widthLabel);
        personBoxSetupPanel.add(siblingWidthSpinner);

        personBoxSetupPanel.add(adultHeightSpinner);
        personBoxSetupPanel.add(heightLabel);
        personBoxSetupPanel.add(siblingHeightSpinner);

        personBoxSetupPanel.add(adultFontSizeSpinner);
        personBoxSetupPanel.add(fontSizeLabel);
        personBoxSetupPanel.add(siblingFontSizeSpinner);

        personBoxSetupPanel.add(verticalShiftSpinner);
        personBoxSetupPanel.add(verticalShiftLabel);
    }

    private void adultWidthSpinnerStateChanged(ChangeEvent evt) {
        int adultWidth = Integer.parseInt(adultWidthSpinner.getValue().toString());
        configuration.setAdultImageWidth(adultWidth);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void adultHeightSpinnerStateChanged(ChangeEvent evt) {
        int adultHeight = Integer.parseInt(adultHeightSpinner.getValue().toString());
        configuration.setAdultImageHeight(adultHeight);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void siblingsWidthSpinnerStateChanged(ChangeEvent evt) {
        int siblingsWidth = Integer.parseInt(siblingWidthSpinner.getValue().toString());
        configuration.setSiblingImageWidth(siblingsWidth);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void siblingsHeightSpinnerStateChanged(ChangeEvent evt) {
        int siblingsHeight = Integer.parseInt(siblingHeightSpinner.getValue().toString());
        configuration.setSiblingImageHeight(siblingsHeight);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void fontSizeSpinnerStateChanged(ChangeEvent evt) {
        int adultFontSize = Integer.parseInt(adultFontSizeSpinner.getValue().toString());
        configuration.setAdultFontSize(adultFontSize);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void siblingFontSizeSpinnerStateChanged(ChangeEvent evt) {
        int siblingsFontSize = Integer.parseInt(siblingFontSizeSpinner.getValue().toString());
        configuration.setSiblingFontSize(siblingsFontSize);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void adultVerticalShiftSpinnerStateChanged(ChangeEvent evt) {
        int adultVerticalShift = Integer.parseInt(verticalShiftSpinner.getValue().toString());
        configuration.setVerticalShift(adultVerticalShift);
        window.updateConfiguration(configuration);
        window.updateTree();
    }

}
