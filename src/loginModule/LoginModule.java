package loginModule;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;

import util.ConnectionManager;

@SuppressWarnings("serial")
public class LoginModule extends GenericServlet {
    public void service(ServletRequest request, ServletResponse response)
    throws IOException
    {
    	String username = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter out = response.getWriter();
		
		//HttpSession session = request.getSession(true);
		//session.setAttribute("username", username);
		//session.setAttribute("password", password);
		
		ConnectionManager conManager = new ConnectionManager();

		Statement stmt;
		ResultSet rset = null;

		String userSearch = "select password from users where user_name = '"
				+ username + "'";
		
		out.println("Username: " + username);
		out.println("Password: " + password);
		

		try {
			rset = conManager.exec(userSearch);

			String truepwd = "";

			while (rset != null && rset.next())
				truepwd = (rset.getString(1)).trim();

			// display the result
			if (password.equals(truepwd))
				out.println("<p><b>Your Login is Successful!</b></p>");
			else
				out.println("<p><b>Either your userName or Your password is inValid!</b></p>");

		} catch (SQLException ex) {

			System.err.println("SQLException: " + ex.getMessage());

		}
    }
}
                                                                            
