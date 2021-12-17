package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.put.poznan.transformer.logic.Json;

import java.util.List;

public class JsonCutterDecorator extends JsonDecorator {

    private List<String> to_remove;

    public JsonCutterDecorator(Json content) {
        super(content);
    }

    public JsonCutterDecorator(Json content, List<String> to_remove) {
        this(content);
        this.to_remove = to_remove;
    }

    public String getData() {
        return cut(super.getData());
    }

    public String cut(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            deleteNodes(node, to_remove);
            return node.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void deleteNodes(JsonNode root, List<String> to_remove){
        for(String remove_child: to_remove) {
            List<JsonNode> parents = root.findParents(remove_child);
            for(JsonNode parent: parents) {
                try {
                    ((ObjectNode) parent).remove(remove_child);
                } catch (Exception e) {
                    System.out.println("Could not remove a node!");
                    e.printStackTrace();
                }
            }
        }
    }
}
