import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class CreateRecord extends HttpServlet {
    public String response_message;
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	//  change the following parameters to connect to the oracle database
	String username = "c391g1";
	String password = "c3911337";
	String drivername = "oracle.jdbc.driver.OracleDriver";
	String dbstring ="jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

	String record_id="";
	String patient_name="";
	String doctor_name="";
	String radiologist_name="";
	String test_type="";
	String prescribing_date="";
	String test_date="";
	String diagnosis="";
	String description="";

	try {
	    // Parse the HTTP request to get the text items
	    Iterator i =  (new DiskFileUpload()).parseRequest(request).iterator();
	    FileItem item;
	    int inti=0;
	    for (item = (FileItem) i.next(); i.hasNext(); item=(FileItem)i.next()) {
		if(item.getFieldName().equals("record_id")){
		    record_id=new String(item.get());
		}else if(item.getFieldName().equals("patient_name")){
		    patient_name=new String(item.get());
		}else if(item.getFieldName().equals("doctor_name")){
		    doctor_name=new String(item.get());
		}else if(item.getFieldName().equals("radiologist_name")){
		    radiologist_name=new String(item.get());
		}else if(item.getFieldName().equals("test_type")){
		    test_type=new String(item.get());
		}else if(item.getFieldName().equals("prescribing_date")){
		    prescribing_date=new String(item.get());
		}else if(item.getFieldName().equals("test_date")){
		    test_date=new String(item.get());
		}else if(item.getFieldName().equals("diagnosis")){
		    diagnosis=new String(item.get());
		}else if(item.getFieldName().equals("description")){
		    description=new String(item.get());
		}
	    }
	    //run one more time for the last item 
		if(item.getFieldName().equals("record_id")){
		    record_id=new String(item.get());
		}else if(item.getFieldName().equals("patient_name")){
		    patient_name=new String(item.get());
		}else if(item.getFieldName().equals("doctor_name")){
		    doctor_name=new String(item.get());
		}else if(item.getFieldName().equals("radiologist_name")){
		    radiologist_name=new String(item.get());
		}else if(item.getFieldName().equals("test_type")){
		    test_type=new String(item.get());
		}else if(item.getFieldName().equals("prescribing_date")){
		    prescribing_date=new String(item.get());
		}else if(item.getFieldName().equals("test_date")){
		    test_date=new String(item.get());
		}else if(item.getFieldName().equals("diagnosis")){
		    diagnosis=new String(item.get());
		}else if(item.getFieldName().equals("description")){
		    description=new String(item.get());
		}

            // Connect to the database and create a statement
            Connection conn = getConnected(drivername,dbstring, username,password);
	    PreparedStatement stmt = conn.prepareStatement( "insert into radiology_record(record_id,patient_name,doctor_name,radiologist_name,test_type,prescribing_date,test_date,diagnosis,description) values (?,?,?, ?,?,?, ?,?,?)");
	    stmt.setInt(1,(new Integer(record_id)).intValue());
	    stmt.setString(2,patient_name);
	    stmt.setString(3,doctor_name);
	    stmt.setString(4,radiologist_name);
	    stmt.setString(5,test_type);
	    stmt.setDate(6, Date.valueOf(prescribing_date));//might need a dateformatter.parse()
	    stmt.setDate(7, Date.valueOf(test_date));//same here
	    stmt.setString(8,diagnosis);
	    stmt.setString(9,description);
	    stmt.executeUpdate();
	    System.out.println( "insert into radiology_record(record_id, patient_name, doctor_name, radiologist_name, test_type, prescribing_date, test_date, diagnosis, description) values ("+(new Integer(record_id)).intValue()+","+patient_name+","+doctor_name+","+radiologist_name+","+test_type+","+Date.valueOf(prescribing_date)+","+Date.valueOf(test_date)+","+diagnosis+","+description+")");
            conn.commit();
	    response_message = " Create OK!  ";
            conn.close();

	} catch( Exception ex ) {
	    //System.out.println( ex.getMessage());
	    response_message = ex.getMessage();
	}

	//Output response to the client
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	//include here a redirect to upload_image or just got there directly
	out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
		    "Transitional//EN\">\n" +
		    "<HTML>\n" +
		    "<meta http-equiv=\"refresh\" content=\"2;url=\"../upload_image.html\"\">\n"+
		    "<HEAD><TITLE>Create Message</TITLE></HEAD>\n" +
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
}
