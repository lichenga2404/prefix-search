package unit;

import enron.trie.Serializer;
import enron.trie.Trie;
import org.junit.Test;

import java.io.IOException;

public class FileTest {
    @Test
    public void simpleTest() throws IOException {
        Trie trie = new Trie();
        long docId = 1;
        trie.addWord("A", docId++);
        trie.addWord("AA", docId++);
        trie.addWord("ABC", docId++);
        trie.addWord("ABCD", docId++);
        trie.addWord("AACD", docId++);
        trie.addWord("AZ", docId++);
        trie.addWord("ZYZ", docId++);
        trie.addWord("ABCD", docId++);
        trie.addWord("ABCD", docId++);

        Serializer serializer = new Serializer(trie, "data/test.txt");
        serializer.serialize();
        serializer.close();
    }
}
