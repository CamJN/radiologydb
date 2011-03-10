/***
 *  the table shall be created using the following
 *     CREATE TABLE pictures (
 *           pic_id int,
 *	        pic_desc  varchar(100),
 *		    pic  BLOB,
 *		        primary key(pic_id)
 *     );
 *
 *  One may also need to create a sequence using the following 
 *  SQL statement to automatically generate a unique pic_id:
 *
 *   CREATE SEQUENCE pic_id_sequence;
 *
 ***/

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.imageio.ImageIO;

/**
 *  The package commons-fileupload-1.0.jar is downloaded from 
 *         http://jakarta.apache.org/commons/fileupload/ 
 *  and it has to be put under WEB-INF/lib/ directory in your servlet context.
 *  One shall also modify the CLASSPATH to include this jar file.
 */
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadImage extends HttpServlet {
    public String response_message;
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	//  change the following parameters to connect to the oracle database
	String username = "c391g1";
	String password = "c3911337";
	String drivername = "oracle.jdbc.driver.OracleDriver";
	String dbstring ="jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	int pic_id;

	try {
	    //Parse the HTTP request to get the image stream
	    DiskFileUpload fu = new DiskFileUpload();
	    List FileItems = fu.parseRequest(request);
	        
	    // Process the uploaded items, assuming only 1 image file uploaded
	    Iterator i = FileItems.iterator();
	    FileItem item = (FileItem) i.next();
	    int record_id=3;
	    while (i.hasNext() && item.isFormField()) {
		item = (FileItem) i.next();
		//get record_id from form
	    }

	    //Get the image stream
	    InputStream instream = item.getInputStream();

	    BufferedImage img = ImageIO.read(instream);
	    BufferedImage thumbNail = shrink(img, 150);
	    BufferedImage normalSize = shrink(img,550);

            // Connect to the database and create a statement
            Connection conn = getConnected(drivername,dbstring, username,password);
	    Statement stmt = conn.createStatement();
	    
	    /*
	     *  First, to generate a unique pic_id using an SQL sequence
	     */
	    ResultSet rset1 = stmt.executeQuery("SELECT pic_id_sequence.nextval from dual");
	    rset1.next();
	    pic_id = rset1.getInt(1);

	    //Insert an empty blob into the table first. Note that you have to 
	    //use the Oracle specific function empty_blob() to create an empty blob
	    stmt.execute( "insert into pacs_images (record_id,image_id,thumbnail,regular_size,full_size) values ("+record_id+","+pic_id+",empty_blob(),empty_blob(),empty_blob())");
 
	    // to retrieve the lob_locator 
	    // Note that you must use "FOR UPDATE" in the select statement
	    String cmd = "SELECT * FROM pacs_images WHERE image_id = "+pic_id+" AND record_id = "+record_id+" FOR UPDATE";
	    ResultSet rset = stmt.executeQuery(cmd);
	    rset.next();
	    BLOB thumbBlob = ((OracleResultSet)rset).getBLOB(3);
	    BLOB normalBlob = ((OracleResultSet)rset).getBLOB(4);
	    BLOB fullBlob = ((OracleResultSet)rset).getBLOB(5);


	    //Write the image to the blob object
	    OutputStream thumb_outstream = thumbBlob.getBinaryOutputStream();
	    ImageIO.write(thumbNail, "jpg", thumb_outstream);


	    //Write the image to the blob object
	    OutputStream reg_outstream = normalBlob.getBinaryOutputStream();
	    ImageIO.write(img, "jpg", reg_outstream);


	    //Write the image to the blob object
	    OutputStream full_outstream = fullBlob.getBinaryOutputStream();
	    ImageIO.write(normalSize, "jpg", full_outstream);

	    
	    /*
	    int size = myblob.getBufferSize();
	    byte[] buffer = new byte[size];
	    int length = -1;
	    while ((length = instream.read(buffer)) != -1)
		outstream.write(buffer, 0, length);
	    */
	    instream.close();
	    thumb_outstream.close();
	    reg_outstream.close();
	    full_outstream.close();

            stmt.executeUpdate("commit");
	    response_message = " Upload OK!  ";
            conn.close();

	} catch( Exception ex ) {
	    //System.out.println( ex.getMessage());
	    response_message = ex.getMessage();
	}

	//Output response to the client
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
		    "Transitional//EN\">\n" +
		    "<HTML>\n" +
		    "<HEAD><TITLE>Upload Message</TITLE></HEAD>\n" +
		    "<BODY>\n" +
		    "<H1>" +
		            response_message +
		    "</H1>\n" +
		    "</BODY></HTML>");
    }

    /*
     *   To connect to the specified database
     */
    private static Connection getConnected( String drivername,
					    String dbstring,
					    String username, 
					    String password) throws Exception {
	Class drvClass = Class.forName(drivername); 
	DriverManager.registerDriver((Driver) drvClass.newInstance());
	return( DriverManager.getConnection(dbstring,username,password));
    } 

    //shrink image to have height n, and return the shrinked image
    public static BufferedImage shrink(BufferedImage image, int h) {

        int r = image.getWidth() / image.getHeight();
        int w = h*r;

	//(tDim is the required new Dimension)
	Dimension tDim = new Dimension(w,h);
	Image newImg = image.getScaledInstance(tDim.width,tDim.height,Image.SCALE_SMOOTH);
	java.awt.image.BufferedImage bim = new java.awt.image.BufferedImage(tDim.width,tDim.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
	bim.createGraphics().drawImage(newImg, 0, 0, null);//needed?
   
	return bim;
    }
}
