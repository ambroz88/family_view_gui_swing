package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

    public static final int MINIMAL_WIDTH = 140;
    public static final int IMAGE_WIDTH = MINIMAL_WIDTH - 20;
    public static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 0.8);
    public static final int MINIMAL_HEIGHT = IMAGE_HEIGHT * 2;
    public static final int BORDER_WIDTH = 50;

    public final static int FONT_SIZE = 11;

    protected final ArrayList<Line> lines;

    public RootFamilyPanel() {
        lines = new ArrayList<>();
    }

    protected void drawPerson(int centerX, int centerY, final Person person) {
        JPanel personPanel = new PersonPanel(person);
        personPanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        this.add(personPanel);
        personPanel.setBounds(centerX - IMAGE_WIDTH / 2, centerY - IMAGE_HEIGHT / 2, IMAGE_WIDTH, IMAGE_HEIGHT);
        personPanel.repaint();
    }

    protected void drawLabel(int centerX, int centerY, String text) {
        JLabel date = new JLabel(text, JLabel.CENTER);
        date.setPreferredSize(new Dimension(100, 20));
        this.add(date);
        date.setBounds(centerX - 50, centerY - BORDER_WIDTH / 2, 100, 20);
        date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE - 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        for (Line line : lines) {
            g2.setStroke(new BasicStroke(line.getType()));
            g2.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }

    }

    protected void addLineToParents(int childXPosition, int childYPosition) {
        lines.add(new Line(childXPosition, childYPosition, childXPosition, childYPosition - MINIMAL_HEIGHT));
        lines.add(new Line(childXPosition - MINIMAL_WIDTH, childYPosition - MINIMAL_HEIGHT, childXPosition + MINIMAL_WIDTH, childYPosition - MINIMAL_HEIGHT));
    }

    protected void addSiblingsToParents(int startX, int rootSiblingY, int rootSiblingX) {
        lines.add(new Line(startX, rootSiblingY - MINIMAL_HEIGHT / 2, rootSiblingX, rootSiblingY - MINIMAL_HEIGHT / 2));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
        lines.add(new Line(startX, rootSiblingY - MINIMAL_HEIGHT / 2, startX, rootSiblingY));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }
}
