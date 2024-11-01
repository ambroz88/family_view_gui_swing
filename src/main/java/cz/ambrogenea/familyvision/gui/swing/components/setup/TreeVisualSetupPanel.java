package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.TreeVisualSetup;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeVisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.enums.Background;
import cz.ambrogenea.familyvision.gui.swing.enums.CoupleType;
import cz.ambrogenea.familyvision.gui.swing.enums.LabelShape;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeVisualSetupPanel extends JPanel {

    private final Window window;
    private final TreeVisualConfiguration configuration;

    private JLabel coupleTypeLabel;
    private JComboBox<String> coupleTypeComboBox;
    private JLabel marriageShapeLabel;
    private JComboBox<String> marriageShapeComboBox;
    private JLabel backgroundLabel;
    private JComboBox<String> backgroundComboBox;
    private JCheckBox titleCheckBox;
    private JCheckBox childrenCountCheckBox;
    private JCheckBox heraldryCheckBox;
    private JCheckBox residenceCheckBox;


    public TreeVisualSetupPanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.window = window;
        this.setPreferredSize(Dimensions.SETUP_PANEL_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);
        configuration = Config.tree();

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/treeVisualSetup", configuration.getLocale());

        coupleTypeLabel = new JLabel(description.getString(TreeVisualSetup.COUPLE_TYPE));
        coupleTypeLabel.setOpaque(false);
        coupleTypeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(CoupleType.getStrings()));
        coupleTypeComboBox.setSelectedItem(configuration.getCoupleType().toString());
        marriageShapeLabel = new JLabel(description.getString(TreeVisualSetup.MARRIAGE_SHAPE), JLabel.LEFT);
        marriageShapeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(LabelShape.getStrings()));
        marriageShapeComboBox.setSelectedItem(configuration.getMarriageLabelShape().toString());
        backgroundLabel = new JLabel(description.getString(TreeVisualSetup.BACKGROUND), JLabel.LEFT);
        backgroundComboBox = new JComboBox<>(new DefaultComboBoxModel<>(Background.getStrings()));
        backgroundComboBox.setSelectedItem(configuration.getBackground().toString());

        titleCheckBox = new JCheckBox(description.getString(TreeVisualSetup.SHOW_TITLE));
        titleCheckBox.setSelected(configuration.isShowTitle());
        titleCheckBox.setOpaque(false);
        childrenCountCheckBox = new JCheckBox(description.getString(TreeVisualSetup.SHOW_CHILDREN_COUNT));
        childrenCountCheckBox.setSelected(configuration.isShowChildrenCount());
        childrenCountCheckBox.setOpaque(false);
        heraldryCheckBox = new JCheckBox(description.getString(TreeVisualSetup.HERALDRY));
        heraldryCheckBox.setSelected(configuration.isShowHeraldry());
        heraldryCheckBox.setOpaque(false);
        residenceCheckBox = new JCheckBox(description.getString(TreeVisualSetup.RESIDENCE));
        residenceCheckBox.setSelected(configuration.isShowResidence());
        residenceCheckBox.setOpaque(false);
    }

    private void initActions() {
        coupleTypeComboBox.addActionListener(this::coupleTypeComboBoxActionPerformed);
        marriageShapeComboBox.addActionListener(this::shapeLabelBoxActionPerformed);
        backgroundComboBox.addActionListener(this::backgroundComboBoxActionPerformed);
        titleCheckBox.addActionListener(this::titleCheckBoxActionPerformed);
        childrenCountCheckBox.addActionListener(this::childrenCountCheckBoxActionPerformed);
        heraldryCheckBox.addActionListener(this::heraldryCheckBoxActionPerformed);
        residenceCheckBox.addActionListener(this::residenceCheckBoxActionPerformed);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(coupleTypeLabel);
        panel.add(coupleTypeComboBox);
        panel.add(marriageShapeLabel);
        panel.add(marriageShapeComboBox);
        panel.add(backgroundLabel);
        panel.add(backgroundComboBox);
        panel.add(titleCheckBox);
        panel.add(childrenCountCheckBox);
        panel.add(heraldryCheckBox);
        panel.add(residenceCheckBox);
        panel.setBackground(Colors.SW_BACKGROUND);
        this.add(panel);
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

    private void coupleTypeComboBoxActionPerformed(ActionEvent evt) {
        configuration.setCoupleType(CoupleType.valueOf(coupleTypeComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.updateTree();
    }

    private void backgroundComboBoxActionPerformed(ActionEvent actionEvent) {
        configuration.setBackground(Background.valueOf(backgroundComboBox.getSelectedItem().toString()));
        window.updateConfiguration(configuration);
        window.updateTree();
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
