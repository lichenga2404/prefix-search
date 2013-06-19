package enron.trie;

import enron.FileWalker;
import enron.MailReader;
import enron.Tokenizer;
import enron.db.DBSearcher;
import enron.db.DocPathIndex;
import enron.db.NodeDocIndex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {

        FileWalker walker = new FileWalker("/home/arjun/Sandbox/osd/data/small/skilling-j/");
        Tokenizer tokenizer = new Tokenizer();
        char[] data = new char[10];
        char[] message = "Message-ID".toCharArray();

        Trie trie = new Trie();

        int LIM = 10;
        List<String> queries = new ArrayList<String>();

        //Tables
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        DocPathIndex docPathIndexer = new DocPathIndex(connection);
        NodeVisitor visitor = new NodeVisitor(connection);

        long DOCID = 1;
        while (walker.hasNext()) {
            File f = walker.next();
            InputStreamReader r = new InputStreamReader(new FileInputStream(f));

            if (r.read(data, 0, 10) == -1) continue;
            if ( !Arrays.equals(data, message) ) continue;

            r.close();

            String content = (new MailReader()).load(f);
            Set<String> words = tokenizer.extract(content);
            System.out.println("Got " + words.size() + " words from " + f);

            queries.add(words.iterator().next());
            for (String w : words) trie.addWord(w, DOCID);

            docPathIndexer.add(DOCID, f.getAbsolutePath());
            DOCID++;

            if (--LIM == 0) break;
        }

        for (String w: queries) {
            System.out.println(w + " " + trie.find(w));
        }

        trie.visit(visitor);

        //close conn
        connection.close();



        //search
        connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        DBSearcher searcher = new DBSearcher(connection);
        for (String w: queries) {
            System.out.println(w + " " + searcher.search(w));
        }


    }

}
