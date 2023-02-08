package cz.ambrogenea.familyvision.gui.swing.utils;

import cz.ambrogenea.familyvision.gui.swing.components.draw.PersonPanel;
import cz.ambrogenea.familyvision.gui.swing.service.Config;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanelMouseController extends MouseAdapter {

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private final PersonPanel personPanel;


    public PersonPanelMouseController(PersonPanel image) {
        this.personPanel = image;
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
        if (Config.visual().isResetMode()) {
            personPanel.setAnonymous();
        }
    }

}
