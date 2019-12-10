package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

//    public static final int IMAGE_WIDTH = 140;
//    public static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH * 0.8);
    public static final int MARRIAGE_LABEL_WIDTH = 120;

    public static final int HORIZONTAL_GAP = 20;
    public static final int VERTICAL_GAP = 100;

    public final static int FONT_SIZE = 13;

    protected final ArrayList<Line> lines;
    protected final AncestorPerson personModel;
    protected final Configuration configuration;

    public RootFamilyPanel(AncestorPerson model, Configuration config) {
        this.personModel = model;
        this.configuration = config;
        lines = new ArrayList<>();
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.WHITE);
        this.setLayout(null);
    }

    protected void drawPerson(int centerX, int centerY, final Person person) {
        JPanel personPanel = new PersonPanel(person, configuration);
        personPanel.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
        this.add(personPanel);
        personPanel.setBounds(centerX - configuration.getAdultImageWidth() / 2, centerY - configuration.getAdultImageHeight() / 2, configuration.getAdultImageWidth(), configuration.getAdultImageHeight());
    }

    protected void drawLabel(int centerX, int centerY, String text) {
        if (text != null && !text.isEmpty()) {
            int labelHeight = 30;
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setPreferredSize(new Dimension(MARRIAGE_LABEL_WIDTH, labelHeight));
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE - 1));
            date.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
            this.add(date);
            date.setBounds(centerX - MARRIAGE_LABEL_WIDTH / 2, centerY - labelHeight, MARRIAGE_LABEL_WIDTH, labelHeight);
        }
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
        int horizontalShift = (configuration.getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) / 2;
        lines.add(new Line(childXPosition, childYPosition, childXPosition, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP));
        lines.add(new Line(childXPosition - horizontalShift, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP, childXPosition + horizontalShift, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP));
    }

    protected void addSiblingsToParents(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;
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

    protected Configuration getConfiguration() {
        return configuration;
    }

}
