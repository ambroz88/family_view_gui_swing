package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.PersonBoxSetup;
import cz.ambrogenea.familyvision.gui.swing.description.PersonSetup;
import cz.ambrogenea.familyvision.gui.swing.dto.PersonVisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.enums.Diagram;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonSetupPanel extends JPanel {

    private final Window window;
    private final PersonVisualConfiguration configuration;

    private JCheckBox ageCheckBox;
    private JCheckBox occupationCheckBox;
    private JCheckBox placesCheckBox;
    private JCheckBox shortenPlacesCheckBox;
    private JCheckBox templeCheckBox;

    private JLabel diagramLabel;
    private JComboBox<String> diagramComboBox;

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
        super(new FlowLayout(FlowLayout.LEFT));
        this.window = window;
        this.setPreferredSize(Dimensions.SETUP_PANEL_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);
        configuration = Config.person();

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/personSetup", configuration.getLocale());

        diagramLabel = new JLabel(description.getString(PersonSetup.DIAGRAM), JLabel.LEFT);
        String[] names = new String[Diagram.values().length];
        for (int i = 0; i < names.length; i++) {
            names[i] = description.getString(Diagram.values()[i].toString());
        }
        diagramComboBox = new JComboBox<>(new DefaultComboBoxModel<>(names));
        diagramComboBox.setSelectedItem(description.getString(configuration.getDiagram().toString()));
        verticalShiftSpinner = new JSpinner(new SpinnerNumberModel(configuration.getVerticalShift(), -30, 30, 5));
        verticalShiftLabel = new JLabel(description.getString(PersonBoxSetup.VERTICAL_SHIFT));
        verticalShiftLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);

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

        directLabel = new JLabel(description.getString(PersonBoxSetup.DIRECT), JLabel.CENTER);
        sideLabel = new JLabel(description.getString(PersonBoxSetup.SIDE), JLabel.CENTER);
        heightLabel = new JLabel(description.getString(PersonBoxSetup.HEIGHT));
        widthLabel = new JLabel(description.getString(PersonBoxSetup.WIDTH));
        fontSizeLabel = new JLabel(description.getString(PersonBoxSetup.FONT_SIZE));

        adultWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageWidth(), 100, 300, 10));
        adultHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageHeight(), 100, 300, 10));
        adultFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultFontSize(), 10, 22, 1));
        siblingWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageWidth(), 100, 300, 10));
        siblingHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageHeight(), 100, 300, 10));
        siblingFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingFontSize(), 10, 22, 1));
    }

    private void initActions() {
        ageCheckBox.addActionListener(this::ageCheckBoxActionPerformed);
        occupationCheckBox.addActionListener(this::occupationCheckBoxActionPerformed);
        placesCheckBox.addActionListener(this::placesCheckBoxActionPerformed);
        shortenPlacesCheckBox.addActionListener(this::shortenPlacesCheckBoxActionPerformed);
        templeCheckBox.addActionListener(this::templeCheckBoxActionPerformed);
        diagramComboBox.addActionListener(this::diagramComboBoxActionPerformed);

        adultWidthSpinner.addChangeListener(this::adultWidthSpinnerStateChanged);
        adultHeightSpinner.addChangeListener(this::adultHeightSpinnerStateChanged);
        adultFontSizeSpinner.addChangeListener(this::fontSizeSpinnerStateChanged);
        siblingWidthSpinner.addChangeListener(this::siblingsWidthSpinnerStateChanged);
        siblingHeightSpinner.addChangeListener(this::siblingsHeightSpinnerStateChanged);
        siblingFontSizeSpinner.addChangeListener(this::siblingFontSizeSpinnerStateChanged);
        verticalShiftSpinner.addChangeListener(this::adultVerticalShiftSpinnerStateChanged);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Colors.SW_BACKGROUND);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 5);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 2;
        panel.add(diagramLabel, constraints);
        constraints.gridwidth = 1;
        panel.add(diagramComboBox, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(verticalShiftLabel, constraints);
        constraints.gridwidth = 1;
        panel.add(verticalShiftSpinner, constraints);

        constraints.gridy = 2;
        panel.add(occupationCheckBox, constraints);
        panel.add(ageCheckBox, constraints);
        panel.add(templeCheckBox, constraints);

        constraints.gridy = 3;
        panel.add(placesCheckBox, constraints);
        constraints.gridwidth = 2;
        panel.add(shortenPlacesCheckBox, constraints);

        constraints.gridwidth = 1;
        constraints.insets.top = 10;
        constraints.gridy = 4;
        panel.add(new JLabel(""), constraints);
        panel.add(directLabel, constraints);
        panel.add(sideLabel, constraints);

        constraints.gridy = 5;
        constraints.insets.top = 5;
        panel.add(widthLabel, constraints);
        panel.add(adultWidthSpinner, constraints);
        panel.add(siblingWidthSpinner, constraints);

        constraints.gridy = 6;
        panel.add(heightLabel, constraints);
        panel.add(adultHeightSpinner, constraints);
        panel.add(siblingHeightSpinner, constraints);

        constraints.gridy = 7;
        panel.add(fontSizeLabel, constraints);
        panel.add(adultFontSizeSpinner, constraints);
        panel.add(siblingFontSizeSpinner, constraints);

        this.add(panel);
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
