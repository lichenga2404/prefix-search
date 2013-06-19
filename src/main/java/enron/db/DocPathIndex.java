package enron.db;

import java.sql.*;

public class DocPathIndex {

    public static final String TABLE = "doc_path_index";
    private Statement statement;

    public DocPathIndex(Connection connection) {
        Statement _statement = null;

        try
        {
            _statement = connection.createStatement();
            _statement.setQueryTimeout(30);  // set timeout to 30 sec.

            _statement.executeUpdate("drop table if exists " + TABLE);
            _statement.executeUpdate("create table " + TABLE + "  (doc_id integer, path string)");
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

    public void add(long id, String path) {
        try {
            statement.executeUpdate("insert into " + TABLE +  " values(" + id + ", '" + path + "')");
        } catch (SQLException e) {
            System.err.println("Fail: " + id + " " + path);
            e.printStackTrace();
        }
    }

    public String fetch(long docID) throws SQLException  {
        ResultSet rs = statement.executeQuery("select path from " + TABLE + " where doc_id = " + docID);
        String value = null;
        if(rs.next())
        {
            value = rs.getString("path");
            System.out.println(docID+ " -> " + value);
        }
        return value;
    }

    public void listAll() throws Exception {
        ResultSet rs = statement.executeQuery("select * from " + TABLE);
        while(rs.next())
        {
            // read the result set
            System.out.println("DocPathList " + rs.getLong("doc_id") + ", " + rs.getString("path"));
        }
    }

}
