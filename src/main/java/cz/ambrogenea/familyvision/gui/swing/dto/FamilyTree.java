package cz.ambrogenea.familyvision.gui.swing.dto;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public record FamilyTree(
        Long id,
        String treeName
) {
    @Override
    public String toString() {
        return treeName;
    }
}
