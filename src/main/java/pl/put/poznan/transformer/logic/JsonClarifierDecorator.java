package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;

        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (node != null) {
            return node.toPrettyString();
        }
        else {
            return "";
        }
    }
}
