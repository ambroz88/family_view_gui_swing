package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.Background;
import cz.ambrogenea.familyvision.gui.swing.enums.CoupleType;
import cz.ambrogenea.familyvision.gui.swing.enums.LabelShape;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TreeVisualConfiguration {
    CoupleType coupleType;
    LabelShape marriageLabelShape;
    Background background;
    boolean showTitle;
    boolean showChildrenCount;
    boolean showHeraldry;
    boolean showResidence;
    Locale locale;
    boolean resetMode;

    public TreeVisualConfiguration() {
    }

    public CoupleType getCoupleType() {
        return coupleType;
    }

    public void setCoupleType(CoupleType coupleType) {
        this.coupleType = coupleType;
    }

    public LabelShape getMarriageLabelShape() {
        return marriageLabelShape;
    }

    public void setMarriageLabelShape(LabelShape marriageLabelShape) {
        this.marriageLabelShape = marriageLabelShape;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowChildrenCount() {
        return showChildrenCount;
    }

    public void setShowChildrenCount(boolean showChildrenCount) {
        this.showChildrenCount = showChildrenCount;
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

    public Locale getLocale() {
        return new Locale("cs", "CZ");
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isResetMode() {
        return resetMode;
    }

    public void setResetMode(boolean resetMode) {
        this.resetMode = resetMode;
    }
}
