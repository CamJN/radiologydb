package util;

import java.sql.*;

import util.ConnectionManager;

/**
 *
 * @author Steven Maschmeyer
 */
public class Query {

    protected ResultSet resultSet;
    protected ResultSetMetaData metaData;
    protected int columnCount;
    private ConnectionManager connectionManager;
    
    public Query(String query) throws SQLException {
        this(query, ResultSet.TYPE_FORWARD_ONLY);
    }

    public Query(String query, int resultSetType) throws SQLException {
        Connection c = ConnectionManager.getConnection();
        Statement s = c.createStatement(resultSetType, ResultSet.CONCUR_READ_ONLY);
        resultSet = s.executeQuery(query);
        metaData = resultSet.getMetaData();
        columnCount = metaData.getColumnCount();
    }

    public boolean absolute(int row) throws SQLException {
        return resultSet.absolute(row);
    }

    public boolean next() throws SQLException {
        return resultSet.next();
    }

    public String getString(int column) throws SQLException {
        return resultSet.getString(column);
    }
    
    public int getRowCount() throws SQLException {
        int initialRow = resultSet.getRow();
        resultSet.last();
        int lastRow = resultSet.getRow();
        if (initialRow != 0) resultSet.absolute(initialRow);
        else resultSet.beforeFirst();
        
        return lastRow;
    }

    public void close() throws SQLException {
        resultSet.close();
        //connectionManager.closeCon(); //TODO
    }

}
