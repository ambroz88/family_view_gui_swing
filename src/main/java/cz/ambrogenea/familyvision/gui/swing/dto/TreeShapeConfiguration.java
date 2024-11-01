package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.LineageType;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeShapeConfiguration {
    private LineageType lineageType;
    private int ancestorGenerations;
    private int descendentGenerations;
    private boolean showSiblings;
    private boolean showSiblingSpouses;
    private boolean showSpouses;

    public TreeShapeConfiguration() {
    }

    public LineageType getLineageType() {
        return lineageType;
    }

    public void setLineageType(LineageType lineageType) {
        this.lineageType = lineageType;
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

    public Locale getLocale() {
        return new Locale("cs", "CZ");
    }
}
