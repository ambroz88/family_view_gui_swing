package org.ambrogenea.familyview.gui.swing.components;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.enums.Diagrams;
import org.ambrogenea.familyview.model.enums.Sex;

/**
 *
 * @author Jiri Ambroz
 */
public class AdultPanel extends PersonPanel {

    public AdultPanel(AncestorPerson person, Configuration config) {
        super(person, config);
    }

    @Override
    protected void loadPictures() {
        String imagePath;

        if (person.getSex().equals(Sex.MALE)) {
            imagePath = configuration.getAdultManImagePath();
        } else {
            imagePath = configuration.getAdultWomanImagePath();
        }

        try {
            personDiagram = ImageIO.read(ClassLoader.getSystemResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("Image " + imagePath + " can't be open.");
            e.printStackTrace();
        }
    }

    @Override
    protected void showPlaces() {
        int shift = 0;

        int imageWidth = configuration.getAdultImageWidth() / 2;
        if (configuration.getAdultDiagram().equals(Diagrams.PERGAMEN)) {
            shift = 12;
        }

        birth.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        birthPlace.setPreferredSize(new Dimension(imageWidth - shift, birth.getPreferredSize().height));
        death.setPreferredSize(new Dimension(imageWidth + shift, birth.getPreferredSize().height));
        deathPlace.setPreferredSize(new Dimension(imageWidth - shift, birth.getPreferredSize().height));
    }

    @Override
    protected void addLabels() {
        super.addLabels(configuration.getAdultTopOffset(), configuration.getAdultBottomOffset());
    }

}
