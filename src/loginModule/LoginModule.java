package loginModule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import javax.servlet.http.HttpSession;

import util.ConnectionManager;

@SuppressWarnings("serial")
public class LoginModule extends HttpServlet {
    
    private static final String HEADER = "<!doctype html><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/default.css\" /></head><body>";
    private static final String FOOTER = "</body></html>";
    
  public void doGet( HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		response.setContentType("text/html");
    	String username = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter out = response.getWriter();
		
				
		Connection conn;
		ResultSet rset = null;

		PreparedStatement statement = null;  
		
		
		try {
			conn = ConnectionManager.getConnection();
			statement = conn.prepareStatement("select password, class from users where user_name = ?");
			statement.setString(1, username);
			rset = statement.executeQuery();

			String truepwd = "";
			String userClass = "";

			while (rset != null && rset.next())
			{
				truepwd = (rset.getString(1)).trim();
				userClass = rset.getString(2).trim();
			}
			
			rset.close();
			statement.close();
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
				out.println(HEADER+"<p><b>Either your userName or Your password is inValid!</b></p>"+FOOTER);

		} catch (SQLException ex) {
			out.println(HEADER+"<p>Could not connecect to server</p>"+FOOTER);
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
                                                                            
