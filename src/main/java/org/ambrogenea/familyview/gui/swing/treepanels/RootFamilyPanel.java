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
import javax.swing.border.LineBorder;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

    public static final int IMAGE_WIDTH = 140;
    public static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 0.8);
    public static final int MARRIAGE_LABEL_WIDTH = 120;

    public static final int HORIZONTAL_GAP = 20;
    public static final int VERTICAL_GAP = IMAGE_HEIGHT;

    public final static int FONT_SIZE = 13;

    protected final ArrayList<Line> lines;
    protected final AncestorPerson model;

    public RootFamilyPanel(AncestorPerson model) {
        this.model = model;
        lines = new ArrayList<>();
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.WHITE);
        this.setLayout(null);
    }

    protected void drawPerson(int centerX, int centerY, final Person person) {
        JPanel personPanel = new PersonPanel(person);
        personPanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        this.add(personPanel);
        personPanel.setBounds(centerX - IMAGE_WIDTH / 2, centerY - IMAGE_HEIGHT / 2, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    protected void drawLabel(int centerX, int centerY, String text) {
        int labelHeight = 30;
        JLabel date = new JLabel(text, JLabel.CENTER);
        date.setPreferredSize(new Dimension(MARRIAGE_LABEL_WIDTH, labelHeight));
        date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE - 1));
        date.setBorder(new LineBorder(Color.BLACK));
        this.add(date);
        date.setBounds(centerX - MARRIAGE_LABEL_WIDTH / 2, centerY - labelHeight, MARRIAGE_LABEL_WIDTH, labelHeight);
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
        int horizontalShift = (IMAGE_WIDTH + MARRIAGE_LABEL_WIDTH) / 2;
        lines.add(new Line(childXPosition, childYPosition, childXPosition, childYPosition - IMAGE_HEIGHT - VERTICAL_GAP));
        lines.add(new Line(childXPosition - horizontalShift, childYPosition - IMAGE_HEIGHT - VERTICAL_GAP, childXPosition + horizontalShift, childYPosition - IMAGE_HEIGHT - VERTICAL_GAP));
    }

    protected void addSiblingsToParents(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (IMAGE_HEIGHT + VERTICAL_GAP) / 2;
        lines.add(new Line(startX, rootSiblingY - verticalShift, rootSiblingX, rootSiblingY - verticalShift));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
        lines.add(new Line(startX, rootSiblingY - verticalShift, startX, rootSiblingY));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }
}
