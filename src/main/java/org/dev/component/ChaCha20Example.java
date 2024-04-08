package org.dev.component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * Description: A ChaCha20 encryption and decryption tool
 * Created by EXCaster on 2024/4/8
 */
public class ChaCha20Example {
    // Constants for ChaCha20 algorithm parameters
    private static final int NONCE_SIZE = 12; // Nonce size in bytes (96 bits)
    private static final int KEY_SIZE = 32; // Key size in bytes (256 bits)
    private static final int BLOCK_SIZE = 64; // Block size in bytes (512 bits)
    private static final int[] SIGMA = {0x61707865, 0x3320646e, 0x79622d32, 0x6b206574}; // The constant "sigma" (expanded "expand 32-byte k")
    private static final int skew = 6; // The skew constant determines where to insert the nonce in the ciphertext

    public static void main(String[] args) {
        // Generate a random plaintext using UUID
        String plaintext = UUID.randomUUID().toString();
        // Generate a random key and nonce for encryption
        byte[] key = generateKey();
        byte[] nonce = generateNonce();

        // Encrypt the plaintext and embed the nonce at the specific position
        String ciphertext = encrypt(plaintext, key, nonce);
        // Decrypt the ciphertext to retrieve the original plaintext
        String decryptedText = decrypt(ciphertext, key);

        // Output the results
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static String encrypt(String plaintext, byte[] key, byte[] nonce) {
        // Convert plaintext to bytes using UTF-8 encoding
        byte[] plainBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        // Prepare an array to hold the encrypted bytes
        byte[] cipherBytes = new byte[plainBytes.length];

        // Initialize the ChaCha20 state with the key and nonce
        int[] state = initState(key, nonce);

        // Encrypt the plaintext by XORing with the keystream blocks
        for (int i = 0; i < plainBytes.length; i += BLOCK_SIZE) {
            // Generate a keystream block
            int[] keyStream = generateKeyStream(state);
            // Determine the size of the current block
            int blockSize = Math.min(BLOCK_SIZE, plainBytes.length - i);
            // Perform the encryption
            for (int j = 0; j < blockSize; j++) {
                cipherBytes[i + j] = (byte) (plainBytes[i + j] ^ keyStream[j]);
            }
            // Increment the counter for the next block
            incrementCounter(state);
        }

        // Encode the ciphertext and nonce as Base64 strings
        String ciphertext = Base64.getEncoder().encodeToString(cipherBytes);
        String nonceString = Base64.getEncoder().encodeToString(nonce);
        // Insert the nonce at the specified position in the ciphertext
        return ciphertext.substring(0, skew) + nonceString + ciphertext.substring(skew);
    }

    public static String decrypt(String ciphertext, byte[] key) {
        // Extract the nonce from the specified position in the ciphertext
        String nonceString = ciphertext.substring(skew, skew + NONCE_SIZE * 4 / 3);
        // Decode the nonce from Base64
        byte[] nonce = Base64.getDecoder().decode(nonceString);
        // Reconstruct the real ciphertext by removing the nonce part
        String realCiphertext = ciphertext.substring(0, skew) + ciphertext.substring(skew + NONCE_SIZE * 4 / 3);
        // Decode the ciphertext from Base64
        byte[] cipherBytes = Base64.getDecoder().decode(realCiphertext);
        // Prepare an array to hold the decrypted bytes
        byte[] plainBytes = new byte[cipherBytes.length];

        // Initialize the ChaCha20 state with the key and nonce
        int[] state = initState(key, nonce);

        // Decrypt the ciphertext by XORing with the keystream blocks
        for (int i = 0; i < cipherBytes.length; i += BLOCK_SIZE) {
            // Generate a keystream block
            int[] keyStream = generateKeyStream(state);
            // Determine the size of the current block
            int blockSize = Math.min(BLOCK_SIZE, cipherBytes.length - i);
            // Perform the decryption
            for (int j = 0; j < blockSize; j++) {
                plainBytes[i + j] = (byte) (cipherBytes[i + j] ^ keyStream[j]);
            }
            // Increment the counter for the next block
            incrementCounter(state);
        }

        // Convert the decrypted bytes back to a string using UTF-8 encoding
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    private static int[] initState(byte[] key, byte[] nonce) {
        int[] state = new int[16];
        System.arraycopy(SIGMA, 0, state, 0, SIGMA.length);
        for (int i = 0; i < 8; i++) {
            state[4 + i] = ByteBuffer.wrap(key, i * 4, 4).getInt();
        }
        state[12] = 0;
        for (int i = 0; i < 3; i++) {
            state[13 + i] = ByteBuffer.wrap(nonce, i * 4, 4).getInt();
        }
        return state;
    }

    private static int[] generateKeyStream(int[] state) {
        int[] keyStream = new int[BLOCK_SIZE];
        int[] workingState = state.clone();
        for (int i = 0; i < 10; i++) {
            quarterRound(workingState, 0, 4, 8, 12);
            quarterRound(workingState, 1, 5, 9, 13);
            quarterRound(workingState, 2, 6, 10, 14);
            quarterRound(workingState, 3, 7, 11, 15);
            quarterRound(workingState, 0, 5, 10, 15);
            quarterRound(workingState, 1, 6, 11, 12);
            quarterRound(workingState, 2, 7, 8, 13);
            quarterRound(workingState, 3, 4, 9, 14);
        }
        for (int i = 0; i < 16; i++) {
            keyStream[i * 4] = workingState[i] + state[i];
            keyStream[i * 4 + 1] = workingState[i] + state[i] >>> 8;
            keyStream[i * 4 + 2] = workingState[i] + state[i] >>> 16;
            keyStream[i * 4 + 3] = workingState[i] + state[i] >>> 24;
        }
        return keyStream;
    }

    private static void quarterRound(int[] state, int a, int b, int c, int d) {
        state[a] += state[b];
        state[d] ^= state[a];
        state[d] = Integer.rotateLeft(state[d], 16);
        state[c] += state[d];
        state[b] ^= state[c];
        state[b] = Integer.rotateLeft(state[b], 12);
        state[a] += state[b];
        state[d] ^= state[a];
        state[d] = Integer.rotateLeft(state[d], 8);
        state[c] += state[d];
        state[b] ^= state[c];
        state[b] = Integer.rotateLeft(state[b], 7);
    }

    private static void incrementCounter(int[] state) {
        state[12]++;
    }

    private static byte[] generateKey() {
        byte[] key = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(key);
        return key;
    }

    private static byte[] generateNonce() {
        byte[] nonce = new byte[NONCE_SIZE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

}
