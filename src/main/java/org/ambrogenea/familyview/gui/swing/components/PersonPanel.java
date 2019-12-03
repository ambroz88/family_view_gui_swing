package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.IMAGE_HEIGHT;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.IMAGE_WIDTH;

import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.Person;

/**
 *
 * @author Jiri Ambroz
 */
public class PersonPanel extends JPanel {

    private final static int FONT_SIZE = 11;

    private Person person;
    private BufferedImage manDiagram;
    private BufferedImage womanDiagram;

    private JLabel firstName = new JLabel("", JLabel.CENTER);
    private JLabel surName = new JLabel("", JLabel.CENTER);
    private JLabel birth = new JLabel("", JLabel.CENTER);
    private JLabel death = new JLabel("", JLabel.CENTER);

    public PersonPanel(Person person) {
        super(new GridLayout(6, 1, 5, 0));
        this.person = person;

        try {
            String manImagePath = this.getClass().getResource("/diagrams/man_diagram.png").getPath();
            String womanImagePath = this.getClass().getResource("/diagrams/woman_diagram.png").getPath();
            manDiagram = ImageIO.read(new File(manImagePath));
            womanDiagram = ImageIO.read(new File(womanImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

        firstName = new JLabel("", JLabel.CENTER);
        surName = new JLabel("", JLabel.CENTER);
        birth = new JLabel("", JLabel.CENTER);
        death = new JLabel("", JLabel.CENTER);

        firstName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
        surName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE));
        birth.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, FONT_SIZE - 1));
        death.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, FONT_SIZE - 1));

        firstName.setBackground(null);
        surName.setBackground(null);
        birth.setBackground(null);
        death.setBackground(null);

        add(new JLabel());
        add(firstName);
        add(surName);
        add(birth);
        add(death);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (person.getSex().equals(Couple.MALE)) {
            g.drawImage(manDiagram, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        } else {
            g.drawImage(womanDiagram, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        }

        firstName.setText(person.getFirstName());
        surName.setText(person.getSurname().toUpperCase());
        birth.setText(person.getBirthDate());
        death.setText(person.getDeathDate());
    }

}
