package org.ambrogenea.familyview.gui.swing.treepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.components.AdultPanel;
import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.components.SiblingPanel;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Arc;
import org.ambrogenea.familyview.gui.swing.model.ImageModel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.Position;
import org.ambrogenea.familyview.gui.swing.model.ResidenceModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.model.Residence;
import org.ambrogenea.familyview.model.enums.LabelShape;
import org.ambrogenea.familyview.model.enums.Sex;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public abstract class CommonTreePanel extends JPanel implements RootFamilyInterface {

    public static final Color LABEL_BACKGROUND = new Color(250, 250, 250);
    public static final Color LINE_COLOR = Color.GRAY;

    protected final Configuration configuration;
    protected final Set<Line> lines;
    protected final Set<Arc> arcs;
    protected final Set<ImageModel> images;
    protected final TreeMap<String, Color> cityRegister;
    protected final ArrayList<ResidenceModel> residences;

    public CommonTreePanel(Configuration configuration) {
        this.configuration = configuration;
        lines = new HashSet<>();
        arcs = new HashSet<>();
        images = new HashSet<>();
        residences = new ArrayList<>();
        cityRegister = new TreeMap<>();
        initPanel();
    }

    private void initPanel() {
//        setBackground(Color.WHITE);
        setOpaque(false);
        this.setLayout(null);
    }

    @Override
    public void drawPerson(Position personCenter, AncestorPerson person) {
        PersonPanel personPanel;
        if (person.isDirectLineage()) {
            personPanel = new AdultPanel(person, configuration);
        } else {
            personPanel = new SiblingPanel(person, configuration);
        }

        personPanel.addMouseAdapter();
        int imageWidth;
        int imageHeight;
        if (person.isDirectLineage()) {
            imageWidth = configuration.getAdultImageWidth();
            imageHeight = configuration.getAdultImageHeight();
        } else {
            imageWidth = configuration.getSiblingImageWidth();
            imageHeight = configuration.getSiblingImageHeight();
        }
        personPanel.setPreferredSize(new Dimension(imageWidth, imageHeight));
        this.add(personPanel);
        personPanel.setBounds(personCenter.getX() - imageWidth / 2, personCenter.getY() - imageHeight / 2, imageWidth, imageHeight);

        if (configuration.isShowResidence()) {
            drawResidence(person, personPanel);
        }
    }

    @Override
    public void drawSiblings(Position rootSiblingPosition, AncestorPerson rootSibling) {
        drawOlderSiblings(rootSiblingPosition, rootSibling);
        drawYoungerSiblings(rootSiblingPosition, rootSibling);
    }

    protected void drawOlderSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = new Position(rootSiblingPosition);
        lineEnd.addY(-(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int olderSiblingCount = rootChild.getOlderSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() - Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = olderSiblingCount - 1; i >= 0; i--) {
            sibling = rootChild.getOlderSiblings().get(i);

            startX = siblingPosition.getX() - getConfiguration().getSiblingImageWidth() - Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());
            if (getConfiguration().isShowSiblingSpouses() && sibling.getSpouse() != null) {
                sibling.getSpouse().setDirectLineage(false);
                siblingPosition.addX(-getConfiguration().getSpouseDistance());
                drawSpouse(siblingPosition, sibling);
            }

            drawPerson(siblingPosition, sibling);
            drawLine(siblingPosition, lineEnd, Line.SIBLINGS);
        }
    }

    protected void drawYoungerSiblings(Position rootSiblingPosition, AncestorPerson rootChild) {
        AncestorPerson sibling;
        int startX;

        Position lineEnd = new Position(rootSiblingPosition);
        lineEnd.addY(-(configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2);

        int youngerSiblingsCount = rootChild.getYoungerSiblings().size();
        Position siblingPosition = new Position(rootSiblingPosition.getX() + Spaces.SIBLINGS_GAP, rootSiblingPosition.getY());

        for (int i = 0; i < youngerSiblingsCount; i++) {
            sibling = rootChild.getYoungerSiblings().get(i);

            startX = siblingPosition.getX() + getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP;
            siblingPosition = new Position(startX, rootSiblingPosition.getY());

            drawPerson(siblingPosition, sibling);
            drawLine(siblingPosition, lineEnd, Line.SIBLINGS);

            if (getConfiguration().isShowSiblingSpouses() && sibling.getSpouse() != null) {
                sibling.getSpouse().setDirectLineage(false);
                drawSpouse(siblingPosition, sibling);
                siblingPosition.addX(getConfiguration().getSpouseDistance());
            }
        }
    }

    protected void drawLine(Position start, Position end, int lineType) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {

            Line straigthLine = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            straigthLine.setType(lineType);
            lines.add(straigthLine);

        } else {

            Line horizontal;
            Line vertical;
            if (getConfiguration().getLabelShape().equals(LabelShape.RECTANGLE)) {

                horizontal = new Line(start.getX(), start.getY(), end.getX(), start.getY());
                vertical = new Line(end.getX(), start.getY(), end.getX(), end.getY());
                lines.add(horizontal);
                lines.add(vertical);

            } else if (getConfiguration().getLabelShape().equals(LabelShape.OVAL)) {

                Arc arc;
                if (start.getX() < end.getX()) {
                    if (start.getY() < end.getY()) {//ancestors
                        horizontal = new Line(start.getX(), start.getY(), end.getX() - Arc.RADIUS, start.getY());
                        vertical = new Line(end.getX(), start.getY() - Arc.RADIUS, end.getX(), end.getY());
                        arc = new Arc(new Position(end.getX() - 2 * Arc.RADIUS, start.getY() - 2 * Arc.RADIUS), -90);
                    } else {//children
                        horizontal = new Line(start.getX() + Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX(), end.getY()), 90);
                    }
                } else {
                    if (start.getY() < end.getY()) {
                        horizontal = new Line(start.getX(), start.getY(), end.getX() + Arc.RADIUS, start.getY());
                        vertical = new Line(end.getX(), start.getY() - Arc.RADIUS, end.getX(), end.getY());
                        arc = new Arc(new Position(end.getX(), start.getY() - 2 * Arc.RADIUS), 180);
                    } else {
                        horizontal = new Line(start.getX() - Arc.RADIUS, end.getY(), end.getX(), end.getY());
                        vertical = new Line(start.getX(), start.getY(), start.getX(), end.getY() + Arc.RADIUS);
                        arc = new Arc(new Position(start.getX() - 2 * Arc.RADIUS, end.getY()), 0);
                    }
                }

                arc.setType(lineType);
                horizontal.setType(lineType);
                vertical.setType(lineType);

                arcs.add(arc);
                lines.add(horizontal);
                lines.add(vertical);
            }
        }
    }

    protected void addHeraldry(Position childPosition, String simpleBirthPlace) {
        if (!simpleBirthPlace.isEmpty()) {
            String birthPlace = Tools.replaceDiacritics(simpleBirthPlace);

            InputStream heraldry = ClassLoader.getSystemResourceAsStream("heraldry/" + birthPlace + ".png");
            if (heraldry != null) {
                try {
                    BufferedImage heraldryImage = ImageIO.read(heraldry);
                    Position heraldryPosition = new Position(childPosition);
                    heraldryPosition.addY(-(configuration.getSiblingImageHeight() + Spaces.VERTICAL_GAP) / 2);

                    images.add(new ImageModel(heraldryImage, heraldryPosition, Spaces.VERTICAL_GAP / 2));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    protected void addChildrenHeraldry(Position childPosition, Couple spouseCouple) {
        String birthPlace = spouseCouple.getChildren().get(0).getSimpleBirthPlace();
        addHeraldry(childPosition, birthPlace);
    }

    public BufferedImage getPicture() {
        BufferedImage image = new BufferedImage(this.getPreferredSize().width, this.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        this.paint(g);
        return image;
    }

    public InputStream getStream() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(getPicture(), "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            os.close();
            return is;
        } catch (IOException e) {
            System.out.println("Image is not possible to convert: " + e.getMessage());
        }
        return null;
    }

    protected void drawResidence(final Person person, JPanel personPanel) {
        Residence residence;
        JLabel number;
        int x;
        int y;
        for (int i = 0; i < person.getResidenceList().size(); i++) {
            residence = person.getResidenceList().get(i);
            if (!residence.getCity().isEmpty()) {

                if (person.getSex().equals(Sex.MALE)) {
                    x = personPanel.getX() - Spaces.RESIDENCE_SIZE - Spaces.HORIZONTAL_GAP / 2;
                } else {
                    x = personPanel.getX() + configuration.getAdultImageWidth() + Spaces.HORIZONTAL_GAP / 2;
                }

                y = personPanel.getY() + i * (Spaces.RESIDENCE_SIZE + 5);
                residences.add(new ResidenceModel(x, y, residence));
                addCityToRegister(residence.getCity());
                if (residence.getNumber() > 0) {
                    number = new JLabel("" + residence.getNumber(), JLabel.CENTER);
                    number.setSize(Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
                    number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getAdultFontSize() - 2));
                    this.add(number);
                    number.setBounds(x, y, Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
                }

            }
        }
    }

    private void addCityToRegister(String city) {
        if (!cityRegister.containsKey(city)) {
            if (cityRegister.size() >= Colors.getColors().length) {
                cityRegister.put(city, Color.BLACK);
            } else {
                cityRegister.put(city, Colors.getColors()[cityRegister.size()]);
            }
        }
    }

    public TreeMap<String, Color> getCityRegister() {
        return cityRegister;
    }

    protected Configuration getConfiguration() {
        return configuration;
    }

}
