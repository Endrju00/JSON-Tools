package pl.put.poznan.transformer.logic.decorators;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.exceptions.InvalidJson;
import pl.put.poznan.transformer.logic.Json;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidatorDecorator extends JsonDecorator {
    public JsonValidatorDecorator(Json content) {
        super(content);
    }
    private final Logger logger = LoggerFactory.getLogger(JsonValidatorDecorator.class);

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
