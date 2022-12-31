package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.constant.Spaces;
import cz.ambrogenea.familyvision.dto.tree.*;
import cz.ambrogenea.familyvision.enums.Background;
import cz.ambrogenea.familyvision.enums.Diagram;
import cz.ambrogenea.familyvision.enums.LabelShape;
import cz.ambrogenea.familyvision.enums.LabelType;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Fonts;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;
import cz.ambrogenea.familyvision.service.util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreePanel extends JPanel {

    private static final String TITLE_FONT = "Monotype Corsiva";
    private static final int TITLE_SIZE = 50;
    private static final int LABEL_GAP = 4;

    private final TreeModel treeModel;
    private final VisualConfigurationService configuration;
    private final PageSetup page;
    private JTextField title;

    public TreePanel(TreeModel treeModel) {
        this.page = treeModel.getPageSetup();
        this.treeModel = treeModel;
        this.configuration = Config.visual();
        initPanel();
    }

    private void initPanel() {
        if (configuration.getBackground() == Background.WHITE) {
            setBackground(Color.WHITE);
        } else if (configuration.getBackground() == Background.TRANSPARENT) {
            setOpaque(false);
        }
        this.setLayout(null);
        setPreferredSize(new Dimension(page.pictureWidth(), page.pictureHeight()));
        title = new JTextField(treeModel.treeName());
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setFont(new Font(TITLE_FONT, Font.BOLD, TITLE_SIZE));
        title.setBorder(null);
        title.setPreferredSize(new Dimension(page.pictureWidth(), Spaces.TITLE_HEIGHT));
        title.setOpaque(false);

        this.add(title);

        treeModel.persons().forEach(this::drawPerson);

        treeModel.marriages().forEach(marriage -> {
            JComponent dateComponent = createDateComponent(marriage);
            dateComponent.setOpaque(false);
            this.add(dateComponent);
            dateComponent.setBounds(getMarriageDateRect(marriage));
        });

        treeModel.residences().forEach(this::drawResidence);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (configuration.getBackground() == Background.PAPER) {
            addImageBackground(g2);
        }
        g2.setColor(Colors.LINE_COLOR);
        title.setBounds(
                0, Spaces.HORIZONTAL_GAP,
                page.pictureWidth(), Spaces.TITLE_HEIGHT
        );

        final int lineStrokeExtra;
        if (configuration.getAdultFontSize() >= 18) {
            lineStrokeExtra = 1;
        } else {
            lineStrokeExtra = 0;
        }

        int cornerSize = 20;
        treeModel.lines().forEach(line -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
            g2.drawLine(
                    recalculateX(line.startX()),
                    recalculateY(line.startY()),
                    recalculateX(line.endX()),
                    recalculateY(line.endY())
            );
        });

        treeModel.marriages().forEach(marriage -> {
                    Rectangle rect = getMarriageLabelRect(marriage);

                    g2.setColor(Colors.LABEL_BACKGROUND);
                    g2.setStroke(new BasicStroke(lineStrokeExtra + 2));

                    if (configuration.getMarriageLabelShape().equals(LabelShape.OVAL)) {
                        g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                        g2.setColor(Colors.LINE_COLOR);
                        g2.drawRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                    } else if (configuration.getMarriageLabelShape().equals(LabelShape.RECTANGLE)) {
                        g2.fillRect(rect.x, rect.y, rect.width, rect.height);
                        g2.setColor(Colors.LINE_COLOR);
                        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
                    }
                }
        );

        g2.setStroke(new BasicStroke(lineStrokeExtra + 1));
        g2.setColor(Colors.LINE_COLOR);
        treeModel.arcs().forEach(arc -> {
            g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
            g2.drawArc(
                    recalculateX(arc.leftUpperCorner().x()),
                    recalculateY(arc.leftUpperCorner().y()),
                    2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.startAngle(), Arc.ANGLE_SIZE
            );
        });

        treeModel.images().forEach(image -> {
                    try {
                        InputStream heraldry = ClassLoader.getSystemResourceAsStream("heraldry/" + image.imageName() + ".png");

                        if (heraldry != null) {
                            BufferedImage heraldryImage = ImageIO.read(heraldry);
                            int height = Spaces.VERTICAL_GAP / 2;
                            int width = (int) (heraldryImage.getWidth() * (height / (double) heraldryImage.getHeight()));
                            g2.drawImage(heraldryImage,
                                    recalculateX(image.x() - width / 2),
                                    recalculateY(image.y() - height / 2),
                                    width, height, null);
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
        );

        g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
        treeModel.residences().forEach(residence -> {
            g2.setColor(Colors.LABEL_BACKGROUND);
            g2.fillRoundRect(
                    recalculateX(residence.position().x()),
                    recalculateY(residence.position().y()),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);

            g2.setColor(getCityColor(treeModel.cityRegister().indexOf(residence.city())));
            g2.drawRoundRect(
                    recalculateX(residence.position().x()),
                    recalculateY(residence.position().y()),
                    Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);
        });

    }

    private void drawPerson(PersonRecord person) {
        PersonPanel personPanel;
        if (configuration.getDiagram() == Diagram.HERALDRY) {
            personPanel = new VerticalPersonPanel(person);
        } else {
            personPanel = new HorizontalPersonPanel(person);
        }

        personPanel.addMouseAdapter();
        int imageWidth;
        int imageHeight;
        if (person.directLineage()) {
            imageWidth = configuration.getAdultImageWidth();
            imageHeight = configuration.getAdultImageHeight();
        } else {
            imageWidth = configuration.getSiblingImageWidth();
            imageHeight = configuration.getSiblingImageHeight();
        }
        personPanel.setPreferredSize(new Dimension(imageWidth, imageHeight));
        this.add(personPanel);
        personPanel.setBounds(recalculation(
                person.position().x() - imageWidth / 2,
                person.position().y() - imageHeight / 2,
                imageWidth, imageHeight));
    }

    private JComponent createDateComponent(Marriage marriage) {
        JComponent dateComponent;
        if (marriage.labelType() == LabelType.TALL) {
            int index = marriage.date().lastIndexOf(" ");
            if (index != -1) {
                String date = marriage.date().substring(0, index);
                String dateYear = marriage.date().substring(index + 1);
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
                dateComponent = createCenteredLabel(marriage.date());
                dateComponent.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
            }
        } else {
            dateComponent = createCenteredLabel(marriage.date());
            dateComponent.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, configuration.getAdultFontSize()));
        }
        return dateComponent;
    }

    private Rectangle getMarriageDateRect(Marriage marriage) {
        if (marriage.labelType() == LabelType.LONG) {
            return getLongRectangle(marriage);
        } else {
            return getTallRectangle(marriage, Spaces.HORIZ_MARRIAGE_LABEL_WIDTH);
        }
    }

    private Rectangle getMarriageLabelRect(Marriage marriage) {
        if (marriage.labelType() == LabelType.LONG) {
            return getLongRectangle(marriage);
        } else {
            int width;
            if (configuration.getMarriageLabelShape() == LabelShape.OVAL) {
                width = Spaces.HORIZ_MARRIAGE_LABEL_WIDTH - LABEL_GAP;
            } else {
                width = configuration.getAdultImageWidth() + Spaces.HORIZ_MARRIAGE_LABEL_WIDTH;
            }
            return getTallRectangle(marriage, width);
        }
    }

    private Rectangle getTallRectangle(Marriage marriage, int width) {
        return recalculation(
                marriage.position().x() - width / 2,
                marriage.position().y() - Spaces.HORIZ_MARRIAGE_LABEL_HEIGHT / 2,
                width,
                Spaces.HORIZ_MARRIAGE_LABEL_HEIGHT
        );
    }

    private Rectangle getLongRectangle(Marriage marriage) {
        int height = Spaces.VERT_MARRIAGE_LABEL_HEIGHT - LABEL_GAP;
        return recalculation(
                marriage.position().x(),
                marriage.position().y() - height / 2,
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
            g2.drawImage(image, 0, 0, page.pictureWidth(), page.pictureHeight(), null);
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
        return treeModel.treeName();
    }

    private void drawResidence(ResidenceDto residence) {
        if (residence.number() > 0) {
            JLabel number = createCenteredLabel(String.valueOf(residence.number()));
            number.setSize(Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE);
            number.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getAdultFontSize() - 2));
            this.add(number);
            number.setBounds(recalculation(residence.position(), Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE));
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
                recalculateX(position.x()),
                recalculateY(position.y()),
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
        return x - treeModel.pageMaxCoordinates().getMinX();
    }

    private int recalculateY(int y) {
        return y - treeModel.pageMaxCoordinates().getMinY() + Spaces.TITLE_HEIGHT;
    }


}
