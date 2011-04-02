package search;

import java.sql.SQLException;

import util.Query;

public class RecordsQuery {

    private Query recordsQuery;
    private PicsQuery pics;

    public RecordsQuery(String searchInput, String username, String userClass, String startDate, String endDate, boolean orderByDate) throws SQLException {
        if (searchInput == null || searchInput.equals("")) return;
        if (username == null) return;
        if (userClass == null) return;
        if (startDate != null && startDate.equals("")) startDate = null;
        if (endDate != null && endDate.equals("")) endDate = null;

        String doctorname = null;

        String query = "SELECT (6*score(1) + 3*score(2) + score(3)) as myscore, record_id, patient_name, doctor_name, radiologist_name, test_type, prescribing_date, test_date, diagnosis, description" +
        " FROM radiology_record" +
        " WHERE (contains(patient_name, ?, 1) > 0 OR"+
              " contains(diagnosis, ?, 2) > 0 OR"+
              " contains(description, ?, 3) > 0)";
        
        if (userClass.equals("p")) {
            query += " AND patient_name = ?";
        } else if (userClass.equals("r")) {
            query += " AND radiologist_name = ?";
        } else if (userClass.equals("d")) {
            query += " AND (doctor_name = ? OR patient_name in (SELECT patient_name FROM family_doctor WHERE doctor_name = ?))";
            doctorname = username;
        } else {
            username = null;
        }
        
        if (startDate != null) query += " AND test_date >= to_date(?, 'dd/MM/yyyy')";
        if (endDate != null) query += " AND test_date <= to_date(?, 'dd/MM/yyyy')";
        
        if (orderByDate) query += " ORDER BY test_date";
        else query += " ORDER BY myscore";
        
        System.out.println("QUERY: " + query);
        recordsQuery = new Query(query, searchInput, searchInput, searchInput, username, doctorname, startDate, endDate);
    }
  
    public boolean absolute(int row) throws SQLException {
        if (recordsQuery.absolute(row)) {
            pics = new PicsQuery(getRecordID());
            return true;
        }
        return false;
    }

    public int getRecordCount() throws SQLException {
        return recordsQuery.getRowCount();
    }

    public boolean nextRecord() throws SQLException {
        if (recordsQuery.next()) {
            pics = new PicsQuery(getRecordID());
            return true;
        }
        return false;
    }

    public String getRecordID() throws SQLException {
        return recordsQuery.getString(2);
    }
    
    public String getPatientName() throws SQLException {
        return recordsQuery.getString(3);
    }
    
    public String getDoctorName() throws SQLException {
        return recordsQuery.getString(4);
    }

    public String getRadiologistName() throws SQLException {
        return recordsQuery.getString(5);
    }
    
    public String getTestType() throws SQLException {
        return recordsQuery.getString(6);
    }
    
    public String getPrescribingDate() throws SQLException {
        return recordsQuery.getString(7);
    }
    
    public String getTestDate() throws SQLException {
        return recordsQuery.getString(8);
    }
    
    public String getDiagnosis() throws SQLException {
        return recordsQuery.getString(9);
    }
    
    public String getDescription() throws SQLException {
        return recordsQuery.getString(10);
    }
    
    public String[] getThumbnailURLs() {
        return pics.getThumbnailURLs();
    }
    
    public String[] getRegularsizeURLs() {
        return pics.getRegularsizeURLs();
    }
    
    public String[] getFullsizeURLs() {
        return pics.getFullsizeURLs();
    }
    
    public void close() throws SQLException {
        recordsQuery.close();
    }

}
