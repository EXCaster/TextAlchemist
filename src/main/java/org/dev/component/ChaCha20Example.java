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
    private static final int NONCE_SIZE = 12; // 96 bits
    private static final int KEY_SIZE = 32; // 256 bits
    private static final int BLOCK_SIZE = 64;
    private static final int[] SIGMA = {0x61707865, 0x3320646e, 0x79622d32, 0x6b206574};

    public static void main(String[] args) {
        String plaintext = UUID.randomUUID().toString();
        byte[] key = generateKey();
        byte[] nonce = generateNonce();

        String ciphertext = encrypt(plaintext, key, nonce);
        String decryptedText = decrypt(ciphertext, key);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static String encrypt(String plaintext, byte[] key, byte[] nonce) {
        byte[] plainBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] cipherBytes = new byte[plainBytes.length];

        int[] state = initState(key, nonce);

        for (int i = 0; i < plainBytes.length; i += BLOCK_SIZE) {
            int[] keyStream = generateKeyStream(state);
            int blockSize = Math.min(BLOCK_SIZE, plainBytes.length - i);
            for (int j = 0; j < blockSize; j++) {
                cipherBytes[i + j] = (byte) (plainBytes[i + j] ^ keyStream[j]);
            }
            incrementCounter(state);
        }

        String ciphertext = Base64.getEncoder().encodeToString(cipherBytes);
        String nonceString = Base64.getEncoder().encodeToString(nonce);
        return ciphertext.substring(0, 2) + nonceString + ciphertext.substring(2);
    }

    public static String decrypt(String ciphertext, byte[] key) {
        String nonceString = ciphertext.substring(2, 2 + NONCE_SIZE * 4 / 3);
        byte[] nonce = Base64.getDecoder().decode(nonceString);
        String realCiphertext = ciphertext.substring(0, 2) + ciphertext.substring(2 + NONCE_SIZE * 4 / 3);
        byte[] cipherBytes = Base64.getDecoder().decode(realCiphertext);
        byte[] plainBytes = new byte[cipherBytes.length];

        int[] state = initState(key, nonce);

        for (int i = 0; i < cipherBytes.length; i += BLOCK_SIZE) {
            int[] keyStream = generateKeyStream(state);
            int blockSize = Math.min(BLOCK_SIZE, cipherBytes.length - i);
            for (int j = 0; j < blockSize; j++) {
                plainBytes[i + j] = (byte) (cipherBytes[i + j] ^ keyStream[j]);
            }
            incrementCounter(state);
        }

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
