package org.ambrogenea.familyview.gui.swing;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ambrogenea.familyview.gui.swing.components.DrawingFrame;
import org.ambrogenea.familyview.gui.swing.model.Table;
import org.ambrogenea.familyview.model.AncestorModel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.DataModel;
import org.ambrogenea.familyview.model.utils.FileIO;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ApplicationWindow extends JFrame {

    private static final int BORDER_SIZE = 70;

    private DataModel dataModel;
    private final JFileChooser openFC;

    /**
     * Creates new form ApplicationWindow
     */
    public ApplicationWindow() {
        initComponents();
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("SW Icon.png"));
        setIconImage(img.getImage());

        openFC = new JFileChooser(System.getProperty("user.home") + "/Documents/Genealogie");
        openFC.setFileFilter(new FileNameExtensionFilter("GEDCOM files", "ged"));
        openFC.setDialogType(JFileChooser.OPEN_DIALOG);

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
        generateAncestorButton = new javax.swing.JButton();
        generateLineageButton = new javax.swing.JButton();
        generateSiblingsButton = new javax.swing.JButton();

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

        generateAncestorButton.setText("Generate ancestors");
        generateAncestorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateAncestorButtonActionPerformed(evt);
            }
        });

        generateLineageButton.setText("Generate lineage");
        generateLineageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateLineageButtonActionPerformed(evt);
            }
        });

        generateSiblingsButton.setText("Generate lineage with siblings");
        generateSiblingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateSiblingsButtonActionPerformed(evt);
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
                        .addComponent(generateLineageButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(generateSiblingsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(generateAncestorButton))
                    .addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE))
                .addContainerGap())
        );
        settingsRootPanelLayout.setVerticalGroup(
            settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsRootPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(settingsRootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadInputButton)
                    .addComponent(generateAncestorButton)
                    .addComponent(generateLineageButton)
                    .addComponent(generateSiblingsButton))
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
        int returnVal = openFC.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFC.getSelectedFile();
            loadTable(file.getAbsolutePath());
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_loadInputButtonActionPerformed

    private void loadTable(String absolutePath) {
        try {
            ArrayList<String> lines = FileIO.FileToLines(absolutePath);
            dataModel = new DataModel();
            dataModel.loadGEDCOMLines(lines);
            recordsTable.setModel(new Table(dataModel));
        } catch (IOException ex) {
            Logger.getLogger(ApplicationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateAncestorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateAncestorButtonActionPerformed
        if (recordsTable.getSelectedRow() != -1) {

            AncestorModel ancestors = new AncestorModel(dataModel);
            AncestorPerson personWithAncestors = ancestors.generateAncestors(recordsTable.getSelectedRow());
            System.out.println("There will be generated all ancestors of: " + personWithAncestors.getName());

            DrawingFrame drawing = new DrawingFrame("All ancestors of " + personWithAncestors.getName());
            drawing.setSize(this.getSize());
            drawing.setPreferredSize(this.getPreferredSize());
            drawing.generateAllAncestors(personWithAncestors);

            System.out.println("Family tree was created.");
        }
    }//GEN-LAST:event_generateAncestorButtonActionPerformed

    private void generateLineageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateLineageButtonActionPerformed
        if (recordsTable.getSelectedRow() != -1) {

            AncestorModel ancestors = new AncestorModel(dataModel);
            AncestorPerson personWithAncestors = ancestors.generateManParents(recordsTable.getSelectedRow());
            System.out.println("There will be generated father's parents of: " + personWithAncestors.getName());

            DrawingFrame drawing = new DrawingFrame("All father's parents for " + personWithAncestors.getName());
            drawing.setSize(this.getSize());
            drawing.setPreferredSize(this.getPreferredSize());
            drawing.generateFathersParents(personWithAncestors);

            System.out.println("Family tree was created.");
        }
    }//GEN-LAST:event_generateLineageButtonActionPerformed

    private void generateSiblingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateSiblingsButtonActionPerformed
        if (recordsTable.getSelectedRow() != -1) {

            AncestorModel ancestors = new AncestorModel(dataModel);
            AncestorPerson personWithAncestors = ancestors.generateFathersFamily(recordsTable.getSelectedRow());
            System.out.println("There will be generated fathers of: " + personWithAncestors.getName() + " with their siblings");

            DrawingFrame drawing = new DrawingFrame("Families of fathers of " + personWithAncestors.getName());
            drawing.setSize(this.getSize());
            drawing.setPreferredSize(this.getPreferredSize());
            drawing.generateFathersParentsWithSiblings(personWithAncestors);

            System.out.println("Family tree was created.");
        }
    }//GEN-LAST:event_generateSiblingsButtonActionPerformed

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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generateAncestorButton;
    private javax.swing.JButton generateLineageButton;
    private javax.swing.JButton generateSiblingsButton;
    private javax.swing.JButton loadInputButton;
    private javax.swing.JTable recordsTable;
    private javax.swing.JPanel settingsRootPanel;
    private javax.swing.JTabbedPane settingsTab;
    private javax.swing.JScrollPane tableScroll;
    // End of variables declaration//GEN-END:variables

}
