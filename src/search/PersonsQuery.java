package search;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Query;

public class PersonsQuery {

    private Query personsQuery;

    public PersonsQuery(String searchInput) throws SQLException {
        if (searchInput == null || searchInput.equals("")) return;
        
        String query = 
         "SELECT (6*score(1) + 3*score(2) + score(3)) as myscore, user_name, first_name, last_name, address,email,phone" +
        " FROM persons" +
        " WHERE (contains(user_name, '"+searchInput+"', 1) > 0 OR"+
              " contains(last_name, '"+searchInput+"', 2) > 0 OR"+
              " contains(first_name, '"+searchInput+"', 3) > 0)";
           
        query += " ORDER BY myscore";
        
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
    
    public String getEmail() throws SQLException {
        return personsQuery.getString(6);
    }
    
    public String getPhone() throws SQLException {
        return personsQuery.getString(7);
    }

    public void close() throws SQLException {
        personsQuery.close();
    }

}
