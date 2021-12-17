package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.put.poznan.transformer.exceptions.InvalidJson;
import pl.put.poznan.transformer.exceptions.JsonProcessingError;
import pl.put.poznan.transformer.logic.Json;

public class JsonClarifierDecorator extends JsonDecorator {
    public JsonClarifierDecorator(Json content) {
        super(content);
    }

    @Override
    public String getData() throws JsonProcessingError {
        try {
            return clarify(super.getData());
        }
        catch (JsonProcessingException ex) {
            throw new JsonProcessingError("Error in JSON processing");
        }
    }

    public String clarify(String json) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.toPrettyString();
        }
        catch (JsonProcessingException ex) {
            throw ex;
        }
    }
}
