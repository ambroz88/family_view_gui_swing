package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.PersonSetup;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;
import cz.ambrogenea.familyvision.service.util.Config;

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
    private final VisualConfigurationService configuration;

    private JCheckBox ageCheckBox;
    private JCheckBox occupationCheckBox;
    private JCheckBox placesCheckBox;
    private JCheckBox shortenPlacesCheckBox;
    private JCheckBox templeCheckBox;
    private JCheckBox titleCheckBox;

    private JLabel diagramLabel;
    private JComboBox<String> diagramComboBox;
    private JLabel backgroundLabel;
    private JComboBox<String> backgroundComboBox;
    private JLabel marriageShapeLabel;
    private JComboBox<String> marriageShapeComboBox;

    private PersonBoxSetupPanel personBoxSetupPanel;

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

        diagramLabel = new JLabel(description.getString(PersonSetup.DIAGRAM), JLabel.LEFT);
        String[] names = new String[Diagram.values().length];
        for (int i = 0; i < names.length; i++) {
            names[i] = description.getString(Diagram.values()[i].toString());
        }
        diagramComboBox = new JComboBox<>(new DefaultComboBoxModel<>(names));
        diagramComboBox.setSelectedItem(description.getString(configuration.getDiagram().toString()));
        backgroundLabel = new JLabel(description.getString(PersonSetup.BACKGROUND), JLabel.LEFT);
        backgroundComboBox = new JComboBox<>(new DefaultComboBoxModel<>(Background.getStrings()));
        backgroundComboBox.setSelectedItem(configuration.getBackground());
        marriageShapeLabel = new JLabel(description.getString(PersonSetup.MARRIAGE_SHAPE), JLabel.LEFT);
        marriageShapeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        marriageShapeComboBox.setSelectedItem(configuration.getMarriageLabelShape());

        personBoxSetupPanel = new PersonBoxSetupPanel(window);
    }

    private void initActions() {
        ageCheckBox.addActionListener(this::ageCheckBoxActionPerformed);
        occupationCheckBox.addActionListener(this::occupationCheckBoxActionPerformed);
        placesCheckBox.addActionListener(this::placesCheckBoxActionPerformed);
        shortenPlacesCheckBox.addActionListener(this::shortenPlacesCheckBoxActionPerformed);
        templeCheckBox.addActionListener(this::templeCheckBoxActionPerformed);
        titleCheckBox.addActionListener(this::titleCheckBoxActionPerformed);
        diagramComboBox.addActionListener(this::diagramComboBoxActionPerformed);
        backgroundComboBox.addActionListener(this::backgroundComboBoxActionPerformed);
        marriageShapeComboBox.addActionListener(this::shapeLabelBoxActionPerformed);
    }

    private void addComponents() {
        JPanel checkboxes = new JPanel(new GridLayout(2,3, 0, 5));
        checkboxes.add(ageCheckBox);
        checkboxes.add(occupationCheckBox);
        checkboxes.add(titleCheckBox);
        checkboxes.add(placesCheckBox);
        checkboxes.add(shortenPlacesCheckBox);
        checkboxes.add(templeCheckBox);
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

        JPanel comboBoxes = new JPanel(new GridLayout(1,3, 10, 0));
        comboBoxes.add(diagramPanel);
        comboBoxes.add(backgroundPanel);
        comboBoxes.add(marriagePanel);
        comboBoxes.setBackground(Colors.SW_BACKGROUND);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, 5));

        JPanel top = new JPanel(new BorderLayout(0, 5));
        top.add(comboBoxes, BorderLayout.NORTH);
        top.add(checkboxes, BorderLayout.SOUTH);
        top.setBackground(Colors.SW_BACKGROUND);

        this.add(top, BorderLayout.NORTH);
        this.add(sep, BorderLayout.CENTER);
        this.add(personBoxSetupPanel, BorderLayout.SOUTH);
    }

    private void ageCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowAge(ageCheckBox.isSelected());
        window.generateTree();
    }

    private void occupationCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowOccupation(occupationCheckBox.isSelected());
        window.generateTree();
    }

    private void placesCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowPlaces(placesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            shortenPlacesCheckBox.setSelected(false);
            configuration.setShortenPlaces(false);
        }
        window.generateTree();
    }

    private void shortenPlacesCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShortenPlaces(shortenPlacesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            placesCheckBox.setSelected(true);
            configuration.setShowPlaces(true);
        }
        window.generateTree();
    }

    private void templeCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowOrdinances(templeCheckBox.isSelected());
        window.generateTree();
    }

    private void titleCheckBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setShowTitle(titleCheckBox.isSelected());
        window.generateTree();
    }

    private void shapeLabelBoxActionPerformed(ActionEvent evt) {
        configuration.setMarriageLabelShape(LabelShape.valueOf(marriageShapeComboBox.getSelectedItem().toString()));
        window.generateTree();
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
                window.generateTree();
                break;
            }
        }
    }

    private void backgroundComboBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setBackground(Background.valueOf(backgroundComboBox.getSelectedItem().toString()));
        window.generateTree();
    }

}
