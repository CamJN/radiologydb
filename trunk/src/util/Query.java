package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
        connectionManager = new ConnectionManager();
        resultSet = connectionManager.exec(query, resultSetType);
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

    public void close() throws SQLException {
        resultSet.close();
        connectionManager.closeCon(); //TODO
    }

}
