package util;

import java.sql.SQLException;
import java.sql.Statement;

public class IndexUpdate {
	
	private static final String dropRadioName = "DROP INDEX radioname_index";
	private static final String dropRadioDiag = "DROP INDEX radiodiag_index";
	private static final String dropRadioDesc = "DROP INDEX radiodesc_index";
	
	private static final String createRadioName = "CREATE INDEX radioname_index ON radiology_record(patient_name) INDEXTYPE IS CTXSYS.CONTEXT";
	private static final String createRadioDiag = "CREATE INDEX radiodiag_index ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT";
	private static final String createRadioDesc = "CREATE INDEX radiodesc_index ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT";
	
	private static final String dropFname = "DROP INDEX fname_index";
	private static final String dropLname = "DROP INDEX lname_index";
	private static final String dropUname = "DROP INDEX uname_index";
	
	private static final String createFname = "CREATE INDEX fname_index ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT";
	private static final String createLname = "CREATE INDEX fname_index ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT";
	private static final String createUname = "CREATE INDEX uname_index ON persons(user_name) INDEXTYPE IS CTXSYS.CONTEXT";
	
	public static void updateRadiologyIndex(Statement stmt) throws SQLException
	{
		stmt.executeQuery(dropRadioName);
		stmt.executeQuery(dropRadioDiag);
		stmt.executeQuery(dropRadioDesc);
		
		stmt.executeQuery(createRadioName);
		stmt.executeQuery(createRadioDiag);
		stmt.executeQuery(createRadioDesc);
	}
	
	public static void updatePersonIndex(Statement stmt) throws SQLException
	{
		stmt.executeQuery(dropFname);
		stmt.executeQuery(dropLname);
		stmt.executeQuery(dropUname);
		
		stmt.executeQuery(createFname);
		stmt.executeQuery(createLname);
		stmt.executeQuery(createUname);
	}

}
