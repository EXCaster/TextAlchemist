package org.dev.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: Calculates the md5 value of the string
 * Created by EXMaster on 2024/3/27
 */
public class MD5Example {
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String yourString = "All matter originates and exists only by virtue of a force which brings the particles of an atom to vibration and holds this most minute solar system of the atom together. . . . We must assume behind this force the existence of a conscious and intelligent Mind. This Mind is the matrix of all matter";
        System.out.println("MD5 of '" + yourString + "' is: " + getMD5(yourString));
    }
}
