package org.ambrogenea.familyview.gui.swing.components.setup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import org.ambrogenea.familyview.gui.swing.Window;
import org.ambrogenea.familyview.gui.swing.constant.Colors;
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

    private PersonBoxSetupPanel personBoxSetupPanel;

    public PersonSetupPanel(Window window) {
        super(new BorderLayout(0, 5));
        this.window = window;
        this.setPreferredSize(Dimensions.PERSON_SETUP_DIMENSION);
        this.setBackground(Colors.SW_BACKGROUND);

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

        personBoxSetupPanel = new PersonBoxSetupPanel(window);
    }

    private void initActions() {
        ageCheckBox.addActionListener(this::ageCheckBoxActionPerformed);
        occupationCheckBox.addActionListener(this::occupationCheckBoxActionPerformed);
        placesCheckBox.addActionListener(this::placesCheckBoxActionPerformed);
        shortenPlacesCheckBox.addActionListener(this::shortenPlacesCheckBoxActionPerformed);
        templeCheckBox.addActionListener(this::templeCheckBoxActionPerformed);
    }

    private void addComponents() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        top.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, Dimensions.BUTTON_MENU_HEIGHT));
        top.setBackground(Colors.SW_BACKGROUND);
        top.add(ageCheckBox);
        top.add(occupationCheckBox);
        top.add(placesCheckBox);
        top.add(shortenPlacesCheckBox);
        top.add(templeCheckBox);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, 5));
        top.add(sep);
        this.add(top, BorderLayout.NORTH);
        this.add(personBoxSetupPanel, BorderLayout.CENTER);
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
