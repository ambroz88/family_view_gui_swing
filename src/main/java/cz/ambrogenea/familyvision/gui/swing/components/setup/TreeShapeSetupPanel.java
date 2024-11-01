package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.TreeSetup;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.gui.swing.enums.LineageType;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeShapeSetupPanel extends JPanel {

    private final Window window;
    private final TreeShapeConfiguration configuration;

    private JLabel lineageTypeLabel;
    private JComboBox<String> lineageTypeComboBox;

    private JLabel ancestorGenerationLabel;
    private JSpinner ancestorGenerationSpinner;
    private JLabel descendentGenerationLabel;
    private JSpinner descendentGenerationSpinner;
    private JLabel showSiblingsLabel;
    private JCheckBox showSiblingsCheckbox;
    private JLabel showSiblingSpouseLabel;
    private JCheckBox showSiblingSpouseCheckbox;
    private JLabel showSpousesLabel;
    private JCheckBox showSpousesCheckbox;

    public TreeShapeSetupPanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.window = window;
        this.configuration = Config.treeShape();
        this.setPreferredSize(Dimensions.SETUP_PANEL_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/treeSetup", configuration.getLocale());

        lineageTypeLabel = new JLabel(description.getString(TreeSetup.LINEAGE_TYPE));
        lineageTypeLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        lineageTypeLabel.setOpaque(false);
        lineageTypeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(LineageType.getStrings()));
        lineageTypeComboBox.setSelectedItem(configuration.getLineageType().toString());

        ancestorGenerationLabel = new JLabel(description.getString(TreeSetup.ANCESTORS));
        ancestorGenerationLabel.setOpaque(false);
        ancestorGenerationLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        ancestorGenerationSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAncestorGenerations(), 0, 20, 1));
        descendentGenerationLabel = new JLabel(description.getString(TreeSetup.DESCENDENTS));
        descendentGenerationLabel.setOpaque(false);
        descendentGenerationLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        descendentGenerationSpinner = new JSpinner(new SpinnerNumberModel(configuration.getDescendentGenerations(), 0, 20, 1));

        showSiblingsLabel = new JLabel(description.getString(TreeSetup.SIBLINGS));
        showSiblingsLabel.setOpaque(false);
        showSiblingsLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        showSiblingsCheckbox = new JCheckBox();
        showSiblingsCheckbox.setOpaque(false);

        showSiblingSpouseLabel = new JLabel(description.getString(TreeSetup.SIBLINGS_SPOUSE));
        showSiblingSpouseLabel.setOpaque(false);
        showSiblingSpouseLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        showSiblingSpouseCheckbox = new JCheckBox();
        showSiblingSpouseCheckbox.setSelected(configuration.isShowSiblingSpouses());
        showSiblingSpouseCheckbox.setOpaque(false);

        showSpousesLabel = new JLabel(description.getString(TreeSetup.SPOUSES));
        showSpousesLabel.setOpaque(false);
        showSpousesLabel.setPreferredSize(Dimensions.LABEL_DIMENSION);
        showSpousesCheckbox = new JCheckBox();
        showSpousesCheckbox.setSelected(configuration.isShowSpouses());
        showSpousesCheckbox.setOpaque(false);
    }

    private void initActions() {
        lineageTypeComboBox.addActionListener(this::lineageTypeComboBoxActionPerformed);
        ancestorGenerationSpinner.addChangeListener(this::ancestorGenerationSpinnerStateChanged);
        descendentGenerationSpinner.addChangeListener(this::descendentGenerationSpinnerStateChanged);
        showSiblingsCheckbox.addActionListener(this::showSiblingsCheckboxActionPerformed);
        showSiblingSpouseCheckbox.addActionListener(this::showSiblingSpouseActionPerformed);
        showSpousesCheckbox.addActionListener(this::showSpousesCheckboxActionPerformed);
    }

    private void addComponents() {
        JPanel generationPanel = new JPanel(new GridBagLayout());
        generationPanel.setBackground(Colors.SW_BACKGROUND);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,0,0,5);
        constraints.anchor = GridBagConstraints.WEST;
        generationPanel.add(lineageTypeLabel, constraints);
        constraints.gridwidth = 2;
        generationPanel.add(lineageTypeComboBox, constraints);

        constraints.gridy = 1;
        generationPanel.add(ancestorGenerationLabel, constraints);
        constraints.gridwidth = 1;
        generationPanel.add(ancestorGenerationSpinner, constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 2;
        generationPanel.add(descendentGenerationLabel, constraints);
        constraints.gridwidth = 1;
        generationPanel.add(descendentGenerationSpinner, constraints);

        constraints.insets.top = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        generationPanel.add(showSiblingsLabel, constraints);
        constraints.gridwidth = 1;
        generationPanel.add(showSiblingsCheckbox, constraints);

        constraints.gridy = 4;
        constraints.gridwidth = 2;
        generationPanel.add(showSiblingSpouseLabel, constraints);
        constraints.gridwidth = 1;
        generationPanel.add(showSiblingSpouseCheckbox, constraints);

        constraints.gridy = 5;
        constraints.gridwidth = 2;
        generationPanel.add(showSpousesLabel, constraints);
        constraints.gridwidth = 1;
        generationPanel.add(showSpousesCheckbox, constraints);

        this.add(generationPanel);
    }

    private void lineageTypeComboBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setLineageType(LineageType.valueOf(lineageTypeComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void ancestorGenerationSpinnerStateChanged(ChangeEvent evt) {
        configuration.setAncestorGenerations((int) ancestorGenerationSpinner.getValue());
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void descendentGenerationSpinnerStateChanged(ChangeEvent evt) {
        int descendentGenerations = (int) descendentGenerationSpinner.getValue();
        configuration.setDescendentGenerations(descendentGenerations);
        if (descendentGenerations > 0 && !showSpousesCheckbox.isSelected()) {
            showSpousesCheckbox.setSelected(true);
            configuration.setShowSpouses(true);
        }
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void showSiblingsCheckboxActionPerformed(ActionEvent evt) {
        boolean selected = showSiblingsCheckbox.isSelected();
        configuration.setShowSiblings(selected);
        if (!selected && showSiblingSpouseCheckbox.isSelected()) {
            showSiblingSpouseCheckbox.setSelected(false);
            configuration.setShowSiblingSpouses(false);
        }
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void showSiblingSpouseActionPerformed(ActionEvent evt) {
        boolean selected = showSiblingSpouseCheckbox.isSelected();
        configuration.setShowSiblingSpouses(selected);
        if (selected && !showSiblingsCheckbox.isSelected()) {
            showSiblingsCheckbox.setSelected(true);
            configuration.setShowSiblings(true);
        }
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void showSpousesCheckboxActionPerformed(ActionEvent evt) {
        boolean showSpouses = showSpousesCheckbox.isSelected();
        configuration.setShowSpouses(showSpouses);
        if (!showSpouses && (int) descendentGenerationSpinner.getValue() > 0) {
            descendentGenerationSpinner.setValue(0);
            configuration.setDescendentGenerations(0);
        }
        window.updateConfiguration(configuration);
        window.generateTree();
    }

}
