package org.ambrogenea.familyview.gui.swing.components.setup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
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
    private JSpinner adultBottomPaddingSpinner;
    private JSpinner adultTopPaddingSpinner;

    private JSpinner siblingWidthSpinner;
    private JSpinner siblingHeightSpinner;
    private JSpinner siblingFontSizeSpinner;
    private JSpinner siblingBottomPaddingSpinner;
    private JSpinner siblingTopPaddingSpinner;

    private JComboBox adultDiagramBox;
    private JComboBox siblingDiagramBox;

    private JLabel directLabel;
    private JLabel sideLabel;
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel fontSizeLabel;
    private JLabel topPaddingLabel;
    private JLabel bottomPaddingLabel;
    private JLabel diagramLabel;

    public PersonBoxSetupPanel(Window window) {
        this.window = window;
//        this.setPreferredSize(Dimensions.PERSON_BOX_SETUP_DIMENSION);
        this.setLayout(new GridLayout(7, 3));
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
        topPaddingLabel = new JLabel(description.getString(PersonBoxSetup.TOP_PADDING), JLabel.CENTER);
        bottomPaddingLabel = new JLabel(description.getString(PersonBoxSetup.BOTTOM_PADDING), JLabel.CENTER);
        diagramLabel = new JLabel(description.getString(PersonBoxSetup.DIAGRAM), JLabel.CENTER);

        adultWidthSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultImageWidth(), 120, 240, 10));
        adultHeightSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultImageHeight(), 120, 240, 10));
        siblingWidthSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingImageWidth(), 120, 240, 10));
        siblingHeightSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingImageHeight(), 120, 240, 10));

        adultFontSizeSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultFontSize(), 10, 22, 1));
        adultTopPaddingSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultTopOffset(), 0, 40, 1));
        adultBottomPaddingSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getAdultBottomOffset(), 0, 40, 1));

        siblingFontSizeSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingFontSize(), 10, 22, 1));
        siblingTopPaddingSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingTopOffset(), 0, 40, 1));
        siblingBottomPaddingSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getSiblingBottomOffset(), 0, 40, 1));

        String[] names = new String[Diagrams.values().length];
        for (int i = 0; i < names.length; i++) {
            names[i] = description.getString(Diagrams.values()[i].toString());
        }
        adultDiagramBox = new JComboBox(new DefaultComboBoxModel<>(names));
        adultDiagramBox.setSelectedItem(description.getString(getConfiguration().getAdultDiagram().toString()));

        siblingDiagramBox = new JComboBox(new DefaultComboBoxModel<>(names));
        siblingDiagramBox.setSelectedItem(description.getString(getConfiguration().getSiblingDiagram().toString()));

    }

    private void initActions() {
        adultWidthSpinner.addChangeListener(this::adultWidthSpinnerStateChanged);
        adultHeightSpinner.addChangeListener(this::adultHeightSpinnerStateChanged);
        siblingWidthSpinner.addChangeListener(this::siblingsWidthSpinnerStateChanged);
        siblingHeightSpinner.addChangeListener(this::siblingsHeightSpinnerStateChanged);

        adultFontSizeSpinner.addChangeListener(this::fontSizeSpinnerStateChanged);
        adultTopPaddingSpinner.addChangeListener(this::topOffsetSpinnerStateChanged);
        adultBottomPaddingSpinner.addChangeListener(this::bottomOffsetSpinnerStateChanged);
        siblingFontSizeSpinner.addChangeListener(this::siblingFontSizeSpinnerStateChanged);
        siblingTopPaddingSpinner.addChangeListener(this::siblingsTopOffsetSpinnerStateChanged);
        siblingBottomPaddingSpinner.addChangeListener(this::siblingsBottomOffsetSpinnerStateChanged);

        adultDiagramBox.addActionListener(this::adultDiagramComboBoxActionPerformed);
        siblingDiagramBox.addActionListener(this::siblingsDiagramComboBoxActionPerformed);
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

        this.add(adultTopPaddingSpinner);
        this.add(topPaddingLabel);
        this.add(siblingTopPaddingSpinner);

        this.add(adultBottomPaddingSpinner);
        this.add(bottomPaddingLabel);
        this.add(siblingBottomPaddingSpinner);

        this.add(adultDiagramBox);
        this.add(diagramLabel);
        this.add(siblingDiagramBox);

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

    private void topOffsetSpinnerStateChanged(ChangeEvent evt) {
        int adultTtopOffset = Integer.parseInt(adultTopPaddingSpinner.getValue().toString());
        getConfiguration().setAdultTopOffset(adultTtopOffset);
        window.generateTree();
    }

    private void bottomOffsetSpinnerStateChanged(ChangeEvent evt) {
        int adultBottomOffset = Integer.parseInt(adultBottomPaddingSpinner.getValue().toString());
        getConfiguration().setAdultBottomOffset(adultBottomOffset);
        window.generateTree();
    }

    private void siblingFontSizeSpinnerStateChanged(ChangeEvent evt) {
        int siblingsFontSize = Integer.parseInt(siblingFontSizeSpinner.getValue().toString());
        getConfiguration().setSiblingFontSize(siblingsFontSize);
        window.generateTree();
    }

    private void siblingsBottomOffsetSpinnerStateChanged(ChangeEvent evt) {
        int siblingsBottomOffset = Integer.parseInt(siblingBottomPaddingSpinner.getValue().toString());
        getConfiguration().setSiblingBottomOffset(siblingsBottomOffset);
        window.generateTree();
    }

    private void siblingsTopOffsetSpinnerStateChanged(ChangeEvent evt) {
        int siblingsTopOffset = Integer.parseInt(siblingTopPaddingSpinner.getValue().toString());
        getConfiguration().setSiblingTopOffset(siblingsTopOffset);
        window.generateTree();
    }

    private void adultDiagramComboBoxActionPerformed(ActionEvent evt) {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", getConfiguration().getLocale());
        Iterator it = description.getKeys().asIterator();
        String selectedDiagramName = adultDiagramBox.getSelectedItem().toString();
        String diagramType;
        while (it.hasNext()) {
            diagramType = it.next().toString();
            if (description.getString(diagramType).equals(selectedDiagramName)) {
                getConfiguration().setAdultDiagram(Diagrams.fromString(diagramType));
                window.generateTree();
                break;
            }
        }
    }

    private void siblingsDiagramComboBoxActionPerformed(ActionEvent evt) {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", getConfiguration().getLocale());
        Iterator it = description.getKeys().asIterator();
        String selectedDiagramName = siblingDiagramBox.getSelectedItem().toString();
        String diagramType;
        while (it.hasNext()) {
            diagramType = it.next().toString();
            if (description.getString(diagramType).equals(selectedDiagramName)) {
                getConfiguration().setSiblingDiagram(Diagrams.fromString(diagramType));
                window.generateTree();
                break;
            }
        }
    }

    private ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
