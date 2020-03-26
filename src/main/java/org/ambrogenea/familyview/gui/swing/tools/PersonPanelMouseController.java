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

import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanelMouseController extends MouseAdapter {

    private final static Dimension BUTTON_DIMENSION = new Dimension(42, 36);

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private final PersonPanel personPanel;
    private final Person personModel;
    private final Configuration configuration;

    private final JDialog floatMenu;
    private final JButton closeFamily;
    private final JButton fatherLineage;
    private final JButton motherLineage;
    private final JButton allGenerations;

    public PersonPanelMouseController(PersonPanel image, Configuration config, Person person) {
        this.personPanel = image;
        this.personPanel.setToolTipText("Show tree possibilities");
        this.personModel = person;
        this.configuration = config;

        this.floatMenu = new JDialog();
        this.floatMenu.setType(Window.Type.UTILITY);
        this.floatMenu.setTitle("Generate...");
        this.floatMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
        this.floatMenu.setSize(new Dimension(4 * (BUTTON_DIMENSION.width + 10), 85));
        this.floatMenu.setResizable(false);

        Icon closeFamilyIcon = new ImageIcon(getClass().getClassLoader().getResource("\\icons\\CloseFamilyIcon.png"));
        closeFamily = new JButton(closeFamilyIcon);
        closeFamily.setToolTipText("Generate close family");
        closeFamily.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        closeFamily.setSize(BUTTON_DIMENSION);
        closeFamily.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame();
                AncestorPerson personWithAncestors = configuration.getAncestorModel().generateCloseFamily(personModel.getPosition());
                drawing.generateCloseFamily(personWithAncestors, configuration);
                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange("New Tree", personWithAncestors.getName(), drawing);
            }
        });

        Icon fatherLineageIcon = new ImageIcon(getClass().getClassLoader().getResource("\\icons\\FatherLineageIcon.png"));
        fatherLineage = new JButton(fatherLineageIcon);
        fatherLineage.setToolTipText("Generate father lineage");
        fatherLineage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fatherLineage.setSize(BUTTON_DIMENSION);
        fatherLineage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame();
                AncestorPerson personWithAncestors = configuration.getAncestorModel().generateFatherLineage(personModel.getPosition());
                drawing.generateFatherLineage(personWithAncestors, configuration);
                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange("New Tree", personWithAncestors.getName(), drawing);
            }
        });

        Icon motherLineageIcon = new ImageIcon(getClass().getClassLoader().getResource("\\icons\\MotherLineageIcon.png"));
        motherLineage = new JButton(motherLineageIcon);
        motherLineage.setToolTipText("Generate mother lineage");
        motherLineage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        motherLineage.setSize(BUTTON_DIMENSION);
        motherLineage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame();
                AncestorPerson personWithAncestors = configuration.getAncestorModel().generateMotherLineage(personModel.getPosition());
                drawing.generateMotherLineage(personWithAncestors, configuration);
                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange("New Tree", personWithAncestors.getName(), drawing);
            }
        });

        Icon allGenerationIcon = new ImageIcon(getClass().getClassLoader().getResource("\\icons\\AllGenerationsIcon.png"));
        allGenerations = new JButton(allGenerationIcon);
        allGenerations.setToolTipText("Generate all generations");
        allGenerations.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        allGenerations.setSize(BUTTON_DIMENSION);
        allGenerations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingFrame drawing = new DrawingFrame();
                AncestorPerson personWithAncestors = configuration.getAncestorModel().generateAncestors(personModel.getPosition());
                drawing.generateAllAncestors(personWithAncestors, configuration);

                floatMenu.dispose();
                //ApplicationWindow is catching this propertyChange
                configuration.firePropertyChange("New Tree", personWithAncestors.getName(), drawing);
            }
        });

        this.floatMenu.add(fatherLineage);
        this.floatMenu.add(motherLineage);
        this.floatMenu.add(closeFamily);
        this.floatMenu.add(allGenerations);
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
