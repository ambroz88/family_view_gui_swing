package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.gui.swing.tools.PersonPanelMouseController;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Information;
import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.model.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanel extends JPanel {

    private static final String SPACE = "  ";

    private final Person person;
    private BufferedImage personDiagram;
    protected final Configuration configuration;

    private JLabel firstName;
    private JLabel surName;
    private JLabel blankSpace;
    private JLabel birth;
    private JLabel birthPlace;
    private JLabel blankSpace2;
    private JLabel death;
    private JLabel deathPlace;

    public PersonPanel(Person person, Configuration config) {
        super(new GridBagLayout());

        this.person = person;
        this.configuration = config;

        loadPictures();
        initLabels();
        addLabels();
        MouseAdapter m = new PersonPanelMouseController(this, config, person);
        this.addMouseListener(m);
    }

    private void loadPictures() {
        try {
            String imagePath;
            if (person.getSex().equals(Information.VALUE_MALE)) {
                imagePath = configuration.getAdultManImagePath();
            } else {
                imagePath = configuration.getAdultWomanImagePath();
            }
            personDiagram = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLabels() {
        firstName = new JLabel(" ", JLabel.CENTER);
        surName = new JLabel(" ", JLabel.CENTER);
        blankSpace = new JLabel("", JLabel.CENTER);
        birth = new JLabel(" ", JLabel.RIGHT);
        birthPlace = new JLabel("", JLabel.LEFT);
        blankSpace2 = new JLabel("", JLabel.CENTER);
        death = new JLabel(" ", JLabel.RIGHT);
        deathPlace = new JLabel("", JLabel.LEFT);

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

        if (!person.getBirthDate().isEmpty()) {
            birth.setText("\u2605 " + person.getBirthDateCzech());
            if (configuration.isShowPlaces() && !person.getBirthPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlace.setText(SPACE + Tools.cityShortVersion(person.getSimpleBirthPlace()));
                } else {
                    birthPlace.setText(SPACE + person.getSimpleBirthPlace());
                }
            }
        }

        if (!person.getDeathDate().isEmpty()) {
            death.setText("\u271D " + person.getDeathDateCzech());
            if (configuration.isShowPlaces() && !person.getDeathPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    deathPlace.setText(SPACE + Tools.cityShortVersion(person.getSimpleDeathPlace()));
                } else {
                    deathPlace.setText(SPACE + person.getSimpleDeathPlace());
                }
            }
        }

        firstName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getFontSize() + 1));
        surName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getFontSize() + 1));
        birth.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize()));
        birthPlace.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize() - 1));
        death.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize()));
        deathPlace.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize() - 1));

        if (configuration.isShowPlaces()) {
            if (!birthPlace.getText().isEmpty() || !deathPlace.getText().isEmpty()) {
                int shift = 12;
                birth.setPreferredSize(new Dimension(configuration.getAdultImageWidth() / 2 + shift, birth.getPreferredSize().height));
                birthPlace.setPreferredSize(new Dimension(configuration.getAdultImageWidth() / 2 - shift, birth.getPreferredSize().height));
                death.setPreferredSize(new Dimension(configuration.getAdultImageWidth() / 2 + shift, birth.getPreferredSize().height));
                deathPlace.setPreferredSize(new Dimension(configuration.getAdultImageWidth() / 2 - shift, birth.getPreferredSize().height));
            }
        }
    }

    private void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = configuration.getAdultTopOffset();
        c.weighty = 5;
        c.gridwidth = 2;
        add(new JLabel(""), c);

        c.gridy = 1;
        c.ipady = 0;
        c.weighty = 0;
        add(firstName, c);
        c.gridy = 2;
        c.ipady = 4;
        add(surName, c);

        c.gridy = 3;
        c.ipady = 4;
        add(blankSpace, c);

        if (configuration.isShowPlaces()) {
            c.gridwidth = 1;
        }
        c.gridy = 4;
        c.ipady = 0;
        c.weighty = 0;
        add(birth, c);

        c.gridy = 7;
        add(death, c);

        if (configuration.isShowPlaces()) {
            c.gridy = 4;
            c.gridx = 1;
            add(birthPlace, c);

            c.gridy = 7;
            add(deathPlace, c);

            c.gridx = 0;
            c.gridwidth = 2;
        }

        if (configuration.isShowTemple() && !person.isChild() && !person.isLiving()) {
            c.gridy = 8;
            c.ipady = 8;
            add(blankSpace2, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 9;
            add(templeBox, c);
        }

        c.gridy = 10;
        c.weighty = 5;
        c.ipady = configuration.getAdultBottomOffset();
        add(new JLabel(""), c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (person.isChild() && configuration.isShowTemple()) {
            g.drawImage(personDiagram, 0, (configuration.getAdultImageHeight() / 7) / 2, configuration.getAdultImageWidth(), (int) (configuration.getAdultImageHeight() / 7.0 * 6), null);
        } else {
            g.drawImage(personDiagram, 0, 0, configuration.getAdultImageWidth(), configuration.getAdultImageHeight(), null);
        }
    }

    private JPanel creteTempleBox() {
        JPanel templeBox = new JPanel(new GridLayout(1, 3, -1, -1));
        templeBox.setBackground(Color.WHITE);

        JLabel baptism = new JLabel(" KK ", JLabel.CENTER);
        baptism.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize()));
        baptism.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel initiatory = new JLabel(" PO ", JLabel.CENTER);
        initiatory.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize()));
        initiatory.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel endowment = new JLabel(" OB ", JLabel.CENTER);
        endowment.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize()));
        endowment.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        templeBox.add(baptism);
        templeBox.add(initiatory);
        templeBox.add(endowment);

        return templeBox;
    }

}
