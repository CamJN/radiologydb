package search;

import java.util.List;
import java.util.LinkedList;

import java.sql.SQLException;

import util.Query;

public class PicsQuery {
    
    private List<String> thumbnailURLs;
    private List<String> regularsizeURLs;
    private List<String> fullsizeURLs;
    
    public PicsQuery(String recordID) throws SQLException {
        Query q = new Query(
            "SELECT image_id" +
           " FROM pacs_images"+
           " WHERE record_id = '" + recordID + "'"+
           " ORDER BY image_id"
        );

        thumbnailURLs = new LinkedList<String>();
        regularsizeURLs = new LinkedList<String>();
        fullsizeURLs = new LinkedList<String>();

        while (q.next()) {
            thumbnailURLs.add(getImageURL(recordID, q.getString(1), "thumbnail"));
            regularsizeURLs.add(getImageURL(recordID, q.getString(1), "regular_size"));
            fullsizeURLs.add(getImageURL(recordID, q.getString(1), "full_size"));
        }
        
        q.close();
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