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

	out.println("<script language=\"JavaScript\" type=\"text/javascript\" src=\"../contentflow.js\" load=\"DEFAULT\"></script>");
	out.println("<title> Photo List </title>");
	out.println("</head>");
	out.println("<body bgcolor=\"#000000\" text=\"#cccccc\" >");
	out.println("<center>");
	out.println("<h3>The List of Images </h3>");

     String argsString = request.getQueryString();//args was picid

     String record_id = "1";
     //     System.out.println("PictureBrowse: "+record_id);

	/*
	 *   to execute the given query
	 */
	try {
	    
	    String query = "select image_id from pacs_images where record_id = "+record_id;
	    //System.out.println(query);
	    Connection conn = getConnected();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery(query);
	    String p_id;

            out.println("<div style=\"width: 25%;\">");
            //out.println("<div id=\"contentFlow\" class=\"ContentFlow\">");
            out.println("<div id=\"contentFlow\" class=\"ContentFlow\" useAddOns\"DEFAULT\">");
            //out.println("<div class=\"loadIndicator\"><div class=\"indicator\"></div></div>");
            out.println("<div class=\"flow\">");
	    while (rset.next() ) {
		p_id = (rset.getObject(1)).toString();
                
	       // specify the servlet for the image
               //out.println("<a href=\"/radiologydb/servlet/GetOnePic?image_id="+p_id+"&record_id="+record_id+"&style=regular_size\">");

            out.println("<div class=\"item\">");
            out.println("<img class=\"content\" src=\"/radiologydb/servlet/GetOnePic?image_id="+p_id+"&record_id="+record_id+"&style=thumbnail\">");
            out.println("<div class=\"caption\">yo</div></div>");

	       // specify the servlet for the thumbnail
	       //out.println("<img src=\"/radiologydb/servlet/GetOnePic?image_id="+p_id+"&record_id="+record_id+"&style=thumbnail\"></a>");
	    }


            out.println("</div>");
            //out.println("<div class=\"globalCaption\"></div>");
            out.println("<div class=\"scrollbar\">");
            out.println("<div class=\"preButton\"></div>");
            out.println("<div class=\"nextButton\"></div>");
            out.println("<div class=\"slider\"><div class=\"position\"></div></div>");
            out.println("</div>");
	    out.println("</div>");
            out.println("</div>");

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




