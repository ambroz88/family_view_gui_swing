package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Information;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanel extends JPanel {

    private final Person person;
    private BufferedImage personDiagram;
    protected final Configuration configuration;

    private JLabel firstName = new JLabel("", JLabel.CENTER);
    private JLabel surName = new JLabel("", JLabel.CENTER);
    private JLabel birth = new JLabel("", JLabel.CENTER);
    private JLabel death = new JLabel("", JLabel.CENTER);

    public PersonPanel(Person person, Configuration config) {
        super(new GridBagLayout());

        this.person = person;
        this.configuration = config;

        loadPictures();
        initLabels();
        addLabels();
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
        firstName = new JLabel("", JLabel.CENTER);
        surName = new JLabel("", JLabel.CENTER);
        birth = new JLabel("", JLabel.CENTER);
        death = new JLabel("", JLabel.CENTER);

        firstName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getFontSize()));
        surName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, configuration.getFontSize()));
        birth.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize() - 1));
        death.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, configuration.getFontSize() - 1));

        firstName.setBackground(null);
        surName.setBackground(null);
        birth.setBackground(null);
        death.setBackground(null);
    }

    private void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = configuration.getAdultVerticalOffset();
//        c.weighty = 5;
        add(new JLabel(), c);
        c.gridy = 1;
        c.ipady = 0;
        add(firstName, c);
        c.gridy = 2;
        c.ipady = 5;
        add(surName, c);
        c.gridy = 3;
        add(birth, c);
        c.gridy = 4;
        c.ipady = 0;
        add(death, c);
        c.gridy = 5;
        c.ipady = configuration.getAdultVerticalOffset();
        add(new JLabel(), c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(personDiagram, 0, 0, configuration.getAdultImageWidth(), configuration.getAdultImageHeight(), null);

        firstName.setText(person.getFirstName());
        surName.setText(person.getSurname().toUpperCase());
        if (!person.getBirthDate().isEmpty()) {
            birth.setText("\u2605 " + person.getBirthDate());
        }
        if (!person.getDeathDate().isEmpty()) {
            death.setText("\u271D " + person.getDeathDate());
        }

    }

}
