package cz.ambrogenea.familyvision.gui.swing.service;

import cz.ambrogenea.familyvision.gui.swing.dto.PersonVisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeShapeConfiguration;
import cz.ambrogenea.familyvision.gui.swing.dto.TreeVisualConfiguration;
import cz.ambrogenea.familyvision.gui.swing.http.Connections;

import java.io.IOException;

public class Config {

    private static TreeVisualConfiguration treeVisualConfiguration;
    private static PersonVisualConfiguration personVisualConfiguration;
    private static TreeShapeConfiguration treeShapeConfiguration;

    public static TreeVisualConfiguration tree() {
        if (treeVisualConfiguration == null) {
            try {
                treeVisualConfiguration = Connections.getTreeVisualConfiguration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return treeVisualConfiguration;
    }

    public static PersonVisualConfiguration person() {
        if (personVisualConfiguration == null) {
            try {
                personVisualConfiguration = Connections.getPersonVisualConfiguration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return personVisualConfiguration;
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
