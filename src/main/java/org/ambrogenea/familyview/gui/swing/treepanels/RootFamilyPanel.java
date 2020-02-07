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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.model.ImageModel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.ResidenceModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.Information;
import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.model.Residence;
import org.ambrogenea.familyview.model.utils.FileIO;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends JPanel {

    private static final Color[] COLORS = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.LIGHT_GRAY, Color.ORANGE, Color.CYAN, Color.PINK, Color.MAGENTA};
    public static final int MARRIAGE_LABEL_WIDTH = 150;

    public static final int HORIZONTAL_GAP = 20;
    public static final int SIBLINGS_GAP = 2 * HORIZONTAL_GAP;
    public static final int VERTICAL_GAP = 100;
    public static final int LABEL_HEIGHT = 30;
    public static final int RESIDENCE_SIZE = 25;

    protected final ArrayList<Line> lines;
    protected final ArrayList<ImageModel> images;
    protected final ArrayList<ResidenceModel> residences;
    protected final AncestorPerson personModel;
    protected final Configuration configuration;
    private final HashMap<String, Color> cityRegister;

    public RootFamilyPanel(AncestorPerson model, Configuration config) {
        this.personModel = model;
        this.configuration = config;
        lines = new ArrayList<>();
        images = new ArrayList<>();
        residences = new ArrayList<>();
        cityRegister = new HashMap<>();
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
            if (person.getSex().equals(Information.VALUE_MALE)) {
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

    protected int drawMother(int childXPosition, int y, AncestorPerson child) {
        int motherXPosition;
        if (child.getFather() != null) {
            motherXPosition = childXPosition + (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
            drawLabel(childXPosition - MARRIAGE_LABEL_WIDTH / 2, childXPosition + MARRIAGE_LABEL_WIDTH / 2, y, child.getParents().getMarriageDate());
        } else {
            motherXPosition = childXPosition;
        }

        if (child.getMother() != null) {
            drawPerson(motherXPosition, y, child.getMother());
        }
        return motherXPosition;
    }

    protected void drawSpouse(int husbandX, int motherY, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseXPosition = husbandX + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
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
                spouseXPosition = spouseXPosition + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);

                drawPerson(spouseXPosition, y, person.getSpouse(index));
                labelXPosition = startXPosition + (getConfiguration().getAdultImageWidth() / 2);
                drawLabel(labelXPosition, labelXPosition + MARRIAGE_LABEL_WIDTH, y, person.getSpouseCouple(index).getMarriageDate());
            }
        }
        return spouseXPosition;
    }

    protected int drawAllSpousesWithKids(int husbandXPosition, int y, AncestorPerson person) {
        int spouseXPosition = husbandXPosition;
        if (person.getSpouse() != null) {
            int labelXPosition;
            int childrenShift = -(person.getChildrenCount(0) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) / 2) + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) - SIBLINGS_GAP;

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                husbandXPosition = spouseXPosition;
                int childrenWidth = Math.max(1, person.getChildrenCount(index)) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) / 2 - (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
                spouseXPosition = spouseXPosition + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) + childrenShift + childrenWidth + SIBLINGS_GAP;

                drawPerson(spouseXPosition, y, person.getSpouse(index));
                labelXPosition = spouseXPosition - (getConfiguration().getAdultImageWidth() / 2 + MARRIAGE_LABEL_WIDTH / 2);
                drawLabel(husbandXPosition + getConfiguration().getAdultImageWidth() / 2, spouseXPosition - getConfiguration().getAdultImageWidth() / 2, y, person.getSpouseCouple(index).getMarriageDate());
                childrenShift = drawChildren(labelXPosition, y, person.getSpouseCouple(index));
                childrenShift = Math.max((getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) / 2, childrenShift);
            }
        }
        return spouseXPosition;
    }

    protected void drawLabel(int startX, int endX, int centerY, String text) {
        if (text != null && !text.isEmpty()) {
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
        if (text != null && !text.isEmpty()) {
            int wideMarriageLabel = getConfiguration().getWideMarriageLabel();
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, getConfiguration().getFontSize() - 1));
            date.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.BLACK));
            date.setOpaque(true);
            date.setBackground(new Color(250, 250, 250));
            this.add(date);
            date.setBounds(centerX - wideMarriageLabel / 2, centerY - LABEL_HEIGHT, wideMarriageLabel, LABEL_HEIGHT);
        }
    }

    protected void drawSiblings(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;
        int olderSiblingCount = rootChild.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int startX;
        for (int i = 0; i < rootChild.getYoungerSiblings().size(); i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = rootSiblingX + HORIZONTAL_GAP + (i + 1) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

    protected void drawSiblingsAroundMother(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;
        int olderSiblingCount = rootChild.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int startX;
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
        }

        for (int i = 0; i < rootChild.getYoungerSiblings().size(); i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = rootSiblingX + spouseGap + HORIZONTAL_GAP + (i + 1) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

    protected void drawSiblingsAroundFather(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild) {
        Person sibling;
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH);
        }

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        int startX;
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = rootSiblingX - spouseGap - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        for (int i = 0; i < rootChild.getYoungerSiblings().size(); i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = rootSiblingX + HORIZONTAL_GAP + (i + 1) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
    }

    protected void drawSiblingsAroundWifes(int rootSiblingX, int rootSiblingY, AncestorPerson rootChild, int lastSpouseX) {
        Person sibling;
        int olderSiblingCount = rootChild.getOlderSiblings().size();
        for (int i = 0; i < olderSiblingCount; i++) {
            sibling = rootChild.getOlderSiblings().get(i);

            int startX = rootSiblingX - (olderSiblingCount - i) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP) - HORIZONTAL_GAP;
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }

        int startX;
        int spouseGap;
        if (!rootChild.getSpouseID().isEmpty() && lastSpouseX == 0) {
            spouseGap = rootSiblingX + (getConfiguration().getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) * rootChild.getSpouseCouples().size();
        } else {
            spouseGap = lastSpouseX;
        }

        for (int i = 0; i < rootChild.getYoungerSiblings().size(); i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = spouseGap + HORIZONTAL_GAP + (i + 1) * (getConfiguration().getAdultImageWidth() + HORIZONTAL_GAP);
            drawPerson(startX, rootSiblingY, sibling);
            addSiblingsToParents(startX, rootSiblingY, rootSiblingX);
        }
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
                    addSiblingsToParents(childXPosition, childrenY, labelXPosition);
                    drawPerson(childXPosition, childrenY, spouseCouple.getChildren().get(i));
                }
                childrenWidth = childrenWidth / 2;

                if (getConfiguration().isShowHeraldry()) {
                    addChildrenHeraldry(labelXPosition, y + getConfiguration().getAdultImageHeight() / 2 + VERTICAL_GAP / 2, spouseCouple);
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
        int horizontalShift = (configuration.getAdultImageWidth() + MARRIAGE_LABEL_WIDTH) / 2;
        lines.add(new Line(childXPosition, childYPosition, childXPosition, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP));
//        lines.add(new Line(childXPosition - horizontalShift, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP, childXPosition + horizontalShift, childYPosition - configuration.getAdultImageHeight() - VERTICAL_GAP));
    }

    protected void addSiblingsToParents(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;
        lines.add(new Line(startX, rootSiblingY - verticalShift, rootSiblingX, rootSiblingY - verticalShift));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
        lines.add(new Line(startX, rootSiblingY - verticalShift, startX, rootSiblingY));
        lines.get(lines.size() - 1).setType(Line.SIBLINGS);
    }

    protected void addHeraldry(int childXPosition, int childYPosition, AncestorPerson person) {
        String birthPlace = person.getSimpleBirthPlace();
        if (!birthPlace.isEmpty()) {
            birthPlace = Tools.replaceDiacritics(birthPlace);
            int verticalShift = (configuration.getAdultImageHeight() + VERTICAL_GAP) / 2;

            File heraldry = FileIO.loadFileFromResources("/heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    images.add(new ImageModel(heraldryImage, childXPosition, childYPosition - verticalShift, VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println("Heraldry image " + heraldry.getAbsolutePath() + " cannot be open." + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

    }

    protected void addChildrenHeraldry(int heraldryXPosition, int heraldryYPosition, Couple spouseCouple) {
        String birthPlace = spouseCouple.getChildren().get(0).getSimpleBirthPlace();
        if (!birthPlace.isEmpty()) {
            birthPlace = Tools.replaceDiacritics(birthPlace);
            File heraldry = FileIO.loadFileFromResources("/heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    images.add(new ImageModel(heraldryImage, heraldryXPosition, heraldryYPosition, VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println("Heraldry image " + heraldry.getAbsolutePath() + " cannot be open." + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
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

    public HashMap<String, Color> getCityRegister() {
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
