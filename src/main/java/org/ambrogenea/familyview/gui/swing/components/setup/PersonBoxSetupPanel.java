package org.ambrogenea.familyview.gui.swing.components.setup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.description.PersonBoxSetup;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonBoxSetupPanel extends JPanel {

    private final Window window;

    private JSpinner adultWidthSpinner;
    private JSpinner adultHeightSpinner;
    private JSpinner adultFontSizeSpinner;
    private JSpinner adultVerticalShiftSpinner;

    private JSpinner siblingWidthSpinner;
    private JSpinner siblingHeightSpinner;
    private JSpinner siblingFontSizeSpinner;

    private JComboBox<String> adultDiagramBox;

    private JLabel directLabel;
    private JLabel sideLabel;
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel fontSizeLabel;
    private JLabel verticalShiftLabel;
    private JLabel diagramLabel;

    public PersonBoxSetupPanel(Window window) {
        this.window = window;
//        this.setPreferredSize(Dimensions.PERSON_BOX_SETUP_DIMENSION);
        this.setLayout(new GridLayout(6, 3));
        this.setBackground(Colors.SW_BACKGROUND);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", getConfiguration().getLocale());
        directLabel = new JLabel(description.getString(PersonBoxSetup.DIRECT), JLabel.CENTER);
        sideLabel = new JLabel(description.getString(PersonBoxSetup.SIDE), JLabel.CENTER);
        heightLabel = new JLabel(description.getString(PersonBoxSetup.HEIGHT), JLabel.CENTER);
        widthLabel = new JLabel(description.getString(PersonBoxSetup.WIDTH), JLabel.CENTER);
        fontSizeLabel = new JLabel(description.getString(PersonBoxSetup.FONT_SIZE), JLabel.CENTER);
        verticalShiftLabel = new JLabel(description.getString(PersonBoxSetup.VERTICAL_SHIFT), JLabel.CENTER);
        diagramLabel = new JLabel(description.getString(PersonBoxSetup.DIAGRAM), JLabel.CENTER);

        adultWidthSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultImageWidth(), 100, 300, 10));
        adultHeightSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultImageHeight(), 100, 300, 10));
        siblingWidthSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingImageWidth(), 100, 300, 10));
        siblingHeightSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingImageHeight(), 100, 300, 10));

        adultFontSizeSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultFontSize(), 10, 22, 1));
        adultVerticalShiftSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultVerticalShift(), -30, 30, 5));

        siblingFontSizeSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingFontSize(), 10, 22, 1));

        String[] names = new String[Diagrams.values().length];
        for (int i = 0; i < names.length; i++) {
            names[i] = description.getString(Diagrams.values()[i].toString());
        }
        adultDiagramBox = new JComboBox<>(new DefaultComboBoxModel<>(names));
        adultDiagramBox.setSelectedItem(description.getString(getConfiguration().getAdultDiagram().toString()));
    }

    private void initActions() {
        adultWidthSpinner.addChangeListener(this::adultWidthSpinnerStateChanged);
        adultHeightSpinner.addChangeListener(this::adultHeightSpinnerStateChanged);
        siblingWidthSpinner.addChangeListener(this::siblingsWidthSpinnerStateChanged);
        siblingHeightSpinner.addChangeListener(this::siblingsHeightSpinnerStateChanged);

        adultFontSizeSpinner.addChangeListener(this::fontSizeSpinnerStateChanged);
        adultVerticalShiftSpinner.addChangeListener(this::adultVerticalShiftSpinnerStateChanged);
        siblingFontSizeSpinner.addChangeListener(this::siblingFontSizeSpinnerStateChanged);

        adultDiagramBox.addActionListener(this::adultDiagramComboBoxActionPerformed);
    }

    private void addComponents() {
        this.add(directLabel);
        this.add(new JLabel(""));
        this.add(sideLabel);

        this.add(adultWidthSpinner);
        this.add(widthLabel);
        this.add(siblingWidthSpinner);

        this.add(adultHeightSpinner);
        this.add(heightLabel);
        this.add(siblingHeightSpinner);

        this.add(adultFontSizeSpinner);
        this.add(fontSizeLabel);
        this.add(siblingFontSizeSpinner);

        this.add(adultVerticalShiftSpinner);
        this.add(verticalShiftLabel);
        this.add(new JLabel(""));

        this.add(adultDiagramBox);
        this.add(diagramLabel);
    }

    private void adultWidthSpinnerStateChanged(ChangeEvent evt) {
        int adultWidth = Integer.parseInt(adultWidthSpinner.getValue().toString());
        getConfiguration().setAdultImageWidth(adultWidth);
        window.generateTree();
    }

    private void adultHeightSpinnerStateChanged(ChangeEvent evt) {
        int adultHeight = Integer.parseInt(adultHeightSpinner.getValue().toString());
        getConfiguration().setAdultImageHeight(adultHeight);
        window.generateTree();
    }

    private void siblingsWidthSpinnerStateChanged(ChangeEvent evt) {
        int siblingsWidth = Integer.parseInt(siblingWidthSpinner.getValue().toString());
        getConfiguration().setSiblingImageWidth(siblingsWidth);
        window.generateTree();
    }

    private void siblingsHeightSpinnerStateChanged(ChangeEvent evt) {
        int siblingsHeight = Integer.parseInt(siblingHeightSpinner.getValue().toString());
        getConfiguration().setSiblingImageHeight(siblingsHeight);
        window.generateTree();
    }

    private void fontSizeSpinnerStateChanged(ChangeEvent evt) {
        int adultFontSize = Integer.parseInt(adultFontSizeSpinner.getValue().toString());
        getConfiguration().setAdultFontSize(adultFontSize);
        window.generateTree();
    }

    private void adultVerticalShiftSpinnerStateChanged(ChangeEvent evt) {
        int adultVerticalShift = Integer.parseInt(adultVerticalShiftSpinner.getValue().toString());
        getConfiguration().setAdultVerticalShift(adultVerticalShift);
        window.generateTree();
    }

    private void siblingFontSizeSpinnerStateChanged(ChangeEvent evt) {
        int siblingsFontSize = Integer.parseInt(siblingFontSizeSpinner.getValue().toString());
        getConfiguration().setSiblingFontSize(siblingsFontSize);
        window.generateTree();
    }

    private void adultDiagramComboBoxActionPerformed(ActionEvent evt) {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", getConfiguration().getLocale());
        Iterator<String> it = description.getKeys().asIterator();
        String selectedDiagramName = Objects.requireNonNull(adultDiagramBox.getSelectedItem()).toString();
        String diagramType;
        while (it.hasNext()) {
            diagramType = it.next();
            if (description.getString(diagramType).equals(selectedDiagramName)) {
                getConfiguration().setAdultDiagram(Diagrams.fromString(diagramType));
                window.generateTree();
                break;
            }
        }
    }

    private ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
