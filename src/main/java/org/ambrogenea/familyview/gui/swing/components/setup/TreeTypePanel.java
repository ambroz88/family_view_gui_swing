package org.ambrogenea.familyview.gui.swing.components.setup;

import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.TreeType;
import org.ambrogenea.familyview.service.impl.selection.*;
import org.ambrogenea.familyview.service.impl.tree.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeTypePanel extends JPanel {

    private final Window window;
    private JToggleButton allAncestorType;
    private JToggleButton fatherLineageType;
    private JToggleButton motherLineageType;
    private JToggleButton parentLineageType;
    private JToggleButton descendentsLineageType;

    public TreeTypePanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT, 6, 1));
        this.window = window;
        this.setPreferredSize(Dimensions.TREE_TYPE_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        Locale locale = window.getConfiguration().getLocale();
        ResourceBundle description = ResourceBundle.getBundle("language/treeType", locale);
        this.setBorder(new TitledBorder(description.getString(TreeType.TITLE)));

        Icon allGenerationIcon = new ImageIcon(ClassLoader.getSystemResource("icons/AllGenerationsIcon.png"));
        allAncestorType = new JToggleButton(allGenerationIcon);
        allAncestorType.setToolTipText(description.getString(TreeType.ALL_ANCESTORS));
        allAncestorType.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        Icon parentLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/ParentLineageIcon.png"));
        parentLineageType = new JToggleButton(parentLineageIcon);
        parentLineageType.setToolTipText(description.getString(TreeType.PARENTS));
        parentLineageType.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        Icon fatherLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/FatherLineageIcon.png"));
        fatherLineageType = new JToggleButton(fatherLineageIcon);
        fatherLineageType.setToolTipText(description.getString(TreeType.FATHERS));
        fatherLineageType.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);
        fatherLineageType.setSelected(true);

        Icon motherLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/MotherLineageIcon.png"));
        motherLineageType = new JToggleButton(motherLineageIcon);
        motherLineageType.setToolTipText(description.getString(TreeType.MOTHERS));
        motherLineageType.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);

        descendentsLineageType = new JToggleButton("DESC.");
        descendentsLineageType.setToolTipText(description.getString(TreeType.DESCENDENTS));
        descendentsLineageType.setPreferredSize(Dimensions.TREE_BUTTON_DIMENSION);
    }

    private void addComponents() {
        ButtonGroup treeTypeGroup = new ButtonGroup();
        treeTypeGroup.add(allAncestorType);
        treeTypeGroup.add(parentLineageType);
        treeTypeGroup.add(fatherLineageType);
        treeTypeGroup.add(motherLineageType);
        treeTypeGroup.add(descendentsLineageType);

        this.add(allAncestorType);
        this.add(parentLineageType);
        this.add(fatherLineageType);
        this.add(motherLineageType);
        this.add(descendentsLineageType);
    }

    private void initActions() {
        allAncestorType.addChangeListener(this::allAncestorTypeStateChanged);
        parentLineageType.addActionListener(this::parentLineageTypeActionPerformed);
        fatherLineageType.addActionListener(this::fatherLineageTypeActionPerformed);
        motherLineageType.addActionListener(this::motherLineageTypeActionPerformed);
        descendentsLineageType.addChangeListener(this::descendentsLineageTypeStateChanged);
    }

    private void fatherLineageTypeActionPerformed(ActionEvent evt) {
        if (fatherLineageType.isSelected()) {
            window.getConfiguration().setShowParentLineage(false);
            window.setTreeService(new FatherLineageTreeService());
            window.setSelectionService(new FathersSelectionService(window.getConfiguration()));
            window.generateTree();
        }
    }

    private void motherLineageTypeActionPerformed(ActionEvent evt) {
        if (motherLineageType.isSelected()) {
            window.getConfiguration().setShowParentLineage(false);
            window.setTreeService(new MotherLineageTreeService());
            window.setSelectionService(new MothersSelectionService(window.getConfiguration()));
            window.generateTree();
        }
    }

    private void parentLineageTypeActionPerformed(ActionEvent evt) {
        if (parentLineageType.isSelected()) {
            window.getConfiguration().setShowParentLineage(true);
            window.setTreeService(new ParentLineageTreeService());
            window.setSelectionService(new ParentsSelectionService(window.getConfiguration()));
            window.generateTree();
        }

    }

    private void allAncestorTypeStateChanged(ChangeEvent evt) {
        if (allAncestorType.isSelected()) {
            window.getConfiguration().setShowParentLineage(false);
            window.setTreeService(new AllAncestorTreeService());
            window.setSelectionService(new AllAncestorsSelectionService(window.getConfiguration()));
            window.generateTree();
        }
    }

    private void descendentsLineageTypeStateChanged(ChangeEvent evt) {
        if (descendentsLineageType.isSelected()) {
            window.getConfiguration().setShowParentLineage(false);
            window.setTreeService(new AllDescendentsTreeService());
            window.setSelectionService(new DescendentSelectionService(window.getConfiguration()));
            window.generateTree();
        }
    }

}
