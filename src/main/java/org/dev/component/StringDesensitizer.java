package org.dev.component;

/**
 * Description: easy string desensitizer
 * Created by EXCaster on 2024/4/11
 */
public class StringDesensitizer {
    public static String desensitize(String str, int startIndex, int endIndex, char maskChar) {
        if (str == null || str.isEmpty() || startIndex < 0 || endIndex > str.length() || startIndex > endIndex) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        for (int i = startIndex; i < endIndex; i++) {
            sb.setCharAt(i, maskChar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "1234567890";
        String desensitized = desensitize(str, 3, 7, '*');
        System.out.println("Original: " + str);
        System.out.println("Desensitized: " + desensitized);
    }
}
