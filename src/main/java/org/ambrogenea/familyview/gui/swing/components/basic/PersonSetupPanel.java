package org.ambrogenea.familyview.gui.swing.components.basic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Dimensions;
import org.ambrogenea.familyview.gui.swing.description.PersonSetup;
import org.ambrogenea.familyview.service.ConfigurationService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonSetupPanel extends JPanel {

    private final Window window;

    private JToggleButton ageCheckBox;
    private JToggleButton occupationCheckBox;
    private JToggleButton placesCheckBox;
    private JToggleButton shortenPlacesCheckBox;
    private JToggleButton templeCheckBox;

    public PersonSetupPanel(Window window) {
        super(new FlowLayout(FlowLayout.LEFT, 5, 1));
        this.window = window;
        this.setPreferredSize(Dimensions.TREE_TYPE_DIMENSION);

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        Locale locale = getConfiguration().getLocale();
        ResourceBundle description = ResourceBundle.getBundle("language/personSetup", locale);
        this.setBorder(new TitledBorder(description.getString(PersonSetup.TITLE)));

        ageCheckBox = new JToggleButton("A");
        ageCheckBox.setPreferredSize(Dimensions.BUTTON_DIMENSION);
        ageCheckBox.setSelected(getConfiguration().isShowAge());
        ageCheckBox.setToolTipText(description.getString(PersonSetup.AGE));

        occupationCheckBox = new JToggleButton("O");
        occupationCheckBox.setPreferredSize(Dimensions.BUTTON_DIMENSION);
        occupationCheckBox.setSelected(getConfiguration().isShowOccupation());
        occupationCheckBox.setToolTipText(description.getString(PersonSetup.OCCUPATION));

        placesCheckBox = new JToggleButton("P");
        placesCheckBox.setPreferredSize(Dimensions.BUTTON_DIMENSION);
        placesCheckBox.setSelected(getConfiguration().isShowPlaces());
        placesCheckBox.setToolTipText(description.getString(PersonSetup.PLACES));

        shortenPlacesCheckBox = new JToggleButton("SP");
        shortenPlacesCheckBox.setPreferredSize(Dimensions.BUTTON_DIMENSION);
        shortenPlacesCheckBox.setSelected(getConfiguration().isShortenPlaces());
        shortenPlacesCheckBox.setToolTipText(description.getString(PersonSetup.SHORT_PLACES));

        templeCheckBox = new JToggleButton("T");
        templeCheckBox.setPreferredSize(Dimensions.BUTTON_DIMENSION);
        templeCheckBox.setSelected(getConfiguration().isShowTemple());
        templeCheckBox.setToolTipText(description.getString(PersonSetup.TEMPLE));
    }

    private void initActions() {
        ageCheckBox.addActionListener(this::ageCheckBoxActionPerformed);
        occupationCheckBox.addActionListener(this::occupationCheckBoxActionPerformed);
        placesCheckBox.addActionListener(this::placesCheckBoxActionPerformed);
        shortenPlacesCheckBox.addActionListener(this::shortenPlacesCheckBoxActionPerformed);
        templeCheckBox.addActionListener(this::templeCheckBoxActionPerformed);
    }

    private void addComponents() {
        this.add(ageCheckBox);
        this.add(occupationCheckBox);
        this.add(placesCheckBox);
        this.add(shortenPlacesCheckBox);
        this.add(templeCheckBox);
    }

    private void ageCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowAge(ageCheckBox.isSelected());
        window.generateTree();
    }

    private void occupationCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowOccupation(occupationCheckBox.isSelected());
        window.generateTree();
    }

    private void placesCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowPlaces(placesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            shortenPlacesCheckBox.setSelected(false);
            getConfiguration().setShortenPlaces(false);
        }
        window.generateTree();
    }

    private void shortenPlacesCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShortenPlaces(shortenPlacesCheckBox.isSelected());
        if (!placesCheckBox.isSelected() && shortenPlacesCheckBox.isSelected()) {
            placesCheckBox.setSelected(true);
            getConfiguration().setShowPlaces(true);
        }
        window.generateTree();
    }

    private void templeCheckBoxActionPerformed(ActionEvent evt) {
        getConfiguration().setShowTemple(templeCheckBox.isSelected());
        window.generateTree();
    }

    private ConfigurationService getConfiguration() {
        return window.getConfiguration();
    }

}
