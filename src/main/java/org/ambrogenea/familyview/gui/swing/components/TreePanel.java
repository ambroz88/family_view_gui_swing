package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.domain.Arc;
import org.ambrogenea.familyview.domain.ImageModel;
import org.ambrogenea.familyview.domain.Line;
import org.ambrogenea.familyview.domain.Marriage;
import org.ambrogenea.familyview.domain.PersonRecord;
import org.ambrogenea.familyview.domain.ResidenceModel;
import org.ambrogenea.familyview.domain.TreeModel;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.model.Residence;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreePanel extends JPanel {

    private final TreeModel treeModel;
    private final ConfigurationService configuration;

    protected final TreeMap<String, Color> cityRegister;
    protected final ArrayList<ResidenceModel> residences;

    public TreePanel(TreeModel treeModel, ConfigurationService configuration) {
        this.treeModel = treeModel;
        this.configuration = configuration;
        residences = new ArrayList<>();
        cityRegister = new TreeMap<>();
        initPanel();
    }

    private void initPanel() {
//        setBackground(Color.WHITE);
        setOpaque(false);
        this.setLayout(null);

        for (PersonRecord person : treeModel.getPersons()) {
            drawPerson(person);
        }

        if (configuration.isShowMarriage()) {
            int labelHeight = configuration.getMarriageLabelHeight();
            for (Marriage marriage : treeModel.getMarriages()) {
                JLabel date = new JLabel(marriage.getDate(), JLabel.CENTER);
                date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getAdultFontSize() - 1));
                date.setOpaque(false);
                this.add(date);
                date.setBounds(marriage.getPosition().getX(), marriage.getPosition().getY(), marriage.getLength(), labelHeight);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Colors.LINE_COLOR);

        int lineStrokeExtra = 0;
        if (configuration.getAdultFontSize() >= 18) {
            lineStrokeExtra = 1;
        }

        int cornerSize = 20;
        for (Line line : treeModel.getLines()) {
            g2.setStroke(new BasicStroke(lineStrokeExtra + line.getType()));
            g2.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }

        if (configuration.isShowMarriage()) {
            int labelHeight = configuration.getMarriageLabelHeight();

            for (Marriage marriage : treeModel.getMarriages()) {
                Rectangle rect = new Rectangle(marriage.getPosition().getX(), marriage.getPosition().getY(), marriage.getLength(), labelHeight);

                g2.setColor(Colors.LABEL_BACKGROUND);
                g2.setStroke(new BasicStroke(lineStrokeExtra + 2));

                if (configuration.getLabelShape().equals(LabelShape.OVAL)) {
                    g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                    g2.setColor(Colors.LINE_COLOR);
                    g2.drawRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                } else if (configuration.getLabelShape().equals(LabelShape.RECTANGLE)) {
                    g2.fillRect(rect.x - Spaces.SIBLINGS_GAP, rect.y, rect.width + 2 * Spaces.SIBLINGS_GAP, rect.height);
                    g2.setColor(Colors.LINE_COLOR);
                    g2.drawRect(rect.x - Spaces.SIBLINGS_GAP, rect.y, rect.width + 2 * Spaces.SIBLINGS_GAP, rect.height);
                }
            }
        }

        g2.setStroke(new BasicStroke(lineStrokeExtra + 1));
        g2.setColor(Colors.LINE_COLOR);
        for (Arc arc : treeModel.getArcs()) {
            g2.drawArc(arc.getLeftUpperCorner().getX(), arc.getLeftUpperCorner().getY(), 2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.getStartAngle(), Arc.ANGLE_SIZE);
        }

        for (ImageModel image : treeModel.getImages()) {
            g2.drawImage(image.getImage(), image.getX() - image.getWidth() / 2, image.getY() - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
        }

        g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
        for (ResidenceModel residence : residences) {
            g2.setColor(cityRegister.get(residence.getCity()));
            g2.drawRoundRect(residence.getX(), residence.getY(), Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);
        }

    }

    private void drawPerson(PersonRecord person) {
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
        personPanel.setBounds(person.getPosition().getX() - imageWidth / 2, person.getPosition().getY() - imageHeight / 2, imageWidth, imageHeight);

        if (configuration.isShowResidence()) {
            drawResidence(person, personPanel);
        }
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

    private void drawResidence(final PersonRecord person, JPanel personPanel) {
        Residence residence;
        JLabel number;
        int x;
        int y;
        for (int i = 0; i < person.getResidences().size(); i++) {
            residence = person.getResidences().get(i);
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

}
