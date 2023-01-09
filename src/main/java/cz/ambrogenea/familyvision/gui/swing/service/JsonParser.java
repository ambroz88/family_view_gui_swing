package cz.ambrogenea.familyvision.gui.swing.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private static ObjectMapper objectMapper;

    public static ObjectMapper get() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }

}
