package cz.ambrogenea.familyvision.gui.swing.utils;

import java.net.URL;
import java.util.Properties;

public class Tools {

    public static String cityShortVersion(String placeName) {
        String filePath;
        URL fileURL = cz.ambrogenea.familyvision.utils.Tools.class.getResource("/text/CityShortcuts.properties");

        if (fileURL != null) {
            filePath = fileURL.getPath();
            Properties prop = FileIO.loadProperties(filePath);

            String normalizeName = placeName.toLowerCase().replace(" ", "");
            if (prop.containsKey(normalizeName)) {
                return prop.getProperty(normalizeName);
            }
        }

        return placeName;
    }

}
