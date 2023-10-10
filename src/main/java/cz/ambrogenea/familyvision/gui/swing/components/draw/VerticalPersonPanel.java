package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.gui.swing.constant.Fonts;
import cz.ambrogenea.familyvision.gui.swing.dto.DatePlace;
import cz.ambrogenea.familyvision.gui.swing.dto.PersonRecord;
import cz.ambrogenea.familyvision.gui.swing.utils.Tools;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jiri Ambroz
 */
public class VerticalPersonPanel extends PersonPanel {

    public VerticalPersonPanel(PersonRecord person, Dimension dimension) {
        super(person, dimension);
    }

    @Override
    protected void initDateLabels() {
        birth = new JLabel(" ", JLabel.CENTER);
        birthPlace = new JLabel("", JLabel.CENTER);
        death = new JLabel(" ", JLabel.CENTER);
        deathPlace = new JLabel("", JLabel.CENTER);

        String birthPlaceString = "";
        String birthDateString = "";
        DatePlace birthDatePlace = person.birthDatePlace();
        if (birthDatePlace.date() != null) {
            birthDateString = "\u002A " + birthDatePlace.getLocalizedDate(configuration.getLocale());
            if (configuration.isShowPlaces() && !birthDatePlace.place().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = birthDatePlace.getSimplePlace();
                }
            }
        } else {
            if (configuration.isShowPlaces() && !birthDatePlace.place().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = "\u002A " + Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = "\u002A " + birthDatePlace.getSimplePlace();
                }
            }
        }

        birth.setText(birthDateString);
        birthPlace.setText(birthPlaceString);

        DatePlace deathDatePlace = person.deathDatePlace();
        if (configuration.isShowAge() && deathDatePlace.date() != null) {
            death.setText("\u2020 " + deathDatePlace.getLocalizedDate(configuration.getLocale()));
            if (configuration.isShowPlaces() && !deathDatePlace.place().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    deathPlace.setText(Tools.cityShortVersion(deathDatePlace.getSimplePlace()));
                } else {
                    deathPlace.setText(deathDatePlace.getSimplePlace());
                }
            }
        }

        initDateLabelsFont();
    }

    private void initDateLabelsFont() {
        birth.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        birthPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        death.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        deathPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
    }

    @Override
    protected void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = -configuration.getVerticalShift();
        c.weighty = 5;
        c.gridwidth = 1;
        add(new JLabel(""), c);

        int row = 1;
        c.gridy = row;
        c.ipady = 0;
        c.weighty = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(firstName, c);

        row++;
        c.gridy = row;
        c.ipady = 4;
        add(surName, c);

        if (configuration.isShowOccupation() && !person.occupation().isEmpty()) {
            row++;
            c.gridy = row;
            c.ipady = 0;
            add(occupation, c);
        }

        row++;
        addEmptyLabel(row, c);

        row++;
        c.gridy = row;
        c.ipady = 0;
        add(birth, c);

        if (configuration.isShowPlaces() && person.birthDatePlace().place() != null) {
            row++;
            c.gridy = row;
            add(birthPlace, c);
        }

        if (configuration.isShowAge()) {
            row++;
            addEmptyLabel(row, c);
            if (person.deathDatePlace().date() != null) {
                row++;
                c.gridy = row;
                c.ipady = 0;
                add(death, c);
            }

            if (configuration.isShowPlaces() && person.deathDatePlace().place() != null) {
                row++;
                c.gridy = row;
                add(deathPlace, c);
            }
        }

        if (configuration.isShowOrdinances() && !person.isChild() && !person.living()) {
            row++;
            addEmptyLabel(row, c);

            row++;
            c.ipady = 0;
            c.gridy = row;
            JPanel templeBox = creteTempleBox();
            add(templeBox, c);
        }

        row++;
        c.gridy = row;
        c.weighty = 5;
        c.ipady = configuration.getVerticalShift();
        add(new JLabel(""), c);
    }

    @Override
    protected void setLabelsOffset() {
        int imageWidth = configuration.getAdultImageWidth();
        birth.setPreferredSize(new Dimension(imageWidth, birth.getPreferredSize().height));
        birthPlace.setPreferredSize(new Dimension(imageWidth, birthPlace.getPreferredSize().height));
        death.setPreferredSize(new Dimension(imageWidth, death.getPreferredSize().height));
        deathPlace.setPreferredSize(new Dimension(imageWidth, deathPlace.getPreferredSize().height));
    }

}
