package search;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionManager.ConnectionKit;

public class DiagnosisQuery {

	protected ResultSet resultSet;
	private ConnectionKit connectionKit;

    public DiagnosisQuery(String searchInput, String startDate, String endDate) throws SQLException {
    	if (searchInput == null || searchInput.equals("")) return;
    	if (startDate != null && startDate.equals("")) startDate = null;
        if (endDate != null && endDate.equals("")) endDate = null;
    	String query = 
            	"SELECT myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone, MIN(test_date) from persons p," +
            	"( select score(1) as myscore, patient_name,test_date from radiology_record " +
            	"where (contains(diagnosis, '"+ searchInput +"', 1)) >0) r1 where patient_name = p.user_name";
    	
    	if (startDate != null) query += " AND test_date >= to_date(?, 'dd/MM/yyyy')";
        if (endDate != null) query += " AND test_date <= to_date(?, 'dd/MM/yyyy')";
        
        query += " group by myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone";
        	
       
        connectionKit = new ConnectionKit();
        resultSet = connectionKit.exec(query, startDate, endDate);
    }
    
    public boolean absolute(int row) throws SQLException {
        if (resultSet.absolute(row)) {
            return true;
        }
        return false;
    }

    public int getPersonCount() throws SQLException {
        return ConnectionKit.getRowCount(resultSet);
    }

    public boolean nextRecord() throws SQLException {
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    public String getUname() throws SQLException {
        return resultSet.getString(2);
    }
    
    public String getFname() throws SQLException {
        return resultSet.getString(3);
    }
    
    public String getLname() throws SQLException {
        return resultSet.getString(4);
    }

    public String getAddress() throws SQLException {
        return resultSet.getString(5);
    }

    public String getPhone() throws SQLException {
        return resultSet.getString(6);
    }
    
    public String getDate() throws SQLException {
        return resultSet.getString(7).substring(0, resultSet.getString(7).indexOf(':') - 1);
    }

    public void close() throws SQLException {
    	resultSet.close();
    	connectionKit.close();
    }
    


}
