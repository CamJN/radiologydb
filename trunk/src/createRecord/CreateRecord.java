package createRecord;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import util.ConnectionManager;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class CreateRecord extends HttpServlet {
    public String response_message;
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

	int record_id=-1;
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
		if(item.getFieldName().equals("patient_name")){
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
	    if(item.getFieldName().equals("patient_name")){
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
            Connection conn = ConnectionManager.getConnection();
	    /*
	     *  First, to generate a unique rec_id using an SQL sequence
	     */
    	    Statement s_stmt = conn.createStatement();
	    ResultSet rset1 = s_stmt.executeQuery("SELECT rec_id_sequence.nextval from dual");
	    rset1.next();
	    record_id = rset1.getInt(1);
	    

	    PreparedStatement stmt = conn.prepareStatement( "insert into radiology_record(record_id,patient_name,doctor_name,radiologist_name,test_type,prescribing_date,test_date,diagnosis,description) values (?,?,?, ?,?,?, ?,?,?)");
	    stmt.setInt(1,record_id);
	    stmt.setString(2,patient_name);
	    stmt.setString(3,doctor_name);
	    stmt.setString(4,radiologist_name);
	    stmt.setString(5,test_type);
	    stmt.setDate(6, Date.valueOf(prescribing_date));//might need a dateformatter.parse()
	    stmt.setDate(7, Date.valueOf(test_date));//same here
	    stmt.setString(8,diagnosis);
	    stmt.setString(9,description);
	    stmt.executeUpdate();

            conn.commit();
	    response_message = "Create OK!";
	    
	    s_stmt.close();
	    rset1.close();
	    stmt.close();
            conn.close();

	} catch( Exception ex ) {
	    response_message = ex.getMessage();
	}

	//Output response to the client
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	//include here a redirect to upload_image or just got there directly
	if(response_message != null && response_message.equals("Create OK!")){
	    out.println("<!DOCTYPE html>\n"+
			"<html>\n"+
			"<head>\n"+
	        "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/default.css\">"+
			"<meta http-equiv=\"refresh\" content=\"0;url='upload-image.jsp?record_id="+record_id+"&state=Record+Created!'\">\n"+
			"</head>\n"+
			"</html>");
	}else{
	    out.println("<!DOCTYPE html>\n"+
			"<html>\n"+
			"<head>\n"+
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/default.css\">"+
			"<script type=\"text/javascript\">\n"+
			"window.setTimeout(\"history.back(1)\", 5000);\n"+
			"</script>\n"+
			"</head>\n"+
			"<body>\n"+
			"<h2>"+"Record was incomplete or invalid and could not be uploaded."+"</h2>\n"+	
			"</body>\n"+
			"</html>");
	}
    }
}
