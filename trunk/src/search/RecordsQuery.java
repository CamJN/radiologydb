package search;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Query;

public class RecordsQuery {

    private Query recordsQuery;
    private PicsQuery pics;

    public RecordsQuery(String searchInput) throws SQLException {
        this(searchInput, null, null, false);
    }

    public RecordsQuery(String searchInput, boolean orderByDate) throws SQLException {
        this(searchInput, null, null, orderByDate);
    }

    public RecordsQuery(String searchInput, String startDate, String endDate, boolean orderByDate) throws SQLException {
        if (searchInput == null || searchInput.equals("")) return;
        
        String query = 
         "SELECT (6*score(1) + 3*score(2) + score(3)) as myscore, record_id, patient_name, doctor_name, radiologist_name, test_type, prescribing_date, test_date, diagnosis, description" +
        " FROM radiology_record" +
        " WHERE (contains(patient_name, '"+searchInput+"', 1) > 0 OR"+
              " contains(diagnosis, '"+searchInput+"', 2) > 0 OR"+
              " contains(description, '"+searchInput+"', 3) > 0)";
        
        if (startDate != null && !startDate.equals("")) {
            query += " AND test_date >= to_date('" + startDate + "', 'dd/MM/yyyy')";
        }
        
        if (endDate != null && !endDate.equals("")) {
            query += " AND test_date <= to_date('" + endDate + "', 'dd/MM/yyyy')";
        }
        
        if (orderByDate) {
            query += " ORDER BY test_date";
        } else {
            query += " ORDER BY myscore";
        }
        
        System.out.println("query: " + query);
        recordsQuery = new Query(query, ResultSet.TYPE_SCROLL_INSENSITIVE);
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
