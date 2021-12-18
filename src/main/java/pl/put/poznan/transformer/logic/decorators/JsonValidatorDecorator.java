package pl.put.poznan.transformer.logic.decorators;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.InvalidJson;
import pl.put.poznan.transformer.logic.Json;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class purpose is to minify provided JSON.
 * @version 1.3.1
 */
public class JsonValidatorDecorator extends JsonDecorator {
    private final Logger logger = LoggerFactory.getLogger(JsonValidatorDecorator.class);

    /**
     * Constructor calling base class constructor
     *
     * @param content is component
     */
    public JsonValidatorDecorator(Json content) {
        super(content);
    }

    /**
     * Method returns provided JSON string or throws an error
     * when JSON is invalid
     *
     * @return validated JSON
     * @throws InvalidJson in case JSON is invalid
     */
    @Override
    public String getData() throws InvalidJson {
        if(isValidJson(super.getData())){
            return super.getData();
        }
        else {
            if(logger.isDebugEnabled())
                logger.debug("Provided json is invalid");

            throw new InvalidJson("Json format is invalid");
        }
    }

    /**
     * Uses Jackson library to check whether JSON is valid
     *
     * @param json JSON that has to be validated
     * @return boolean value indicating result of validation
     */
    public boolean isValidJson(String json) {
        boolean valid = true;
        try {
            new ObjectMapper().readTree(json);
        }
        catch (Exception e) {
            valid = false;
        }

        if(valid) {
            valid = areBracketsBalanced(json);
        }

        return valid;
    }

    /**
     * Checks whether brackets are balanced and checks if
     * there are no extra letters after a bracket or at the
     * end or beginning of the JSON
     *
     * @param json JSON that has to be validated
     * @return boolean value indicating result of validation
     */
    private boolean areBracketsBalanced(String json) {
        Stack<Character> stack = new Stack<Character>();
        boolean firstAddition = false;

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (stack.isEmpty() && i != (json.length() - 1) && firstAddition && !Character.isWhitespace(ch)) {
                return false;
            }

            if (ch == '{' || ch == '[') {
                stack.add(ch);

                if(!firstAddition)
                    firstAddition = true;
            }
            else if(ch == '}' || ch == ']') {
                if (!stack.isEmpty() && ((stack.peek() == '{' && ch == '}') || (stack.peek() == '[' && ch == ']'))) {
                    stack.pop();
                }
                else {
                    if(logger.isDebugEnabled())
                        logger.debug("Error in brackets: stack is empty or no matching brackets");

                    return false;
                }
            }
        }

        return true;
    }
}
