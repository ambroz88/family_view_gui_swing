package org.ambrogenea.familyview.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ambrogenea.familyview.constant.Spaces;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ResidencePanel extends JPanel {

    private final TreeMap<String, Color> cityRegister;

    public ResidencePanel(TreeMap<String, Color> cityRegister) {
        super(new GridLayout(cityRegister.size() + 1, 1));
        this.cityRegister = cityRegister;
        initPanel(cityRegister);

        createTitle();

        addCities(cityRegister);
    }

    private void initPanel(TreeMap<String, Color> cityRegister1) {
        int citiesCount = cityRegister1.size();
        setPreferredSize(new Dimension(250, (citiesCount + 1) * (Spaces.RESIDENCE_SIZE + Spaces.HORIZONTAL_GAP / 2) + Spaces.HORIZONTAL_GAP / 2));
        setSize(new Dimension(250, (citiesCount + 1) * (Spaces.RESIDENCE_SIZE + Spaces.HORIZONTAL_GAP / 2) + Spaces.HORIZONTAL_GAP / 2));
        setBackground(Color.WHITE);
    }

    private void createTitle() {
        JLabel legendTitle = new JLabel("      Názvy obcí");
        legendTitle.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(legendTitle);
    }

    private void addCities(TreeMap<String, Color> cityRegister1) {
        JLabel cityLabel;
        String space = "                 ";
        for (String city : cityRegister1.keySet()) {
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
            g2.drawRoundRect(
                    Spaces.HORIZONTAL_GAP,
                    count * (Spaces.RESIDENCE_SIZE + Spaces.HORIZONTAL_GAP / 2) + Spaces.HORIZONTAL_GAP / 2,
                    Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE,
                    Spaces.RESIDENCE_SIZE / 2,
                    Spaces.RESIDENCE_SIZE / 2
            );
            count++;
        }

    }

}
