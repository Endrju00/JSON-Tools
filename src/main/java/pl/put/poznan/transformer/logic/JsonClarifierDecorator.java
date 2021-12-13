package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonClarifierDecorator extends JsonDecorator {
    public JsonClarifierDecorator(Json content) {
        super(content);
    }

    @Override
    public String getData() {
        return clarify(super.getData());
    }

    public String clarify(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.toPrettyString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
