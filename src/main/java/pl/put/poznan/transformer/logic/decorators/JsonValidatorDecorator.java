package pl.put.poznan.transformer.logic.decorators;

import java.io.IOException;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.decorators.JsonDecorator;

public class JsonValidatorDecorator extends JsonDecorator {
    public JsonValidatorDecorator(Json content) {
        super(content);
    }

    @Override
    public String getData() {
        if(isValidJson(super.getData())){
            return super.getData();
        }
        else {
            return "Invalid Json";
        }
    }

    public boolean isValidJson(String json) {
        boolean valid = true;
        try {
            new Gson().getAdapter(JsonObject.class).fromJson(json);
        }
        catch (IOException e) {
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
                    System.out.println("Second" + ch);
                    return false;
                }
            }
        }
        return true;
    }
}
