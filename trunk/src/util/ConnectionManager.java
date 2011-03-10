package util;

import java.sql.*;

public class ConnectionManager {

	static final String m_url = "oracle.jdbc.driver.OracleDriver";
	static final String m_driverName = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:crs";
	static final String m_userName = "username";
	static final String m_password = "passwd";

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

			m_con = DriverManager.getConnection(m_url, m_userName, m_password);
			System.err.println("Conn Created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Exception here");
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ResultSet exec(String query)
	{
		ResultSet rset = null;
		try {
			Statement stmt = m_con.createStatement();
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