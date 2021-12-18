package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.InvalidJson;
import pl.put.poznan.transformer.exceptions.JsonProcessingError;
import pl.put.poznan.transformer.logic.Json;

public class JsonClarifierDecorator extends JsonDecorator {
    public JsonClarifierDecorator(Json content) {
        super(content);
    }
    private final Logger logger = LoggerFactory.getLogger(JsonClarifierDecorator.class);

    @Override
    public String getData() throws JsonProcessingError {
        try {
            return clarify(super.getData());
        }
        catch (JsonProcessingException e) {
            logger.debug(e.getClass().getCanonicalName() + ": error during JSON processing");
            throw new JsonProcessingError("Error in JSON processing");
        }
    }

    public String clarify(String json) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.toPrettyString();
        }
        catch (JsonProcessingException e) {
            throw e;
        }
    }
}
