package org.ambrogenea.familyview.gui.swing.tools;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanelMouseController extends MouseAdapter {

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private final JPanel personPanel;
    private final Person person;
    private final Configuration configuration;

    public PersonPanelMouseController(JPanel image, Configuration config, Person person) {
        this.personPanel = image;
        this.person = person;
        this.configuration = config;
        this.personPanel.setToolTipText("Show close family of " + person.getName());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        personPanel.setCursor(defCursor);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        personPanel.setCursor(hndCursor);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        DrawingFrame drawing = new DrawingFrame("Close family of " + person.getName());
        AncestorPerson personWithAncestors = configuration.getAncestorModel().generateCloseFamily(person.getPosition());
        JPanel panel = drawing.generateCloseFamily(personWithAncestors, configuration);
        drawing.setSize(panel.getWidth() + 50, panel.getHeight() + 120);
        drawing.setPreferredSize(new Dimension(panel.getWidth() + 50, panel.getHeight() + 120));
        drawing.setLocationRelativeTo(personPanel);
    }

}
