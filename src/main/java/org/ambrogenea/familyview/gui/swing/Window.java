package org.ambrogenea.familyview.gui.swing;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import org.ambrogenea.familyview.enums.PropertyName;
import org.ambrogenea.familyview.gui.swing.components.SettingsPanel;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.impl.DefaultConfigurationService;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Window extends JFrame implements PropertyChangeListener {

    private static final int BORDER_SIZE = 70;

    private SettingsPanel settingsPanel;
    private ConfigurationService configuration;

    private JTabbedPane settingsTab;

    public Window() {
        configuration = new DefaultConfigurationService();
        configuration.addPropertyChangeListener(this);

        initComponents();
        setWindowSize();
    }

    private void setWindowSize() throws HeadlessException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
        this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Family Viewer");

        settingsPanel = new SettingsPanel(this);
        settingsTab = new JTabbedPane();
        settingsTab.addTab("Settings", settingsPanel);

        this.add(settingsTab);
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> new Window().setVisible(true));
    }

    public void addTab(String name, JComponent panel) {
        settingsTab.addTab(name, panel);
        settingsTab.setSelectedIndex(settingsTab.getTabCount() - 1);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyName.NEW_TREE.toString())) {
            JComponent panel = (JComponent) evt.getNewValue();
            addTab(evt.getOldValue().toString(), panel);
        } else if (evt.getPropertyName().equals(PropertyName.LINEAGE_CONFIG_CHANGE.toString())) {
//            personImage.update();
//            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
//            personImagePanel.repaint();
        } else if (evt.getPropertyName().equals(PropertyName.LINEAGE_SIZE_CHANGE.toString())) {
//            personImage.update();
//            personImage.setPreferredSize(new Dimension(configuration.getAdultImageWidth(), configuration.getAdultImageHeight()));
//            adultSize.setText(configuration.getAdultImageWidth() + "x" + configuration.getAdultImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_SIZE_CHANGE.toString())) {
//            siblingImage.update();
//            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
//            siblingsSize.setText(configuration.getSiblingImageWidth() + "x" + configuration.getSiblingImageHeight());
        } else if (evt.getPropertyName().equals(PropertyName.SIBLING_CONFIG_CHANGE.toString())) {
//            siblingImage.update();
//            siblingImage.setPreferredSize(new Dimension(configuration.getSiblingImageWidth(), configuration.getSiblingImageHeight()));
//            siblingsImagePanel.repaint();
        }
    }

    public ConfigurationService getConfiguration() {
        return configuration;
    }

}
