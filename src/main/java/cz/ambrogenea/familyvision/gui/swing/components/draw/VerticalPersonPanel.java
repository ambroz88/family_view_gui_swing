package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.domain.DatePlace;
import cz.ambrogenea.familyvision.dto.tree.PersonRecord;
import cz.ambrogenea.familyvision.gui.swing.constant.Fonts;
import cz.ambrogenea.familyvision.utils.Tools;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jiri Ambroz
 */
public class VerticalPersonPanel extends PersonPanel {

    public VerticalPersonPanel(PersonRecord person) {
        super(person);
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
        if (birthDatePlace.getDate() != null) {
            birthDateString = "\u002A " + birthDatePlace.getLocalizedDate(configuration.getLocale());
            if (configuration.isShowPlaces() && !birthDatePlace.getPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = birthDatePlace.getSimplePlace();
                }
            }
        } else {
            if (configuration.isShowPlaces() && !birthDatePlace.getPlace().isEmpty()) {
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
        if (configuration.isShowAge() && deathDatePlace.getDate() != null) {
            death.setText("\u2020 " + deathDatePlace.getLocalizedDate(configuration.getLocale()));
            if (configuration.isShowPlaces() && !deathDatePlace.getPlace().isEmpty()) {
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
        c.ipady = - configuration.getVerticalShift();
        c.weighty = 5;
        c.gridwidth = 1;
        add(new JLabel(""), c);

        c.gridy = 1;
        c.ipady = 0;
        c.weighty = 0;
        add(firstName, c);

        c.gridy = 2;
        c.ipady = 4;
        add(surName, c);

        if (configuration.isShowOccupation() && !person.occupation().isEmpty()) {
            c.gridy = 3;
            c.ipady = 0;
            add(occupation, c);
        }

        addEmptyLabel(4, c);

        c.gridy = 5;
        c.ipady = 0;
        add(birth, c);

        if (configuration.isShowPlaces() && person.birthDatePlace().getPlace() != null) {
            c.gridy = 6;
            add(birthPlace, c);
        }

        if (configuration.isShowAge()) {
            addEmptyLabel(7, c);
            c.gridy = 8;
            c.ipady = 0;
            add(death, c);
            if (configuration.isShowPlaces()) {
                c.gridy = 9;
                add(deathPlace, c);
            }
        }

        if (configuration.isShowOrdinances() && !person.isChild() && !person.living()) {
            addEmptyLabel(10, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 11;
            add(templeBox, c);
        }

        c.gridy = 12;
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
