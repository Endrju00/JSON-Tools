package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.JsonProcessingError;
import pl.put.poznan.transformer.logic.Json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonSaverDecorator extends JsonDecorator {
    private final Logger logger = LoggerFactory.getLogger(JsonSaverDecorator.class);

    private List<String> toSave;

    public JsonSaverDecorator(Json content) {
        super(content);
    }

    public JsonSaverDecorator(Json content, List<String> toSave) {
        this(content);
        this.toSave = toSave;
    }

    @Override
    public String getData() throws JsonProcessingError {
        try {
            return save(super.getData());
        }
        catch (JsonProcessingException e) {
            logger.debug(e.getClass().getCanonicalName() + ": error during JSON processing");
            throw new JsonProcessingError("Error in JSON processing");
        }
    }

    public String save(String json) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            saveNodes(node, toSave);
            return node.toPrettyString();
        }
        catch (JsonProcessingException e) {
            throw e;
        }
    }

    private void saveNodes(JsonNode root, List<String> toSave){
        if(root.isObject()){
            List<String> children = new ArrayList<>();
            Iterator<String> NameIterator = root.fieldNames();
            while(NameIterator.hasNext()) {
                String fieldName = NameIterator.next();
                children.add(fieldName);
            }
            for(String child: children) {
                if (!toSave.contains(child)) {
                    ((ObjectNode) root).remove(child);
                }
                else {
                    JsonNode fieldValue = root.get(child);
                    if (fieldValue.isObject() || fieldValue.isArray()) {
                        saveNodes(fieldValue, toSave);
                    }
                }
            }
        }
        else if(root.isArray()) {
            ArrayNode rootArray = (ArrayNode) root;
            for (int i = 0; i < rootArray.size(); i++) {
                JsonNode ElementNode = rootArray.get(i);
                if (ElementNode.isObject() || ElementNode.isArray()) {
                    saveNodes(ElementNode, toSave);
                }
            }
        }
    }
}