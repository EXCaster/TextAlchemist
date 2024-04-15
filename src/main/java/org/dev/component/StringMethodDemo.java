package org.dev.component;

import java.util.Arrays;
import java.util.UUID;

/**
 * Description: StringMethodDemo
 * Created by EXCaster on 2024/4/15
 */

public class StringMethodDemo {
    public static void main(String[] args) {
        String str1 = UUID.randomUUID().toString();
        String str2 = "| by StringMethodDemo";

        // length()
        System.out.println("Length of str1: " + str1.length());

        // trim()
        System.out.println("Trimmed str1: '" + str1.trim() + "'");

        // charAt()
        System.out.println("Character at index 3 in str1: " + str1.charAt(3));

        // indexOf()
        System.out.println("Index of 'M' in str2: " + str2.indexOf('M'));
        System.out.println("Index of 'Method' in str2: " + str2.indexOf("Method"));

        // substring()
        System.out.println("Substring of str1 from index 7: '" + str1.substring(7) + "'");
        System.out.println("Substring of str1 from index 7 to 12: '" + str1.substring(7, 12) + "'");

        // concat()
        String str3 = str1.concat(str2);
        System.out.println("Concatenated string: '" + str3 + "'");

        // replace()
        String str4 = str2.replace("by", "to");
        System.out.println("Replaced string: '" + str4 + "'");

        // split()
        String[] words = str2.split(" ");
        System.out.println("Split str2 into array: " + Arrays.toString(words));

        // toLowerCase() and toUpperCase()
        System.out.println("Lower case of str2: '" + str2.toLowerCase() + "'");
        System.out.println("Upper case of str1: '" + str1.toUpperCase() + "'");

        // equals() and equalsIgnoreCase()
        System.out.println("str1 equals str4: " + str1.equals(str4));
        System.out.println("str1 equalsIgnoreCase str4: " + str1.equalsIgnoreCase(str4));
    }
}
