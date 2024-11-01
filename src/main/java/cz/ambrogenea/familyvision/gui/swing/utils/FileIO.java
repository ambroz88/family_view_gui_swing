package cz.ambrogenea.familyvision.gui.swing.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FileIO {

    public static Properties loadProperties(InputStream inputStream) {
        Properties propertyFile = new Properties();

        try {
            propertyFile.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return propertyFile;
    }

    public static File copyInputStreamToFile(InputStream inputStream, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
        return file;
    }

}
