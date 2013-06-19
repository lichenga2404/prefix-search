package unit;

import enron.db.DocPathIndex;
import enron.db.NodeDocIndex;
import enron.db.TrieIndex;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBIndexTesters {

    @Test
    public void testNodeDocIndex() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        NodeDocIndex indexer = new NodeDocIndex(connection);

        for (int i=0; i<10; i++) indexer.add(i%3, i);

        System.out.println(indexer.fetch(0));
        System.out.println(indexer.fetch(1));
        System.out.println(indexer.fetch(2));

        connection.close();

    }

    @Test
    public void testTrieIndex() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        TrieIndex indexer = new TrieIndex(connection);

        for (int i=0; i<10; i++) indexer.add(i, i%3, 'A');

        System.out.println(indexer.fetch(0));
        System.out.println(indexer.fetch(1));
        System.out.println(indexer.fetch(2));

        connection.close();

    }

}
