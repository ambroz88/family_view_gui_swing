package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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

    private JLabel firstName;
    private JLabel surName;
    private JLabel birth;
    private JLabel death;

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
        firstName = new JLabel(" ", JLabel.CENTER);
        surName = new JLabel(" ", JLabel.CENTER);
        birth = new JLabel(" ", JLabel.CENTER);
        death = new JLabel(" ", JLabel.CENTER);

        if (!person.getFirstName().isEmpty()) {
            firstName.setText(person.getFirstName());
        }
        if (!person.getSurname().isEmpty()) {
            surName.setText(person.getSurname().toUpperCase());
        }
        if (!person.getBirthDate().isEmpty()) {
            birth.setText("\u2605 " + person.getBirthDateCzech());
        }
        if (!person.getDeathDate().isEmpty()) {
            death.setText("\u271D " + person.getDeathDateCzech());
        }

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
        c.ipady = configuration.getAdultTopOffset();
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

        if (configuration.isShowTemple() && !person.isChild()) {
            JPanel templeBox = creteTempleBox();
            c.weighty = 10;
            c.ipady = 8;
            c.gridy = 5;
            add(templeBox, c);

            c.gridy = 6;
            c.ipady = configuration.getAdultBottomOffset();
            add(new JLabel(), c);
        } else {
            c.gridy = 5;
            c.ipady = configuration.getAdultBottomOffset();
            add(new JLabel(), c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(personDiagram, 0, 0, configuration.getAdultImageWidth(), configuration.getAdultImageHeight(), null);
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
