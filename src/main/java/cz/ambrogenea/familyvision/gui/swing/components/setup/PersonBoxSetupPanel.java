package cz.ambrogenea.familyvision.gui.swing.components.setup;

import cz.ambrogenea.familyvision.gui.swing.Window;
import cz.ambrogenea.familyvision.gui.swing.constant.Colors;
import cz.ambrogenea.familyvision.gui.swing.constant.Dimensions;
import cz.ambrogenea.familyvision.gui.swing.description.PersonBoxSetup;
import cz.ambrogenea.familyvision.service.VisualConfigurationService;
import cz.ambrogenea.familyvision.service.util.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.ResourceBundle;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonBoxSetupPanel extends JPanel {

    private final Window window;
    private final VisualConfigurationService configuration;

    private JLabel directLabel;
    private JLabel sideLabel;

    private JSpinner adultWidthSpinner;
    private JLabel widthLabel;
    private JSpinner siblingWidthSpinner;

    private JSpinner adultHeightSpinner;
    private JLabel heightLabel;
    private JSpinner siblingHeightSpinner;

    private JSpinner adultFontSizeSpinner;
    private JLabel fontSizeLabel;
    private JSpinner siblingFontSizeSpinner;

    private JSpinner verticalShiftSpinner;
    private JLabel verticalShiftLabel;

    public PersonBoxSetupPanel(Window window) {
        this.window = window;
//        this.setPreferredSize(Dimensions.PERSON_BOX_SETUP_DIMENSION);
        this.setLayout(new GridLayout(5, 3, 10, 5));
        this.setBackground(Colors.SW_BACKGROUND);
        configuration = Config.visual();

        initComponents();
        initActions();
        addComponents();
    }

    private void initComponents() {
        ResourceBundle description = ResourceBundle.getBundle("language/personBoxSetup", configuration.getLocale());
        directLabel = new JLabel(description.getString(PersonBoxSetup.DIRECT), JLabel.CENTER);
        sideLabel = new JLabel(description.getString(PersonBoxSetup.SIDE), JLabel.CENTER);
        heightLabel = new JLabel(description.getString(PersonBoxSetup.HEIGHT), JLabel.CENTER);
        heightLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_MENU_HEIGHT / 2));
        widthLabel = new JLabel(description.getString(PersonBoxSetup.WIDTH), JLabel.CENTER);
        widthLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_MENU_HEIGHT / 2));
        fontSizeLabel = new JLabel(description.getString(PersonBoxSetup.FONT_SIZE), JLabel.CENTER);
        fontSizeLabel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH / 3, Dimensions.BUTTON_MENU_HEIGHT / 2));

        adultWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageWidth(), 100, 300, 10));
        adultHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultImageHeight(), 100, 300, 10));
        adultFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getAdultFontSize(), 10, 22, 1));
        siblingWidthSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageWidth(), 100, 300, 10));
        siblingHeightSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingImageHeight(), 100, 300, 10));
        siblingFontSizeSpinner = new JSpinner(new SpinnerNumberModel(configuration.getSiblingFontSize(), 10, 22, 1));

        verticalShiftSpinner = new JSpinner(new SpinnerNumberModel(configuration.getVerticalShift(), -30, 30, 5));
        verticalShiftLabel = new JLabel(description.getString(PersonBoxSetup.VERTICAL_SHIFT), JLabel.CENTER);
    }

    private void initActions() {
        adultWidthSpinner.addChangeListener(this::adultWidthSpinnerStateChanged);
        adultHeightSpinner.addChangeListener(this::adultHeightSpinnerStateChanged);
        adultFontSizeSpinner.addChangeListener(this::fontSizeSpinnerStateChanged);
        siblingWidthSpinner.addChangeListener(this::siblingsWidthSpinnerStateChanged);
        siblingHeightSpinner.addChangeListener(this::siblingsHeightSpinnerStateChanged);
        siblingFontSizeSpinner.addChangeListener(this::siblingFontSizeSpinnerStateChanged);
        verticalShiftSpinner.addChangeListener(this::adultVerticalShiftSpinnerStateChanged);
    }

    private void addComponents() {
        this.add(directLabel);
        this.add(new JLabel(""));
        this.add(sideLabel);

        this.add(adultWidthSpinner);
        this.add(widthLabel);
        this.add(siblingWidthSpinner);

        this.add(adultHeightSpinner);
        this.add(heightLabel);
        this.add(siblingHeightSpinner);

        this.add(adultFontSizeSpinner);
        this.add(fontSizeLabel);
        this.add(siblingFontSizeSpinner);

        this.add(verticalShiftSpinner);
        this.add(verticalShiftLabel);
    }

    private void adultWidthSpinnerStateChanged(ChangeEvent evt) {
        int adultWidth = Integer.parseInt(adultWidthSpinner.getValue().toString());
        configuration.setAdultImageWidth(adultWidth);
        window.generateTree();
    }

    private void adultHeightSpinnerStateChanged(ChangeEvent evt) {
        int adultHeight = Integer.parseInt(adultHeightSpinner.getValue().toString());
        configuration.setAdultImageHeight(adultHeight);
        window.generateTree();
    }

    private void siblingsWidthSpinnerStateChanged(ChangeEvent evt) {
        int siblingsWidth = Integer.parseInt(siblingWidthSpinner.getValue().toString());
        configuration.setSiblingImageWidth(siblingsWidth);
        window.generateTree();
    }

    private void siblingsHeightSpinnerStateChanged(ChangeEvent evt) {
        int siblingsHeight = Integer.parseInt(siblingHeightSpinner.getValue().toString());
        configuration.setSiblingImageHeight(siblingsHeight);
        window.generateTree();
    }

    private void fontSizeSpinnerStateChanged(ChangeEvent evt) {
        int adultFontSize = Integer.parseInt(adultFontSizeSpinner.getValue().toString());
        configuration.setAdultFontSize(adultFontSize);
        window.generateTree();
    }

    private void siblingFontSizeSpinnerStateChanged(ChangeEvent evt) {
        int siblingsFontSize = Integer.parseInt(siblingFontSizeSpinner.getValue().toString());
        configuration.setSiblingFontSize(siblingsFontSize);
        window.generateTree();
    }

    private void adultVerticalShiftSpinnerStateChanged(ChangeEvent evt) {
        int adultVerticalShift = Integer.parseInt(verticalShiftSpinner.getValue().toString());
        configuration.setVerticalShift(adultVerticalShift);
        window.generateTree();
    }

}
