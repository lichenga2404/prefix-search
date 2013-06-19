package enron.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class NodeDocIndex {

    public static final String TABLE = "node_doc_index";
    private Statement statement;

    public NodeDocIndex(Connection connection) {
        Statement _statement = null;

        try
        {
            _statement = connection.createStatement();
            _statement.setQueryTimeout(30);  // set timeout to 30 sec.

            _statement.executeUpdate("drop table if exists " + TABLE);
            _statement.executeUpdate("create table " + TABLE + "  (node_id integer, doc_id integer)");
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


    public void add(long node_id, long doc_id) {
        try {
            statement.executeUpdate("insert into " + TABLE +  " values(" + node_id + ", " + doc_id + ")");
        } catch (SQLException e) {
            System.err.println("Fail: " + node_id + " " + doc_id);
            e.printStackTrace();
        }
    }

    public Set<Long> fetch(long node_id) throws SQLException  {
        ResultSet rs = statement.executeQuery("select doc_id from " + TABLE + " where node_id = " + node_id);
        Set<Long> values = new HashSet<Long>();
        while(rs.next())
        {
            long v = rs.getLong("doc_id");
            System.out.println(node_id + " -> " + v);
            values.add(v);
        }

        return values;
    }

    public void listAll() throws SQLException {

        ResultSet rs = statement.executeQuery("select * from " + TABLE);
        while(rs.next())
        {
            System.out.println("NodeDocIxList "  + rs.getLong("node_id") + " " + rs.getLong("doc_id"));
        }
    }
}
