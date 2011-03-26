package util;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleConnectionCacheImpl;

import java.sql.*;

public class ConnectionManager {
    
    private static OracleConnectionPoolDataSource dataSource;
    private static OracleConnectionCacheImpl connectionCache;
    
    static { initCache(); }

    private ConnectionManager() {}
    
    public static Connection getConnection() throws SQLException {
        return connectionCache.getConnection();
    }

	public static ResultSet exec(String query) throws SQLException {
		return exec(query, ResultSet.TYPE_FORWARD_ONLY);
	}

	public static ResultSet exec(String query, int resultSetType) throws SQLException {
		Statement s = getConnection().createStatement(resultSetType, ResultSet.CONCUR_READ_ONLY);
		return s.executeQuery(query);
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

}
