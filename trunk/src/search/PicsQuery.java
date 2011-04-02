package search;

import java.util.List;
import java.util.LinkedList;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionManager.ConnectionKit;

public class PicsQuery {
    
    private List<String> thumbnailURLs;
    private List<String> regularsizeURLs;
    private List<String> fullsizeURLs;
    
    public PicsQuery(String recordID) throws SQLException {
        ConnectionKit connectionKit = new ConnectionKit();
        ResultSet images = connectionKit.exec(
            "SELECT image_id" +
           " FROM pacs_images"+
           " WHERE record_id = '" + recordID + "'"+
           " ORDER BY image_id"
        );

        thumbnailURLs = new LinkedList<String>();
        regularsizeURLs = new LinkedList<String>();
        fullsizeURLs = new LinkedList<String>();

        while (images.next()) {
            String imageID = images.getString(1);
            thumbnailURLs.add(getImageURL(recordID, imageID, "thumbnail"));
            regularsizeURLs.add(getImageURL(recordID, imageID, "regular_size"));
            fullsizeURLs.add(getImageURL(recordID, imageID, "full_size"));
        }
        
        connectionKit.close();
    }
    
    private String getImageURL(String recordID, String imageID, String style) {       
        return "/radiologydb/servlet/GetOnePic?image_id="+imageID+"&record_id="+recordID+"&style="+style;
    }

    public String[] getThumbnailURLs() {
        return thumbnailURLs.toArray(new String[thumbnailURLs.size()]);
    }
    
    public String[] getRegularsizeURLs() {
        return regularsizeURLs.toArray(new String[regularsizeURLs.size()]);
    }
    
    public String[] getFullsizeURLs() {
        return fullsizeURLs.toArray(new String[fullsizeURLs.size()]);
    }

}