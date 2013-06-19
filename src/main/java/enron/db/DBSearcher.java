package enron.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DBSearcher {

    private Statement statement;

    public DBSearcher(Connection connection) {

        Statement _statement = null;

        try
        {
            _statement = connection.createStatement();
            _statement.setQueryTimeout(30);  // set timeout to 30 sec.

        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            this.statement = _statement;
        }

    }

    private long findNodeId(long parent_id, char letter) throws SQLException {
        ResultSet rs = statement.executeQuery("select child_id from " + TrieIndex.TABLE + " where parent_id = " +
                parent_id + " and letter = '" + letter + "'");
        long v = -1;
        while(rs.next())
        {
            v = rs.getLong("child_id");
        }
        return v;
    }

    private Set<Long> findDocIDs(long node_id) throws SQLException {
        ResultSet rs = statement.executeQuery("select doc_id from " + NodeDocIndex.TABLE + " where node_id = " + node_id);
        Set<Long> values = new HashSet<Long>();
        while(rs.next())
        {
            long v = rs.getLong("doc_id");
            System.out.println(node_id + " -> " + v);
            values.add(v);
        }

        return values;
    }

    public String findDocPath(long docID) throws SQLException  {
        ResultSet rs = statement.executeQuery("select path from " + DocPathIndex.TABLE + " where doc_id = " + docID);
        String value = null;
        if(rs.next())
        {
            value = rs.getString("path");
        }
        return value;
    }

    public String search(String chars) throws SQLException {
        char[] letters = chars.toCharArray();

        System.out.println("\n\nSearching for " + chars);

        //Find node IDs
        long current_node_id = 1; //root's id = 1
        for (char letter: letters) {
            long v = findNodeId(current_node_id, letter);
            if (v == -1) return "NO RESULT";
            //System.out.println(v);
            current_node_id = v;
        }

        System.out.println("Found " + current_node_id);

        //Find doc IDs
        Set<Long> documentIds = findDocIDs(current_node_id);


        // find doc paths
        Set<String> documents = new HashSet<String>();
        for (long d: documentIds) {
            System.out.println("Document ID: " + d);
            String path = findDocPath(d);
            if (path != null) documents.add(path);
        }

        System.out.println(documents);


        return "FOUND";
    }


}
