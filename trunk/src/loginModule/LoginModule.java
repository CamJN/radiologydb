package loginModule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

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
		
				
		Connection conn;
		ResultSet rset = null;

		String userSearch = "select password, class from users where user_name = '"
				+ username + "'";
		
		try {
			conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery(userSearch);

			String truepwd = "";
			String userClass = "";

			while (rset != null && rset.next())
			{
				truepwd = (rset.getString(1)).trim();
				userClass = rset.getString(2).trim();
			}

            conn.close();
			// display the result
			if (password.equals(truepwd))
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("class", userClass);
				session.setAttribute("username", username);
				session.setMaxInactiveInterval(60*5);//5 minutes
				getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else
				out.println("<p><b>Either your userName or Your password is inValid!</b></p>");

		} catch (SQLException ex) {
			out.println("<p>Could not connecect to server</p>");
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
                                                                            
