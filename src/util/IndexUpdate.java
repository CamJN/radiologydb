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
	
	public static void updateRadiologyIndex(Statement stmt) throws SQLException
	{
		stmt.executeQuery(dropRadioName);
		stmt.executeQuery(dropRadioDiag);
		stmt.executeQuery(dropRadioDesc);
		
		stmt.executeQuery(createRadioName);
		stmt.executeQuery(createRadioDiag);
		stmt.executeQuery(createRadioDesc);
	}

}
