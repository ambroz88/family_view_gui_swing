package cz.ambrogenea.familyvision.gui.swing.service;

import cz.ambrogenea.familyvision.gui.swing.dto.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.gui.swing.dto.VisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.http.Connections;

import java.io.IOException;

public class Config {

    private static VisualConfiguration visualConfiguration;
    private static TreeShapeConfiguration treeShapeConfiguration;

    public static VisualConfiguration visual() {
        if (visualConfiguration == null) {
            try {
                visualConfiguration = Connections.getVisualConfiguration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return visualConfiguration;
    }

    public static TreeShapeConfiguration treeShape() {
        if (treeShapeConfiguration == null) {
            try {
                treeShapeConfiguration = Connections.getThreeShapeConfiguration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return treeShapeConfiguration;
    }

}
