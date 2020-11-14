package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.TreeType;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.impl.paging.AllAncestorPageSetup;
import org.ambrogenea.familyview.service.impl.paging.FatherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.MotherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.ParentLineagePageSetup;
import org.ambrogenea.familyview.service.impl.selection.AllAncestorsSelectionService;
import org.ambrogenea.familyview.service.impl.selection.FathersSelectionService;
import org.ambrogenea.familyview.service.impl.selection.MothersSelectionService;
import org.ambrogenea.familyview.service.impl.selection.ParentsSelectionService;
import org.ambrogenea.familyview.service.impl.tree.AllAncestorTreeService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.MotherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.ParentLineageTreeService;

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

    public TreeTypePanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT, 5, 1));
        this.window = window;
        this.setPreferredSize(Dimensions.TREE_TYPE_DIMENSION);

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
    }

    private void addComponents() {
        ButtonGroup treeTypeGroup = new ButtonGroup();
        treeTypeGroup.add(allAncestorType);
        treeTypeGroup.add(parentLineageType);
        treeTypeGroup.add(fatherLineageType);
        treeTypeGroup.add(motherLineageType);

        this.add(allAncestorType);
        this.add(parentLineageType);
        this.add(fatherLineageType);
        this.add(motherLineageType);
    }

    public PageSetup createPageSetup(AncestorPerson rootPerson) {
        ConfigurationService configuration = window.getConfiguration();
        PageSetup setup;
        if (fatherLineageType.isSelected()) {
            setup = new FatherLineagePageSetup(configuration, rootPerson);
        } else if (motherLineageType.isSelected()) {
            setup = new MotherLineagePageSetup(configuration, rootPerson);
        } else if (parentLineageType.isSelected()) {
            setup = new ParentLineagePageSetup(configuration, rootPerson);
        } else if (allAncestorType.isSelected()) {
            setup = new AllAncestorPageSetup(configuration, rootPerson);
        } else {
            setup = new FatherLineagePageSetup(configuration, rootPerson);
        }
        return setup;
    }

    private void initActions() {
        allAncestorType.addChangeListener(this::allAncestorTypeStateChanged);
        parentLineageType.addActionListener(this::parentLineageTypeActionPerformed);
        fatherLineageType.addActionListener(this::fatherLineageTypeActionPerformed);
        motherLineageType.addActionListener(this::motherLineageTypeActionPerformed);
    }

    private void fatherLineageTypeActionPerformed(ActionEvent evt) {
        if (fatherLineageType.isSelected()) {
            window.setTreeService(new FatherLineageTreeService());
            window.setSelectionService(new FathersSelectionService());
        }
    }

    private void motherLineageTypeActionPerformed(ActionEvent evt) {
        if (motherLineageType.isSelected()) {
            window.setTreeService(new MotherLineageTreeService());
            window.setSelectionService(new MothersSelectionService());
        }
    }

    private void parentLineageTypeActionPerformed(ActionEvent evt) {
        if (parentLineageType.isSelected()) {
            window.setTreeService(new ParentLineageTreeService());
            window.setSelectionService(new ParentsSelectionService());
        }

    }

    private void allAncestorTypeStateChanged(ChangeEvent evt) {
        if (allAncestorType.isSelected()) {
            window.setTreeService(new AllAncestorTreeService());
            window.setSelectionService(new AllAncestorsSelectionService());
            window.setSiblingsShow(false);
        } else {
            window.setSiblingsShow(true);
        }
    }

}
