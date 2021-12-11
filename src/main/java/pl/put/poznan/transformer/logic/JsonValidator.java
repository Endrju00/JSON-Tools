package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonValidator {
    public static boolean isValidJson(String json) {
        boolean valid = true;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(json);
            JsonNode jsonObj = mapper.readTree(parser);
            System.out.println(jsonObj);
        }
        catch(JsonParseException ex) {
            valid = false;
        }
        catch(IOException ex) {
            valid = false;
        }

        return valid;
    }
}
