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
        String str2 = "    | by StringMethodDemo    ";
        StringBuilder output = new StringBuilder();
        output.append("Length of str1: ").append(str1.length()).append("\n");
        output.append("Character at index 3 in str1: ").append(str1.charAt(3)).append("\n");
        output.append("Index of 'M' in str2: ").append(str2.indexOf('M')).append("\n");
        output.append("Index of 'Method' in str2: ").append(str2.indexOf("Method")).append("\n");
        output.append("Substring of str1 from index 7: '").append(str1.substring(7)).append("'\n");
        output.append("Substring of str1 from index 7 to 12: '").append(str1, 7, 12).append("'\n");
        String str3 = str1.concat(str2);
        output.append("Concatenated string: '").append(str3).append("'\n");
        String str4 = str2.replace("by", "to");
        output.append("Replaced string: '").append(str4).append("'\n");
        String[] words = str2.split(" ");
        output.append("Split str2 into array: ").append(Arrays.toString(words)).append("\n");
        output.append("Lower case of str2: '").append(str2.toLowerCase()).append("'\n");
        output.append("Upper case of str1: '").append(str1.toUpperCase()).append("'\n");
        output.append("str1 equals str4: ").append(str1.equals(str4)).append("\n");
        output.append("str1 equalsIgnoreCase str4: ").append(str1.equalsIgnoreCase(str4)).append("\n");
        output.append("Trimmed str2: '").append(str2.trim()).append("'\n");

        System.out.println(output.toString());
    }
}
