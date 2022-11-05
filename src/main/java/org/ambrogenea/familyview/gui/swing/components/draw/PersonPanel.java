package org.ambrogenea.familyview.gui.swing.components.draw;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.gui.swing.constant.Fonts;
import org.ambrogenea.familyview.gui.swing.tools.PersonPanelMouseController;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz
 */
public abstract class PersonPanel extends JPanel {

    protected static final String SPACE = " ";

    protected final PersonRecord person;
    protected final ConfigurationService configuration;
    protected BufferedImage personDiagram;
    protected int fontSize;

    protected JLabel firstName;
    protected JLabel surName;
    protected JLabel occupation;
    protected JLabel birth;
    protected JLabel birthPlace;
    protected JLabel death;
    protected JLabel deathPlace;

    public PersonPanel(PersonRecord person, ConfigurationService config) {
        super(new GridBagLayout());

        this.person = person;
        this.configuration = config;

        initElements();
    }

    protected abstract void addLabels();

    protected abstract void initDateLabels();

    protected abstract void setLabelsOffset();

    public void update() {
        this.removeAll();
        initElements();
        revalidate();
    }

    protected void addEmptyLabel(int yPosition, GridBagConstraints c) {
        c.ipady = 4;
        c.gridy = yPosition;
        add(new JLabel(""), c);
    }

    private void initElements() {
        if (person.isDirectLineage()) {
            fontSize = configuration.getAdultFontSize();
        } else {
            fontSize = configuration.getSiblingFontSize();
        }

        loadPictures();
        initLabels();
        addLabels();
    }

    private void loadPictures() {
        String imagePath;

        if (person.getSex().equals(Sex.MALE)) {
            imagePath = configuration.getAdultManImagePath();
        } else {
            imagePath = configuration.getAdultWomanImagePath();
        }

        try {
            personDiagram = ImageIO.read(ClassLoader.getSystemResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("Image " + imagePath + " can't be open.");
        }
    }

    private void initLabels() {
        occupation = new JLabel("", JLabel.CENTER);
        occupation.setText(person.getOccupation().split(";")[0]);
        occupation.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));

        initNameLabels();
        initDateLabels();

        if (configuration.isShowPlaces() && !birthPlace.getText().isEmpty() || !deathPlace.getText().isEmpty()) {
            setLabelsOffset();
        }
    }

    private void initNameLabels() {
        firstName = new JLabel(" ", JLabel.CENTER);
        surName = new JLabel(" ", JLabel.CENTER);

        if (!person.getFirstName().isEmpty()) {
            firstName.setText(person.getFirstName());
        }

        if (!person.getSurname().isEmpty()) {
            if (configuration.isShowAge() && person.getAge() > -1) {
                surName.setText(person.getSurname().toUpperCase() + " (" + person.getAge() + ")");
            } else {
                surName.setText(person.getSurname().toUpperCase());
            }
        }

        initNamesLabelFont();
    }

    private void initNamesLabelFont() {
        if (firstName.getText().length() > 20) {
            firstName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize - 1));
        } else if (firstName.getText().length() > 15) {
            firstName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize));
        } else {
            firstName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize + 2));
        }

        if (surName.getText().length() > 20) {
            surName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize - 1));
        } else if (surName.getText().length() > 15) {
            surName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize));
        } else {
            surName.setFont(new Font(Fonts.NAMES_FONT, Font.BOLD, fontSize + 2));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int imageWidth;
        int imageHeight;
        if (person.isDirectLineage()) {
            imageWidth = configuration.getAdultImageWidth();
            imageHeight = configuration.getAdultImageHeight();
        } else {
            imageWidth = configuration.getSiblingImageWidth();
            imageHeight = configuration.getSiblingImageHeight();
        }
        if (person.isChild() && configuration.isShowTemple()) {
            g.drawImage(personDiagram, 0, (imageHeight / 7) / 2, imageWidth, (int) (imageHeight / 7.0 * 6), null);
        } else {
            g.drawImage(personDiagram, 0, 0, imageWidth, imageHeight, null);
        }
    }

    protected JPanel creteTempleBox() {
        JPanel templeBox = new JPanel(new GridLayout(1, 3, -1, -1));
        templeBox.setBackground(Color.WHITE);

        JLabel baptism = new JLabel(" KK ", JLabel.CENTER);
        baptism.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        baptism.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel initiatory = new JLabel(" PO ", JLabel.CENTER);
        initiatory.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        initiatory.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel endowment = new JLabel(" OB ", JLabel.CENTER);
        endowment.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        endowment.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        templeBox.add(baptism);
        templeBox.add(initiatory);
        templeBox.add(endowment);

        return templeBox;
    }

    public boolean setAnonymous() {
        if (firstName.isVisible()) {
            firstName.setVisible(false);
            surName.setVisible(false);
            birth.setVisible(false);
            birthPlace.setVisible(false);
            death.setVisible(false);
            deathPlace.setVisible(false);
            occupation.setVisible(false);
            return false;
        } else {
            firstName.setVisible(true);
            surName.setVisible(true);
            birth.setVisible(true);
            birthPlace.setVisible(true);
            death.setVisible(true);
            deathPlace.setVisible(true);
            occupation.setVisible(true);
            return true;
        }
    }

    public void addMouseAdapter() {
        MouseAdapter m = new PersonPanelMouseController(this, configuration, person);
        this.addMouseListener(m);
    }

}
