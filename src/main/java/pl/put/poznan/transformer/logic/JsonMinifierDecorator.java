package pl.put.poznan.transformer.logic;

import java.util.Arrays;
import java.util.List;

public class JsonMinifierDecorator extends JsonDecorator {
    public JsonMinifierDecorator(Json content) {
        super(content);
    }

    @Override
    public String getData() {
        return minify(super.getData());
    }

    public static String minify(String json) {
        boolean inQuotes = false;
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if(c == '\"') {
                inQuotes = !inQuotes;
            }

            if(!inQuotes && Character.isWhitespace(c)) {
                continue;
            }

            output.append(c);
        }

        return  output.toString();
    }
}
