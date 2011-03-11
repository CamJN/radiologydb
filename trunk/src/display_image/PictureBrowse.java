import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;


public class PictureBrowse extends HttpServlet {//implements SingleThreadModel {
    
    /**
     *  Generate and then send an HTML file that displays all the thermonail
     *  images of the photos.
     *
     *  Both the thermonail and images will be generated using another 
     *  servlet, called GetOnePic, with the photo_id as its query string
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

	//  send out the HTML file
	res.setContentType("text/html");
	PrintWriter out = res.getWriter ();

	out.println("<html>");
	out.println("<head>");
	out.println("<title> Photo List </title>");
	out.println("</head>");
	out.println("<body bgcolor=\"#000000\" text=\"#cccccc\" >");
	out.println("<center>");
	out.println("<h3>The List of Images </h3>");

     String argsString = request.getQueryString();//args was picid
     String args[]=argsString.split("&");
     String record_id="";
     for (int i =0;i<args.length;i++){
	 if(args[i].startsWith("record_id=")){
	     record_id=args[i].substring(11);
	 }else {
	     //mangled url
	 }
     }


	/*
	 *   to execute the given query
	 */
	try {
	    String query = "select image_id from pacs_images where record_id = "+record_id+";";

	    Connection conn = getConnected();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery(query);
	    String p_id;

	    while (rset.next() ) {
		p_id = (rset.getObject(1)).toString();

	       // specify the servlet for the image
               out.println("<a href=\"/yuan/servlet/GetOnePic?image_id="+p_id+"&record_id="+record_id+"&style=regular_size\">");
	       // specify the servlet for the thumbnail
	       out.println("<img src=\"/yuan/servlet/GetOnePic?image_id="+p_id+"&record_id="+record_id+"&style=thumbnail\"></a>");
	    }
	    stmt.close();
	    conn.close();
	} catch ( Exception ex ){ out.println( ex.toString() );}
    
	out.println("<P><a href=\"javascript:history.back()\"> Return </a>");
	out.println("</body>");
	out.println("</html>");
    }
    
    /*
     *   Connect to the specified database
     */
    private Connection getConnected() throws Exception {

	String username = "c391g1";
	String password = "c3911337";
        /* one may replace the following for the specified database */
	String drivername = "oracle.jdbc.driver.OracleDriver";
	String dbstring ="jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";


	/*
	 *  to connect to the database
	 */
	Class drvClass = Class.forName(drivername); 
	DriverManager.registerDriver((Driver) drvClass.newInstance());
	return( DriverManager.getConnection(dbstring,username,password) );
    }
}




