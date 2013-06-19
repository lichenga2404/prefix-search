package unit;

import enron.trie.NodeVisitor;
import enron.trie.Trie;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class TrieTester {

    @Test
    public void simpleTest() {
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

        System.out.println(docId + " docs inserted");
    }

    @Test
    public void findTest() {
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
        trie.addWord("ABCD", docId);

        System.out.println(trie.find("A"));
        System.out.println(trie.find("AA"));
        System.out.println(trie.find("ZYZ"));
        System.out.println(trie.find("ABCD"));
        System.out.println(trie.find("hehe"));
    }


    @Test
    public void visitTest() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

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
        trie.addWord("ABCD", docId);

        NodeVisitor v = new NodeVisitor(connection);
        trie.visit(v);

        v.listAll();
    }
}
