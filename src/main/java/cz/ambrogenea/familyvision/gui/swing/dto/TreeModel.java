package cz.ambrogenea.familyvision.gui.swing.dto;

import java.util.ArrayList;
import java.util.Set;

public record TreeModel(
        String treeName,
        PersonRecord rootPerson,
        Set<PersonRecord> persons,
        Set<Marriage> marriages,
        Set<Line> lines,
        Set<Arc> arcs,
        Set<ImageModel> images,
        Set<ResidenceDto> residences,
        ArrayList<String> cityRegister,
        PageSetup pageSetup
) {
}
