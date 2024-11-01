package cz.ambrogenea.familyvision.gui.swing.dto;

import cz.ambrogenea.familyvision.gui.swing.enums.Diagram;

import java.util.Locale;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PersonVisualConfiguration {

    private int adultImageWidth;
    private int adultImageHeight;
    private int adultFontSize;
    private int siblingImageWidth;
    private int siblingImageHeight;
    private int siblingFontSize;
    private Diagram diagram;
    private int verticalShift;
    private boolean showAge;
    private boolean showOccupation;
    private boolean showPlaces;
    private boolean shortenPlaces;
    private boolean showOrdinances;

    public PersonVisualConfiguration() {
    }

    public int getAdultImageWidth() {
        return adultImageWidth;
    }

    public void setAdultImageWidth(int adultImageWidth) {
        this.adultImageWidth = adultImageWidth;
    }

    public int getAdultImageHeight() {
        return adultImageHeight;
    }

    public void setAdultImageHeight(int adultImageHeight) {
        this.adultImageHeight = adultImageHeight;
    }

    public int getAdultFontSize() {
        return adultFontSize;
    }

    public void setAdultFontSize(int adultFontSize) {
        this.adultFontSize = adultFontSize;
    }

    public int getSiblingImageWidth() {
        return siblingImageWidth;
    }

    public void setSiblingImageWidth(int siblingImageWidth) {
        this.siblingImageWidth = siblingImageWidth;
    }

    public int getSiblingImageHeight() {
        return siblingImageHeight;
    }

    public void setSiblingImageHeight(int siblingImageHeight) {
        this.siblingImageHeight = siblingImageHeight;
    }

    public int getSiblingFontSize() {
        return siblingFontSize;
    }

    public void setSiblingFontSize(int siblingFontSize) {
        this.siblingFontSize = siblingFontSize;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public int getVerticalShift() {
        return verticalShift;
    }

    public void setVerticalShift(int verticalShift) {
        this.verticalShift = verticalShift;
    }

    public boolean isShowAge() {
        return showAge;
    }

    public void setShowAge(boolean showAge) {
        this.showAge = showAge;
    }

    public boolean isShowOccupation() {
        return showOccupation;
    }

    public void setShowOccupation(boolean showOccupation) {
        this.showOccupation = showOccupation;
    }

    public boolean isShowPlaces() {
        return showPlaces;
    }

    public void setShowPlaces(boolean showPlaces) {
        this.showPlaces = showPlaces;
    }

    public boolean isShortenPlaces() {
        return shortenPlaces;
    }

    public void setShortenPlaces(boolean shortenPlaces) {
        this.shortenPlaces = shortenPlaces;
    }

    public boolean isShowOrdinances() {
        return showOrdinances;
    }

    public void setShowOrdinances(boolean showOrdinances) {
        this.showOrdinances = showOrdinances;
    }

    public Locale getLocale() {
        return new Locale("cs", "CZ");
    }
}
