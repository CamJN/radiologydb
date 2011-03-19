package util;

import java.sql.*;

public class ConnectionManager {
	
	static final String m_driverName = "oracle.jdbc.driver.OracleDriver";
	static final String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	
	static final String m_userName = "c391g1";
	static final String m_password = "c3911337";

	private Connection m_con = null;

	public ConnectionManager() {
		try {
			@SuppressWarnings("rawtypes")
			Class drvClass = Class.forName(m_driverName);
			DriverManager.registerDriver((Driver) drvClass.newInstance());
		} catch (Exception e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());

		}

		try {

			m_con = DriverManager.getConnection(dbstring, m_userName, m_password);
			System.err.println("Conn Created");
		} catch (SQLException e) {
            System.out.println("Could not get SQL connection: " + e.getMessage());
		}
	}
	
	public ResultSet exec(String query) {
		return exec(query, ResultSet.TYPE_FORWARD_ONLY);
	}

	public ResultSet exec(String query, int resultSetType) {
		ResultSet rset = null;
		try {
			Statement stmt = m_con.createStatement(resultSetType, ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery(query);
			//stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rset;
	}

	public void closeCon() {
		try {
			m_con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
