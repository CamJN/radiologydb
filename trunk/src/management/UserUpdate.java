package management;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.util.ArrayList;

import util.ConnectionManager;

@SuppressWarnings("serial")
public class UserUpdate extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException{

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
		
		ArrayList<String>unames = new ArrayList<String>();
		String temp;
		
		if(userClass.equals("d") || userClass.equals("p"))
		{
			for(int i = 0; (temp = request.getParameter("uname"+i)) != null; ++i)
				unames.add(temp);
		}
		
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
			if(!count.equals("1"))
			{
				out.println("User_name does not exist!");
				return;
			}
			
			conn.setAutoCommit(false);
	   
	        PreparedStatement statement = null;  
			statement = conn.prepareStatement("update users set password=?,class=cast(? as char(1)) where user_name=?");  
			statement.setString(1, password);
	        statement.setString(2, userClass.trim().substring(0, 1));
	        statement.setString(3, username);
	        statement.execute();
	        
	        statement = conn.prepareStatement("update persons set first_name=?, last_name=?, address=?, email=?, phone =? where user_name=?");
	        statement.setString(1, fname);
	        statement.setString(2, lname);
	        statement.setString(3, address);
	        statement.setString(4, email);
	        statement.setString(5, phone);
	        statement.setString(6, username);
	        statement.execute();
	        
	        ArrayList<String> deleteUnames = new ArrayList<String>();
	        rset.close();
	        if(userClass.equals("d"))
	        {
	        	String patients = "select UNIQUE patient_name from family_doctor where doctor_name = '" + username + "'";
	        	rset = stmt.executeQuery(patients);
	        	int index = 0;
	        	out.println("<p>Found:</p>");
	        	while(rset != null && rset.next())
	        	{
	        		if((index = unames.indexOf(rset.getString(1))) != -1)
	        		{
	        			out.println("<p>" + unames.get(index) + "</p>");
	        			unames.remove(index);
	        		}
	        		else
	        		{
	        			deleteUnames.add(rset.getString(1));
	        		}
	        	}
	        	out.println("<p>remaining:</p>");
	        	rset.close();
	        	for(String uname: unames)
	        	{
	        		out.println("<p>" + uname + "</p>");
	        		stmt.executeQuery("insert into family_doctor values('" + username + "', '" + uname + "')");
	        	}
	        	rset.close();
	        	
	        	for(String uname: deleteUnames)
	        	{
	        		stmt.executeQuery("delete from family_doctor where doctor_name = '" + username + "' and patient_name = '" + uname + "'");
	        	}
	        	
	        }
	        else if(userClass.equals("p"))
	        {
	        	String patients = "select UNIQUE doctor_name from family_doctor where patient_name = '" + username + "'";
	        	rset = stmt.executeQuery(patients);
	        	int index = 0;
	        	out.println("<p>Found:</p>");
	        	while(rset != null && rset.next())
	        	{
	        		if((index = unames.indexOf(rset.getString(1))) != -1)
	        		{
	        			out.println("<p>" + unames.get(index) + "</p>");
	        			unames.remove(index);
	        		}
	        		else
	        		{
	        			deleteUnames.add(rset.getString(1));
	        		}
	        	}
	        	rset.close();
	        	out.println("<p>remaining:</p>");
	        	for(String uname: unames)
	        	{
	        		out.println("<p>" + uname + "</p>");
	        		out.println("<p>" + "insert into family_doctor values('" + uname + "', '" + username + "')" + "</p>");
	        		stmt.executeQuery("insert into family_doctor values('" + uname + "', '" + username + "')");
	        	}
	        	
	        	for(String uname: deleteUnames)
	        	{
	        		stmt.executeQuery("delete from family_doctor where doctor_name = '" + uname + "' and patient_name = '" + username + "'");
	        	}
	        }
	        stmt.close();
	        
	        conn.commit();
	        conn.setAutoCommit(true);
            conn.close();
	        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			
		} catch (SQLException ex) {
			out.println("<p>Error occurred</p>");
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
