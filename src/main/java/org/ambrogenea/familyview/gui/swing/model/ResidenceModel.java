package org.ambrogenea.familyview.gui.swing.model;

import org.ambrogenea.familyview.model.Residence;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ResidenceModel extends Residence {

    private int x;
    private int y;

    public ResidenceModel(int x, int y, Residence residence) {
        this.x = x;
        this.y = y;
        super.setCity(residence.getCity());
        super.setDate(residence.getDate());
        super.setNumber(residence.getNumber());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
