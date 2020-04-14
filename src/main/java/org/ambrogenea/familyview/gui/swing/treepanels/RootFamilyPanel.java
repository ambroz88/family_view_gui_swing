package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.model.Arc;
import org.ambrogenea.familyview.gui.swing.model.ImageModel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.ResidenceModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.model.Residence;
import org.ambrogenea.familyview.model.enums.Sex;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

    private static final Color[] COLORS = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.LIGHT_GRAY, Color.ORANGE, Color.CYAN, Color.PINK, Color.MAGENTA};

    public static final int HORIZONTAL_GAP = 20;
    public static final int SIBLINGS_GAP = 2 * HORIZONTAL_GAP;
    public static final int VERTICAL_GAP = 100;
    public static final int LABEL_HEIGHT = 30;
    public static final int RESIDENCE_SIZE = 25;

    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<ImageModel> images;
    protected final ArrayList<ResidenceModel> residences;
    protected final AncestorPerson personModel;
    protected final Configuration configuration;
    private final TreeMap<String, Color> cityRegister;

    public RootFamilyPanel(AncestorPerson model, Configuration config) {
        this.personModel = model;
        this.configuration = config;
        lines = new HashSet<>();
        arcs = new HashSet<>();
        images = new HashSet<>();
        residences = new ArrayList<>();
        cityRegister = new TreeMap<>();
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.WHITE);
        this.setLayout(null);
    }

    protected void drawPerson(int centerX, int centerY, final Person person) {
        PersonPanel personPanel = new PersonPanel(person, configuration);
        personPanel.addMouseAdapter();
        personPanel.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
        this.add(personPanel);
        personPanel.setBounds(centerX - configuration.getAdultImageWidth() / 2, centerY - configuration.getAdultImageHeight() / 2, configuration.getAdultImageWidth(), configuration.getAdultImageHeight());

        if (configuration.isShowResidence()) {
            drawResidence(person, personPanel);
        }
    }

    private void drawResidence(final Person person, JPanel personPanel) {
        Residence residence;
        JLabel number;
        int x;
        int y;
        for (int i = 0; i < person.getResidenceList().size(); i++) {
            residence = person.getResidenceList().get(i);
            if (!residence.getCity().isEmpty()) {

                if (person.getSex().equals(Sex.MALE)) {
                    x = personPanel.getX() - RESIDENCE_SIZE - HORIZONTAL_GAP / 2;
                } else {
                    x = personPanel.getX() + configuration.getAdultImageWidth() + HORIZONTAL_GAP / 2;
                }

                y = personPanel.getY() + i * (RESIDENCE_SIZE + 5);
                residences.add(new ResidenceModel(x, y, residence));
                addCityToRegister(residence.getCity());
                if (residence.getNumber() > 0) {
                    number = new JLabel("" + residence.getNumber(), JLabel.CENTER);
                    number.setSize(RESIDENCE_SIZE, RESIDENCE_SIZE);
                    number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getFontSize() - 2));
                    this.add(number);
                    number.setBounds(x, y, RESIDENCE_SIZE, RESIDENCE_SIZE);
                }

            }
        }
    }

    protected int drawMother(int childXPosition, int y, AncestorPerson child) {
        int motherXPosition;
        if (child.getFather() != null) {
            int halfLabelWidth = getConfiguration().getMarriageLabelWidth() / 2;
            motherXPosition = childXPosition + getConfiguration().getHalfSpouseLabelSpace();
            drawLabel(childXPosition - halfLabelWidth, childXPosition + halfLabelWidth, y, child.getParents().getMarriageDate());
        } else {
            motherXPosition = childXPosition;
        }

        drawPerson(motherXPosition, y, child.getMother());
        return motherXPosition;
    }

    protected void drawSpouse(int husbandX, int motherY, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseXPosition = husbandX + getConfiguration().getSpouseLabelSpace();
            drawPerson(spouseXPosition, motherY, person.getSpouse());
            drawLabel(husbandX + getConfiguration().getAdultImageWidth() / 2, spouseXPosition - getConfiguration().getAdultImageWidth() / 2, motherY, person.getSpouseCouple().getMarriageDate());
        }
    }

    protected int drawAllSpouses(int startXPosition, int y, AncestorPerson person) {
        int spouseXPosition = startXPosition;
        if (person.getSpouse() != null) {
            int labelXPosition;
            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                startXPosition = spouseXPosition;
                spouseXPosition = spouseXPosition + getConfiguration().getSpouseLabelSpace();

                drawPerson(spouseXPosition, y, person.getSpouse(index));
                labelXPosition = startXPosition + (getConfiguration().getAdultImageWidth() / 2);
                drawLabel(labelXPosition, labelXPosition + getConfiguration().getMarriageLabelWidth(), y, person.getSpouseCouple(index).getMarriageDate());
            }
        }
        return spouseXPosition;
    }

    protected int drawAllSpousesWithKids(int husbandXPosition, int y, AncestorPerson person) {
        int spouseXPosition = husbandXPosition;
        if (person.getSpouse() != null) {
            int labelXPosition;
            int labelWidth = getConfiguration().getMarriageLabelWidth();
            int childrenShift = -(Math.max(1, person.getChildrenCount(0)) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) / 2) + (getConfiguration().getAdultImageWidth() + labelWidth) - SIBLINGS_GAP;

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                husbandXPosition = spouseXPosition;
                int childrenWidth = Math.max(1, person.getChildrenCount(index)) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) / 2 - (getConfiguration().getAdultImageWidth() + labelWidth);
                spouseXPosition = spouseXPosition + (getConfiguration().getAdultImageWidth() + labelWidth) + childrenShift + childrenWidth + SIBLINGS_GAP;

                drawPerson(spouseXPosition, y, person.getSpouse(index));
                labelXPosition = spouseXPosition - (getConfiguration().getHalfSpouseLabelSpace());
                drawLabel(husbandXPosition + getConfiguration().getAdultImageWidth() / 2, spouseXPosition - getConfiguration().getAdultImageWidth() / 2, y, person.getSpouseCouple(index).getMarriageDate());
                childrenShift = drawChildren(labelXPosition, y, person.getSpouseCouple(index));
                childrenShift = Math.max(getConfiguration().getHalfSpouseLabelSpace(), childrenShift);
            }
        }
        return spouseXPosition;
    }

    protected void drawLabel(int startX, int endX, int centerY, String text) {
        if (text != null && !text.isEmpty() && configuration.isShowMarriage()) {
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, getConfiguration().getFontSize() - 1));
            date.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.BLACK));
            date.setOpaque(true);
            date.setBackground(new Color(250, 250, 250));
            this.add(date);
            date.setBounds(startX, centerY - LABEL_HEIGHT, endX - startX, LABEL_HEIGHT);
        } else {
            lines.add(new Line(startX, centerY, endX, centerY));
        }
    }

    protected void drawLongerLabel(int centerX, int centerY, String text) {
        int wideMarriageLabel = getConfiguration().getWideMarriageLabel();
        if (text != null && !text.isEmpty()) {
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, getConfiguration().getFontSize() - 1));
            date.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.BLACK));
            date.setOpaque(true);
            date.setBackground(new Color(250, 250, 250));
            this.add(date);
            date.setBounds(centerX - wideMarriageLabel / 2, centerY - LABEL_HEIGHT, wideMarriageLabel, LABEL_HEIGHT);
        } else {
            lines.add(new Line(centerX - wideMarriageLabel / 2, centerY, centerX + wideMarriageLabel / 2, centerY));
        }
    }

    protected void drawOlderSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        int startX;
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            if (i == 0) {
                addRoundChildrenLine(startX, rootSiblingY, rootSiblingX);
            } else {
                addStraightChildrenLine(startX, rootSiblingY, rootSiblingX);
            }
        }
    }

    protected void drawYoungerSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;

        int startX;
        LinkedList<Person> youngerSiblings = rootChild.getYoungerSiblings();
        for (int i = 0; i < youngerSiblings.size(); i++) {
            sibling = youngerSiblings.get(i);

            startX = rootSiblingX + HORIZONTAL_GAP + (i + 1) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            if (i == youngerSiblings.size() - 1) {
                addRoundChildrenLine(startX, rootSiblingY, rootSiblingX);
            } else {
                addStraightChildrenLine(startX, rootSiblingY, rootSiblingX);
            }
        }
    }

    protected void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        drawOlderSiblings(rootSiblingX, rootSiblingY, rootChild);
        drawYoungerSiblings(rootSiblingX, rootSiblingY, rootChild);
    }

    protected void drawSiblingsAroundMother(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth());
        }

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSiblingX, rootSiblingY, rootSiblingX + spouseGap);
        }

        drawOlderSiblings(rootSiblingX, rootSiblingY, rootChild);
        drawYoungerSiblings(rootSiblingX + spouseGap, rootSiblingY, rootChild);
    }

    protected void drawSiblingsAroundFather(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth());
        }

        if (!rootChild.getOlderSiblings().isEmpty()) {
            addLineAboveSpouse(rootSiblingX, rootSiblingY, rootSiblingX - spouseGap);
        }

        drawOlderSiblings(rootSiblingX - spouseGap, rootSiblingY, rootChild);
        drawYoungerSiblings(rootSiblingX, rootSiblingY, rootChild);
    }

    protected void drawSiblingsAroundWifes(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap;
        if (!rootChild.getSpouseID().isEmpty() && lastSpouseX == 0) {
            spouseGap = rootSiblingX + (getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth()) * rootChild.getSpouseCouples().size();
        } else {
            spouseGap = lastSpouseX;
        }

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSiblingX, rootSiblingY, spouseGap);
        }

        drawOlderSiblings(rootSiblingX, rootSiblingY, rootChild);
        drawYoungerSiblings(spouseGap, rootSiblingY, rootChild);
    }

    private void addLineAboveSpouse(int rootSiblingX, int rootSiblingY, int spouseGap) {
        int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;
        Line spouseLine = new Line(rootSiblingX, rootSiblingY - verticalShift, spouseGap, rootSiblingY - verticalShift);
        spouseLine.setType(Line.SIBLINGS);
        lines.add(spouseLine);
    }

    protected int drawChildren(int labelXPosition, int y, Couple spouseCouple) {
        int childrenWidth = 0;
        if (spouseCouple != null) {
            int childrenCount = spouseCouple.getChildren().size();
            if (getConfiguration().isShowChildren() && childrenCount > 0) {
                Line toChildren = new Line(labelXPosition, y, labelXPosition, y + getConfiguration().getAdultImageHeight() / 2 + VERTICAL_GAP / 2);
                toChildren.setType(Line.SIBLINGS);
                lines.add(toChildren);

                int childrenY = y + getConfiguration().getAdultImageHeight() + VERTICAL_GAP;
                childrenWidth = childrenCount * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
                int startXPosition = labelXPosition - childrenWidth / 2;

                for (int i = 0; i < childrenCount; i++) {
                    int childXPosition = startXPosition + getConfiguration().getAdultImageWidth() / 2 + i * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
                    if (i == 0 && childrenCount > 1) {
                        addRoundChildrenLine(childXPosition, childrenY, labelXPosition);
                    } else if (i == childrenCount - 1) {
                        addRoundChildrenLine(childXPosition, childrenY, labelXPosition);
                    } else {
                        addStraightChildrenLine(childXPosition, childrenY, labelXPosition);
                    }
                    drawPerson(childXPosition, childrenY, spouseCouple.getChildren().get(i));
                }
                childrenWidth = childrenWidth / 2;

                if (getConfiguration().isShowHeraldry()) {
                    addChildrenHeraldry(labelXPosition, childrenY, spouseCouple);
                }
            }
        }
        return childrenWidth;
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

        for (Arc arc : arcs) {
            g2.setStroke(new BasicStroke(1));
            g2.drawArc(arc.getLeftUpperX(), arc.getLeftUpperY(), 2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.getStartAngle(), Arc.ANGLE_SIZE);
        }

        for (ImageModel image : images) {
            g2.drawImage(image.getImage(), image.getX() - image.getWidth() / 2, image.getY() - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
        }

        g2.setStroke(new BasicStroke(2));
        for (ResidenceModel residence : residences) {
            g2.setColor(cityRegister.get(residence.getCity()));
            g2.drawRoundRect(residence.getX(), residence.getY(), RESIDENCE_SIZE, RESIDENCE_SIZE, RESIDENCE_SIZE / 2, RESIDENCE_SIZE / 2);
        }

    }

    protected void addLineToParents(int childXPosition, int childYPosition) {
        lines.add(new Line(childXPosition, childYPosition, childXPosition, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP));
    }

    protected void addStraightChildrenLine(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;

        Line vertical = new Line(startX, rootSiblingY - verticalShift, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        lines.add(vertical);
    }

    protected void addRoundChildrenLine(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;

        int startAngle;
        int xRadius;
        int yRadius;
        Arc arc;
        if (startX < rootSiblingX) {
            //older siblings
            yRadius = Arc.RADIUS;
            xRadius = Arc.RADIUS;
            startAngle = 90;
            arc = new Arc(startX, rootSiblingY - verticalShift, startAngle);
            arcs.add(arc);
        } else if (startX > rootSiblingX) {
            //younger siblings
            yRadius = Arc.RADIUS;
            xRadius = -Arc.RADIUS;
            startAngle = 0;
            arc = new Arc(startX - 2 * Arc.RADIUS, rootSiblingY - verticalShift, startAngle);
            arcs.add(arc);
        } else {
            xRadius = 0;
            yRadius = 0;
        }

        addLineAboveSpouse(startX + xRadius, rootSiblingY, rootSiblingX);

        Line vertical = new Line(startX, rootSiblingY - verticalShift + yRadius, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        lines.add(vertical);
    }

    protected void addHeraldry(int childXPosition, int childYPosition, String simpleBirthPlace) {
        if (!simpleBirthPlace.isEmpty()) {
            String birthPlace = Tools.replaceDiacritics(simpleBirthPlace);
            int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;

            InputStream heraldry = ClassLoader.getSystemResourceAsStream("heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    images.add(new ImageModel(heraldryImage, childXPosition, childYPosition - verticalShift, VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    protected void addChildrenHeraldry(int heraldryXPosition, int heraldryYPosition, Couple spouseCouple) {
        String birthPlace = spouseCouple.getChildren().get(0).getSimpleBirthPlace();
        addHeraldry(heraldryXPosition, heraldryYPosition, birthPlace);
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getPreferredSize().width, this.getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }

    public InputStream getStream() {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(getPicture(), "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            os.close();
            return is;
        } catch (IOException ex) {
            return null;
        }
    }

    public TreeMap<String, Color> getCityRegister() {
        return cityRegister;
    }

    protected Configuration getConfiguration() {
        return configuration;
    }

    private void addCityToRegister(String city) {
        if (!cityRegister.containsKey(city)) {
            if (cityRegister.size() >= COLORS.length) {
                cityRegister.put(city, Color.BLACK);
            } else {
                cityRegister.put(city, COLORS[cityRegister.size()]);
            }
        }
    }
}
