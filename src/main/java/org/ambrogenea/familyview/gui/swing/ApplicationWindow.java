package org.ambrogenea.familyview.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.ambrogenea.familyview.gui.swing.components.AncestorPanel;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.model.AncestorModel;
import org.ambrogenea.familyview.model.DataModel;
import org.ambrogenea.familyview.model.Person;
import org.ambrogenea.familyview.model.utils.FileIO;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ApplicationWindow extends JFrame {

    private static final int BORDER_SIZE = 70;
    private final DataModel dataModel;

    /**
     * Creates new form ApplicationWindow
     */
    public ApplicationWindow() {
        initComponents();
        dataModel = new DataModel();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
        this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth() - BORDER_SIZE, gd.getDisplayMode().getHeight() - BORDER_SIZE));
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        settingsTab = new javax.swing.JTabbedPane();
        settingsRootPanel = new javax.swing.JPanel();
        loadInputButton = new javax.swing.JButton();
        tableScroll = new javax.swing.JScrollPane();
        recordsTable = new javax.swing.JTable();
        generateViewButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Family Viewer");
        setName("rootFrame"); // NOI18N

        settingsTab.setName(""); // NOI18N

        loadInputButton.setText("Load GEDCOM file");
        loadInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadInputButtonActionPerformed(evt);
            }
        });

        recordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tableScroll.setViewportView(recordsTable);

        generateViewButton.setText("Generate ancestors");
        generateViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateViewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsRootPanelLayout = new javax.swing.GroupLayout(settingsRootPanel);
        settingsRootPanel.setLayout(settingsRootPanelLayout);
        settingsRootPanelLayout.setHorizontalGroup(
            settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsRootPanelLayout.createSequentialGroup()
                        .addComponent(loadInputButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateViewButton))
                    .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE))
                .addContainerGap())
        );
        settingsRootPanelLayout.setVerticalGroup(
            settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadInputButton)
                    .addComponent(generateViewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
        );

        settingsTab.addTab("Settings", settingsRootPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsTab)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsTab)
                .addContainerGap())
        );

        settingsTab.getAccessibleContext().setAccessibleName("Settings");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadInputButtonActionPerformed
        JFileChooser fc = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            loadTable(file.getAbsolutePath());
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_loadInputButtonActionPerformed

    private void loadTable(String absolutePath) {
        try {
            ArrayList<String> lines = FileIO.FileToLines(absolutePath);
            dataModel.loadGEDCOMLines(lines);
            recordsTable.setModel(new Table(dataModel));
        } catch (IOException ex) {
            Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateViewButtonActionPerformed
        if (recordsTable.getSelectedRow() != -1) {
            System.out.println("Row index: " + recordsTable.getSelectedRow());
            System.out.println("Column index: " + recordsTable.getSelectedColumn());

            AncestorModel ancestors = new AncestorModel(dataModel);
            Person personWithAncestors = ancestors.generateAncestors(recordsTable.getSelectedRow());

            JFrame drawing = new JFrame("Ancestors of " + personWithAncestors.getName());
            drawing.setLayout(new BorderLayout());
            drawing.setSize(this.getSize());
            drawing.setPreferredSize(this.getPreferredSize());
            drawing.setMinimumSize(new Dimension(800, 600));
            ScrollPane scrollAncestorPane = new ScrollPane();

            AncestorPanel ancestorPanel = new AncestorPanel(personWithAncestors);
            ancestorPanel.setPreferredSize(new Dimension(AncestorPanel.MINIMAL_WIDTH * ((int) Math.pow(2, personWithAncestors.getAncestorGenerations()) + 2), getHeight()));

            scrollAncestorPane.add(ancestorPanel);
            drawing.add(scrollAncestorPane);
            drawing.setVisible(true);

            System.out.println("Family links done.");
        }
    }//GEN-LAST:event_generateViewButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generateViewButton;
    private javax.swing.JButton loadInputButton;
    private javax.swing.JTable recordsTable;
    private javax.swing.JPanel settingsRootPanel;
    private javax.swing.JTabbedPane settingsTab;
    private javax.swing.JScrollPane tableScroll;
    // End of variables declaration//GEN-END:variables

}
