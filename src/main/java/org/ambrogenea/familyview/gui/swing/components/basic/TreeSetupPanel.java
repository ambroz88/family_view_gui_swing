package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.TreeSetup;
import org.ambrogenea.familyview.gui.swing.description.TreeType;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeSetupPanel extends JPanel {

    private final ConfigurationService configuration;

    private JCheckBox verticalViewCheckBox;
    private JCheckBox heraldryCheckBox;
    private JCheckBox marriageCheckBox;
    private JCheckBox residenceCheckBox;
    private JCheckBox spousesCheckbox;
    private JCheckBox childrenCheckbox;
    private JCheckBox showParentsCheckbox;
    private JCheckBox showSiblingsCheckbox;
    private JCheckBox showSiblingSpouse;
    private JLabel generationsLabel;
    private JSpinner generationSpinner;
    private JComboBox<String> shapeLabelBox;

    public TreeSetupPanel(ConfigurationService configuration) {
        this.configuration = configuration;
        this.setPreferredSize(new Dimension(Dimensions.TREE_SETUP_PANEL_WIDTH, Dimensions.TREE_SETUP_PANEL_HEIGHT));
        this.setLayout(new GridLayout(11, 1));

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/treeSetup", configuration.getLocale());
        this.setBorder(new TitledBorder(description.getString(TreeSetup.TITLE)));

        verticalViewCheckBox = new JCheckBox();
        verticalViewCheckBox.setSelected(configuration.isShowCouplesVertical());
        verticalViewCheckBox.setText(description.getString(TreeSetup.VERTICAL));

        heraldryCheckBox = new JCheckBox();
        heraldryCheckBox.setSelected(configuration.isShowHeraldry());
        heraldryCheckBox.setText(description.getString(TreeSetup.HERALDRY));

        marriageCheckBox = new JCheckBox();
        marriageCheckBox.setSelected(configuration.isShowMarriage());
        marriageCheckBox.setText(description.getString(TreeSetup.MARRIAGE));

        residenceCheckBox = new JCheckBox();
        residenceCheckBox.setSelected(configuration.isShowResidence());
        residenceCheckBox.setText(description.getString(TreeSetup.RESIDENCE));

        spousesCheckbox = new JCheckBox();
        spousesCheckbox.setSelected(configuration.isShowSpouses());
        spousesCheckbox.setText(description.getString(TreeSetup.SPOUSES));

        childrenCheckbox = new JCheckBox();
        childrenCheckbox.setSelected(configuration.isShowChildren());
        childrenCheckbox.setText(description.getString(TreeSetup.CHILDREN));

        showParentsCheckbox = new JCheckBox();
        showParentsCheckbox.setSelected(configuration.isShowParents());
        showParentsCheckbox.setText(description.getString(TreeSetup.PARENTS));
        showParentsCheckbox.setEnabled(false);

        showSiblingsCheckbox = new JCheckBox();
        showSiblingsCheckbox.setSelected(configuration.isShowSiblings());
        showSiblingsCheckbox.setText(description.getString(TreeSetup.SIBLINGS));

        showSiblingSpouse = new JCheckBox();
        showSiblingSpouse.setSelected(configuration.isShowSiblingSpouses());
        showSiblingSpouse.setText(description.getString(TreeSetup.SIBLINGS_SPOUSE));

        generationsLabel = new JLabel();
        generationsLabel.setText(description.getString(TreeSetup.GENERATIONS));

        generationSpinner = new JSpinner(new SpinnerNumberModel(configuration.getGenerationCount(), 1, 20, 1));

        shapeLabelBox = new JComboBox<>(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        shapeLabelBox.setSelectedItem(configuration.getLabelShape());
    }

    private void initActions() {
        verticalViewCheckBox.addActionListener(this::verticalViewCheckBoxActionPerformed);
        heraldryCheckBox.addActionListener(this::heraldryCheckBoxActionPerformed);
        marriageCheckBox.addActionListener(this::marriageCheckBoxActionPerformed);
        residenceCheckBox.addActionListener(this::residenceCheckBoxActionPerformed);
        spousesCheckbox.addActionListener(this::spousesCheckboxActionPerformed);
        childrenCheckbox.addActionListener(this::childrenCheckboxActionPerformed);
        showParentsCheckbox.addActionListener(this::showParentsCheckboxActionPerformed);
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
        this.add(showParentsCheckbox);
        this.add(showSiblingsCheckbox);
        this.add(showSiblingSpouse);

        JPanel generationPanel = new JPanel(new BorderLayout());
        generationPanel.add(generationSpinner, BorderLayout.EAST);
        generationPanel.add(generationsLabel, BorderLayout.CENTER);

        this.add(generationPanel);
        this.add(shapeLabelBox);
    }

    public void setSiblingsShow(boolean show) {
        showSiblingsCheckbox.setEnabled(show);
        showSiblingSpouse.setEnabled(show);
    }

    private void verticalViewCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowCouplesVertical(verticalViewCheckBox.isSelected());
    }

    private void heraldryCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowHeraldry(heraldryCheckBox.isSelected());
    }

    private void marriageCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowMarriage(marriageCheckBox.isSelected());
    }

    private void residenceCheckBoxActionPerformed(ActionEvent evt) {
        configuration.setShowResidence(residenceCheckBox.isSelected());
    }

    private void spousesCheckboxActionPerformed(ActionEvent evt) {
        configuration.setShowSpouses(spousesCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            childrenCheckbox.setSelected(false);
            configuration.setShowChildren(false);
        }
    }

    private void childrenCheckboxActionPerformed(ActionEvent evt) {
        configuration.setShowChildren(childrenCheckbox.isSelected());
        if (childrenCheckbox.isSelected() && !spousesCheckbox.isSelected()) {
            spousesCheckbox.setSelected(true);
            configuration.setShowSpouses(true);
        }
    }

    private void showParentsCheckboxActionPerformed(ActionEvent evt) {
        configuration.setShowParents(showParentsCheckbox.isSelected());
        if (!showParentsCheckbox.isSelected() && showSiblingsCheckbox.isSelected()) {
            configuration.setShowSiblings(false);
            showSiblingsCheckbox.setSelected(false);
        }
    }

    private void showSiblingsCheckboxActionPerformed(ActionEvent evt) {
        configuration.setShowSiblings(showSiblingsCheckbox.isSelected());
    }

    private void showSiblingSpouseActionPerformed(ActionEvent evt) {
        configuration.setShowSiblingSpouses(showSiblingSpouse.isSelected());
    }

    private void generationSpinnerStateChanged(ChangeEvent evt) {
        configuration.setGenerationCount((int) generationSpinner.getValue());
    }

    private void shapeLabelBoxActionPerformed(ActionEvent evt) {
        configuration.setLabelShape(LabelShape.valueOf(shapeLabelBox.getSelectedItem().toString()));
    }

}
