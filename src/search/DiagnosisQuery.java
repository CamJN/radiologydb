package search;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Query;

public class DiagnosisQuery {

    private Query personsQuery;

    public DiagnosisQuery(String searchInput, int year) throws SQLException {
    	if (searchInput == null || searchInput.equals("")) return;
    	String query;
    	if(year == 0)
    	{
    		query = 
            	"SELECT myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone, MIN(test_date) from persons p," +
            	"( select score(1) as myscore, patient_name,test_date from radiology_record " +
            	"where (contains(diagnosis, '"+ searchInput +"', 1)) >0) r1 where patient_name = p.user_name" +
            	" group by myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone";
    	}
    	else
    	{
    		query = 
            	"SELECT myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone, MIN(test_date) from persons p," +
            	"( select score(1) as myscore, patient_name,test_date from radiology_record " +
            	"where (contains(diagnosis, '"+ searchInput +"', 1)) >0) r1 where patient_name = p.user_name and  EXTRACT(YEAR FROM test_date) = "+ year +
            	" group by myscore, p.user_name, p.first_name, p.last_name, p.address, p.phone";
    	}
        
        
        
        	
        System.out.println(query);
        personsQuery = new Query(query, ResultSet.TYPE_SCROLL_INSENSITIVE);
    }
    
    public boolean absolute(int row) throws SQLException {
        if (personsQuery.absolute(row)) {
            return true;
        }
        return false;
    }

    public int getPersonCount() throws SQLException {
        return personsQuery.getRowCount();
    }

    public boolean nextRecord() throws SQLException {
        if (personsQuery.next()) {
            return true;
        }
        return false;
    }

    public String getUname() throws SQLException {
        return personsQuery.getString(2);
    }
    
    public String getFname() throws SQLException {
        return personsQuery.getString(3);
    }
    
    public String getLname() throws SQLException {
        return personsQuery.getString(4);
    }

    public String getAddress() throws SQLException {
        return personsQuery.getString(5);
    }

    public String getPhone() throws SQLException {
        return personsQuery.getString(6);
    }
    
    public String getDate() throws SQLException {
        return personsQuery.getString(7).substring(0, personsQuery.getString(7).indexOf(':') - 1);
    }

    public void close() throws SQLException {
        personsQuery.close();
    }

}
