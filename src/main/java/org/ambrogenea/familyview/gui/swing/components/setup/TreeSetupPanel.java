package org.ambrogenea.familyview.gui.swing.components.setup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.TreeSetup;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeSetupPanel extends JPanel {

    private final Window window;

    private JCheckBox verticalViewCheckBox;
    private JCheckBox heraldryCheckBox;
    private JCheckBox marriageCheckBox;
    private JCheckBox residenceCheckBox;
    private JCheckBox spousesCheckbox;
    private JCheckBox childrenCheckbox;
    private JCheckBox showSiblingsCheckbox;
    private JCheckBox showSiblingSpouse;
    private JLabel generationsLabel;
    private JSpinner generationSpinner;
    private JComboBox<String> shapeLabelBox;

    public TreeSetupPanel(Window window) {
        this.window = window;
        this.setPreferredSize(Dimensions.TREE_SETUP_DIMENSION);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.setBackground(Colors.SW_BACKGROUND);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/treeSetup", getConfiguration().getLocale());
        this.setBorder(new TitledBorder(description.getString(TreeSetup.TITLE)));

        verticalViewCheckBox = new JCheckBox(description.getString(TreeSetup.VERTICAL));
        verticalViewCheckBox.setSelected(getConfiguration().isShowCouplesVertical());
        verticalViewCheckBox.setOpaque(false);

        heraldryCheckBox = new JCheckBox(description.getString(TreeSetup.HERALDRY));
        heraldryCheckBox.setSelected(getConfiguration().isShowHeraldry());
        heraldryCheckBox.setOpaque(false);

        marriageCheckBox = new JCheckBox(description.getString(TreeSetup.MARRIAGE));
        marriageCheckBox.setSelected(getConfiguration().isShowMarriage());
        marriageCheckBox.setOpaque(false);

        residenceCheckBox = new JCheckBox(description.getString(TreeSetup.RESIDENCE));
        residenceCheckBox.setSelected(getConfiguration().isShowResidence());
        residenceCheckBox.setOpaque(false);

        spousesCheckbox = new JCheckBox(description.getString(TreeSetup.SPOUSES));
        spousesCheckbox.setSelected(getConfiguration().isShowSpouses());
        spousesCheckbox.setOpaque(false);

        childrenCheckbox = new JCheckBox(description.getString(TreeSetup.CHILDREN));
        childrenCheckbox.setSelected(getConfiguration().isShowChildren());
        childrenCheckbox.setOpaque(false);

        showSiblingsCheckbox = new JCheckBox(description.getString(TreeSetup.SIBLINGS));
        showSiblingsCheckbox.setSelected(getConfiguration().isShowSiblings());
        showSiblingsCheckbox.setOpaque(false);

        showSiblingSpouse = new JCheckBox(description.getString(TreeSetup.SIBLINGS_SPOUSE));
        showSiblingSpouse.setSelected(getConfiguration().isShowSiblingSpouses());
        showSiblingSpouse.setOpaque(false);

        generationsLabel = new JLabel(description.getString(TreeSetup.GENERATIONS));
        generationsLabel.setOpaque(false);

        generationSpinner = new JSpinner(new SpinnerNumberModel(getConfiguration().getGenerationCount(), 1, 20, 1));

        shapeLabelBox = new JComboBox<>(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        shapeLabelBox.setSelectedItem(getConfiguration().getLabelShape());
    }

    private void initActions() {
        verticalViewCheckBox.addActionListener(this::verticalViewCheckBoxActionPerformed);
        heraldryCheckBox.addActionListener(this::heraldryCheckBoxActionPerformed);
        marriageCheckBox.addActionListener(this::marriageCheckBoxActionPerformed);
        residenceCheckBox.addActionListener(this::residenceCheckBoxActionPerformed);
        spousesCheckbox.addActionListener(this::spousesCheckboxActionPerformed);
        childrenCheckbox.addActionListener(this::childrenCheckboxActionPerformed);
        showSiblingsCheckbox.addActionListener(this::showSiblingsCheckboxActionPerformed);
        showSiblingSpouse.addActionListener(this::showSiblingSpouseActionPerformed);
        generationSpinner.addChangeListener(this::generationSpinnerStateChanged);
        shapeLabelBox.addActionListener(this::shapeLabelBoxActionPerformed);
    }

    private void addComponents() {
        this.add(verticalViewCheckBox);
        this.add(heraldryCheckBox);
        this.add(marriageCheckBox);
        this.add(residenceCheckBox);
        this.add(spousesCheckbox);
        this.add(childrenCheckbox);
        this.add(showSiblingsCheckbox);
        this.add(showSiblingSpouse);

        JPanel generationPanel = new JPanel(new BorderLayout());
        generationPanel.add(generationSpinner, BorderLayout.EAST);
        generationPanel.add(generationsLabel, BorderLayout.CENTER);
        generationPanel.setBackground(Colors.SW_BACKGROUND);

        this.add(generationPanel);
        this.add(shapeLabelBox);
    }

    private void verticalViewCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowCouplesVertical(verticalViewCheckBox.isSelected());
        window.generateTree();
    }

    private void heraldryCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowHeraldry(heraldryCheckBox.isSelected());
        window.generateTree();
    }

    private void marriageCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowMarriage(marriageCheckBox.isSelected());
        window.generateTree();
    }

    private void residenceCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowResidence(residenceCheckBox.isSelected());
        window.generateTree();
    }

    private void spousesCheckboxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowSpouses(spousesCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            childrenCheckbox.setSelected(false);
            getConfiguration().setShowChildren(false);
        }
        window.generateTree();
    }

    private void childrenCheckboxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowChildren(childrenCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            spousesCheckbox.setSelected(true);
            getConfiguration().setShowSpouses(true);
        }
        window.generateTree();
    }

    private void showSiblingsCheckboxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowSiblings(showSiblingsCheckbox.isSelected());
        window.generateTree();
    }

    private void showSiblingSpouseActionPerformed(ActionEvent evt) {
        getConfiguration().setShowSiblingSpouses(showSiblingSpouse.isSelected());
        window.generateTree();
    }

    private void generationSpinnerStateChanged(ChangeEvent evt) {
        getConfiguration().setGenerationCount((int) generationSpinner.getValue());
        window.generateTree();
    }

    private void shapeLabelBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setLabelShape(LabelShape.valueOf(shapeLabelBox.getSelectedItem().toString()));
        window.generateTree();
    }

    private ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
