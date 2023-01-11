package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.CoupleType;
import cz.ambrogenea.familyvision.gui.swing.enums.LineageType;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class TreeShapeConfiguration {

    private LineageType lineageType;
    private CoupleType coupleType;
    private int ancestorGenerations;
    private int descendentGenerations;

    private boolean showSiblings;
    private boolean showSiblingSpouses;
    private boolean showSpouses;
    private boolean showHeraldry;
    private boolean showResidence;

    public TreeShapeConfiguration() {}

    public LineageType getLineageType() {
        return lineageType;
    }

    public void setLineageType(LineageType lineageType) {
        this.lineageType = lineageType;
    }

    public CoupleType getCoupleType() {
        return coupleType;
    }

    public void setCoupleType(CoupleType coupleType) {
        this.coupleType = coupleType;
    }

    public int getAncestorGenerations() {
        return ancestorGenerations;
    }

    public void setAncestorGenerations(int ancestorGenerations) {
        this.ancestorGenerations = ancestorGenerations;
    }

    public int getDescendentGenerations() {
        return descendentGenerations;
    }

    public void setDescendentGenerations(int descendentGenerations) {
        this.descendentGenerations = descendentGenerations;
    }

    public boolean isShowSiblings() {
        return showSiblings;
    }

    public void setShowSiblings(boolean showSiblings) {
        this.showSiblings = showSiblings;
    }

    public boolean isShowSiblingSpouses() {
        return showSiblingSpouses;
    }

    public void setShowSiblingSpouses(boolean showSiblingSpouses) {
        this.showSiblingSpouses = showSiblingSpouses;
    }

    public boolean isShowSpouses() {
        return showSpouses;
    }

    public void setShowSpouses(boolean showSpouses) {
        this.showSpouses = showSpouses;
    }

    public boolean isShowHeraldry() {
        return showHeraldry;
    }

    public void setShowHeraldry(boolean showHeraldry) {
        this.showHeraldry = showHeraldry;
    }

    public boolean isShowResidence() {
        return showResidence;
    }

    public void setShowResidence(boolean showResidence) {
        this.showResidence = showResidence;
    }
}
