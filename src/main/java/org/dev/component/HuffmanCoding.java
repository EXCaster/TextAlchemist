package org.dev.component;

import java.util.*;

/**
 * Description:  HuffmanCoding
 * Created by EXCaster on 2024/4/1
 */
public class HuffmanCoding {
    s static class HuffmanNode {
        int frequency;
        char c;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char c, int frequency) {
            this.c = c;
            this.frequency = frequency;
        }

        HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    public static HuffmanNode buildTree(Map<Character, Integer> freq) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        freq.forEach((c, frequency) -> queue.offer(new HuffmanNode(c, frequency)));

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            queue.offer(new HuffmanNode(left.frequency + right.frequency, left, right));
        }

        return queue.poll();
    }

    public static void generateCode(HuffmanNode root, StringBuilder prefix, Map<Character, String> code) {
        if (root.left == null && root.right == null && Character.isDefined(root.c)) {
            code.put(root.c, prefix.toString());
            return;
        }
        prefix.append('0');
        generateCode(root.left, prefix, code);
        prefix.deleteCharAt(prefix.length() - 1);
        prefix.append('1');
        generateCode(root.right, prefix, code);
        prefix.deleteCharAt(prefix.length() - 1);
    }

    public static BitSet encode(String text, Map<Character, String> huffmanCode) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(huffmanCode.get(c));
        }
        BitSet bitSet = new BitSet(encoded.length());
        for (int i = 0; i < encoded.length(); i++) {
            if (encoded.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static void main(String[] args) {
        String test = UUID.randomUUID().toString();

        Map<Character, Integer> freq = new HashMap<>();
        for (char c : test.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        HuffmanNode root = buildTree(freq);

        Map<Character, String> huffmanCode = new HashMap<>();
        StringBuilder prefix = new StringBuilder();
        generateCode(root, prefix, huffmanCode);
        BitSet encodedBitSet = encode(test, huffmanCode);
        System.out.println("Text size: " + test.length());
        System.out.println("Encoded size: " + new String(encodedBitSet.toByteArray()).length());

        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
