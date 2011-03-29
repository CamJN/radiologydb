package management;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.HttpSession;

import util.ConnectionManager;

@SuppressWarnings("serial")
public class NewUser extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		response.setContentType("text/html");
   		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userClass = request.getParameter("class");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		PrintWriter out = response.getWriter();
		
				
		
		
		ResultSet rset = null;
		

		String checkUserName = "select count(*) from users where user_name='" + username + "'";
		
		
        
		

		try {
			Connection conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery(checkUserName);
			String count = "";
			
			while (rset != null && rset.next())
			{
				count = (rset.getString(1)).trim();
			}
			if(!count.equals("0"))
			{
				out.println("User_name already being used!");
				return;
			}
			conn.setAutoCommit(false);
			Date currentDatetime = new Date(System.currentTimeMillis());  
	        java.sql.Timestamp timestamp = new java.sql.Timestamp(currentDatetime.getTime());  
	   
	        PreparedStatement statement = null;  
			statement = conn.prepareStatement("insert into users values (?,?,?,?)");  
			statement.setString(1, username);
	        statement.setString(2,password);
	        statement.setString(3,userClass);
	        statement.setTimestamp(4, timestamp);
	        statement.execute();
	        
	        statement = conn.prepareStatement("insert into persons values(?,?,?,?,?,?)");
	        statement.setString(1, username);
	        statement.setString(2, fname);
	        statement.setString(3, lname);
	        statement.setString(4, address);
	        statement.setString(5, email);
	        statement.setString(6, phone);
	        statement.execute();
	        
	        conn.commit();
	        conn.setAutoCommit(true);
	        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

		}
		
		
    }

	/**
	 * Handles HTTP POST request
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet(request, response);
	}

	/**
	 * Returns a brief description about this servlet
	 */
	public String getServletInfo() {
		return "Servlet that returns Session information";
	}
}
