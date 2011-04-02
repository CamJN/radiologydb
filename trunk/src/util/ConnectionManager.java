package util;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleConnectionCacheImpl;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ConnectionManager {
    
    private static OracleConnectionPoolDataSource dataSource;
    private static OracleConnectionCacheImpl connectionCache;
    
    static { initCache(); }

    private ConnectionManager() {}
    
    public static Connection getConnection() throws SQLException {
        return connectionCache.getConnection();
    }
	
    private static void initCache() {
        try {
            dataSource = new OracleConnectionPoolDataSource();
            dataSource.setURL("jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS");
            dataSource.setUser("c391g1");
            dataSource.setPassword("c3911337");
            
            connectionCache = new OracleConnectionCacheImpl(dataSource);
            connectionCache.setMaxLimit(10);
            connectionCache.setCacheScheme(OracleConnectionCacheImpl.FIXED_WAIT_SCHEME);
        } catch (SQLException e) {
            throw new Error("Could not initialize data source.");
        }
    }

    public static class ConnectionKit {

        private Connection connection;
        private List<Statement> statements; //Keeping a list of statements to close because pooled connections may not do this

        public ConnectionKit() throws SQLException {
            this(true);
        }
        
        public ConnectionKit(boolean autoCommit) throws SQLException {
            connection = ConnectionManager.getConnection();
            statements = new ArrayList<Statement>();
        }
        
        public ResultSet exec(String query) throws SQLException {
            return exec(query, ResultSet.TYPE_FORWARD_ONLY);
        }
        
        public ResultSet exec(String query, int resultSetType) throws SQLException {
            Statement s = connection.createStatement(resultSetType, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery(query);
            statements.add(s);
            return rs;
        }

        public ResultSet exec(String query, Object... statementParameters) throws SQLException {
            PreparedStatement ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int p = 1, i = 0; i < statementParameters.length; i++) {
                Object o = statementParameters[i];
                if (o == null) continue;
                ps.setObject(p++, o);
            }
            ResultSet rs = ps.executeQuery();
            statements.add(ps);
            return rs;
        }
        
        public void close() throws SQLException {
            for (Statement s : statements) s.close();
            connection.close();
        }

        public static int getRowCount(ResultSet resultSet) throws SQLException {
            int initialRow = resultSet.getRow();
            resultSet.last();
            int lastRow = resultSet.getRow();
            if (initialRow != 0) resultSet.absolute(initialRow);
            else resultSet.beforeFirst();
            
            return lastRow;
        }

    }

}
