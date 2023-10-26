package cz.ambrogenea.familyvision.gui.swing.utils;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FileIO {

    public static File loadFileFromResources(String fileName) {
        String filePath;
        URL fileURL = ClassLoader.getSystemResource(fileName);

        if (fileURL != null) {
            filePath = fileURL.getPath();
            return new File(filePath);
        }
        System.out.println("File " + fileName + " cannot be open or does not exist in resources.");
        return null;
    }

    public static Properties loadProperties(InputStream inputStream) {
        Properties propertyFile = new Properties();

        try {
            propertyFile.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return propertyFile;
    }
}
