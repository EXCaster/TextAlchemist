package org.dev.component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;
import java.util.UUID;

/**
 * Description: Simple string manipulation aid
 * Created by EXCaster on 2024/4/7
 */
public class EasyStringHelper {
    public static String reverseString(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }
        return reversed.toString();
    }

    public static String[] splitAndSort(String str) {
        String[] words = str.split("\\s+");
        Arrays.sort(words);
        return words;
    }

    public static String formatDateString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        String originalString = UUID.randomUUID().toString();
        String reversedString = EasyStringHelper.reverseString(originalString);
        System.out.println("Reversed String: " + reversedString);

        String sentence = "the quick brown fox jumps over the lazy dog";
        String[] sortedWords = EasyStringHelper.splitAndSort(sentence);
        System.out.println("Sorted Words: " + Arrays.toString(sortedWords));

        Date currentDate = new Date();
        String formattedDate = EasyStringHelper.formatDateString(currentDate, "yyyy-MM-dd HH:mm:ss");
        System.out.println("Formatted Date: " + formattedDate);
    }
}
