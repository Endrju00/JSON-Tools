package pl.put.poznan.transformer.logic.decorators;

import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.decorators.JsonDecorator;

/**
 * Class purpose is to minify provided JSON.
 */
public class JsonMinifierDecorator extends JsonDecorator {

    /**
     * Constructor calling base class constructor
     *
     * @param content is an object that will be minified
     */
    public JsonMinifierDecorator(Json content) {
        super(content);
    }

    /**
     * Method overrides its superclass method by minifying the output
     *
     * @return minified JSON string
     */
    @Override
    public String getData() {
        return minify(super.getData());
    }

    /**
     * Method removes whitespace characters
     * @param json String that will be minified
     * @return single line JSON without whitespace characters
     */
    public String minify(String json) {
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
