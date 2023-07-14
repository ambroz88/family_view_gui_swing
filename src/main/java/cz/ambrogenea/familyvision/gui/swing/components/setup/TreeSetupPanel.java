package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.TreeSetup;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.gui.swing.enums.CoupleType;
import cz.ambrogenea.familyvision.gui.swing.enums.LineageType;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeSetupPanel extends JPanel {

    private final Window window;
    private final TreeShapeConfiguration configuration;

    private JLabel lineageTypeLabel;
    private JComboBox<String> lineageTypeComboBox;
    private JLabel coupleTypeLabel;
    private JComboBox<String> coupleTypeComboBox;
    private JLabel ancestorGenerationLabel;
    private JSpinner ancestorGenerationSpinner;
    private JLabel descendentGenerationLabel;
    private JSpinner descendentGenerationSpinner;
    private JCheckBox showSiblingsCheckbox;
    private JCheckBox showSiblingSpouseCheckbox;
    private JCheckBox showSpousesCheckbox;
    private JCheckBox heraldryCheckBox;
    private JCheckBox residenceCheckBox;

    public TreeSetupPanel(Window window) {
        this.window = window;
        this.configuration = Config.treeShape();
        this.setPreferredSize(Dimensions.TREE_SETUP_DIMENSION);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setBackground(Colors.SW_BACKGROUND);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/treeSetup", Config.visual().getLocale());
        this.setBorder(new TitledBorder(description.getString(TreeSetup.TITLE)));

        lineageTypeLabel = new JLabel(description.getString(TreeSetup.LINEAGE_TYPE));
        lineageTypeLabel.setOpaque(false);
        lineageTypeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(LineageType.getStrings()));
        lineageTypeComboBox.setSelectedItem(configuration.getLineageType());
        coupleTypeLabel = new JLabel(description.getString(TreeSetup.COUPLE_TYPE));
        coupleTypeLabel.setOpaque(false);
        coupleTypeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(CoupleType.getStrings()));
        coupleTypeComboBox.setSelectedItem(configuration.getCoupleType());

        ancestorGenerationLabel = new JLabel(description.getString(TreeSetup.ANCESTORS));
        ancestorGenerationLabel.setOpaque(false);
        ancestorGenerationSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAncestorGenerations(), 0, 20, 1));
        descendentGenerationLabel = new JLabel(description.getString(TreeSetup.DESCENDENTS));
        descendentGenerationLabel.setOpaque(false);
        descendentGenerationSpinner = new JSpinner(new SpinnerNumberModel(configuration.getDescendentGenerations(), 0, 20, 1));

        showSiblingsCheckbox = new JCheckBox(description.getString(TreeSetup.SIBLINGS));
        showSiblingsCheckbox.setSelected(configuration.isShowSiblings());
        showSiblingsCheckbox.setOpaque(false);
        showSiblingSpouseCheckbox = new JCheckBox(description.getString(TreeSetup.SIBLINGS_SPOUSE));
        showSiblingSpouseCheckbox.setSelected(configuration.isShowSiblingSpouses());
        showSiblingSpouseCheckbox.setOpaque(false);
        showSpousesCheckbox = new JCheckBox(description.getString(TreeSetup.SPOUSES));
        showSpousesCheckbox.setSelected(configuration.isShowSpouses());
        showSpousesCheckbox.setOpaque(false);
        heraldryCheckBox = new JCheckBox(description.getString(TreeSetup.HERALDRY));
        heraldryCheckBox.setSelected(configuration.isShowHeraldry());
        heraldryCheckBox.setOpaque(false);
        residenceCheckBox = new JCheckBox(description.getString(TreeSetup.RESIDENCE));
        residenceCheckBox.setSelected(configuration.isShowResidence());
        residenceCheckBox.setOpaque(false);
    }

    private void initActions() {
        lineageTypeComboBox.addActionListener(this::lineageTypeComboBoxActionPerformed);
        coupleTypeComboBox.addActionListener(this::coupleTypeComboBoxActionPerformed);
        ancestorGenerationSpinner.addChangeListener(this::ancestorGenerationSpinnerStateChanged);
        descendentGenerationSpinner.addChangeListener(this::descendentGenerationSpinnerStateChanged);
        showSiblingsCheckbox.addActionListener(this::showSiblingsCheckboxActionPerformed);
        showSiblingSpouseCheckbox.addActionListener(this::showSiblingSpouseActionPerformed);
        showSpousesCheckbox.addActionListener(this::showSpousesCheckboxActionPerformed);
        heraldryCheckBox.addActionListener(this::heraldryCheckBoxActionPerformed);
        residenceCheckBox.addActionListener(this::residenceCheckBoxActionPerformed);
    }

    private void addComponents() {
        JPanel lineagePanel = new JPanel(new BorderLayout());
        lineagePanel.add(lineageTypeLabel, BorderLayout.WEST);
        lineagePanel.add(lineageTypeComboBox, BorderLayout.EAST);
        lineagePanel.setBackground(Colors.SW_BACKGROUND);

        JPanel couplePanel = new JPanel(new BorderLayout());
        couplePanel.add(coupleTypeLabel, BorderLayout.WEST);
        couplePanel.add(coupleTypeComboBox, BorderLayout.EAST);
        couplePanel.setBackground(Colors.SW_BACKGROUND);

        JPanel ancestorPanel = new JPanel(new BorderLayout());
        ancestorPanel.add(ancestorGenerationSpinner, BorderLayout.EAST);
        ancestorPanel.add(ancestorGenerationLabel, BorderLayout.CENTER);
        ancestorPanel.setBackground(Colors.SW_BACKGROUND);

        JPanel descendentPanel = new JPanel(new BorderLayout());
        descendentPanel.add(descendentGenerationSpinner, BorderLayout.EAST);
        descendentPanel.add(descendentGenerationLabel, BorderLayout.CENTER);
        descendentPanel.setBackground(Colors.SW_BACKGROUND);

        this.add(lineagePanel);
        this.add(couplePanel);
        this.add(ancestorPanel);
        this.add(descendentPanel);

        this.add(showSiblingsCheckbox);
        this.add(showSiblingSpouseCheckbox);
        this.add(showSpousesCheckbox);
        this.add(heraldryCheckBox);
        this.add(residenceCheckBox);
    }

    private void lineageTypeComboBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setLineageType(LineageType.valueOf(lineageTypeComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.generateTree();
    }

    private void coupleTypeComboBoxActionPerformed(ActionEvent evt) {
        configuration.setCoupleType(CoupleType.valueOf(coupleTypeComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.updateTree();
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

    private void heraldryCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowHeraldry(heraldryCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void residenceCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowResidence(residenceCheckBox.isSelected());
        window.updateConfiguration(configuration);
        window.updateTree();
    }

}
