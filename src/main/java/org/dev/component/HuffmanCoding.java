import java.util.*;

/**
 * Description:  HuffmanCoding
 * Created by EXCaster on 2024/4/1
 */
public class HuffmanCoding {
    static class HuffmanNode {
        int frequency;
        char c;
        HuffmanNode left;
        HuffmanNode right;
    }

    static class HuffmanComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode node1, HuffmanNode node2) {
            return Integer.compare(node1.frequency, node2.frequency);
        }
    }
    
    public static HuffmanNode buildTree(Map<Character, Integer> freq) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(new HuffmanComparator());

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = entry.getKey();
            hn.frequency = entry.getValue();
            hn.left = null;
            hn.right = null;
            queue.add(hn);
        }

        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();
            HuffmanNode sum = new HuffmanNode();
            sum.frequency = x.frequency + y.frequency;
            sum.left = x;
            sum.right = y;
            queue.add(sum);
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
        String test = "this is an example for huffman encoding";

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
