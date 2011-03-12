import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *  This servlet sends one picture stored in the table below to the client 
 *  who requested the servlet.
 *
 *  pacs_images(record_id,image_id,thumbnail,regular_size,full_size)
 *
 *  The request must come with a query string as follows:
 *    GetOnePic?image_id=12&record_id=3&style=thumbnail: sends the picture in thumbnail with image_id = 12 and record_id = 3
 *    GetOnePic?image_id=12&record_id=3&style=regular_size: sends the picture in regular_size with image_id = 12 and record_id = 3
 *
 */
public class GetOnePic extends HttpServlet {//implements SingleThreadModel {

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	//  construct the query  from the client's QueryString
     String argsString = request.getQueryString();//args was picid
     // System.out.println("GetOnePic: "+argsString);
     String args[]=argsString.split("&");
     String image_id="", record_id="",style="";
     for (int i =0;i<args.length;i++){
	 if(args[i].startsWith("image_id=")){
	     image_id=args[i].substring(9);
	 }else if(args[i].startsWith("record_id=")){
	     record_id=args[i].substring(10);
	 }else if(args[i].startsWith("style=")){
	     style=args[i].substring(6);
	 }else {
	     //mangled url
	 }
     }//System.out.println("GetOnePic: image_id:"+image_id+" record_id:"+record_id+" style:"+style);
	String query = "select "+style+" from pacs_images where record_id = "+record_id+"AND image_id = "+image_id; 

	ServletOutputStream out = response.getOutputStream();

	/*
	 *   to execute the given query
	 */
	Connection conn = null;
	try {
	    conn = getConnected();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery(query);

	    if ( rset.next() ) {
		response.setContentType("image/gif");
		InputStream input = rset.getBinaryStream(1);	    
		int imageByte;
		while((imageByte = input.read()) != -1) {
		    out.write(imageByte);
		}
		input.close();
	    } 
	    else 
		out.println("no picture available");
	} catch( Exception ex ) {
	    out.println(ex.getMessage() );
	}
	// to close the connection
	finally {
	    try {
		conn.close();
	    } catch ( SQLException ex) {
		out.println( ex.getMessage() );
	    }
	}
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
