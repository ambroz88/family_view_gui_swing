package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.gui.swing.tools.PersonPanelMouseController;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public abstract class PersonPanel extends JPanel {

    protected static final String SPACE = "  ";
    private static final String GENERAL_FONT = "Calibri";
    private static final String NAMES_FONT = Font.SANS_SERIF;

    protected final PersonRecord person;
    protected final ConfigurationService configuration;
    protected BufferedImage personDiagram;
    private int fontSize;

    protected JLabel firstName;
    protected JLabel surName;
    protected JLabel occupation;
    protected JLabel belowNamesSpace;
    protected JLabel birth;
    protected JLabel birthPlace;
    protected JLabel belowDatesSpace;
    protected JLabel death;
    protected JLabel deathPlace;

    public PersonPanel(PersonRecord person, ConfigurationService config) {
        super(new GridBagLayout());

        this.person = person;
        this.configuration = config;

        initElements();
    }

    protected abstract void loadPictures();

    protected abstract void showPlaces();

    protected abstract void addLabels();

    public void update() {
        this.removeAll();
        initElements();
        revalidate();
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

    private void initLabels() {
        belowNamesSpace = new JLabel("", JLabel.CENTER);
        belowDatesSpace = new JLabel("", JLabel.CENTER);

        occupation = new JLabel("", JLabel.CENTER);
        occupation.setText(person.getOccupation().split(";")[0]);
        occupation.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));

        initNameLabels();
        initDateLabels();

        if (configuration.isShowPlaces() && !birthPlace.getText().isEmpty() || !deathPlace.getText().isEmpty()) {
            showPlaces();
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
            firstName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize - 1));
        } else if (firstName.getText().length() > 15) {
            firstName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize));
        } else {
            firstName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize + 1));
        }

        if (surName.getText().length() > 20) {
            surName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize - 1));
        } else if (surName.getText().length() > 15) {
            surName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize));
        } else {
            surName.setFont(new Font(NAMES_FONT, Font.BOLD, fontSize + 1));
        }
    }

    private void initDateLabels() {
        birth = new JLabel(" ", JLabel.RIGHT);
        birthPlace = new JLabel("", JLabel.LEFT);
        death = new JLabel(" ", JLabel.RIGHT);
        deathPlace = new JLabel("", JLabel.LEFT);

        if (!person.getBirthDate().isEmpty()) {
            birth.setText("\u002A " + person.getBirthDateCzech());
            if (configuration.isShowPlaces() && !person.getBirthPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlace.setText("," + SPACE + Tools.cityShortVersion(person.getSimpleBirthPlace()));
                } else {
                    birthPlace.setText("," + SPACE + person.getSimpleBirthPlace());
                }
            }
        } else {
            if (configuration.isShowPlaces() && !person.getBirthPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlace.setText("\u002A " + Tools.cityShortVersion(person.getSimpleBirthPlace()));
                } else {
                    birthPlace.setText("\u002A " + person.getSimpleBirthPlace());
                }
                birthPlace.setHorizontalAlignment(JLabel.CENTER);
            }
        }

        if (!person.getDeathDate().isEmpty()) {
            death.setText("\u2D15 " + person.getDeathDateCzech());
            if (configuration.isShowPlaces() && !person.getDeathPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    deathPlace.setText("," + SPACE + Tools.cityShortVersion(person.getSimpleDeathPlace()));
                } else {
                    deathPlace.setText("," + SPACE + person.getSimpleDeathPlace());
                }
            }
        }

        initDateLabelsFont();
    }

    private void initDateLabelsFont() {
        birth.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));
        birthPlace.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize - 1));
        death.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));
        deathPlace.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize - 1));
    }

    protected void addLabels(int topOffset, int bottomOffset) {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = topOffset;
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

        if (configuration.isShowOccupation() && !person.getOccupation().isEmpty()) {
            c.gridy = 3;
            c.ipady = 0;
            add(occupation, c);
        }

        c.gridy = 4;
        c.ipady = 4;
        add(belowNamesSpace, c);

        if (configuration.isShowPlaces()) {
            c.gridwidth = 1;
        }
        c.gridy = 5;
        c.ipady = 0;
        c.weighty = 0;
        if (!person.getBirthDate().isEmpty()) {
            add(birth, c);
        }

        if (configuration.isShowAge()) {
            c.gridy = 7;
            add(death, c);
        }

        if (configuration.isShowPlaces()) {
            c.gridy = 5;
            if (!person.getBirthDate().isEmpty()) {
                c.gridx = 1;
            } else {
                c.gridx = 0;
            }
            add(birthPlace, c);

            if (configuration.isShowAge()) {
//                c.gridx = 1;
                c.gridy = 7;
                add(deathPlace, c);
            }

            c.gridx = 0;
            c.gridwidth = 2;
        }

        if (configuration.isShowTemple() && !person.isChild() && !person.isLiving()) {
            c.gridy = 9;
            c.ipady = 8;
            add(belowDatesSpace, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 10;
            add(templeBox, c);
        }

        c.gridy = 11;
        c.weighty = 5;
        c.ipady = bottomOffset;
        add(new JLabel(""), c);
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

    private JPanel creteTempleBox() {
        JPanel templeBox = new JPanel(new GridLayout(1, 3, -1, -1));
        templeBox.setBackground(Color.WHITE);

        JLabel baptism = new JLabel(" KK ", JLabel.CENTER);
        baptism.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));
        baptism.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel initiatory = new JLabel(" PO ", JLabel.CENTER);
        initiatory.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));
        initiatory.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel endowment = new JLabel(" OB ", JLabel.CENTER);
        endowment.setFont(new Font(GENERAL_FONT, Font.PLAIN, fontSize));
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
