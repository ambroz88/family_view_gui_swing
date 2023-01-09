package cz.ambrogenea.familyvision.gui.swing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.controller.TreeShapeConfigurationController;
import cz.ambrogenea.familyvision.controller.VisualConfigurationController;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.gui.swing.dto.VisualConfiguration;

public class Config {

    private static VisualConfiguration visualConfiguration;
    private static TreeShapeConfiguration treeShapeConfiguration;

    public static VisualConfiguration visual() {
        if (visualConfiguration == null) {
            try {
                visualConfiguration = JsonParser.get().readValue(new VisualConfigurationController().get(), VisualConfiguration.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return visualConfiguration;
    }

    public static TreeShapeConfiguration treeShape() {
        if (treeShapeConfiguration == null) {
            try {
                treeShapeConfiguration = JsonParser.get().readValue(new TreeShapeConfigurationController().get(), TreeShapeConfiguration.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return treeShapeConfiguration;
    }

}
