package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.ambrogenea.familyview.domain.PersonRecord;
import org.ambrogenea.familyview.enums.Diagrams;
import org.ambrogenea.familyview.enums.Sex;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz
 */
public class SiblingPanel extends PersonPanel {

    public SiblingPanel(PersonRecord person, ConfigurationService config) {
        super(person, config);
    }

    @Override
    protected void loadPictures() {
        String imagePath;

        if (person.getSex().equals(Sex.MALE)) {
            imagePath = configuration.getSiblingManImagePath();
        } else {
            imagePath = configuration.getSiblingWomanImagePath();
        }

        try {
            personDiagram = ImageIO.read(ClassLoader.getSystemResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("Image " + imagePath + " can't be open.");
        }
    }

    @Override
    protected void showPlaces() {
        int shift = 0;

        int imageWidth = configuration.getSiblingImageWidth() / 2;
        if (configuration.getSiblingDiagram().equals(Diagrams.PERGAMEN)) {
            shift = 12;
        }

        birth.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        birthPlace.setPreferredSize(new Dimension(imageWidth - shift, birth.getPreferredSize().height));
        death.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        deathPlace.setPreferredSize(new Dimension(imageWidth - shift, birth.getPreferredSize().height));
    }

    @Override
    protected void addLabels() {
        super.addLabels(configuration.getSiblingTopOffset(), configuration.getSiblingBottomOffset());
    }

}
