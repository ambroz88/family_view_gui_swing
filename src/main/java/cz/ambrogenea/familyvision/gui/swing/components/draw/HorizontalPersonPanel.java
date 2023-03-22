package cz.ambrogenea.familyvision.gui.swing.components.draw;

import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.constant.Fonts;
import cz.ambrogenea.familyvision.gui.swing.dto.DatePlace;
import cz.ambrogenea.familyvision.gui.swing.dto.PersonRecord;
import cz.ambrogenea.familyvision.gui.swing.enums.Diagram;
import cz.ambrogenea.familyvision.gui.swing.utils.Tools;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jiri Ambroz
 */
public class HorizontalPersonPanel extends PersonPanel {

    public HorizontalPersonPanel(PersonRecord person, Dimension dimension) {
        super(person, dimension);
    }

    @Override
    protected void initDateLabels() {
        birth = new JLabel(" ", JLabel.RIGHT);
        birthPlace = new JLabel("", JLabel.LEFT);
        death = new JLabel(" ", JLabel.RIGHT);
        deathPlace = new JLabel("", JLabel.LEFT);

        String birthPlaceString = "";
        String birthDateString = "";
        DatePlace birthDatePlace = person.birthDatePlace();
        if (birthDatePlace.date() != null) {
            birthDateString = "\u002A" + birthDatePlace.getLocalizedDate(configuration.getLocale()) + "";
            if (configuration.isShowPlaces() && !birthDatePlace.place().isEmpty()) {
                if (configuration.isShortenPlaces()) {
                    birthPlaceString = "," + SPACE + Tools.cityShortVersion(birthDatePlace.getSimplePlace());
                } else {
                    birthPlaceString = "," + SPACE + birthDatePlace.getSimplePlace();
                }
            }
        } else {
            if (configuration.isShowPlaces() && !birthDatePlace.place().isEmpty()) {
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

        DatePlace deathDatePlace = person.deathDatePlace();
        if (configuration.isShowAge() && deathDatePlace.date() != null) {
            death.setText("\u2020" + deathDatePlace.getLocalizedDate(configuration.getLocale()) + "");
            if (configuration.isShowPlaces() && !deathDatePlace.place().isEmpty()) {
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
        birthPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        death.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
        deathPlace.setFont(new Font(Fonts.GENERAL_FONT, Font.PLAIN, fontSize));
    }

    @Override
    protected void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        int defaultColumn = 0;
        if (configuration.getDiagram().equals(Diagram.SCROLL)) {
            c.ipadx = Dimensions.SCROLL_SHIFT;
            add(new JLabel(""), c);
            defaultColumn = 1;
            c.ipadx = 0;
        }
        c.gridx = defaultColumn;
        c.ipady = -configuration.getVerticalShift();
        c.weighty = 5;
        c.gridwidth = 2;
        add(new JLabel(""), c);

        c.gridy = 1;
        c.ipady = 0;
        c.weighty = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
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

        if (configuration.isShowPlaces()) {
            c.gridwidth = 1;
        }
        c.gridy = 5;
        c.ipady = 0;
        c.weighty = 0;
        if (person.birthDatePlace().date() != null) {
            if (!configuration.isShowAge()) {
                c.gridwidth = 2;
            }
            if (!configuration.isShowAge() || !configuration.isShowPlaces()) {
                birth.setHorizontalAlignment(JLabel.CENTER);
            }
            add(birth, c);
        }

        if (configuration.isShowAge()) {
            c.gridy = 7;
            if (!configuration.isShowPlaces()) {
                death.setHorizontalAlignment(JLabel.CENTER);
            }
            add(death, c);
        }

        if (configuration.isShowPlaces()) {
            if (configuration.isShowAge()) {
                c.gridy = 5;
                if (person.birthDatePlace().date() != null) {
                    c.gridx = defaultColumn + 1;
                } else {
                    c.gridx = defaultColumn;
                }
                add(birthPlace, c);
                c.gridx = defaultColumn + 1;
                c.gridy = 7;
                add(deathPlace, c);
            }

            c.gridx = defaultColumn;
            c.gridwidth = 2;
        }

        if (configuration.isShowOrdinances() && !person.isChild() && !person.living()) {
            addEmptyLabel(9, c);
            JPanel templeBox = creteTempleBox();
            c.ipady = 5;
            c.gridy = 10;
            add(templeBox, c);
        }

        c.gridy = 11;
        c.weighty = 5;
        c.ipady = configuration.getVerticalShift();
        add(new JLabel(""), c);
    }

    @Override
    protected void setLabelsOffset() {
        int shift = 0;

        int imageWidth = configuration.getAdultImageWidth() / 2;
        if (configuration.getDiagram().equals(Diagram.SCROLL)) {
            shift = Dimensions.SCROLL_SHIFT;
        }
        firstName.setPreferredSize(new Dimension(configuration.getAdultImageWidth() - shift, firstName.getPreferredSize().height));
        surName.setPreferredSize(new Dimension(configuration.getAdultImageWidth() - shift, surName.getPreferredSize().height));
        occupation.setPreferredSize(new Dimension(configuration.getAdultImageWidth() - shift, occupation.getPreferredSize().height));
        birth.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        birthPlace.setPreferredSize(new Dimension(imageWidth - shift, birthPlace.getPreferredSize().height));
        death.setPreferredSize(new Dimension(imageWidth + shift, death.getPreferredSize().height));
        deathPlace.setPreferredSize(new Dimension(imageWidth - shift, deathPlace.getPreferredSize().height));
    }
}
