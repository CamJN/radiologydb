package util;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


/**
 * This servlet sends one picture stored in the table below to the client
 * who requested the servlet.
 * 
 * pacs_images(record_id,image_id,thumbnail,regular_size,full_size)
 * 
 * The request must come with a query string as follows:
 * GetOnePic?image_id=12&record_id=3&style=thumbnail: sends the picture in thumbnail with image_id = 12 and record_id =
 * 3
 * GetOnePic?image_id=12&record_id=3&style=regular_size: sends the picture in regular_size with image_id = 12 and
 * record_id = 3
 * 
 */
public class GetOnePic extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String image_id = request.getParameter("image_id").toString();
        String record_id = (String)request.getParameter("record_id");
        String style = (String)request.getParameter("style");

        String query = "SELECT " + style + 
                      " FROM pacs_images" +
                      " WHERE record_id = " + record_id + 
                      " AND image_id = " + image_id;

        ServletOutputStream out = response.getOutputStream();

        ConnectionKit connectionKit = null;
        try {
            connectionKit = new ConnectionKit();
            ResultSet rset = connectionKit.exec(query);

            if (rset.next()) {
                response.setContentType("image/gif");
                InputStream input = rset.getBinaryStream(1);
                int imageByte;
                while ((imageByte = input.read()) != -1) out.write(imageByte);
                input.close();
            } else out.println("no picture available");

        } catch (Exception ex) {
            throw new Error();
        } finally {
            try {
                connectionKit.close();
            } catch (SQLException ex) {
                throw new Error();
            }
        }
    }
}
