package org.dev.component;

/**
 * Description: easy string desensitizer
 * Created by EXCaster on 2024/4/11
 */
public class StringDesensitizer {
    /**
     * Desensitizes a part of the string by replacing characters from startIndex to endIndex with a maskChar.
     *
     * @param str The input string to be desensitized.
     * @param startIndex The starting index for desensitization (inclusive).
     * @param endIndex The ending index for desensitization (exclusive).
     * @param maskChar The character to use as the mask.
     * @return A new string with the specified range replaced by the mask character.
     * @throws IllegalArgumentException If the input parameters are invalid.
     */
    public static String desensitize(String str, int startIndex, int endIndex, char maskChar) {
        if (str == null) throw new IllegalArgumentException("Input string cannot be null.");
        if (str.isEmpty()) return str;  // Return early for empty string.
        if (startIndex < 0 || endIndex > str.length() || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid start or end index.");
        }

        StringBuilder sb = new StringBuilder(str);
        char[] maskArray = new char[endIndex - startIndex];
        java.util.Arrays.fill(maskArray, maskChar);
        sb.replace(startIndex, endIndex, new String(maskArray));

        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            String str = "1234567890";
            String desensitized = desensitize(str, 3, 7, '*');
            System.out.println("Original: " + str);
            System.out.println("Desensitized: " + desensitized);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
