package enron.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class TrieIndex {

    public static final String TABLE = "trie_index";
    private Statement statement;

    public TrieIndex(Connection connection) {
        Statement _statement = null;

        try
        {
            _statement = connection.createStatement();
            _statement.setQueryTimeout(30);  // set timeout to 30 sec.

            _statement.executeUpdate("drop table if exists " + TABLE);
            _statement.executeUpdate("create table " + TABLE + "  (child_id integer, parent_id integer, letter string)");
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            this.statement = _statement;
            System.out.println(TABLE + " setup complete");
        }
    }

    public void add(long child_id, long parent_id, char letter) {
        try {
            statement.executeUpdate("insert into " + TABLE +  " values(" + child_id + ", " + parent_id + ", '" + letter +"')");
        } catch (SQLException e) {
            System.err.println("Fail: " + child_id + " " + parent_id);
            e.printStackTrace();
        }
    }

    public Set<Long> fetch(long parent_id) throws SQLException  {
        ResultSet rs = statement.executeQuery("select child_id, letter from " + TABLE + " where parent_id = " + parent_id);
        Set<Long> values = new HashSet<Long>();
        while(rs.next())
        {
            long v = rs.getLong("child_id");
            System.out.println(parent_id + " -> " + v + "  " + rs.getString("letter"));
            values.add(v);
        }

        return values;
    }

    public void listAll() throws SQLException {
        ResultSet rs = statement.executeQuery("select * from " + TABLE);
        while(rs.next())
        {
            System.out.println("TrieIxList " + rs.getLong("child_id") + " " + rs.getLong("parent_id") + " " + rs.getString("letter"));
        }
    }

}
