package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.HORIZONTAL_GAP;
import static org.ambrogenea.familyview.gui.swing.treepanels.RootFamilyPanel.RESIDENCE_SIZE;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ResidencePanel extends JPanel {

    private final HashMap<String, Color> cityRegister;

    public ResidencePanel(HashMap<String, Color> cityRegister) {
        super(new GridLayout(cityRegister.size() + 1, 1));
        int citiesCount = cityRegister.size();
        setPreferredSize(new Dimension(250, (citiesCount + 1) * (RESIDENCE_SIZE + HORIZONTAL_GAP / 2) + HORIZONTAL_GAP / 2));
        setSize(new Dimension(250, (citiesCount + 1) * (RESIDENCE_SIZE + HORIZONTAL_GAP / 2) + HORIZONTAL_GAP / 2));
        setBackground(Color.WHITE);
        this.cityRegister = cityRegister;

        JLabel legendTitle = new JLabel("      Názvy obcí");
        legendTitle.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(legendTitle);

        JLabel cityLabel;
        String space = "                 ";
        for (String city : cityRegister.keySet()) {
            cityLabel = new JLabel(space + city, JLabel.LEFT);
            cityLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
            this.add(cityLabel);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        int count = 1;
        for (String city : cityRegister.keySet()) {
            g2.setColor(cityRegister.get(city));
            g2.drawRoundRect(HORIZONTAL_GAP, count * (RESIDENCE_SIZE + HORIZONTAL_GAP / 2) + HORIZONTAL_GAP / 2, RESIDENCE_SIZE, RESIDENCE_SIZE, RESIDENCE_SIZE / 2, RESIDENCE_SIZE / 2);
            count++;
        }

    }

}
