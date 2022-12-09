package org.ambrogenea.familyview.gui.swing.components.draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.tree.*;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.LabelType;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
import org.ambrogenea.familyview.gui.swing.constant.Fonts;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreePanel extends JPanel {

    private static final String TITLE_FONT = "Monotype Corsiva";
    private static final int TITLE_SIZE = 50;
    private static final int LABEL_GAP = 4;

    private final TreeModel treeModel;
    private final ConfigurationService configuration;
    private final PageSetup page;
    private JTextField title;

    public TreePanel(TreeModel treeModel, ConfigurationService configuration) {
        this.page = treeModel.getPageSetup(configuration);
        this.treeModel = treeModel;
        this.configuration = configuration;
        initPanel();
    }

    private void initPanel() {
//        setBackground(Color.WHITE);
        setOpaque(false);
        this.setLayout(null);
        setPreferredSize(new Dimension(page.getWidth(), page.getHeight()));
        title = new JTextField(treeModel.getTreeName());
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setFont(new Font(TITLE_FONT, Font.BOLD, TITLE_SIZE));
        title.setBorder(null);
        title.setPreferredSize(new Dimension(page.getWidth(), Spaces.TITLE_HEIGHT));
        title.setOpaque(false);

        this.add(title);

        treeModel.getPersons().forEach(this::drawPerson);

        if (configuration.isShowMarriage()) {
            treeModel.getMarriages().forEach(marriage -> {
                JComponent dateComponent = createDateComponent(marriage);
                dateComponent.setOpaque(false);
                this.add(dateComponent);
                dateComponent.setBounds(getMarriageDateRect(marriage));
            });
        }

        treeModel.getResidences().forEach(this::drawResidence);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        addImageBackground(g2);
        g2.setColor(Colors.LINE_COLOR);
        title.setBounds(
                0, Spaces.HORIZONTAL_GAP,
                page.getWidth(), Spaces.TITLE_HEIGHT
        );

        final int lineStrokeExtra;
        if (configuration.getAdultFontSize() >= 18) {
            lineStrokeExtra = 1;
        } else {
            lineStrokeExtra = 0;
        }

        int cornerSize = 20;
        treeModel.getLines().forEach(line -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
            g2.drawLine(
                    recalculateX(line.getStartX()),
                    recalculateY(line.getStartY()),
                    recalculateX(line.getEndX()),
                    recalculateY(line.getEndY())
            );
        });

        treeModel.getMarriages().forEach(marriage -> {
                    Rectangle rect = getMarriageLabelRect(marriage);

                    g2.setColor(Colors.LABEL_BACKGROUND);
                    g2.setStroke(new BasicStroke(lineStrokeExtra + 2));

                    if (configuration.getLabelShape().equals(LabelShape.OVAL)) {
                        g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                        g2.setColor(Colors.LINE_COLOR);
                        g2.drawRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                    } else if (configuration.getLabelShape().equals(LabelShape.RECTANGLE)) {
                        g2.fillRect(rect.x, rect.y, rect.width, rect.height);
                        g2.setColor(Colors.LINE_COLOR);
                        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
                    }
                }
        );

        g2.setStroke(new BasicStroke(lineStrokeExtra + 1));
        g2.setColor(Colors.LINE_COLOR);
        treeModel.getArcs().forEach(arc -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
            g2.drawArc(
                    recalculateX(arc.getLeftUpperCorner().getX()),
                    recalculateY(arc.getLeftUpperCorner().getY()),
                    2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.getStartAngle(), Arc.ANGLE_SIZE
            );
        });

        treeModel.getImages().forEach(image ->
                g2.drawImage(image.getImage(),
                        recalculateX(image.getX() - image.getWidth() / 2),
                        recalculateY(image.getY() - image.getHeight() / 2),
                        image.getWidth(), image.getHeight(), null)
        );

        g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
        treeModel.getResidences().forEach(residence -> {
            g2.setColor(Colors.LABEL_BACKGROUND);
            g2.fillRoundRect(
                    recalculateX(residence.getPosition().getX()),
                    recalculateY(residence.getPosition().getY()),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);

            g2.setColor(getCityColor(treeModel.getCityRegister().indexOf(residence.getCity())));
            g2.drawRoundRect(
                    recalculateX(residence.getPosition().getX()),
                    recalculateY(residence.getPosition().getY()),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);
        });

    }

    private void drawPerson(PersonRecord person) {
        PersonPanel personPanel;
        if (configuration.getAdultDiagram() == Diagrams.HERALDRY) {
            personPanel = new VerticalPersonPanel(person, configuration);
        } else {
            personPanel = new HorizontalPersonPanel(person, configuration);
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
        personPanel.setBounds(recalculation(
                person.getPosition().getX() - imageWidth / 2,
                person.getPosition().getY() - imageHeight / 2,
                imageWidth, imageHeight));
    }

    private JComponent createDateComponent(Marriage marriage) {
        JComponent dateComponent;
        if (marriage.getLabelType() == LabelType.TALL) {
            int index = marriage.getDate().lastIndexOf(" ");
            if (index != -1) {
                String date = marriage.getDate().substring(0, index);
                String dateYear = marriage.getDate().substring(index + 1);
                dateComponent = new JPanel();
                dateComponent.setLayout(new GridLayout(2, 1, 0, 2));
                final JLabel dateLabel = createCenteredLabel(date);
                dateLabel.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
                dateLabel.setVerticalAlignment(JLabel.BOTTOM);
                dateComponent.add(dateLabel);
                final JLabel yearLabel = createCenteredLabel(dateYear);
                yearLabel.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
                yearLabel.setVerticalAlignment(JLabel.TOP);
                dateComponent.add(yearLabel);
            } else {
                dateComponent = createCenteredLabel(marriage.getDate());
                dateComponent.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
            }
        } else {
            dateComponent = createCenteredLabel(marriage.getDate());
            dateComponent.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
        }
        return dateComponent;
    }

    private Rectangle getMarriageDateRect(Marriage marriage) {
        if (marriage.getLabelType() == LabelType.LONG) {
            return getLongRectangle(marriage);
        } else {
            return getTallRectangle(marriage, Spaces.HORIZ_MARRIAGE_LABEL_WIDTH);
        }
    }

    private Rectangle getMarriageLabelRect(Marriage marriage) {
        if (marriage.getLabelType() == LabelType.LONG) {
            return getLongRectangle(marriage);
        } else {
            int width;
            if (configuration.getLabelShape() == LabelShape.OVAL) {
                width = Spaces.HORIZ_MARRIAGE_LABEL_WIDTH - LABEL_GAP;
            } else {
                width = configuration.getAdultImageWidth() + Spaces.HORIZ_MARRIAGE_LABEL_WIDTH;
            }
            return getTallRectangle(marriage, width);
        }
    }

    private Rectangle getTallRectangle(Marriage marriage, int width) {
        return recalculation(
                marriage.getPosition().getX() - width / 2,
                marriage.getPosition().getY() - Spaces.HORIZ_MARRIAGE_LABEL_HEIGHT / 2,
                width,
                Spaces.HORIZ_MARRIAGE_LABEL_HEIGHT
        );
    }

    private Rectangle getLongRectangle(Marriage marriage) {
        int height = Spaces.VERT_MARRIAGE_LABEL_HEIGHT - LABEL_GAP;
        return recalculation(
                marriage.getPosition().getX(),
                marriage.getPosition().getY() - height / 2,
                Math.max(Spaces.MIN_VERT_MARRIAGE_LABEL_WIDTH, (int) (configuration.getAdultImageWidth() / 3.0 * 2)),
                height
        );
    }

    private void addImageBackground(Graphics2D g2) {
        try {
            BufferedImage image;
            if (this.getWidth() / (double) this.getHeight() > 2) {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/pergamen-wide.jpg"));
            } else {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/pergamen-landscape.jpg"));
            }
            g2.drawImage(image, 0, 0, page.getWidth(), page.getHeight(), null);
        } catch (IOException ex) {
            Logger.getLogger(TreeScrollPanel.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getTreeName() {
        return treeModel.getTreeName();
    }

    private void drawResidence(ResidenceDto residence) {
        if (residence.getNumber() > 0) {
            JLabel number = createCenteredLabel(String.valueOf(residence.getNumber()));
            number.setSize(Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
            number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getAdultFontSize() - 2));
            this.add(number);
            number.setBounds(recalculation(residence.getPosition(), Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE));
        }
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setOpaque(false);
        return label;
    }

    private Color getCityColor(int colorIndex) {
        if (colorIndex >= Colors.getColors().length) {
            return Color.BLACK;
        } else {
            return Colors.getColors()[colorIndex];
        }
    }

    private Rectangle recalculation(Position position, int width, int height) {
        return new Rectangle(
                recalculateX(position.getX()),
                recalculateY(position.getY()),
                width,
                height
        );
    }

    private Rectangle recalculation(int x, int y, int width, int height) {
        return new Rectangle(
                recalculateX(x),
                recalculateY(y),
                width,
                height
        );
    }

    private int recalculateX(int x) {
        return x - treeModel.getPageMaxCoordinates().getMinX();
    }

    private int recalculateY(int y) {
        return y - treeModel.getPageMaxCoordinates().getMinY() + Spaces.TITLE_HEIGHT;
    }


}
