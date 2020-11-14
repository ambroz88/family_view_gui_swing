package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.tree.Arc;
import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.dto.tree.ResidenceDto;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.gui.swing.components.AdultPanel;
import org.ambrogenea.familyview.gui.swing.components.PersonPanel;
import org.ambrogenea.familyview.gui.swing.components.SiblingPanel;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreePanel extends JPanel {

    private final TreeModel treeModel;
    private final ConfigurationService configuration;

    public TreePanel(TreeModel treeModel, ConfigurationService configuration) {
        this.treeModel = treeModel;
        this.configuration = configuration;
        initPanel();
    }

    private void initPanel() {
//        setBackground(Color.WHITE);
        setOpaque(false);
        this.setLayout(null);

        treeModel.getPersons().forEach(person -> drawPerson(person));

        if (configuration.isShowMarriage()) {
            int labelHeight = configuration.getMarriageLabelHeight();
            treeModel.getMarriages().forEach(marriage -> {
                JLabel date = new JLabel(marriage.getDate(), JLabel.CENTER);
                date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getAdultFontSize() - 1));
                date.setOpaque(false);
                this.add(date);
                date.setBounds(marriage.getPosition().getX(), marriage.getPosition().getY(),
                        marriage.getLength(), labelHeight);
            });
        }

        treeModel.getResidences().stream().forEach(residence -> {
            drawResidence(residence);
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Colors.LINE_COLOR);

        final int lineStrokeExtra;
        if (configuration.getAdultFontSize() >= 18) {
            lineStrokeExtra = 1;
        } else {
            lineStrokeExtra = 0;
        }

        int cornerSize = 20;
        treeModel.getLines().forEach(line -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + line.getRelation().getInt()));
            g2.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        });

        int labelHeight = configuration.getMarriageLabelHeight();

        treeModel.getMarriages().forEach(marriage -> {
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
        );

        g2.setStroke(new BasicStroke(lineStrokeExtra + 1));
        g2.setColor(Colors.LINE_COLOR);
        treeModel.getArcs().forEach(arc -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + arc.getRelation().getInt()));
            g2.drawArc(arc.getLeftUpperCorner().getX(), arc.getLeftUpperCorner().getY(), 2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.getStartAngle(), Arc.ANGLE_SIZE);
        });

        treeModel.getImages().forEach(image -> {
            g2.drawImage(image.getImage(), image.getX() - image.getWidth() / 2, image.getY() - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
        });

        g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
        treeModel.getResidences().stream().forEach(residence -> {
            g2.setColor(getCityColor(treeModel.getCityRegister().indexOf(residence.getCity())));
            g2.drawRoundRect(residence.getPosition().getX(), residence.getPosition().getY(),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);
        });

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
        personPanel.setBounds(person.getPosition().getX() - imageWidth / 2, person.getPosition().getY() - imageHeight / 2,
                imageWidth, imageHeight);
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

    public String getTreeName() {
        return treeModel.getTreeName();
    }

    private void drawResidence(ResidenceDto residence) {
        if (residence.getNumber() > 0) {
            JLabel number = new JLabel("" + residence.getNumber(), JLabel.CENTER);
            number.setSize(Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
            number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getAdultFontSize() - 2));
            this.add(number);
            number.setBounds(residence.getPosition().getX(), residence.getPosition().getY(),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
        }
    }

    private Color getCityColor(int colorIndex) {
        if (colorIndex >= Colors.getColors().length) {
            return Color.BLACK;
        } else {
            return Colors.getColors()[colorIndex];
        }
    }

}
