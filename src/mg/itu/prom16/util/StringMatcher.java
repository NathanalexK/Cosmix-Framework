package mg.itu.prom16.util;

import java.util.List;

public class StringMatcher {
    public static boolean isMatch(String pattern, String input) {
        if (pattern.endsWith("/*")) {
            String basePattern = pattern.substring(0, pattern.length() - 1);
            return input.equals(basePattern.substring(0, pattern.length() - 2)) || input.startsWith(basePattern);
        }
        return input.equals(pattern) || (input.startsWith(pattern) && input.length() > pattern.length() && input.charAt(pattern.length()) == '?');
    }


    public static void main(String[] args) {
        String pattern = "emp/form/*";
        String[] inputs = new String[]{"emp/form", "emp/forms", "emp/form?id=1", "emp/form/update", "emp/form/update?id=1"};

        System.out.println("Pattern: " + pattern);
        for(String input: inputs) {
            System.out.println(input + ": " + StringMatcher.isMatch(pattern, input));
        }
    }
}
