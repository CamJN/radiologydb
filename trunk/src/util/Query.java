package util;

import java.sql.*;

import util.ConnectionManager;
import util.ConnectionManager.ConnectionKit;

/**
 *
 * @author Steven Maschmeyer
 */
public class Query {

    protected ResultSet resultSet;
    protected ResultSetMetaData metaData;
    protected int columnCount;
    private ConnectionKit connectionKit;
    
    public Query(String query) throws SQLException {
        this(query, ResultSet.TYPE_FORWARD_ONLY);
    }

    public Query(String query, Object... statementParameters) throws SQLException {
        connectionKit = new ConnectionKit();
        resultSet = connectionKit.exec(query, statementParameters);
        metaData = resultSet.getMetaData();
        columnCount = metaData.getColumnCount();
    }

    public Query(String query, int resultSetType) throws SQLException {
        connectionKit = new ConnectionKit();
        resultSet = connectionKit.exec(query, resultSetType);
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
        connectionKit.close();
    }

}
