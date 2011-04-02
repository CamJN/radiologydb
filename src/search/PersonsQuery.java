package search;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionManager.ConnectionKit;

public class PersonsQuery {

	protected ResultSet resultSet;
	private ConnectionKit connectionKit;

    public PersonsQuery(String searchInput) throws SQLException {
        if (searchInput == null || searchInput.equals("")) return;
        
        String query = 
         "SELECT (6*score(1) + 3*score(2) + score(3)) as myscore, user_name, first_name, last_name, address,email,phone" +
        " FROM persons" +
        " WHERE (contains(user_name, '"+searchInput+"', 1) > 0 OR"+
              " contains(last_name, '"+searchInput+"', 2) > 0 OR"+
              " contains(first_name, '"+searchInput+"', 3) > 0)";
           
        query += " ORDER BY myscore";
        
        connectionKit = new ConnectionKit();
        resultSet = connectionKit.exec(query, ResultSet.TYPE_SCROLL_INSENSITIVE);
    }
    
    public boolean absolute(int row) throws SQLException {
        if (resultSet.absolute(row)) {
            return true;
        }
        return false;
    }

    public int getPersonCount() throws SQLException {
        return getRowCount();
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
    
    public String getEmail() throws SQLException {
        return resultSet.getString(6);
    }
    
    public String getPhone() throws SQLException {
        return resultSet.getString(7);
    }

    public void close() throws SQLException {
        resultSet.close();
    }
    
    public int getRowCount() throws SQLException {
        int initialRow = resultSet.getRow();
        resultSet.last();
        int lastRow = resultSet.getRow();
        if (initialRow != 0) resultSet.absolute(initialRow);
        else resultSet.beforeFirst();
        
        return lastRow;
    }

}
