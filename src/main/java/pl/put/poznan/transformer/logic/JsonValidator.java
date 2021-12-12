package pl.put.poznan.transformer.logic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonValidator {
    public static boolean isValidJson(String json) {
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

    private static boolean areBracketsBalanced(String json) {
        Queue<Character> queue = new LinkedList<>();

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (queue.isEmpty() && i != (json.length() - 1) && i != 0 && !Character.isWhitespace(ch)) {
                return false;
            }

            if (ch == '{' || ch == '[') {
                queue.add(ch);
            }
            else if(ch == '}' || ch == ']') {
                if (!queue.isEmpty() && ((queue.peek() == '{' && ch == '}') || (queue.peek() == '[' && ch == ']'))) {
                    queue.remove();
                }
                else {
                    return false;
                }
            }
        }

        return true;
    }
}
