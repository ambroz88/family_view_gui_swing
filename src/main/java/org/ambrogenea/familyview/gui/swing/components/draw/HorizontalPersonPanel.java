package org.ambrogenea.familyview.gui.swing.components.draw;

import org.ambrogenea.familyview.domain.DatePlace;
import org.ambrogenea.familyview.dto.tree.PersonRecord;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.constant.Fonts;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jiri Ambroz
 */
public class HorizontalPersonPanel extends PersonPanel {

    public HorizontalPersonPanel(PersonRecord person, ConfigurationService config) {
        super(person, config);
    }

    @Override
    protected void initDateLabels() {
        birth = new JLabel(" ", JLabel.RIGHT);
        birthPlace = new JLabel("", JLabel.LEFT);
        death = new JLabel(" ", JLabel.RIGHT);
        deathPlace = new JLabel("", JLabel.LEFT);

        String birthPlaceString = "";
        String birthDateString = "";
        DatePlace birthDatePlace = person.getBirthDatePlace();
        if (birthDatePlace.getDate() != null) {
            birthDateString = "\u002A" + birthDatePlace.getLocalizedDate(configuration.getLocale()) + "";
            if (configuration.isShowPlaces() && !birthDatePlace.getPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = "," + SPACE + Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = "," + SPACE + birthDatePlace.getSimplePlace();
                }
            }
        } else {
            if (configuration.isShowPlaces() && !birthDatePlace.getPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = "\u002A" + Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = "\u002A" + birthDatePlace.getSimplePlace();
                }
                birthPlace.setHorizontalAlignment(JLabel.CENTER);
            }
        }

        if (configuration.isShowAge()) {
            birth.setText(birthDateString);
            birthPlace.setText(birthPlaceString);
        } else {
            birth.setText(birthDateString + birthPlaceString);
        }

        DatePlace deathDatePlace = person.getDeathDatePlace();
        if (configuration.isShowAge() && deathDatePlace.getDate() != null) {
            death.setText("\u2020" + deathDatePlace.getLocalizedDate(configuration.getLocale()) + "");
            if (configuration.isShowPlaces() && !deathDatePlace.getPlace().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    deathPlace.setText("," + SPACE + Tools.cityShortVersion(deathDatePlace.getSimplePlace()));
                } else {
                    deathPlace.setText("," + SPACE + deathDatePlace.getSimplePlace());
                }
            }
        }

        initDateLabelsFont();
    }

    private void initDateLabelsFont() {
        birth.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        birthPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize - 1));
        death.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        deathPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize - 1));
    }

    @Override
    protected void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = configuration.getAdultVerticalShift();
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

        addEmptyLabel(4, c);

        if (configuration.isShowPlaces()) {
            c.gridwidth = 1;
        }
        c.gridy = 5;
        c.ipady = 0;
        c.weighty = 0;
        if (person.getBirthDatePlace().getDate() != null) {
            if (!configuration.isShowAge()) {
                c.gridwidth = 2;
            }
            add(birth, c);
        }

        if (configuration.isShowAge()) {
            c.gridy = 7;
            add(death, c);
        }

        if (configuration.isShowPlaces()) {
            if (configuration.isShowAge()) {
                c.gridy = 5;
                if (person.getBirthDatePlace().getDate() != null) {
                    c.gridx = 1;
                } else {
                    c.gridx = 0;
                }
                add(birthPlace, c);
                c.gridx = 1;
                c.gridy = 7;
                add(deathPlace, c);
            }

            c.gridx = 0;
            c.gridwidth = 2;
        }

        if (configuration.isShowTemple() && !person.isChild() && !person.isLiving()) {
            addEmptyLabel(9, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 10;
            add(templeBox, c);
        }

        c.gridy = 11;
        c.weighty = 5;
        c.ipady = configuration.getAdultVerticalShift();
        add(new JLabel(""), c);
    }

    @Override
    protected void setLabelsOffset() {
        int shift = 0;

        int imageWidth = configuration.getAdultImageWidth() / 2;
        if (configuration.getAdultDiagram().equals(Diagrams.SCROLL)) {
            shift = Dimensions.SCROLL_SHIFT;
        }

        birth.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        birthPlace.setPreferredSize(new Dimension(imageWidth - shift, birthPlace.getPreferredSize().height));
        death.setPreferredSize(new Dimension(imageWidth + shift, death.getPreferredSize().height));
        deathPlace.setPreferredSize(new Dimension(imageWidth - shift, deathPlace.getPreferredSize().height));
    }
}
