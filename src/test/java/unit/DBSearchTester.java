package unit;

import enron.db.DBSearcher;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSearchTester {

    @Test
    public void tester() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

        DBSearcher searcher = new DBSearcher(connection);
        System.out.println(searcher.search("ABC"));
        System.out.println(searcher.search("AXY"));
        System.out.println(searcher.search("AA"));
        System.out.println(searcher.search("ZYZ"));
        System.out.println(searcher.search("ABCD"));
        System.out.println(searcher.search("ABCDE"));
        System.out.println(searcher.search("hehe"));
    }

}
