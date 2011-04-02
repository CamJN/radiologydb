package uploadImage;
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
import util.ConnectionManager;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadImage extends HttpServlet {
    public String response_message;

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	int pic_id;
	String record_id="";

	try {
	    //Parse the HTTP request to get the image stream
	    DiskFileUpload fu = new DiskFileUpload();
	    List FileItems = fu.parseRequest(request);
	    // Process the uploaded items, assuming only 1 image file uploaded
	    Iterator i = FileItems.iterator();
	    FileItem item;
	    InputStream instream=null;
	    int inti=0;
	    for (item = (FileItem) i.next(); instream==null || record_id.equals(""); item=(FileItem)i.next()) {
		if(item.isFormField()){
		    if(item.getFieldName().equals("record_id")){
			record_id=new String(item.get());
		    }
		}else{
		    instream = item.getInputStream();
		}
		//get record_id from form
	    }
	    //Get the image stream
	    BufferedImage img = ImageIO.read(instream);
	    BufferedImage thumbNail = shrink(img, 150);
	    BufferedImage normalSize = shrink(img,550);

            // Connect to the database and create a statement
            Connection conn = ConnectionManager.getConnection();
	    Statement stmt = conn.createStatement();
	    
	    //  First, to generate a unique pic_id using an SQL sequence
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

	    instream.close();
	    thumb_outstream.close();
	    reg_outstream.close();
	    full_outstream.close();

            stmt.executeUpdate("commit");
	    response_message = "Upload OK!";
	    rset1.close();
	    rset.close();
	    stmt.close();
            conn.close();
	} catch( Exception ex ) {
	    response_message = ex.getMessage();
	    if(response_message==null) response_message = "Invalid Input";
	}

	//Output response to the client
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	if(response_message.equals("Upload OK!")){
	    out.println("<!DOCTYPE html>\n"+
			"<html>\n"+
			"<head>\n"+
			"<meta http-equiv=\"refresh\" content=\"0;url='upload-image.jsp?state=Image+Uploaded!\'\">\n"+
			"</head>\n"+
			"</html>");
	}else{
	    out.println("<!DOCTYPE html>\n"+
			"<html>\n"+
			"<head>\n"+
			"<script type=\"text/javascript\">\n"+
			"window.setTimeout(\"history.back(1)\", 5000);\n"+
			"</script>\n"+
			"</head>\n"+
			"<body>\n"+
			"<h1>"+response_message.trim()+"</h1>\n"+	
			"</body>\n"+
			"</html>");
	}
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
