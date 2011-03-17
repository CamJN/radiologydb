package loginModule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.HttpSession;

import util.ConnectionManager;

@SuppressWarnings("serial")
public class LoginModule extends HttpServlet {
  public void doGet( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		response.setContentType("text/html");
    		String username = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter out = response.getWriter();
		
				
		ConnectionManager conManager = new ConnectionManager();

		Statement stmt;
		ResultSet rset = null;

		String userSearch = "select password, class from users where user_name = '"
				+ username + "'";
		
		out.println("Username: " + username);
		out.println("Password: " + password);
		

		try {
			rset = conManager.exec(userSearch);

			String truepwd = "";
			String userClass = "";

			while (rset != null && rset.next())
			{
				truepwd = (rset.getString(1)).trim();
				userClass = rset.getString(2).trim();

			}

			// display the result
			if (password.equals(truepwd))
			{
				out.println("<p><b>Your Login is Successful!</b></p>");
				out.println("<p>" + userClass + "</p>");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("class", userClass);

				out.println("<A HREF=NextPage.jsp> Next <A/>");
			}
			else
				out.println("<p><b>Either your userName or Your password is inValid!</b></p>");

		} catch (SQLException ex) {

			System.err.println("SQLException: " + ex.getMessage());

		}
		
		
    }


    /**
     * Handles HTTP POST request
     */
    public void doPost( HttpServletRequest request,
			HttpServletResponse response )
	throws ServletException, IOException {
	// Invoke doGet to process this request
	doGet( request, response );
    }
   
    /**
     * Returns a brief description about this servlet
     */
    public String getServletInfo() {
	return "Servlet that returns Session information";
    }
}
                                                                            
