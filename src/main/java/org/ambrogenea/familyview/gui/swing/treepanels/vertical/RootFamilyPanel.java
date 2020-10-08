package org.ambrogenea.familyview.gui.swing.treepanels.vertical;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import org.ambrogenea.familyview.gui.swing.constant.Spaces;
import org.ambrogenea.familyview.gui.swing.model.Arc;
import org.ambrogenea.familyview.gui.swing.model.ImageModel;
import org.ambrogenea.familyview.gui.swing.model.Line;
import org.ambrogenea.familyview.gui.swing.model.Position;
import org.ambrogenea.familyview.gui.swing.model.ResidenceModel;
import org.ambrogenea.familyview.gui.swing.treepanels.CommonTreePanel;
import org.ambrogenea.familyview.model.AncestorPerson;
import org.ambrogenea.familyview.model.Configuration;
import org.ambrogenea.familyview.model.Couple;
import org.ambrogenea.familyview.model.enums.LabelShape;

/**
 *
 * @author Jiri Ambroz
 */
public class RootFamilyPanel extends CommonTreePanel {

    protected final Set<Rectangle> labels;
    protected final AncestorPerson personModel;

    public RootFamilyPanel(AncestorPerson model, Configuration config) {
        super(config);
        this.personModel = model;
        labels = new HashSet<>();
    }

    @Override
    public Position drawMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        Position motherPosition = new Position(childPosition);
        motherPosition.addX(getConfiguration().getMarriageLabelWidth());
        motherPosition.addY(-getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP);

        Position label = new Position(childPosition.getX(), motherPosition.getY()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - getConfiguration().getMarriageLabelHeight());

        drawLabel(label, getConfiguration().getMarriageLabelWidth(), marriageDate);
        drawPerson(motherPosition, mother);
        return motherPosition;
    }

    @Override
    public Position drawFather(Position childPosition, AncestorPerson father) {
        int fatherY = childPosition.getY() - getConfiguration().getAdultImageHeightAlternative()
                - getConfiguration().getAdultImageHeight()
                - getConfiguration().getMarriageLabelHeight() - Spaces.VERTICAL_GAP;
        Position fatherPosition = new Position(childPosition.getX(), fatherY);
        drawPerson(fatherPosition, father);
        return fatherPosition;
    }

    @Override
    public void drawLabel(Position labelPosition, int labelWidth, String text) {
        int labelHeight = getConfiguration().getMarriageLabelHeight() - 6;

        if (configuration.isShowMarriage()) {
            JLabel date = new JLabel(text, JLabel.CENTER);
            date.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, getConfiguration().getAdultFontSize() - 1));
