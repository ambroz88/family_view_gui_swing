package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

    public static final int MINIMAL_WIDTH = 140;
    public static final int MINIMAL_HEIGHT = 150;
    public static final int IMAGE_WIDTH = MINIMAL_WIDTH - 20;
    public static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 0.8);

    public RootFamilyPanel() {
    }

    protected void drawPerson(int x, int y, final Person person) {
        JPanel personPanel = new PersonPanel(person);
        this.add(personPanel);
        personPanel.setBounds(x - IMAGE_WIDTH / 2, y - IMAGE_HEIGHT / 2, IMAGE_WIDTH, IMAGE_HEIGHT);
        personPanel.repaint();
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }
}
