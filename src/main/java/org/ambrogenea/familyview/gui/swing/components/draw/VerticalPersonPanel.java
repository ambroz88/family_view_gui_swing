package org.ambrogenea.familyview.gui.swing.components.draw;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.domain.DatePlace;
import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.gui.swing.constant.Fonts;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

/**
 *
 * @author Jiri Ambroz
 */
public class VerticalPersonPanel extends PersonPanel {

    private static String BORDER_SPACE = "     ";

    public VerticalPersonPanel(PersonRecord person, ConfigurationService config) {
        super(person, config);
    }

    @Override
    protected void initDateLabels() {
        birth = new JLabel(" ", JLabel.CENTER);
        birthPlace = new JLabel("", JLabel.CENTER);
        death = new JLabel(" ", JLabel.CENTER);
        deathPlace = new JLabel("", JLabel.CENTER);

        String birthPlaceString = "";
        String birthDateString = "";
        DatePlace birthDatePlace = person.getBirthDatePlace();
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

        DatePlace deathDatePlace = person.getDeathDatePlace();
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
        c.ipady = 0 - configuration.getAdultVerticalShift();
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

        if (configuration.isShowOccupation() && !person.getOccupation().isEmpty()) {
            c.gridy = 3;
            c.ipady = 0;
            add(occupation, c);
        }

        addEmptyLabel(4, c);

        c.gridy = 5;
        c.ipady = 0;
        add(birth, c);

        if (configuration.isShowPlaces() && person.getBirthDatePlace().getPlace() != null) {
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

        if (configuration.isShowTemple() && !person.isChild() && !person.isLiving()) {
            addEmptyLabel(10, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 11;
            add(templeBox, c);
        }

        c.gridy = 12;
        c.weighty = 5;
        c.ipady = 0 + configuration.getAdultVerticalShift();
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