//            date.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.BLACK));
//            date.setBackground(LABEL_BACKGROUND);
            date.setOpaque(false);
            this.add(date);
            date.setBounds(labelPosition.getX(), labelPosition.getY() + 3, labelWidth, labelHeight);
        }

        Rectangle rect = new Rectangle(labelPosition.getX(), labelPosition.getY() + 3, labelWidth - 1, labelHeight);
        labels.add(rect);

    }

    @Override
    public Position drawSpouse(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            Position spouse = new Position(rootPersonPosition);
            spouse.addX(getConfiguration().getMarriageLabelWidth());
            spouse.addY(getConfiguration().getMarriageLabelHeight() + getConfiguration().getAdultImageHeightAlternative());

            Position label = new Position(rootPersonPosition.getX(),
                    rootPersonPosition.getY() + getConfiguration().getAdultImageHeightAlternative() / 2);

            drawPerson(spouse, person.getSpouse());
            drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple().getMarriageDate());
            return spouse;
        }
        return rootPersonPosition;
    }

    @Override
    public Position drawAllSpouses(Position rootPersonPosition, AncestorPerson person) {
        if (person.getSpouse() != null) {
            int spouseDistance = getConfiguration().getAdultImageWidth()
                    + getConfiguration().getMarriageLabelWidth() / 3 + Spaces.SIBLINGS_GAP;

            Position spousePosition = new Position(rootPersonPosition);
            spousePosition.addX(getConfiguration().getMarriageLabelWidth());
            spousePosition.addY(getConfiguration().getCoupleVerticalDifference());

            Position label = new Position(rootPersonPosition.getX(),
                    rootPersonPosition.getY() + getConfiguration().getAdultImageHeightAlternative() / 2);

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                drawPerson(spousePosition, person.getSpouse(index));
                drawLabel(label, getConfiguration().getMarriageLabelWidth(), person.getSpouseCouple(index).getMarriageDate());

                label.addX(spouseDistance);
                spousePosition.addX(spouseDistance);
            }
            spousePosition.addX(-spouseDistance);
            return spousePosition;
        }
        return rootPersonPosition;
    }

    protected int drawAllSpousesWithKids(int husbandXPosition, int y, AncestorPerson person) {
        int spouseXPosition = husbandXPosition;
        if (person.getSpouse() != null) {
            int labelXPosition;
            int labelWidth = getConfiguration().getMarriageLabelWidth();
            int childrenShift = -(Math.max(1, person.getChildrenCount(0)) * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) / 2) + (getConfiguration().getAdultImageWidth() + labelWidth) - Spaces.SIBLINGS_GAP;

            for (int index = 0; index < person.getSpouseCouples().size(); index++) {
                husbandXPosition = spouseXPosition;
                int childrenWidth = Math.max(1, person.getChildrenCount(index)) * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) / 2 - (getConfiguration().getAdultImageWidth() + labelWidth);
                spouseXPosition = spouseXPosition + (getConfiguration().getAdultImageWidth() + labelWidth) + childrenShift + childrenWidth + Spaces.SIBLINGS_GAP;

                drawPerson(new Position(spouseXPosition, y), person.getSpouse(index));
                labelXPosition = spouseXPosition - (getConfiguration().getHalfSpouseLabelSpace());
                drawLabel(new Position(husbandXPosition + getConfiguration().getAdultImageWidth() / 2, y), labelWidth, person.getSpouseCouple(index).getMarriageDate());
                childrenShift = drawChildren(new Position(labelXPosition, y), person.getSpouseCouple(index));
                childrenShift = Math.max(getConfiguration().getHalfSpouseLabelSpace(), childrenShift);
            }
        }
        return spouseXPosition;
    }

    protected void drawSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {
        int spouseGap = 0;
        if (rootChild.getSpouse() != null) {
            spouseGap = (int) (getConfiguration().getAdultImageWidth() / 2 + getConfiguration().getAdultImageWidth() * 0.25);
        }
        Position spousePosition = new Position(rootSibling.getX() + spouseGap, rootSibling.getY());

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            int lineY = rootSibling.getY() - (getConfiguration().getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
            drawLine(new Position(rootSibling.getX(), lineY),
                    new Position(spousePosition.getX(), lineY),
                    Line.SIBLINGS);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(spousePosition, rootChild);
    }

    protected void drawSiblingsAroundWifes(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap;
        if (!rootChild.getSpouseID().isEmpty() && lastSpouseX == 0) {
            spouseGap = rootSibling.getX() + (getConfiguration().getAdultImageWidth() + getConfiguration().getMarriageLabelWidth()) * rootChild.getSpouseCouples().size();
        } else {
            spouseGap = lastSpouseX;
        }

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        drawOlderSiblings(rootSibling, rootChild);
        drawYoungerSiblings(new Position(spouseGap, rootSibling.getY()), rootChild);
    }

    private void addLineAboveSpouse(Position rootSibling, int spouseGap) {
        int verticalShift = (configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;
        Position linePosition = new Position(rootSibling);
        linePosition.addY(-verticalShift);
        drawLine(linePosition, new Position(linePosition.getX() + spouseGap, linePosition.getY()), Line.SIBLINGS);
    }

    @Override
    public int drawChildren(Position fatherPosition, Couple spouseCouple) {
        int childrenX = fatherPosition.getX();
        int fatherY = fatherPosition.getY();

        int childrenWidth = 0;
        if (spouseCouple != null) {
            int childrenCount = spouseCouple.getChildren().size();
            if (getConfiguration().isShowChildren() && childrenCount > 0) {
                int childrenLineY = fatherY + (int) (1.5 * getConfiguration().getAdultImageHeightAlternative()) + getConfiguration().getMarriageLabelHeight() / 2 + Spaces.VERTICAL_GAP;
                Position lineLevel = new Position(fatherPosition.getX(), childrenLineY);
                int labelY = fatherY + (getConfiguration().getAdultImageHeightAlternative() + getConfiguration().getMarriageLabelHeight()) / 2;
                drawLine(lineLevel, new Position(fatherPosition.getX(), labelY), Line.SIBLINGS);

                int childrenY = childrenLineY + (getConfiguration().getSiblingImageHeight() + Spaces.VERTICAL_GAP + getConfiguration().getMarriageLabelHeight()) / 2;
                childrenWidth = childrenCount * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) - Spaces.HORIZONTAL_GAP;
                int startXPosition = childrenX + getConfiguration().getSiblingImageWidth() / 2 - childrenWidth / 2;

                Position childrenPosition = new Position(startXPosition, childrenY);

                for (int i = 0; i < childrenCount; i++) {
                    int childXPosition = startXPosition + i * (getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP);
                    if (i == 0 && childrenCount > 1) {
                        addRoundChildrenLine(childXPosition, childrenY, childrenX);
                    } else if (i == childrenCount - 1) {
                        addRoundChildrenLine(childXPosition, childrenY, childrenX);
                    } else {
                        addStraightChildrenLine(childXPosition, childrenY, childrenX);
                    }
                    TODO: //draw spouse of the children
                    drawPerson(childrenPosition, spouseCouple.getChildren().get(i));
                    childrenPosition.addX(getConfiguration().getSiblingImageWidth() + Spaces.HORIZONTAL_GAP);
                }
                childrenWidth = childrenWidth / 2;

                if (getConfiguration().isShowHeraldry()) {
                    addChildrenHeraldry(new Position(childrenX, childrenY), spouseCouple);
                }
            }
        }
        return childrenWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(LINE_COLOR);

        int lineStrokeExtra = 0;
        if (configuration.getAdultFontSize() >= 18) {
            lineStrokeExtra = 1;
        }

        int cornerSize = 20;
        for (Line line : lines) {
            g2.setStroke(new BasicStroke(lineStrokeExtra + line.getType()));
            g2.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }

        for (Rectangle rect : labels) {
            g2.setColor(LABEL_BACKGROUND);
            g2.setStroke(new BasicStroke(lineStrokeExtra + 2));

            if (configuration.getLabelShape().equals(LabelShape.OVAL)) {
                g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
                g2.setColor(LINE_COLOR);
                g2.drawRoundRect(rect.x, rect.y, rect.width, rect.height, cornerSize, cornerSize);
            } else if (configuration.getLabelShape().equals(LabelShape.RECTANGLE)) {
                g2.fillRect(rect.x, rect.y, rect.width, rect.height);
                g2.setColor(LINE_COLOR);
                g2.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }

        g2.setStroke(new BasicStroke(lineStrokeExtra + 1));
        g2.setColor(LINE_COLOR);
        for (Arc arc : arcs) {
            g2.setStroke(new BasicStroke(lineStrokeExtra + arc.getType()));
            g2.drawArc(arc.getLeftUpperCorner().getX(), arc.getLeftUpperCorner().getY(), 2 * Arc.RADIUS, 2 * Arc.RADIUS, arc.getStartAngle(), Arc.ANGLE_SIZE);
        }

        for (ImageModel image : images) {
            g2.drawImage(image.getImage(), image.getX() - image.getWidth() / 2, image.getY() - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
        }

        g2.setStroke(new BasicStroke(lineStrokeExtra + 2));
        for (ResidenceModel residence : residences) {
            g2.setColor(cityRegister.get(residence.getCity()));
            g2.drawRoundRect(residence.getX(), residence.getY(), Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE, Spaces.RESIDENCE_SIZE / 2, Spaces.RESIDENCE_SIZE / 2);
        }

    }

    protected void addLineToParentsVertical(Position child) {
        int endY = child.getY() - getConfiguration().getAdultImageHeight()
                - getConfiguration().getAdultImageHeightAlternative() / 2
                - configuration.getMarriageLabelHeight() / 2 - Spaces.VERTICAL_GAP;
        lines.add(new Line(child.getX(), child.getY(), child.getX(), endY));
    }

    protected void addStraightChildrenLine(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;

        Line vertical = new Line(startX, rootSiblingY - verticalShift, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        lines.add(vertical);
    }

    protected void addRoundChildrenLine(int startX, int rootSiblingY, int rootSiblingX) {
        int verticalShift = (configuration.getAdultImageHeight() + Spaces.VERTICAL_GAP) / 2;

        int startAngle;
        int xRadius;
        int yRadius;
        Arc arc;
        if (startX < rootSiblingX) {
            //older siblings
            yRadius = Arc.RADIUS;
            xRadius = Arc.RADIUS;
            startAngle = 90;
            arc = new Arc(new Position(startX, rootSiblingY - verticalShift), startAngle);
            arcs.add(arc);
        } else if (startX > rootSiblingX) {
            //younger siblings
            yRadius = Arc.RADIUS;
            xRadius = -Arc.RADIUS;
            startAngle = 0;
            arc = new Arc(new Position(startX - 2 * Arc.RADIUS, rootSiblingY - verticalShift), startAngle);
            arcs.add(arc);
        } else {
            xRadius = 0;
            yRadius = 0;
        }

        addLineAboveSpouse(new Position(startX + xRadius, rootSiblingY), rootSiblingX - startX);
        Line vertical = new Line(startX, rootSiblingY - verticalShift + yRadius, startX, rootSiblingY);
        vertical.setType(Line.SIBLINGS);
        lines.add(vertical);
    }

}
