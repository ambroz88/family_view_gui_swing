package org.ambrogenea.familyview.gui.swing.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.IllegalComponentStateException;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.ambrogenea.familyview.domain.PersonRecord;
import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.service.PageSetup;
import org.ambrogenea.familyview.service.impl.paging.AllAncestorPageSetup;
import org.ambrogenea.familyview.service.impl.paging.CloseFamilyPageSetup;
import org.ambrogenea.familyview.service.impl.paging.FatherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.MotherLineagePageSetup;
import org.ambrogenea.familyview.service.impl.paging.ParentLineagePageSetup;
import org.ambrogenea.familyview.service.impl.tree.AllAncestorTreeService;
import org.ambrogenea.familyview.service.impl.tree.CloseFamilyTreeService;
import org.ambrogenea.familyview.service.impl.tree.FatherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.MotherLineageTreeService;
import org.ambrogenea.familyview.service.impl.tree.ParentLineageTreeService;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanelMouseController extends MouseAdapter {

    private final static Dimension BUTTON_DIMENSION = new Dimension(42, 36);

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private final PersonPanel personPanel;
    private final PersonRecord personModel;
    private final Configuration configuration;

    private JDialog floatMenu;
    private JButton closeFamily;
    private JButton fatherLineage;
    private JButton motherLineage;
    private JButton parentLineage;
    private JButton allGenerations;

    public PersonPanelMouseController(PersonPanel image, Configuration config, PersonRecord person) {
        this.personPanel = image;
        this.personPanel.setToolTipText("Show tree possibilities");
        this.personModel = person;
        this.configuration = config;

        initFloatMenu();

        initCloseFamily();
        initFatherLineage();
        initMotherLineage();
        initParentLineage();
        initAllGenerations();

        this.floatMenu.add(closeFamily);
        this.floatMenu.add(fatherLineage);
        this.floatMenu.add(motherLineage);
        this.floatMenu.add(parentLineage);
        this.floatMenu.add(allGenerations);
    }

    private void initFloatMenu() {
        this.floatMenu = new JDialog();
        this.floatMenu.setType(Window.Type.UTILITY);
        this.floatMenu.setTitle("Generate...");
        this.floatMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
        this.floatMenu.setSize(new Dimension(5 * (BUTTON_DIMENSION.width + 10), 80));
        this.floatMenu.setResizable(false);
    }

    private void initCloseFamily() {
        Icon closeFamilyIcon = new ImageIcon(ClassLoader.getSystemResource("icons/CloseFamilyIcon.png"));
        closeFamily = new JButton(closeFamilyIcon);
        closeFamily.setToolTipText("Generate close family");
        closeFamily.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closeFamily.setSize(BUTTON_DIMENSION);
        closeFamily.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame(new CloseFamilyTreeService());
                AncestorPerson rootPerson = configuration.getAncestorModel().generateCloseFamily(personModel.getDbPosition());
                PageSetup setup = new CloseFamilyPageSetup(configuration, rootPerson);
                drawing.generateTreePanel(rootPerson, setup, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange(PropertyName.NEW_TREE, rootPerson.getName(), drawing);
            }
        });
    }

    private void initFatherLineage() {
        Icon fatherLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/FatherLineageIcon.png"));
        fatherLineage = new JButton(fatherLineageIcon);
        fatherLineage.setToolTipText("Generate father's lineage");
        fatherLineage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fatherLineage.setSize(BUTTON_DIMENSION);
        fatherLineage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame(new FatherLineageTreeService());
                AncestorPerson rootPerson = configuration.getAncestorModel().generateFatherLineage(personModel.getDbPosition());
                PageSetup setup = new FatherLineagePageSetup(configuration, rootPerson);
                drawing.generateTreePanel(rootPerson, setup, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange(PropertyName.NEW_TREE, rootPerson.getName(), drawing);
            }
        });
    }

    private void initMotherLineage() {
        Icon motherLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/MotherLineageIcon.png"));
        motherLineage = new JButton(motherLineageIcon);
        motherLineage.setToolTipText("Generate mother's lineage");
        motherLineage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        motherLineage.setSize(BUTTON_DIMENSION);
        motherLineage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame(new MotherLineageTreeService());
                AncestorPerson rootPerson = configuration.getAncestorModel().generateMotherLineage(personModel.getDbPosition());
                PageSetup setup = new MotherLineagePageSetup(configuration, rootPerson);
                drawing.generateTreePanel(rootPerson, setup, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange(PropertyName.NEW_TREE, rootPerson.getName(), drawing);
            }
        });
    }

    private void initParentLineage() {
        Icon parentLineageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/ParentLineageIcon.png"));
        parentLineage = new JButton(parentLineageIcon);
        parentLineage.setToolTipText("Generate parent's lineage");
        parentLineage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        parentLineage.setSize(BUTTON_DIMENSION);
        parentLineage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame(new ParentLineageTreeService());
                AncestorPerson rootPerson = configuration.getAncestorModel().generateParentsLineage(personModel.getDbPosition());
                PageSetup setup = new ParentLineagePageSetup(configuration, rootPerson);
                drawing.generateTreePanel(rootPerson, setup, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange(PropertyName.NEW_TREE, rootPerson.getName(), drawing);
            }
        });
    }

    private void initAllGenerations() {
        Icon allGenerationIcon = new ImageIcon(ClassLoader.getSystemResource("icons/AllGenerationsIcon.png"));
        allGenerations = new JButton(allGenerationIcon);
        allGenerations.setToolTipText("Generate all generations");
        allGenerations.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        allGenerations.setSize(BUTTON_DIMENSION);
        allGenerations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame(new AllAncestorTreeService());
                AncestorPerson rootPerson = configuration.getAncestorModel().generateAncestors(personModel.getDbPosition());
                PageSetup setup = new AllAncestorPageSetup(configuration, rootPerson);
                drawing.generateTreePanel(rootPerson, setup, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange(PropertyName.NEW_TREE, rootPerson.getName(), drawing);
            }
        });
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        personPanel.setCursor(defCursor);
        try {
            Rectangle bounds = new Rectangle(personPanel.getLocationOnScreen(), personPanel.getSize());
            if (floatMenu.isVisible() && !bounds.contains(e.getLocationOnScreen())) {
                floatMenu.dispose();
            }
        } catch (IllegalComponentStateException ex) {
            System.out.println("exeption: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        personPanel.setCursor(hndCursor);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (!configuration.isResetMode() || configuration.isResetMode() && personPanel.setAnonymous()) {
            this.floatMenu.setVisible(true);
            this.floatMenu.setLocationRelativeTo(personPanel);
        }
    }

}
