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

    public static final int MARRIAGE_LABEL_WIDTH = 120;

    public static final int HORIZONTAL_GAP = 20;
    public static final int VERTICAL_GAP = 100;

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

    protected void drawMother(int childXPosition, int y, AncestorPerson child) {
        int motherXPosition = childXPosition + (getConfiguration().getAdultImageHeight() / 2 + MARRIAGE_LABEL_WIDTH / 2);
        if (child.getMother() != null) {
            drawPerson(motherXPosition, y, child.getMother());
        }
        drawLabel(childXPosition, y, child.getParents().getMarriageDate());
    }

    protected void drawSpouse(int x, int y, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseXPosition = x + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
            drawPerson(spouseXPosition, y, person.getSpouse());
            lines.add(new Line(x, y, spouseXPosition, y));
            drawLabel(x + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2), y, personModel.getSpouseCouple().getMarriageDate());
        }
    }

    protected void drawLabel(int centerX, int centerY, String text) {
        if (text != null && !text.isEmpty()) {
            int labelHeight = 30;
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setPreferredSize(new Dimension(MARRIAGE_LABEL_WIDTH, labelHeight));
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, getConfiguration().getFontSize() - 1));
            date.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
            this.add(date);
            date.setBounds(centerX - MARRIAGE_LABEL_WIDTH / 2, centerY - labelHeight, MARRIAGE_LABEL_WIDTH, labelHeight);
        }
    }

    protected void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;
        int olderSiblingCount = rootChild.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - 2 * HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int youngerSiblingCount = rootChild.getYoungerSiblings().size();
        int startX;
        for (int i = 0; i < youngerSiblingCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            if (rootChild.getSpouse() != null) {
                startX = rootSiblingX + 2 * getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH + 2 * HORIZONTAL_GAP + i * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            } else {
                startX = rootSiblingX + getConfiguration().getAdultImageWidth() + 3 * HORIZONTAL_GAP + i * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            }
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
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
