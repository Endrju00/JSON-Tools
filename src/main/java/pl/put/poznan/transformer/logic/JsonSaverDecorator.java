package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonSaverDecorator extends JsonDecorator {

    private List<String> to_save;

    public JsonSaverDecorator(Json content) {
        super(content);
    }

    public JsonSaverDecorator(Json content, List<String> to_save) {
        this(content);
        this.to_save = to_save;
    }

    public String getData() {
        return save(super.getData());
    }

    public String save(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            saveNodes(node, to_save);
            return node.toPrettyString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void saveNodes(JsonNode root, List<String> to_save){
        if(root.isObject()){
            List<String> children = new ArrayList<>();
            Iterator<String> NameIterator = root.fieldNames();
            while(NameIterator.hasNext()) {
                String fieldName = NameIterator.next();
                children.add(fieldName);
            }
            for(String child: children) {
                if (!to_save.contains(child)) {
                    ((ObjectNode) root).remove(child);
                } else {
                    JsonNode fieldValue = root.get(child);
                    if (fieldValue.isObject() || fieldValue.isArray()) {
                        saveNodes(fieldValue, to_save);
                    }
                }
            }
        } else if(root.isArray()) {
            ArrayNode rootArray = (ArrayNode) root;
            for (int i = 0; i < rootArray.size(); i++) {
                JsonNode ElementNode = rootArray.get(i);
                if (ElementNode.isObject() || ElementNode.isArray()) {
                    saveNodes(ElementNode, to_save);
                }
            }
        }
    }
}