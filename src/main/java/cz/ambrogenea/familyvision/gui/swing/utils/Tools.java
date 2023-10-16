package cz.ambrogenea.familyvision.gui.swing.utils;

import java.io.InputStream;
import java.util.Properties;

public class Tools {

    public static String cityShortVersion(String placeName) {
        InputStream propertiesInputStream = Tools.class.getResourceAsStream("/text/CityShortcuts.properties");

        if (propertiesInputStream != null) {
            Properties prop = FileIO.loadProperties(propertiesInputStream);

            String normalizeName = placeName.toLowerCase().replace(" ", "");
            if (prop.containsKey(normalizeName)) {
                return prop.getProperty(normalizeName);
            }
        }

        return placeName;
    }

}
