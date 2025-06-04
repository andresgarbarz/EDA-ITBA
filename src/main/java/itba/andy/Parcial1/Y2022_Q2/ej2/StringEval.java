package itba.andy.Parcial1.Y2022_Q2.ej2;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BiFunction;

public class StringEval {
    public String evaluate(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "";
        }

        Scanner line = new Scanner(expression).useDelimiter("\\s+");

        HashMap<String, BiFunction<String, String, String>> operations = new HashMap<>();
        operations.put("+", (a, b) -> a + b);
        operations.put("-", (a, b) -> a.replaceFirst(b, ""));
        operations.put("*", (a, b) -> {
            StringBuilder result = new StringBuilder();
            int i = 0, j = 0;
            int alen = a.length(), blen = b.length();
            while (i < alen || j < blen) {
                if (i < alen) {
                    result.append(a.charAt(i++));
                }
                if (j < blen) {
                    result.append(b.charAt(j++));
                }
            }
            return result.toString();
        });
        operations.put("/", (a, b) -> {
            String result = a;
            for (int i = 0; i < b.length(); i++) {
                result = result.replaceAll(b.substring(i, i + 1), "");
            }
            return result;
        });
        operations.put("^", (a, b) -> {
            StringBuilder result = new StringBuilder();
            for (int i = 1; i <= b.length(); i++) {
                result.append(a).append(b.substring(0, i));
            }
            return result.toString();
        });

        Stack<String> stack = new Stack<>();
        while (line.hasNext()) {
            String token = line.next();
            if (operations.containsKey(token)) {
                String b = stack.pop();
                String a = stack.pop();
                stack.push(operations.get(token).apply(a, b));
            } else {
                stack.push(token);
            }
        }
        line.close();
        return stack.pop();
    }
}
