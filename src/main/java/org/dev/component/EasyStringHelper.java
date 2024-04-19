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
        return new StringBuilder(str).reverse().toString();
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

    public static String removeDuplicates(String str) {
        return Arrays.stream(str.split("")).distinct().collect(Collectors.joining());
    }

    public static void main(String[] args) {
        String originalString = UUID.randomUUID().toString();
        System.out.println("originalString: " + originalString);
        String reversedString = EasyStringHelper.reverseString(originalString);
        System.out.println("Reversed String: " + reversedString);
        String removeDuplicates = removeDuplicates(originalString);
        System.out.println("removeDuplicates String: " + removeDuplicates);
        String sentence = "the quick brown fox jumps over the lazy dog";
        String[] sortedWords = EasyStringHelper.splitAndSort(sentence);
        System.out.println("Sorted Words: " + Arrays.toString(sortedWords));

        Date currentDate = new Date();
        String formattedDate = EasyStringHelper.formatDateString(currentDate, "yyyy-MM-dd HH:mm:ss");
        System.out.println("Formatted Date: " + formattedDate);
    }
}
