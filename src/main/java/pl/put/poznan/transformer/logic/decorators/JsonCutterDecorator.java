package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.JsonCutterError;
import pl.put.poznan.transformer.exceptions.JsonProcessingError;
import pl.put.poznan.transformer.logic.Json;

import java.util.List;

public class JsonCutterDecorator extends JsonDecorator {
    private final Logger logger = LoggerFactory.getLogger(JsonCutterDecorator.class);

    private List<String> toRemove;

    public JsonCutterDecorator(Json content) {
        super(content);
    }

    public JsonCutterDecorator(Json content, List<String> toRemove) {
        this(content);
        this.toRemove = toRemove;
    }

    public String getData() throws JsonProcessingError {
        try {
            return cut(super.getData());
        }
        catch (JsonProcessingException ex) {
            if(logger.isDebugEnabled())
                logger.debug("Error during JSON processing");

            throw new JsonProcessingError("Error in JSON processing");
        }
    }

    public String cut(String json) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            deleteNodes(node, toRemove);
            return node.toPrettyString();
        }
        catch (JsonProcessingException ex) {
            throw ex;
        }
    }

    private void deleteNodes(JsonNode root, List<String> toRemove) throws JsonCutterError {
        for(String removeChild: toRemove) {
            List<JsonNode> parents = root.findParents(removeChild);
            for(JsonNode parent: parents) {
                try {
                    ((ObjectNode) parent).remove(removeChild);
                }
                catch (Exception e) {
                    if(logger.isDebugEnabled())
                        logger.debug("Error during cutting, parent: " + parent + " child: " + removeChild);

                    throw new JsonCutterError("Error during cutting");
                }
            }
        }
    }
}
